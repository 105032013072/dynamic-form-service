package com.bosssoft.dynamic.form.vo;

import java.io.Serializable;

import com.bosssoft.dynamic.form.exception.Error;

public class BasicResponseContentVo<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8748287509576459772L;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 返回码
     */
    private String code;
    /**
     * 描述信息
     */
    private String message;

    public BasicResponseContentVo() {}

    /**
     * 默认成功
     * 
     * @param content
     */
    public BasicResponseContentVo(T data) {
        this.data = data;
        this.code = "200";
        this.message = "处理成功";
    }

    /**
     * 构造异常
     * 
     * @param error
     */
    public BasicResponseContentVo(Error error) {
        // this.msg = new Msg("2", error.getErrMsg());
        this.code = error.getCode();
        this.message = error.getErrMsg();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}