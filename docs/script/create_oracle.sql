

/*==============================================================*/
/* Table: DATA_SOURCE                                           */
/*==============================================================*/
CREATE TABLE DF_DATA_SOURCE 
(
   ID                   number(18)                            NOT NULL,
   DATA_SOURCE_NAME                 VARCHAR2(50)                    NULL,
   HOST_IP                 VARCHAR2(50)                    NULL,
   DB_PORT                 VARCHAR2(5)                     NULL,
   DB_TYPE              VARCHAR2(25)                    NULL,
   DB_DRIVER               VARCHAR2(50)                    NULL,
   DB_INSTANCE             VARCHAR2(50)                    NULL,
   DB_USERNAME             VARCHAR2(50)                    NULL,
   DB_PASSWORD             VARCHAR2(50)                    NULL,
   CREATION_TIME        VARCHAR2(25)                   NULL,
   MODIFY_TIME          VARCHAR2(25)                   NULL,
   CREATION_USER        VARCHAR2(25)                    NULL,
   MODIFY_USER          VARCHAR2(25)                    NULL,
   PRIMARY KEY (ID)
);
COMMENT ON table DF_DATA_SOURCE is '表单字段来源。存储数据库连接信息';



/*==============================================================*/
/* Table: FORM                                                  */
/*==============================================================*/
CREATE TABLE DF_FORM 
(
   ID                   number(18)                            NOT NULL,
   FORM_NAME                 VARCHAR2(50)                    NULL,
   FORM_CODE                 VARCHAR2(50)                    NULL,
   PATH                 VARCHAR2(500)                   NULL,
   TYPE                  VARCHAR2(50)                    NULL,
   CATEGORY                 number(18)                            NULL,
   TEMPLATE             number(1)                            NULL,
   CREATION_TIME        VARCHAR2(25)                   NULL,
   MODIFY_TIME          VARCHAR2(25)                   NULL,
   CREATION_USER        VARCHAR2(25)                    NULL,
   MODIFY_USER          VARCHAR2(25)                    NULL,
   PRIMARY KEY (ID)
);
COMMENT ON table DF_FORM is '表单实例存储，存放表单的存储路径等。';


/*==============================================================*/
/* Table: FORM_FIELD                                            */
/*==============================================================*/
CREATE TABLE DF_FORM_FIELD 
(
   ID                   number(18)                            NOT NULL,
   FIELD_NAME                 VARCHAR2(50)                    NULL,
   FIELD_DESC           VARCHAR2(50)                    NULL,
   DATA_SOURCE_ID       number(18)                            NULL,
   DATA_SOURCE_FIELD                VARCHAR2(50)                    NULL,
   DATA_SOURCE_TABLE              VARCHAR2(50)                    NULL,
   FORM_MODEL_ID        number(18)                             NULL,
   CUSTOM               number(1)                            NULL,
   CREATION_TIME        VARCHAR2(25)                   NULL,
   MODIFY_TIME          VARCHAR2(25)                   NULL,
   CREATION_USER        VARCHAR2(25)                    NULL,
   MODIFY_USER          VARCHAR2(25)                    NULL,
   FIELD_COMMENT            VARCHAR2(100)                   NULL,
   VALIDATION_TYPE      VARCHAR2(10)                   NULL,
   VALIDATION_MESSAGE   VARCHAR2(35)                   NULL,
   VALIDATION           VARCHAR2(100)                   NULL,
   PRIMARY KEY (ID)
);
COMMENT ON table DF_FORM_FIELD is '表单字段映射表';


/*==============================================================*/
/* Table: FORM_MODEL                                            */
/*==============================================================*/
CREATE TABLE DF_FORM_MODEL 
(
   ID                   number(18)                            NOT NULL,
   FORM_ID              number(18)                            NULL,
   FORM_MODEL_NAME                 VARCHAR2(50)                    NULL,
   FORM_MODEL_COMMENT            VARCHAR2(100)                   NULL,
   CREATION_TIME        VARCHAR2(25)                   NULL,
   MODIFY_TIME          VARCHAR2(25)                   NULL,
   CREATION_USER        VARCHAR2(25)                    NULL,
   MODIFY_USER          VARCHAR2(25)                    NULL,
   PRIMARY KEY (ID)
);
COMMENT ON table DF_FORM_MODEL is '表单数据模型，存储表单字段';


/*==============================================================*/
/* Table: FORM_TYPE                                             */
/*==============================================================*/
CREATE TABLE DF_FORM_CATEGORY 
(
   ID                   number(18)                            NOT NULL,
   CATEGORY_NAME                 VARCHAR2(50)                    NULL,
   PARENT               number(18)                            NULL,
   CREATION_TIME        VARCHAR2(25)                   NULL,
   MODIFY_TIME          VARCHAR2(25)                   NULL,
   CREATION_USER        VARCHAR2(25)                    NULL,
   MODIFY_USER          VARCHAR2(25)                    NULL,
   PRIMARY KEY (ID)
);



/*==============================================================*/
/* Table: SYSTEM_CONFIG                                         */
/*==============================================================*/
CREATE TABLE DF_SYSTEM_CONFIG 
(
   ID                   number(18)                            NOT NULL,
   CODE                 VARCHAR2(50)                     NULL,
   NAME                 VARCHAR2(50)                    NULL,
   VALUE                VARCHAR2(100)                   NULL,
   PRIMARY KEY (ID)
);
COMMENT ON table DF_SYSTEM_CONFIG is '系统配置';


ALTER TABLE DF_FORM
   ADD CONSTRAINT FK_FORM_FORM_CATEGORY FOREIGN KEY (CATEGORY)
      REFERENCES DF_FORM_CATEGORY (ID);

ALTER TABLE DF_FORM_FIELD
   ADD CONSTRAINT FK_FORM_FIE_DATA_SOU FOREIGN KEY (DATA_SOURCE_ID)
      REFERENCES DF_DATA_SOURCE (ID);

ALTER TABLE DF_FORM_FIELD
   ADD CONSTRAINT FK_FORM_FIE_FORM_MODE_FORM_MOD FOREIGN KEY (FORM_MODEL_ID)
      REFERENCES DF_FORM_MODEL (ID);

ALTER TABLE DF_FORM_MODEL
   ADD CONSTRAINT FK_FORM_MOD_FORM_ID_FORM FOREIGN KEY (FORM_ID)
      REFERENCES DF_FORM (ID);

commit;

