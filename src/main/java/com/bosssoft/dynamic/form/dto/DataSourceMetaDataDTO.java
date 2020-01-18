 package com.bosssoft.dynamic.form.dto;

 public class DataSourceMetaDataDTO {

     private Long dataSourceId;
     
     private String tableName;
     
     private String tableNameOrCommentLike;
     
     private String fieldName;
     
     private String fieldCommentLike;

    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getTableNameOrCommentLike() {
        return tableNameOrCommentLike;
    }

    public void setTableNameOrCommentLike(String tableNameOrCommentLike) {
        this.tableNameOrCommentLike = tableNameOrCommentLike;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldCommentLike() {
        return fieldCommentLike;
    }

    public void setFieldCommentLike(String fieldCommentLike) {
        this.fieldCommentLike = fieldCommentLike;
    }

    
    
     
}
