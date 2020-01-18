package com.bosssoft.dynamic.form;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.bosssoft.dynamic.form.domain.DataSourceInfo;
import com.bosssoft.dynamic.form.domain.FormCategory;
import com.bosssoft.dynamic.form.domain.FormField;
import com.bosssoft.dynamic.form.dto.DataSourceMetaDataDTO;
import com.bosssoft.dynamic.form.dto.FormExtendDTO;
import com.bosssoft.dynamic.form.dto.FormModelExtendDTO;
import com.bosssoft.dynamic.form.util.SecurityUtils;
import com.bosssoft.dynamic.form.vo.BasicPageInfo;

public class JsonTest {
 
     @Test
     public void DataSourceInfoJson() throws Exception{
         DataSourceInfo mysqlDataSourceInfo=new DataSourceInfo();
         
         mysqlDataSourceInfo.setDataSourceName("mysql测试数据源");
         mysqlDataSourceInfo.setDbDriver("com.mysql.jdbc.Driver");
         mysqlDataSourceInfo.setDbInstance("test");
         mysqlDataSourceInfo.setDbPassword(SecurityUtils.encrypt("root"));
         mysqlDataSourceInfo.setDbPort("3306");
         mysqlDataSourceInfo.setDbType("MySql_5.X");
         mysqlDataSourceInfo.setDbUsername("root");
         mysqlDataSourceInfo.setHostIp("127.0.0.1");
         
         
         
         DataSourceInfo oracleDataSourceInfo=new DataSourceInfo();
         oracleDataSourceInfo.setDataSourceName("oralce测试数据源");
         oracleDataSourceInfo.setDbDriver("oracle.jdbc.driver.OracleDriver");
         oracleDataSourceInfo.setDbInstance("orcl");
         oracleDataSourceInfo.setDbPassword(SecurityUtils.encrypt("bs"));
         oracleDataSourceInfo.setDbPort("1521");
         oracleDataSourceInfo.setDbType("Oracle_9i/10g/11g");
         oracleDataSourceInfo.setDbUsername("form_bs");
         oracleDataSourceInfo.setHostIp("192.168.10.42");
         
         System.out.println(JSON.toJSONString(oracleDataSourceInfo));
     }
     
     @Test
     public void DataSourceJson(){
         DataSourceInfo dataSourcePage=new DataSourceInfo();
         dataSourcePage.setDataSourceName("非税");
         dataSourcePage.setDbType("Oracle_9i/10g/11g");
        
         

         System.out.println(JSON.toJSONString(dataSourcePage));
     }
     
     @Test
     public void dataSourceMetaDataDTOJson(){
         DataSourceMetaDataDTO dataDTO=new DataSourceMetaDataDTO();
         dataDTO.setDataSourceId(157863834353747L);
         dataDTO.setFieldName("PATH");
         dataDTO.setFieldCommentLike("存储路径");
         System.out.println(JSON.toJSONString(dataDTO));
     }
     
     @Test
     public void systemConfigPageDTOJson(){
         
     }
     
     @Test
     public void formCategoryJson(){
         FormCategory formCategory=new FormCategory();
         formCategory.setCategoryName("非税单位报销表单");
         formCategory.setParent(157847452612155L);
         System.out.println(JSON.toJSONString(formCategory));
     }
     
    
     
     @Test
     public void formBasicJson(){
         FormExtendDTO formExtendDTO4=new FormExtendDTO();
         formExtendDTO4.setCategory(157847452624010L);
         formExtendDTO4.setFormCode("notax_form_0007");
         formExtendDTO4.setFormName("非税单位收费表单");
         formExtendDTO4.setTemplateFormId(157915737587215L);
         formExtendDTO4.setType("PC");
         System.out.println(JSON.toJSONString(formExtendDTO4));
     }
     
     @Test
     public void formExtendDTOJson(){
         FormExtendDTO formExtendDTO=new FormExtendDTO();
        // formExtendDTO.setId(157905479143010L);
         formExtendDTO.setCategory(157847433547834L);
         formExtendDTO.setFormCode("notax_form_0006");
         formExtendDTO.setFormName("采购缴费模板表单_拷贝产生");
         formExtendDTO.setType("PC");
         formExtendDTO.setTemplate(true);
         List<FormModelExtendDTO> models=new ArrayList<>();
         formExtendDTO.setFormModels(models);
         
         FormModelExtendDTO formModelExtendDTO=new FormModelExtendDTO();
         formModelExtendDTO.setFormModelName("dfDataSource");
         formModelExtendDTO.setFormModelComment("数据源配置");
         formModelExtendDTO.setFormId(157905479143010L);
         
         List<FormField> formFields=new ArrayList<>();
         FormField formField1=new FormField();
         formField1.setDataSourceField("HOST_IP");
         formField1.setDataSourceId(157863834353747L);
         formField1.setDataSourceTable("df_data_source");
         formField1.setFieldName("hostIp_33");
         formField1.setFieldComment("333合法的IP地址");
         formField1.setFieldDesc("333主机IP地址");
         formFields.add(formField1);
         
         FormField formField2=new FormField();
         formField2.setDataSourceField("DB_PASSWORD");
         formField2.setDataSourceId(157863834353747L);
         formField2.setDataSourceTable("df_data_source");
         formField2.setFieldName("password_222");
         formField2.setFieldComment("333密码6位数");
         formField2.setFieldDesc("数据库密码3333");
         formFields.add(formField2);
         formModelExtendDTO.setFields(formFields);
         models.add(formModelExtendDTO);
         
         FormModelExtendDTO formModelExtendDTO2=new FormModelExtendDTO();
         formModelExtendDTO2.setFormModelName("dfFormCategory");
         formModelExtendDTO2.setFormModelComment("表单分类");
         formModelExtendDTO2.setFormId(157905479143010L);
         
         List<FormField> formFieldsTwo=new ArrayList<>();
         FormField formFieldTwo=new FormField();
         formFieldTwo.setDataSourceField("CATEGORY_NAME");
         formFieldTwo.setDataSourceId(157863834353747L);
         formFieldTwo.setDataSourceTable("df_form_category");
         formFieldTwo.setFieldName("categoryName");
         formFieldTwo.setFieldComment("分类名称33");
         formFieldTwo.setFieldDesc("合法的分类名称33");
         formFieldsTwo.add(formFieldTwo);
         
         FormField formFieldTwo2=new FormField();
         formFieldTwo2.setDataSourceField("PARENT");
         formFieldTwo2.setDataSourceId(157863834353747L);
         formFieldTwo2.setDataSourceTable("df_form_category");
         formFieldTwo2.setFieldName("parent333");
         formFieldTwo2.setFieldComment("父节点333");
         formFieldTwo2.setFieldDesc("没有父节点则为根节点333");
         formFieldsTwo.add(formFieldTwo2);
         formModelExtendDTO2.setFields(formFieldsTwo);
         models.add(formModelExtendDTO2);
         
         formExtendDTO.setFormContext("这是表单第三次修改33333");
         System.out.println(JSON.toJSONString(formExtendDTO));
     }
     
     
     private BasicPageInfo getBasicPageInfo(){
         BasicPageInfo pageInfo=new BasicPageInfo();
         pageInfo.setCurrentPage(1);
         pageInfo.setPageSize(3);
         return pageInfo;
     }
     
     
     
     @Test
     public void one() throws Exception{
        List<Long> list=new ArrayList<Long>();
        list.add(157864329172372L);
        list.add(157864330711340L);
        System.out.println(JSON.toJSONString(list));
     }
}
