package com.bosssoft.dynamic.form.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bosssoft.dynamic.form.domain.FormCategory;
import com.bosssoft.platform.microservice.framework.dal.common.Mapper;

public interface FormCategoryMapper extends Mapper<FormCategory>{
    
    List<FormCategory> selectFormCategoryByNameLike(@Param("nameLike")String nameLike);
}