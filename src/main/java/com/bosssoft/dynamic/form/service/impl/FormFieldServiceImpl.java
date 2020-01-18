 package com.bosssoft.dynamic.form.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.dynamic.form.domain.FormField;
import com.bosssoft.dynamic.form.mapper.FormFieldMapper;
import com.bosssoft.dynamic.form.service.FormFieldService;
import com.bosssoft.dynamic.form.spi.UserService;
import com.bosssoft.dynamic.form.util.DynamicFormUtil;

@Service
public class FormFieldServiceImpl implements FormFieldService{

    @Autowired
    private FormFieldMapper formFieldMapper;
    
    @Autowired
    private UserService userService;
    
    @Override
    public List<FormField> getFormFieldsByModelId(Long modelId) {
        FormField formField=new FormField();
        formField.setFormModelId(modelId);
         return formFieldMapper.select(formField);
    }

    @Override
    public void saveFormField(FormField formField) {
        
        /*if (formField.getId() == null) {
            formField.setId(DynamicFormUtil.generateId());
            formField.setCreationTime(DynamicFormUtil.getCurrentTimeStr());
            formField.setCreationUser(userService.getCurrentUserId());
        } else {
            formField.setModifyTime(DynamicFormUtil.getCurrentTimeStr());
            formField.setModifyUser(userService.getCurrentUserId());
        }*/
        formField.setId(DynamicFormUtil.generateId());
        formField.setCreationTime(DynamicFormUtil.getCurrentTimeStr());
        formField.setCreationUser(userService.getCurrentUserId());
        formFieldMapper.insertSelective(formField);
    }

    @Override
    public void deleteFormFieldByModelId(Long formModelId) {
       // 删除表单字段
        FormField field = new FormField();
        field.setFormModelId(formModelId);
        formFieldMapper.delete(field);
         
    }

}
