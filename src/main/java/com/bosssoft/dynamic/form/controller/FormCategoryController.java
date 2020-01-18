 package com.bosssoft.dynamic.form.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bosssoft.dynamic.form.domain.FormCategory;
import com.bosssoft.dynamic.form.service.FormCategoryService;
import com.bosssoft.dynamic.form.vo.FormCategoryTreeNode;

/**
 * 表单分类管理器
 * @author huangxw
 * @date 2020/01/08
 */
@RestController
@RequestMapping("/form-category")
 public class FormCategoryController {

    @Autowired
    private FormCategoryService formCategoryService;
     
    /**
     * 根据Id获取分类
     * @param formCategoryId
     * @return
     */
    @GetMapping(value="/{formCategoryId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public FormCategory getFormCategoryById(@PathVariable(name="formCategoryId")Long formCategoryId){
        return formCategoryService.getFormCategoryById(formCategoryId);
    }
    
    /**
     * 保存分类
     * @param formCategory
     * @return
     */
    @PostMapping(value="",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public FormCategory saveFormCategory(@RequestBody FormCategory formCategory){
        return formCategoryService.saveFormCategory(formCategory);
    }
    
    @GetMapping(value="/delete-verification/{formCategoryId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean deleteVerif(@PathVariable(name="formCategoryId")Long formCategoryId){
        return !formCategoryService.existReferenceAboutCategory(formCategoryId);
    }
    
    
    /**
     * 根据ID删除分类
     * @param formCategoryId
     * @return
     */
    @DeleteMapping(value="/{formCategoryId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Long deleteFormCategory(@PathVariable(name="formCategoryId")Long formCategoryId){
        formCategoryService.deleteFormCategory(formCategoryId);
        return formCategoryId;
    }
    
    /**
     * 修改分类
     * @param formCategory
     * @return
     */
    @PutMapping(value="",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public FormCategory modifyFormCategory(@RequestBody FormCategory formCategory){
        return formCategoryService.modifyFormCategory(formCategory);
    }
    
    /**
     * 获取分类树
     * @param formCategory
     * @return
     */
    @GetMapping(value="/tree",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<FormCategoryTreeNode> getFormCategoryTree(@RequestParam(required = false) String categoryNameLike){
        return formCategoryService.getFormCategoryTree(categoryNameLike);
    }
    
}
