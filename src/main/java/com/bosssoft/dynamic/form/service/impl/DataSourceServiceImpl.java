package com.bosssoft.dynamic.form.service.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.dynamic.form.domain.DataSourceInfo;
import com.bosssoft.dynamic.form.domain.FormField;
import com.bosssoft.dynamic.form.dto.DataSourceMetaDataDTO;
import com.bosssoft.dynamic.form.exception.ApplicationException;
import com.bosssoft.dynamic.form.exception.Error;
import com.bosssoft.dynamic.form.exception.ErrorType;
import com.bosssoft.dynamic.form.mapper.DataSourceInfoMapper;
import com.bosssoft.dynamic.form.mapper.FormFieldMapper;
import com.bosssoft.dynamic.form.model.BasicConstant;
import com.bosssoft.dynamic.form.model.DataSourceElement;
import com.bosssoft.dynamic.form.service.DataSourceService;
import com.bosssoft.dynamic.form.spi.UserService;
import com.bosssoft.dynamic.form.util.ConnectedDataSourceHandler;
import com.bosssoft.dynamic.form.util.DynamicFormUtil;
import com.bosssoft.dynamic.form.util.SecurityUtils;
import com.bosssoft.dynamic.form.util.SupportDataSourceHandler;
import com.bosssoft.dynamic.form.vo.BasicPageInfo;
import com.bosssoft.dynamic.form.vo.DBConnectionTestResult;
import com.bosssoft.dynamic.form.vo.FieldMeta;
import com.bosssoft.dynamic.form.vo.ResultPageData;
import com.bosssoft.dynamic.form.vo.TableMeta;
import com.bosssoft.platform.microservice.framework.common.data.Page;
import com.bosssoft.platform.microservice.framework.dal.pagination.PageHelper;

@Service
public class DataSourceServiceImpl implements DataSourceService {

    private static Logger log = LoggerFactory.getLogger(DataSourceServiceImpl.class);

    @Autowired
    private DataSourceInfoMapper dataSourceMapper;
    
    @Autowired
    private FormFieldMapper formFieldMapper;
    
    @Autowired
    private UserService userService;

    @Override
    public DataSourceInfo getDataSourceInfoById(Long dataSourceInfoId) {
        return dataSourceMapper.selectByPrimaryKey(dataSourceInfoId);
    }

    @Override
    public ResultPageData<DataSourceInfo> pageDataSourceInfo(DataSourceInfo search, Integer pageNum, Integer pageSize) {
        if(pageNum==null) pageNum=BasicPageInfo.DEFAULE_CURRENTPAGE;
        if(pageSize==null) pageSize=BasicPageInfo.DEFAULE_PAGESIZE;
        if(search==null) search=new DataSourceInfo();
        
        Page<DataSourceInfo> page = PageHelper.startPage(pageNum, pageSize);
        dataSourceMapper.selectDataSourceByCondition(search);
        return new ResultPageData<>(page);
    }

    @Override
    public void deleteDataSourceInfoById(Long dataSourceId) {
        
        dataSourceMapper.deleteByPrimaryKey(dataSourceId);
        ConnectedDataSourceHandler.removeDataSource(dataSourceId);

    }

    @Override
    public DataSourceInfo modifyDataSourceInfo(DataSourceInfo dataSource) {
        dataSource.setModifyTime(DynamicFormUtil.getCurrentTimeStr());
        dataSource.setModifyUser(userService.getCurrentUserId());
        dataSourceMapper.updateByPrimaryKeySelective(dataSource);
        // 数据源缓存中删除
        ConnectedDataSourceHandler.removeDataSource(dataSource.getId());
        return dataSource;

    }

    @Override
    public List<DataSourceElement> getSupportDataSources() {
        List<DataSourceElement> result = new ArrayList<>();
        result.addAll(SupportDataSourceHandler.getSupportDataSources().values());

        return result;
    }

    @Override
    public DataSourceInfo saveDataSourceInfo(DataSourceInfo dataSource) {
        dataSource.setId(DynamicFormUtil.generateId());
        dataSource.setCreationTime(DynamicFormUtil.getCurrentTimeStr());
       dataSource.setCreationUser(userService.getCurrentUserId());

        dataSourceMapper.insertSelective(dataSource);
        
        return dataSource;

    }

    @Override
    public DBConnectionTestResult dataSourceConnectionTest(DataSourceInfo dataSourceInfo) {
        // 连接测试
        DBConnectionTestResult connectionTestResult = new DBConnectionTestResult();
        Connection connection = null;
        try {
            Class.forName(dataSourceInfo.getDbDriver());
            String jdbcUrl = ConnectedDataSourceHandler.getJDBCUrl(dataSourceInfo.getDbType(),
                dataSourceInfo.getHostIp(), dataSourceInfo.getDbPort(), dataSourceInfo.getDbInstance());
            
            Connection conn =
                DriverManager.getConnection(jdbcUrl, dataSourceInfo.getDbUsername(), SecurityUtils.desEncrypt(dataSourceInfo.getDbPassword()));

            connectionTestResult.setSuccess(true);
        } catch (Exception e) {
            connectionTestResult.setSuccess(false);
            connectionTestResult.setErrorMessage(e.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.error("Exception while closing the Database connection", e);
            }
        }
        return connectionTestResult;

    }

    @Override
    public List<TableMeta> getTableMetas(Long dataSourceId, DataSourceMetaDataDTO dataSourceMetaDataDTO)
        throws ApplicationException {
        if(dataSourceMetaDataDTO==null) dataSourceMetaDataDTO=new DataSourceMetaDataDTO();
        
        List<TableMeta> result = new ArrayList<>();
        Connection connection = null;
        try {
            connection = getDBConnection(dataSourceId);
            // 查询获取表
            DatabaseMetaData dbMetaData = connection.getMetaData();
            ResultSet rs =
                dbMetaData.getTables(connection.getCatalog(), getSchema(dbMetaData), null, new String[] {"TABLE"});
            while (rs.next()) {
                if (matchingTableMetaQuery(dataSourceMetaDataDTO, rs)) {
                    TableMeta tableMeta = new TableMeta();
                    tableMeta.setTableName(rs.getString(BasicConstant.COLUMN_NAME_TABLENAME));
                    tableMeta.setTableComment(rs.getString(BasicConstant.COLUMN_NAME_TABLECOMMENT));
                    result.add(tableMeta);
                }

            }

        } catch (ApplicationException e) {
            throw new ApplicationException(e.getError(), e.getThrowable());
        } catch (SQLException e) {
            log.error(e.toString());
            throw new ApplicationException(new Error(ErrorType.BUSINESS_ERROR.getCode(), "获取表元数据失败"), e);
        } finally {
            closeDBConnection(connection);
        }

        return result;
    }

    /**
     * 是否匹配dataSourceMetaDataDTO中的查询条件
     * 
     * @param dataSourceMetaDataDTO
     * @param rs
     * @return
     * @throws SQLException
     */
    private boolean matchingTableMetaQuery(DataSourceMetaDataDTO dataSourceMetaDataDTO, ResultSet rs)
        throws SQLException {
        String tableNameOrCommentLike = dataSourceMetaDataDTO.getTableNameOrCommentLike();
        if (StringUtils.isEmpty(tableNameOrCommentLike))
            return true;

        if (rs.getString(BasicConstant.COLUMN_NAME_TABLENAME).toLowerCase().contains(tableNameOrCommentLike.toLowerCase())) {
            // 表名模糊匹配
            return true;
        } else if (StringUtils.isNotEmpty(rs.getString(BasicConstant.COLUMN_NAME_TABLECOMMENT))
            && rs.getString(BasicConstant.COLUMN_NAME_TABLECOMMENT).contains(tableNameOrCommentLike)) {
            // 表备注模糊配置
            return true;
        }
        return false;
    }

    @Override
    public List<FieldMeta> getFieldMetas(Long dataSourceId, String tableName,
        DataSourceMetaDataDTO dataSourceMetaDataDTO) throws ApplicationException {

        if(dataSourceMetaDataDTO==null) dataSourceMetaDataDTO=new DataSourceMetaDataDTO();
        
        List<FieldMeta> result = new ArrayList<>();
        Connection connection = null;
        try {
            connection = getDBConnection(dataSourceId);
            Map<String,String> columnTypeClassNames=getColumnTypeClassNames(connection, tableName);
            
            // 查询获取表中所有字段
            DatabaseMetaData dbMetaData = connection.getMetaData();
            ResultSet rs = dbMetaData.getColumns(connection.getCatalog(), getSchema(dbMetaData), tableName,
                dataSourceMetaDataDTO.getFieldName());
            while (rs.next()) {
                if (matchingFieldMetaQuery(dataSourceMetaDataDTO, rs)) {
                    FieldMeta fieldMeta = new FieldMeta();
                    String fieldName=rs.getString(BasicConstant.COLUMN_NAME_FIELDNAME);
                    fieldMeta.setFieldName(fieldName);
                    fieldMeta.setFieldComment(rs.getString(BasicConstant.COLUMN_NAME_FIELDCOMMENT));
                    fieldMeta.setType(rs.getString(BasicConstant.COLUMN_NAME_FIELDTYPE));
                    fieldMeta.setLength(rs.getInt(BasicConstant.COLUMN_NAME_FIELDSIZE));
                    fieldMeta.setNullAble(rs.getBoolean(BasicConstant.COLUMN_NAME_FIELDNULLABLE));
                    fieldMeta.setClassName(columnTypeClassNames.get(fieldName));
                    result.add(fieldMeta);
                }
            }

        } catch (ApplicationException e) {
            throw new ApplicationException(e.getError(), e.getThrowable());
        } catch (SQLException e) {
            log.error(e.toString());
            throw new ApplicationException(new Error(ErrorType.BUSINESS_ERROR.getCode(), "获取字段元数据失败"), e);
        } finally {
            closeDBConnection(connection);
        }

        return result;
    }


    private boolean matchingFieldMetaQuery(DataSourceMetaDataDTO dataSourceMetaDataDTO, ResultSet rs)
        throws SQLException {
        if (StringUtils.isEmpty(dataSourceMetaDataDTO.getFieldCommentLike()))
            return true;

        if (StringUtils.isNotEmpty(rs.getString(BasicConstant.COLUMN_NAME_FIELDCOMMENT)) && 
            rs.getString(BasicConstant.COLUMN_NAME_FIELDCOMMENT).contains(dataSourceMetaDataDTO.getFieldCommentLike())) {
            return true;
        }
        return false;
    }

    
    private String getSchema(DatabaseMetaData dbMetaData) throws SQLException{
        String dbType=dbMetaData.getDatabaseProductName();
        if(BasicConstant.DB_TYPE_MYSQL.equalsIgnoreCase(dbType)||BasicConstant.DB_TYPE_POSTGRESQL.equalsIgnoreCase(dbType)) return null;
        else{
            return dbMetaData.getUserName().toUpperCase();
        }
    }
    
    /**
     * 获取表中列名对应的JAVA类型
     * @param connection
     * @param tableName
     * @return
     * @throws SQLException 
     */
    private Map<String,String> getColumnTypeClassNames(Connection connection,String tableName) throws SQLException{
        Map<String,String> result=new HashedMap<>();
        PreparedStatement ps = connection.prepareStatement("select * from "+tableName);
        ResultSet rs=ps.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();

        int columnCount=rsmd.getColumnCount();

        for(int i=0;i<columnCount;i++){
            String columnName=rsmd.getColumnName(i+1);
           String className= rsmd.getColumnClassName(i+1);
           result.put(columnName, className);
        }
        return result;

    }
    
    /**
     * 获取数据源连接
     * @param dataSourceId
     * @return
     * @throws ApplicationException 
     */
    private Connection getDBConnection(Long dataSourceId) throws ApplicationException{
       // 获取数据库连接
        Connection connection=null;
        try {
            DataSource dataSource = ConnectedDataSourceHandler.applyDataSource(dataSourceId);
             connection = dataSource.getConnection();
        } catch (ExecutionException e) {
             log.error(e.toString());
             throw new ApplicationException(new Error(ErrorType.BUSINESS_ERROR.getCode(), "加载数据源对象失败"), e);
        } catch (SQLException e) {
            log.error(e.toString());
            throw new ApplicationException(new Error(ErrorType.BUSINESS_ERROR.getCode(), "获取数据库连接失败"), e);
        }
       return connection;
    }
    
    /**
     * 关闭数据库连接
     * @param connection
     */
    private void closeDBConnection(Connection connection){
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            log.error("Exception while closing the Database connection", e);
        }
    }

    @Override
    public boolean existReferenceAboutDataSource(Long dataSourceInfoId) {
        FormField record=new FormField();
        record.setDataSourceId(dataSourceInfoId);
        
        List<FormField> formFields=formFieldMapper.select(record);
        if(CollectionUtils.isEmpty(formFields)){
            return false;
        }else{
            return true;
        }
    }
}
