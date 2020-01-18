 package com.bosssoft.dynamic.form.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.dynamic.form.domain.SystemConfig;
import com.bosssoft.dynamic.form.mapper.SystemConfigMapper;
import com.bosssoft.dynamic.form.service.SystemConfigService;
import com.bosssoft.dynamic.form.vo.BasicPageInfo;
import com.bosssoft.dynamic.form.vo.ResultPageData;
import com.bosssoft.platform.microservice.framework.common.data.Page;
import com.bosssoft.platform.microservice.framework.dal.pagination.PageHelper;

@Service
 public class SystemConfigServiceImpl implements SystemConfigService{
    
    @Autowired
    private SystemConfigMapper systemConfigMapper;

    
    @Override
    public SystemConfig modifySystemConfig(SystemConfig systemConfig) {
        systemConfigMapper.updateByPrimaryKeySelective(systemConfig);
         return systemConfig;
    }

    @Override
    public ResultPageData<SystemConfig> pageSystemConfig(SystemConfig search, Integer pageNum, Integer pageSize) {
        if(pageNum==null) pageNum=BasicPageInfo.DEFAULE_CURRENTPAGE;
        if(pageSize==null) pageSize=BasicPageInfo.DEFAULE_PAGESIZE;
        if(search==null) search=new SystemConfig();
        
        Page<SystemConfig> page=PageHelper.startPage(pageNum, pageSize);
        systemConfigMapper.selectSystemConfigByCondition(search);
         return new ResultPageData<>(page);
    }

    @Override
    public SystemConfig getSystemConfigByCode(String systemConfigCode) {
        SystemConfig systemConfig=new SystemConfig();
        systemConfig.setCode(systemConfigCode);
         return systemConfigMapper.selectOne(systemConfig);
    }

    @Override
    public SystemConfig getSystemConfigById(Long systemConfigId) {
       
         return systemConfigMapper.selectByPrimaryKey(systemConfigId);
    }

}
