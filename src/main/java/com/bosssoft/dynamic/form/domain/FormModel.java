package com.bosssoft.dynamic.form.domain;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "df_form_model")
public class FormModel {
    @Id
    private Long id;

    private Long formId;

    private String formModelName;

    private String formModelComment;

    private String creationTime;

    private String modifyTime;

    private String creationUser;

    private String modifyUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

  
    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getFormModelName() {
        return formModelName;
    }

    public void setFormModelName(String formModelName) {
        this.formModelName = formModelName == null ? null : formModelName.trim();
    }

    public String getFormModelComment() {
        return formModelComment;
    }

    public void setFormModelComment(String formModelComment) {
        this.formModelComment = formModelComment == null ? null : formModelComment.trim();
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
}