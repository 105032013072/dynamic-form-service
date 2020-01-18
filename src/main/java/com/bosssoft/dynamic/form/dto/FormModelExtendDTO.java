 package com.bosssoft.dynamic.form.dto;

import java.util.ArrayList;
import java.util.List;

import com.bosssoft.dynamic.form.domain.FormField;
import com.bosssoft.dynamic.form.domain.FormModel;

/**
  * 表单域扩展类
  * @author huangxw
  * @date 2020/01/09
  */
 public class FormModelExtendDTO extends FormModel{

     /**
      * 表单域下的表单字段
      */
     private List<FormField> fields=new ArrayList<FormField>();
     
     public FormModelExtendDTO(){
         
     }
     
     public FormModelExtendDTO(FormModel formModel){
         this.setCreationTime(formModel.getCreationTime());
         this.setCreationUser(formModel.getCreationUser());
         this.setFormId(formModel.getFormId());
         this.setFormModelComment(formModel.getFormModelComment());
         this.setFormModelName(formModel.getFormModelName());
         this.setId(formModel.getId());
         this.setModifyTime(formModel.getModifyTime());
         this.setModifyUser(formModel.getModifyUser());
     }

    public List<FormField> getFields() {
        return fields;
    }

    public void setFields(List<FormField> fields) {
        this.fields = fields;
    }
     
     
 }
