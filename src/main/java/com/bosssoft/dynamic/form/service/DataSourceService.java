package com.bosssoft.dynamic.form.service;

import java.util.List;

import com.bosssoft.dynamic.form.domain.DataSourceInfo;
import com.bosssoft.dynamic.form.dto.DataSourceMetaDataDTO;
import com.bosssoft.dynamic.form.exception.ApplicationException;
import com.bosssoft.dynamic.form.model.DataSourceElement;
import com.bosssoft.dynamic.form.vo.DBConnectionTestResult;
import com.bosssoft.dynamic.form.vo.FieldMeta;
import com.bosssoft.dynamic.form.vo.ResultPageData;
import com.bosssoft.dynamic.form.vo.TableMeta;

public interface DataSourceService {

    /**
     * 根据数据源ID查询
     * @param dataSourceInfoById
     * @return
     */
    public DataSourceInfo getDataSourceInfoById(Long dataSourceInfoId);

    /**
     * 分页查询数据源
     * @param search
     * @param pageNum 
     * @param pageSize
     * @return
     */
    public ResultPageData<DataSourceInfo> pageDataSourceInfo(DataSourceInfo search, Integer pageNum, Integer pageSize);

    /**
     * 是否存在关于所给数据源Id的引用
     * @param dataSourceInfoId
     * @return true：存在    false：不存在
     */
    public boolean existReferenceAboutDataSource(Long dataSourceInfoId);
    
    /**
     * 根据Id 删除数据源
     * 
     * @param dataSourceId
     */
    public void deleteDataSourceInfoById(Long dataSourceId);

    /**
     * 修改数据源配置信息
     * @param dataSource
     * @return 修改后的对象
     */
    public DataSourceInfo modifyDataSourceInfo(DataSourceInfo dataSource);

    /**
     * 新增数据源信息
     * @param dataSource
     * @return 新增的对象
     */
    public DataSourceInfo saveDataSourceInfo(DataSourceInfo dataSource);

    /**
     * 获取支持的数据源类型信息
     * 
     * @return
     */
    public List<DataSourceElement> getSupportDataSources();
    
    /**
     * 数据源连接测试
     * @param dataSource
     * @return 
     */
    public DBConnectionTestResult dataSourceConnectionTest(DataSourceInfo dataSource) ;
    
    /**
     * 查询指定数据源下的表格信息
     * @param dataSourceId 数据源Id
     * @param dataSourceMetaDataDTO 查询条件
     * @return
     * @throws ApplicationException
     */
    public List<TableMeta> getTableMetas(Long dataSourceId,DataSourceMetaDataDTO dataSourceMetaDataDTO)throws ApplicationException;

    /**
     * 指定数据源下的表中所有字段信息
     * @param dataSourceId 数据源ID
     * @param tableName 表名
     * @param dataSourceMetaDataDTO 查询条件
     * @return
     * @throws ApplicationException
     */
    public List<FieldMeta> getFieldMetas(Long dataSourceId,String tableName,DataSourceMetaDataDTO dataSourceMetaDataDTO)throws ApplicationException;
}
