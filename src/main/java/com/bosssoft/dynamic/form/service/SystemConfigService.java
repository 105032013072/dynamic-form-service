 package com.bosssoft.dynamic.form.service;

import com.bosssoft.dynamic.form.domain.SystemConfig;
import com.bosssoft.dynamic.form.vo.ResultPageData;

public interface SystemConfigService {
 
       
     /**
      * 修改系统配置项
      * @param systemConfig
      * @return
      */
     public SystemConfig modifySystemConfig(SystemConfig systemConfig);
     
     
     /**
      * 根据系统配置code查询
      * @param systemConfigName
      * @return
      */
     public SystemConfig getSystemConfigByCode(String systemConfigCode);
     
     /**
      * 根据系统配置Id查询
      * @param systemConfigId
      * @return
      */
     public SystemConfig getSystemConfigById(Long systemConfigId);
     
     
    /**
     * 分页查询系统配置项
     * @param search
     * @param pageNum
     * @param pageSize
     * @return
     */
     public ResultPageData<SystemConfig> pageSystemConfig(SystemConfig search, Integer pageNum, Integer pageSize);
}
