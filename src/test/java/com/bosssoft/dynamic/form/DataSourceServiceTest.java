 package com.bosssoft.dynamic.form;

import java.text.MessageFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bosssoft.dynamic.form.domain.DataSourceInfo;
import com.bosssoft.dynamic.form.dto.DataSourceMetaDataDTO;
import com.bosssoft.dynamic.form.exception.ApplicationException;
import com.bosssoft.dynamic.form.mapper.DataSourceInfoMapper;
import com.bosssoft.dynamic.form.service.DataSourceService;
import com.bosssoft.dynamic.form.vo.FieldMeta;
import com.bosssoft.dynamic.form.vo.TableMeta;

@RunWith(SpringRunner.class)   
 @SpringBootTest(classes={DynamicFormApplication.class})// 指定启动类
 public class DataSourceServiceTest {
   
    @Autowired
    private DataSourceService dataSourceService;
    
    @Autowired
    private DataSourceInfoMapper dataSourceInfoMapper;
    
    @Test
    public void saveDataSourceInfoTest(){
        DataSourceInfo mysqlDataSourceInfo=new DataSourceInfo();
        mysqlDataSourceInfo.setDataSourceName("mysql测试数据源");
        mysqlDataSourceInfo.setDbDriver("com.mysql.jdbc.Driver");
        mysqlDataSourceInfo.setDbInstance("test");
        mysqlDataSourceInfo.setDbPassword("root");
        mysqlDataSourceInfo.setDbPort("3306");
        mysqlDataSourceInfo.setDbType("MySql_5.X");
        mysqlDataSourceInfo.setDbUsername("root");
        mysqlDataSourceInfo.setHostIp("127.0.0.1");
        
        
        
        DataSourceInfo oracleDataSourceInfo=new DataSourceInfo();
        oracleDataSourceInfo.setDataSourceName("oralce测试数据源");
        oracleDataSourceInfo.setDbDriver("oracle.jdbc.driver.OracleDriver");
        oracleDataSourceInfo.setDbInstance("orcl");
        oracleDataSourceInfo.setDbPassword("bs");
        oracleDataSourceInfo.setDbPort("1521");
        oracleDataSourceInfo.setDbType("Oracle_9i/10g/11g");
        oracleDataSourceInfo.setDbUsername("form_bs");
        oracleDataSourceInfo.setHostIp("192.168.10.42");
        
        dataSourceService.saveDataSourceInfo(oracleDataSourceInfo);
        dataSourceService.saveDataSourceInfo(mysqlDataSourceInfo);
    }
    
    
    @Test
    public void mysqlTableMateDataTest() throws ApplicationException{
        DataSourceMetaDataDTO dto=new DataSourceMetaDataDTO();
        dto.setTableNameOrCommentLike("df_form_category");
        
        List<TableMeta> list= dataSourceService.getTableMetas(157837843236389L, dto);
        for (TableMeta tableMeta : list) {
            System.out.println(tableMeta.getTableName()+"  "+tableMeta.getTableComment());
        }
        
    }
    
    @Test
    public void mysqlFieldMateDataTest() throws ApplicationException{
        DataSourceMetaDataDTO dto=new DataSourceMetaDataDTO();
       // dto.setFieldName("NAME");
       // dto.setFieldCommentLike("表单");
        List<FieldMeta> list=dataSourceService.getFieldMetas(157837843236389L, "df_form".toUpperCase(), dto);
        for (FieldMeta fieldMeta : list) {
            System.out.println(MessageFormat.format("name={0},  Comment={1},  type={2}, className={3},length={4},  nullAble={5}", fieldMeta.getFieldName(),fieldMeta.getFieldComment(),fieldMeta.getType(),fieldMeta.getClassName(),fieldMeta.getLength(),fieldMeta.isNullAble()));
        }
    }
    
    
    
    @Test
    public void oracleTableMateDataTest() throws ApplicationException{
        DataSourceMetaDataDTO dto=new DataSourceMetaDataDTO();
        dto.setTableNameOrCommentLike("表单");
        
        List<TableMeta> list= dataSourceService.getTableMetas(157837843223218L, dto);
        for (TableMeta tableMeta : list) {
            System.out.println(tableMeta.getTableName()+"  "+tableMeta.getTableComment());
        }
        
    }
    
   
    @Test
    public void oralceFieldMateDataTest() throws ApplicationException{
        DataSourceMetaDataDTO dto=new DataSourceMetaDataDTO();
        
        List<FieldMeta> list=dataSourceService.getFieldMetas(157837843223218L, "DF_DATA_SOURCE", dto);
        for (FieldMeta fieldMeta : list) {
            System.out.println(MessageFormat.format("name={0},  Comment={1},  type={2}, className={3}, length={4},  nullAble={5}", fieldMeta.getFieldName(),fieldMeta.getFieldComment(),fieldMeta.getType(),fieldMeta.getClassName(),fieldMeta.getLength(),fieldMeta.isNullAble()));
        }
    }
    
    @Test
    public void dbConnectionTest(){
        
        /*DBConnectionTestResult result=  dataSourceService.dataSourceConnectionTest(dataSourceInfoMapper.selectByPrimaryKey(157837843223218L));
        System.out.println(result.isSuccess());*/
    }
    
}
