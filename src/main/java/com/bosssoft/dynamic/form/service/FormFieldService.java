 package com.bosssoft.dynamic.form.service;

import java.util.List;

import com.bosssoft.dynamic.form.domain.FormField;

public interface FormFieldService {

    /**
     * 保存表单子段
     * @param formField
     */
    public void saveFormField(FormField formField);
    
    /**
     * 根据表单域Id删除表单字段
     * @param formModelId
     */
    public void deleteFormFieldByModelId(Long formModelId);
    
    
    /**
     * 根据表单域Id获取表单字段
     * @param modelId
     * @return
     */
     public List<FormField> getFormFieldsByModelId(Long modelId);
}
