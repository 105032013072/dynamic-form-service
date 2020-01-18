 package com.bosssoft.dynamic.form.vo;

 /**
  * 表中字段的信息
  * @author huangxw
  * @date 2020/01/06
  */
 public class FieldMeta {
 
     /**
      * 字符名称
      */
     private String fieldName;
     
     /**
      * 字符备注
      */
     private String fieldComment;
     
     /**
      * 字段类型（int，char，varchar等）
      */
     private String type;
     
     /**
      * 字段类型对应的java类型
      */
     private String className;
     
     /**
      * 字段的最大长度
      */
     private Integer length;
     
     /**
      * 是否允许为空
      */
     private boolean nullAble;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldComment() {
        return fieldComment;
    }

    public void setFieldComment(String fieldComment) {
        this.fieldComment = fieldComment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public boolean isNullAble() {
        return nullAble;
    }

    public void setNullAble(boolean nullAble) {
        this.nullAble = nullAble;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    
     
     
}
