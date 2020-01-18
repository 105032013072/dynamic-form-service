package com.bosssoft.dynamic.form.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK, reason = "No such Order")
public class ApplicationException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -7640793404157007568L;
    /**
     * 错误码
     */
    private Error error;
    /**
     * 异常体
     */
    private Throwable throwable;

    public ApplicationException(Error error) {
        // 不生成堆栈跟踪信息
        super(error.getErrMsg(), null, false, false);
        this.error = error;
    }

    public ApplicationException(Error error, Throwable throwable) {
        super(error.getErrMsg(), throwable);
        this.error = error;
        this.throwable = throwable;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        
         return this;
    }
    
    
}
