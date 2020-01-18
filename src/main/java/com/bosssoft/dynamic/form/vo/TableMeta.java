 package com.bosssoft.dynamic.form.vo;

 /**
  * 数据库中表的基本信息
  * @author huangxw
  * @date 2020/01/06
  */
 public class TableMeta {
    
     /**
      * 表名
      */
     private String tableName;
     
     /**
      * 表的备注
      */
     private String tableComment;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }
     
     
}
