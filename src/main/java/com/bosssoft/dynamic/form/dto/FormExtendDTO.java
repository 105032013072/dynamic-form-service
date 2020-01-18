 package com.bosssoft.dynamic.form.dto;

import java.util.ArrayList;
import java.util.List;

import com.bosssoft.dynamic.form.domain.Form;

/**
 * 表单扩展类
 * @author huangxw
 * @date 2020/01/09
 */
 public class FormExtendDTO extends Form{

     /**
      * 模板表单ID
      */
    private Long templateFormId;
    
    /**
     * 表单的文件内容
     */
    private String formContext;
    
    /**
     * 表单下的表单域
     */
    private List<FormModelExtendDTO> formModels=new ArrayList<FormModelExtendDTO>();
    
    public FormExtendDTO(){
        
    }
    
    public FormExtendDTO(Form form){
        this.setCategory(form.getCategory());
        this.setCreationTime(form.getCreationTime());
        this.setCreationUser(form.getCreationUser());
        this.setFormCode(form.getFormCode());
        this.setFormName(form.getFormName());
        this.setModifyTime(form.getModifyTime());
        this.setModifyUser(form.getModifyUser());
        this.setPath(form.getPath());
        this.setTemplate(form.getTemplate());
        this.setType(form.getType());

        
    }

    public Long getTemplateFormId() {
        return templateFormId;
    }

    public void setTemplateFormId(Long templateFormId) {
        this.templateFormId = templateFormId;
    }

    
    public String getFormContext() {
        return formContext;
    }

    public void setFormContext(String formContext) {
        this.formContext = formContext;
    }

    public List<FormModelExtendDTO> getFormModels() {
        return formModels;
    }

    public void setFormModels(List<FormModelExtendDTO> formModels) {
        this.formModels = formModels;
    }

   
    
    
}
