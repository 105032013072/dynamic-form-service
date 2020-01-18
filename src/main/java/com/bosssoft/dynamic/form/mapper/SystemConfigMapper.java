package com.bosssoft.dynamic.form.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bosssoft.dynamic.form.domain.SystemConfig;
import com.bosssoft.platform.microservice.framework.dal.common.Mapper;

public interface SystemConfigMapper extends Mapper<SystemConfig>{
    
    List<SystemConfig> selectSystemConfigByCondition(@Param("search")SystemConfig search);
    
}