 package com.bosssoft.dynamic.form.vo;

 /**
  * 数据源连接测试结果
  * @author huangxw
  * @date 2020/01/06
  */
 public class DBConnectionTestResult {
     
     /**
      * 连接是否成功
      */
    private  boolean success;
    
    /**
     * 连接失败错误信息
     */
    private String errorMessage;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    

}
