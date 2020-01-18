package com.bosssoft.dynamic.form.exception;

public enum ErrorType {

    INPUT_ERROR("000001", "接口参数非法"), DATABAS_EERROR("000002", "数据库操作异常"), UNKNOW_ERROR("000003", "系统未知异常"),
    BUSINESS_ERROR("000004", "业务处理异常");

    private String code;
    private String desc;

    private ErrorType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
