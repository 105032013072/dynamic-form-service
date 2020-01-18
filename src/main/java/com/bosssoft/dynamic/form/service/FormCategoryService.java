 package com.bosssoft.dynamic.form.service;

import java.util.List;

import com.bosssoft.dynamic.form.domain.FormCategory;
import com.bosssoft.dynamic.form.vo.FormCategoryTreeNode;

public interface FormCategoryService {

    /**
     * 根据Id获取表单分类
     * @param formCategoryId
     * @return
     */
     public FormCategory getFormCategoryById(Long formCategoryId);
     
     /**
      * 新增表单分类
      * @param formCategory
      * @return
      */
     public FormCategory saveFormCategory(FormCategory formCategory);
     
     /**
      * 是否存在关于所给分类Id的引用
      * @param formCategoryId
      * @return true：存在引用   false：不存在引用
      */
     public boolean existReferenceAboutCategory(Long formCategoryId);
     
     /**
      * 删除表单分类
      * @param formCategoryId
      */
     public void deleteFormCategory(Long formCategoryId);
     
     /**
      * 修改表单分类
      * @param formCategory
      */
     public FormCategory modifyFormCategory(FormCategory formCategory);
     
     /**
      * 获取表单分类树
      * @param categoryNameLike
      * @return
      */
     public List<FormCategoryTreeNode> getFormCategoryTree(String categoryNameLike);
}
