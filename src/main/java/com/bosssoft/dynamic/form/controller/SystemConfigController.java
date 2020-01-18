 package com.bosssoft.dynamic.form.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bosssoft.dynamic.form.domain.SystemConfig;
import com.bosssoft.dynamic.form.service.SystemConfigService;
import com.bosssoft.dynamic.form.vo.ResultPageData;

/**
 * 系统配置管理器
 * @author huangxw
 * @date 2020/01/08
 */
@RestController
 @RequestMapping("/system-config")
 public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;
    
    @GetMapping(value="/{systemConfigId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SystemConfig getSystemConfigById(@PathVariable(name="systemConfigId")Long systemConfigId){
        return systemConfigService.getSystemConfigById(systemConfigId);
    }
    
    /**
     * 修改系统配置项
     * @param systemConfig
     * @return
     */
    @PutMapping(value="",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SystemConfig modifySystemConfig(@RequestBody SystemConfig systemConfig){
        return systemConfigService.modifySystemConfig(systemConfig);
    }
    

    /**
     * 分页查询
     * @param systemConfigPageDTO
     * @return
     */
    @PostMapping(value="/page-query",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultPageData<SystemConfig> pageSystemConfig(@RequestBody(required = false) SystemConfig systemConfig,@RequestParam(required = false) Integer pageNum,@RequestParam(required = false) Integer pageSize){
        return systemConfigService.pageSystemConfig(systemConfig, pageNum, pageSize);
    }
}
