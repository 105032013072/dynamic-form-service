 package com.bosssoft.dynamic.form.service;

import java.util.List;

import com.bosssoft.dynamic.form.domain.FormModel;
import com.bosssoft.dynamic.form.dto.FormModelExtendDTO;

public interface FormModelService {

     /**
      * 根据Id删除表单域
      * @param formModelId
      */
     public void deleteFormModel(Long formModelId);
     
     /**
      * 根据表单Id删除表单域
      * @param formId
      */
     public void deleteFormModelByFormId(Long formId);
     
     /**
      * 根据表单Id获取表单域
      * @param formId
      * @return
      */
     public List<FormModel> getFormModelByFormId(Long formId);
     
     /**
      * 保存表单域
      * @param formModelExtendDTO
      */
     public void saveFormModel(FormModelExtendDTO formModelExtendDTO);
}
