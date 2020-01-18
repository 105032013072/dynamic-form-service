/*
Navicat MySQL Data Transfer

Source Server         : connection
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : form_bs

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2020-01-18 15:28:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for df_data_source
-- ----------------------------
DROP TABLE IF EXISTS `df_data_source`;
CREATE TABLE `df_data_source` (
  `ID` bigint(20) NOT NULL,
  `DATA_SOURCE_NAME` varchar(50) DEFAULT NULL,
  `HOST_IP` varchar(50) DEFAULT NULL,
  `DB_PORT` varchar(5) DEFAULT NULL,
  `DB_TYPE` varchar(25) DEFAULT NULL,
  `DB_DRIVER` varchar(50) DEFAULT NULL,
  `DB_INSTANCE` varchar(50) DEFAULT NULL,
  `DB_USERNAME` varchar(50) DEFAULT NULL,
  `DB_PASSWORD` varchar(50) DEFAULT NULL,
  `CREATION_TIME` varchar(25) DEFAULT NULL,
  `MODIFY_TIME` varchar(25) DEFAULT NULL,
  `CREATION_USER` varchar(25) DEFAULT NULL,
  `MODIFY_USER` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单字段来源。存储数据库连接信息';

-- ----------------------------
-- Table structure for df_form
-- ----------------------------
DROP TABLE IF EXISTS `df_form`;
CREATE TABLE `df_form` (
  `ID` bigint(20) NOT NULL,
  `FORM_NAME` varchar(50) DEFAULT NULL,
  `FORM_CODE` varchar(50) DEFAULT NULL,
  `PATH` varchar(500) DEFAULT NULL,
  `TYPE` varchar(50) DEFAULT NULL,
  `CATEGORY` bigint(20) DEFAULT NULL,
  `TEMPLATE` bit(1) DEFAULT NULL,
  `CREATION_TIME` varchar(25) DEFAULT NULL,
  `MODIFY_TIME` varchar(25) DEFAULT NULL,
  `CREATION_USER` varchar(25) DEFAULT NULL,
  `MODIFY_USER` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_FORM_FORM_CATEGORY` (`CATEGORY`),
  CONSTRAINT `FK_FORM_FORM_CATEGORY` FOREIGN KEY (`CATEGORY`) REFERENCES `df_form_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单实例存储，存放表单的存储路径等。';

-- ----------------------------
-- Table structure for df_form_category
-- ----------------------------
DROP TABLE IF EXISTS `df_form_category`;
CREATE TABLE `df_form_category` (
  `ID` bigint(20) NOT NULL,
  `CATEGORY_NAME` varchar(50) DEFAULT NULL,
  `PARENT` bigint(20) DEFAULT NULL,
  `CREATION_TIME` varchar(25) DEFAULT NULL,
  `MODIFY_TIME` varchar(25) DEFAULT NULL,
  `CREATION_USER` varchar(25) DEFAULT NULL,
  `MODIFY_USER` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for df_form_field
-- ----------------------------
DROP TABLE IF EXISTS `df_form_field`;
CREATE TABLE `df_form_field` (
  `ID` bigint(20) NOT NULL,
  `FIELD_NAME` varchar(50) DEFAULT NULL,
  `FIELD_DESC` varchar(50) DEFAULT NULL,
  `DATA_SOURCE_ID` bigint(20) DEFAULT NULL,
  `DATA_SOURCE_FIELD` varchar(50) DEFAULT NULL,
  `DATA_SOURCE_TABLE` varchar(50) DEFAULT NULL,
  `FORM_MODEL_ID` bigint(20) DEFAULT NULL,
  `CUSTOM` bit(1) DEFAULT NULL,
  `CREATION_TIME` varchar(25) DEFAULT NULL,
  `MODIFY_TIME` varchar(25) DEFAULT NULL,
  `CREATION_USER` varchar(25) DEFAULT NULL,
  `MODIFY_USER` varchar(25) DEFAULT NULL,
  `FIELD_COMMENT` varchar(100) DEFAULT NULL,
  `VALIDATION_TYPE` varchar(10) DEFAULT NULL,
  `VALIDATION_MESSAGE` varchar(35) DEFAULT NULL,
  `VALIDATION` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_FORM_FIE_FORM_MODE_FORM_MOD` (`FORM_MODEL_ID`),
  KEY `FK_FORM_FIE_DATA_SOU` (`DATA_SOURCE_ID`),
  CONSTRAINT `FK_FORM_FIE_DATA_SOU` FOREIGN KEY (`DATA_SOURCE_ID`) REFERENCES `df_data_source` (`ID`),
  CONSTRAINT `FK_FORM_FIE_FORM_MODE_FORM_MOD` FOREIGN KEY (`FORM_MODEL_ID`) REFERENCES `df_form_model` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单字段映射表';

-- ----------------------------
-- Table structure for df_form_model
-- ----------------------------
DROP TABLE IF EXISTS `df_form_model`;
CREATE TABLE `df_form_model` (
  `ID` bigint(20) NOT NULL,
  `FORM_ID` bigint(20) DEFAULT NULL,
  `FORM_MODEL_NAME` varchar(50) DEFAULT NULL,
  `FORM_MODEL_COMMENT` varchar(100) DEFAULT NULL,
  `CREATION_TIME` varchar(25) DEFAULT NULL,
  `MODIFY_TIME` varchar(25) DEFAULT NULL,
  `CREATION_USER` varchar(25) DEFAULT NULL,
  `MODIFY_USER` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_FORM_MOD_FORM_ID_FORM` (`FORM_ID`),
  CONSTRAINT `FK_FORM_MOD_FORM_ID_FORM` FOREIGN KEY (`FORM_ID`) REFERENCES `df_form` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单数据模型，存储表单字段';

-- ----------------------------
-- Table structure for df_system_config
-- ----------------------------
DROP TABLE IF EXISTS `df_system_config`;
CREATE TABLE `df_system_config` (
  `ID` bigint(20) NOT NULL,
  `CODE` varchar(50) DEFAULT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `VALUE` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置';
