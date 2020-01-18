package com.bosssoft.dynamic.form.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bosssoft.dynamic.form.domain.Form;
import com.bosssoft.platform.microservice.framework.dal.common.Mapper;

public interface FormMapper extends Mapper<Form>{
 
    List<Form> selectFormByCondition(@Param("search")Form search);
}