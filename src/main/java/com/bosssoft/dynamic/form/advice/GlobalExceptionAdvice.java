package com.bosssoft.dynamic.form.advice;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.fastjson.JSON;
import com.bosssoft.dynamic.form.exception.ApplicationException;
import com.bosssoft.dynamic.form.exception.Error;
import com.bosssoft.dynamic.form.exception.ErrorType;
import com.bosssoft.dynamic.form.vo.BasicResponseContentVo;

/**
 * 全局异常处理
 * 
 * @author huangxw
 * @date 2020/01/06
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    private ThreadLocal<Object> modelHolder = new ThreadLocal<>();

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    @ResponseBody
    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BasicResponseContentVo<Object> handlerBindException(BindException e, HttpServletRequest request) {
        logger.error("系统异常!uri={} | requestBody={}", request.getRequestURI(), JSON.toJSONString(modelHolder.get()), e);
        return parseException(e);
    }

    @ResponseBody
    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public BasicResponseContentVo<Object> handlerApplicationException(ApplicationException e,
        HttpServletRequest request) {
        logger.error("业务处理异常!uri={} | requestBody={}", request.getRequestURI(), JSON.toJSONString(modelHolder.get()),
            e);
        return parseException(e);
    }

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BasicResponseContentVo<Object> handlerThrowableException(HttpServletRequest request, Throwable e) {
        logger.error("系统异常!uri={} | requestBody={}", request.getRequestURI(), JSON.toJSONString(modelHolder.get()), e);

        BasicResponseContentVo<Object> basicResponseContentVo = null;
        Class eClazz = Throwable.class;
        // 异常为SQLException或者SQLException的子类
        if (SQLException.class.isAssignableFrom(eClazz) || e instanceof SQLException
            || DataAccessException.class.isAssignableFrom(eClazz) || e instanceof DataAccessException) {
            basicResponseContentVo = new BasicResponseContentVo<Object>(
                new Error(ErrorType.DATABAS_EERROR.getCode(), ErrorType.DATABAS_EERROR.getDesc()));
        } else {
            basicResponseContentVo = new BasicResponseContentVo<Object>(
                new Error(ErrorType.UNKNOW_ERROR.getCode(), ErrorType.UNKNOW_ERROR.getDesc()));
        }
        return basicResponseContentVo;
    }

    private BasicResponseContentVo<Object> parseException(ApplicationException e) {

        return new BasicResponseContentVo<>(e.getError());
    }

    private BasicResponseContentVo<Object> parseException(BindException e) {
        StringBuffer message = new StringBuffer();
        for (ObjectError error : e.getAllErrors()) {
            message.append(error.getDefaultMessage());
            message.append(",");
        }
        Error paasError = new Error(ErrorType.INPUT_ERROR.getCode(), ErrorType.INPUT_ERROR.getDesc() + message);
        return new BasicResponseContentVo<Object>(paasError);
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        // ModelHolder 初始化0
        modelHolder.set(webDataBinder.getTarget());
    }
}
