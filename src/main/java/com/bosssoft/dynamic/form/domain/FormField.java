package com.bosssoft.dynamic.form.domain;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "df_form_field")
public class FormField {
    @Id
    private Long id;

    private String fieldName;

    private String fieldDesc;

    private Long dataSourceId;

    private String dataSourceField;

    private String dataSourceTable;

    private Long formModelId;

    private Boolean custom;

    private String creationTime;

    private String modifyTime;

    private String creationUser;

    private String modifyUser;

    private String fieldComment;

    private String validationType;

    private String validationMessage;

    private String validation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc == null ? null : fieldDesc.trim();
    }

    

    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getDataSourceField() {
        return dataSourceField;
    }

    public void setDataSourceField(String dataSourceField) {
        this.dataSourceField = dataSourceField == null ? null : dataSourceField.trim();
    }

    public String getDataSourceTable() {
        return dataSourceTable;
    }

    public void setDataSourceTable(String dataSourceTable) {
        this.dataSourceTable = dataSourceTable == null ? null : dataSourceTable.trim();
    }

    
    public Long getFormModelId() {
        return formModelId;
    }

    public void setFormModelId(Long formModelId) {
        this.formModelId = formModelId;
    }

    public Boolean getCustom() {
        return custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime == null ? null : creationTime.trim();
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime == null ? null : modifyTime.trim();
    }

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser == null ? null : creationUser.trim();
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public String getFieldComment() {
        return fieldComment;
    }

    public void setFieldComment(String fieldComment) {
        this.fieldComment = fieldComment == null ? null : fieldComment.trim();
    }

    public String getValidationType() {
        return validationType;
    }

    public void setValidationType(String validationType) {
        this.validationType = validationType == null ? null : validationType.trim();
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage == null ? null : validationMessage.trim();
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation == null ? null : validation.trim();
    }
}