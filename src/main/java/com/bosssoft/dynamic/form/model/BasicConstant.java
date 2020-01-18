 package com.bosssoft.dynamic.form.model;

 public class BasicConstant {

     /**
      * 所支持的数据源类型的配置文件路径
      */
     public static final String DATASOURCE_SUPPORTED_FILEPATH="dataSource/dataSource_supported.xml";
     
     /**
      * 数据源获取连接出错时的自动重连次数
      */
     public static final int DATASOURCE_CONNECTIONERROR_RETRYS=10;
     
     /**
      * 数据源设置获取连接时的重试次数，-1为不重试
      */
     public static final int DATASOURCE_TIMEOUT_RETRYS=10;
     
     /**
      * 列名_表名
      */
     public static final String COLUMN_NAME_TABLENAME="TABLE_NAME";
     
     /**
      * 列名_表备注
      */
     public static final String COLUMN_NAME_TABLECOMMENT="REMARKS";
     
     /**
      * 列名_字段名
      */
     public static final String COLUMN_NAME_FIELDNAME="COLUMN_NAME";
     
     /**
      * 列名_字段备注
      */
     public static final String COLUMN_NAME_FIELDCOMMENT="REMARKS";
     
     /**
      * 列名_字段类型
      */
     public static final String COLUMN_NAME_FIELDTYPE="TYPE_NAME"; 
     
     /**
      * 列名_字段长度
      */
     public static final String COLUMN_NAME_FIELDSIZE="COLUMN_SIZE"; 
     
     /**
      * 列名_字段是否可为空
      */
     public static final String COLUMN_NAME_FIELDNULLABLE="NULLABLE"; 
     
     public static final String DB_TYPE_MYSQL="mysql";
     
     public static final String DB_TYPE_ORACLE="oracle";
     
     public static final String DB_TYPE_SQLSERVER="microsoft sql server";
     
     public static final String DB_TYPE_POSTGRESQL="postgres";
     
     /**
      * 表单类型_PC
      */
     public static final String FORM_TYPE_PC="PC";
     
     /**
      * 表单类型_MOBILE
      */
     public static final String FORM_TYPE_MOBILE="MOBILE";
     
     /**
      * 编码格式_UTF-8
      */
     public static final String ENCODING_UTF8="utf-8";
     
}
