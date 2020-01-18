 package com.bosssoft.dynamic.form;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bosssoft.dynamic.form.domain.FormCategory;
import com.bosssoft.dynamic.form.service.FormCategoryService;
import com.bosssoft.dynamic.form.vo.FormCategoryTreeNode;

@RunWith(SpringRunner.class)   
 @SpringBootTest(classes={DynamicFormApplication.class})// 指定启动类
 public class FormCategoryServiceTest {

    @Autowired
    private FormCategoryService formCategoryService;
    
    //========保存表单分类
    @Test
    public void saveParentFormCategoryTest(){
        FormCategory category1=new FormCategory();
        category1.setCategoryName("非税表单");
      
        FormCategory category2=new FormCategory();
        category2.setCategoryName("政府采购表单");
        formCategoryService.saveFormCategory(category1);
        formCategoryService.saveFormCategory(category2);
    }
    
    @Test
    public void saveChihldFormCategoryTest(){
        FormCategory category1=new FormCategory();
        category1.setCategoryName("非税单位收缴表单");
        category1.setParent(157847452612155L);
        
        FormCategory category2=new FormCategory();
        category2.setCategoryName("非税单位收费报销表单");
        category2.setParent(157847452612155L);
        
        formCategoryService.saveFormCategory(category1);
        formCategoryService.saveFormCategory(category2);

    }
    
    //=============分类树测试
    @Test
    public void formCategoryTreeTest(){
        List<FormCategoryTreeNode> list=  formCategoryService.getFormCategoryTree(null);
        System.out.println(list);
    }
    
    
    @Test
    public void deleteCategoryTreeTest(){
        formCategoryService.deleteFormCategory(157847452612155L);
    }
}
