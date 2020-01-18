 package com.bosssoft.dynamic.form;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bosssoft.dynamic.form.domain.FormField;
import com.bosssoft.dynamic.form.dto.FormExtendDTO;
import com.bosssoft.dynamic.form.dto.FormModelExtendDTO;
import com.bosssoft.dynamic.form.exception.ApplicationException;
import com.bosssoft.dynamic.form.service.FormService;

@RunWith(SpringRunner.class)   
 @SpringBootTest(classes={DynamicFormApplication.class})// 指定启动类
 public class FormServiceTest {
   
    @Autowired
    private FormService formService;
    
    
    //==========保存表单
    @Test
    public void saveFormTest() throws ApplicationException{
        FormExtendDTO formExtendDTO=new FormExtendDTO();
        formExtendDTO.setCategory(157847452624010L);
        formExtendDTO.setFormCode("notax_form_0001");
        formExtendDTO.setFormName("非税财政收费表单");
        formExtendDTO.setType("PC");
        
        
        FormExtendDTO formExtendDTO2=new FormExtendDTO();
        formExtendDTO2.setCategory(157847452624010L);
        formExtendDTO2.setFormCode("notax_form_0002");
        formExtendDTO2.setFormName("非税财政缴费表单");
        formExtendDTO2.setType("MOBILE");
        
        FormExtendDTO formExtendDTO3=new FormExtendDTO();
        formExtendDTO3.setCategory(157847433547834L);
        formExtendDTO3.setFormCode("notax_form_0003");
        formExtendDTO3.setFormName("采购申请表单");
        formExtendDTO3.setType("PC");
        
        FormExtendDTO formExtendDTO4=new FormExtendDTO();
        formExtendDTO4.setCategory(157847433547834L);
        formExtendDTO4.setFormCode("notax_form_0004");
        formExtendDTO4.setFormName("采购报销表单");
        formExtendDTO4.setType("PC");
        
        formService.saveForm(formExtendDTO2, (Long)null);
        formService.saveForm(formExtendDTO3, (Long)null);
        formService.saveForm(formExtendDTO4, (Long)null);
        
        //formService.saveForm(formExtendDTO, (Long)null);
    }
    
    
    @Test
    public void getFormVueFileContextTest() throws ApplicationException{
        System.out.println(formService.getFormVueFileContext(157905479143010L));
    }
    
    @Test
    public void getFormModelAndFieldInfoTest(){
        FormExtendDTO formExtendDTO=formService.getFormModelAndFieldInfo(157905479143010L);
        System.out.println("========form info======");
        System.out.println(MessageFormat.format("formId={0},formCode={1},formName={2}", formExtendDTO.getId(),formExtendDTO.getFormCode(),formExtendDTO.getFormName()));
        
       
        for (FormModelExtendDTO model : formExtendDTO.getFormModels()) {
            System.out.println("=======model========");
            System.out.println(MessageFormat.format("modelId={0},formModelName={1},formModelComment={2}", model.getId(),model.getFormModelName(),model.getFormModelComment()));
             
            for (FormField field : model.getFields()) {
                System.out.println(MessageFormat.format("fieldId={0},fieldName={1},fieldDesc={2}", field.getId(),field.getFieldName(),field.getFieldDesc()));
            }
        
        }
    }
    
    /**
     * 修改表单测试
     * @throws ApplicationException 
     */
    @Test
    public void modifyFormTest() throws ApplicationException{
        FormExtendDTO formExtendDTO=new FormExtendDTO();
        formExtendDTO.setId(157905479143010L);
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
        formField1.setFieldName("hostIp");
        formField1.setFieldComment("合法的IP地址");
        formField1.setFieldDesc("主机IP地址");
        formFields.add(formField1);
        
        FormField formField2=new FormField();
        formField2.setDataSourceField("DB_PASSWORD");
        formField2.setDataSourceId(157863834353747L);
        formField2.setDataSourceTable("df_data_source");
        formField2.setFieldName("password");
        formField2.setFieldComment("密码6位数");
        formField2.setFieldDesc("数据库密码");
        formFields.add(formField2);
        formModelExtendDTO.setFields(formFields);
        models.add(formModelExtendDTO);
        
        FormModelExtendDTO formModelExtendDTO2=new FormModelExtendDTO();
        formModelExtendDTO2.setFormModelName("dfSystemConfig");
        formModelExtendDTO2.setFormModelComment("系统配置");
        formModelExtendDTO2.setFormId(157905479143010L);
        models.add(formModelExtendDTO2);
        
        formExtendDTO.setFormContext("表单第一次修改");
        formService.modifyForm(formExtendDTO);
    }
}
