 package com.bosssoft.dynamic.form.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bosssoft.dynamic.form.domain.Form;
import com.bosssoft.dynamic.form.domain.FormCategory;
import com.bosssoft.dynamic.form.mapper.FormCategoryMapper;
import com.bosssoft.dynamic.form.mapper.FormMapper;
import com.bosssoft.dynamic.form.service.FormCategoryService;
import com.bosssoft.dynamic.form.spi.UserService;
import com.bosssoft.dynamic.form.util.DynamicFormUtil;
import com.bosssoft.dynamic.form.vo.FormCategoryTreeNode;

@Service
 public class FormCategoryServiceImpl implements FormCategoryService{

    @Autowired
    private FormCategoryMapper formCategoryMapper;
    
    @Autowired
    private FormMapper formMapper;
    
    @Autowired
    private UserService userService;
    
    
    @Override
    public FormCategory getFormCategoryById(Long formCategoryId) {
       
         return formCategoryMapper.selectByPrimaryKey(formCategoryId);
    }

    @Override
    public FormCategory saveFormCategory(FormCategory formCategory) {
        formCategory.setId(DynamicFormUtil.generateId());
        formCategory.setCreationTime(DynamicFormUtil.getCurrentTimeStr());
        formCategory.setCreationUser(userService.getCurrentUserId());
        
        formCategoryMapper.insertSelective(formCategory);
        
         return formCategory;
    }

    @Override
    @Transactional
    public void deleteFormCategory(Long formCategoryId) {        
        //删除子分类
        FormCategory formCategory=new FormCategory();
        formCategory.setParent(formCategoryId);
        formCategoryMapper.delete(formCategory);
        
        //删除Id对应的分类
        formCategoryMapper.deleteByPrimaryKey(formCategoryId);
         
    }

    @Override
    public FormCategory modifyFormCategory(FormCategory formCategory) {
        formCategory.setModifyTime(DynamicFormUtil.getCurrentTimeStr());
        formCategory.setModifyUser(userService.getCurrentUserId());
        
        formCategoryMapper.updateByPrimaryKeySelective(formCategory);
        return formCategory; 
    }

    @Override
    public List<FormCategoryTreeNode> getFormCategoryTree(String categoryNameLike) {
        
        List<FormCategoryTreeNode> tree=new ArrayList<FormCategoryTreeNode>();
        List<FormCategory> formCategorys=new ArrayList<FormCategory>();
        if(StringUtils.isEmpty(categoryNameLike)){
            //获取所有分类
            formCategorys=formCategoryMapper.selectAll();
            return createFormCategoryTreeHasParent(formCategorys);
        }else{
            //根据条件查询
            formCategorys=formCategoryMapper.selectFormCategoryByNameLike(categoryNameLike);
            return createFormCategoryTreeAllLeaf(formCategorys);
        }

    }
    
    /**
     * 带有父子层级关系
     * @param formCategorys
     * @return
     */
    private List<FormCategoryTreeNode> createFormCategoryTreeHasParent(List<FormCategory> formCategorys){
        List<FormCategoryTreeNode> result=new ArrayList<>();
        //保存父亲节点ID及其对应的孩子节点
        Map<Long, List<FormCategory>> parentChildsMap=new HashMap<>();
        List<FormCategory> rootNodes=new ArrayList<>();
        
        for (FormCategory formCategory : formCategorys) {
           Long parentId=formCategory.getParent();
           if(parentId!=null){
               if(parentChildsMap.containsKey(parentId)){
                   parentChildsMap.get(parentId).add(formCategory);
               }else{
                   List<FormCategory> list=new ArrayList<>();
                   list.add(formCategory);
                   parentChildsMap.put(parentId, list);
               }
           }else{
               rootNodes.add(formCategory);
           }
        }
        
        //构造树节点对象
        for (FormCategory formCategory : rootNodes) {
            result.add(createTreeNode(formCategory, parentChildsMap));
        }
        return result;
    }
    
    private FormCategoryTreeNode createTreeNode(FormCategory formCategory, Map<Long, List<FormCategory>> parentChildsMap){
        FormCategoryTreeNode node=new FormCategoryTreeNode();
        node.setData(formCategory);
        node.setId(formCategory.getId());
        node.setLabel(formCategory.getCategoryName());
        if(parentChildsMap.containsKey(formCategory.getId())) {
            node.setLeaf(false);
            for (FormCategory childFormCategory : parentChildsMap.get(formCategory.getId())) {
                node.addChild(createTreeNode(childFormCategory, parentChildsMap));
            }
        }else{
            //虽然没有孩子节点，但是也没有父节点，说明是根节点
            if(formCategory.getParent()==null) node.setLeaf(false);
            //有父节点但是没有孩子节点，即为叶子节点
            else node.setLeaf(true);
        }
        return node;
    }
    
    
    
    /**
     * 所有均为叶子节点
     * @param formCategorys
     * @return
     */
    private List<FormCategoryTreeNode> createFormCategoryTreeAllLeaf(List<FormCategory> formCategorys){
        List<FormCategoryTreeNode> result=new ArrayList<>();
        for (FormCategory formCategory : formCategorys) {
            FormCategoryTreeNode node=new FormCategoryTreeNode();
            node.setData(formCategory);
            node.setId(formCategory.getId());
            node.setLabel(formCategory.getCategoryName());
            node.setLeaf(true);
            result.add(node);
        }
        return result;
    }

    @Override
    public boolean existReferenceAboutCategory(Long formCategoryId) {
        boolean result=false;
        
        //检查对子分类的引用
        FormCategory category=new FormCategory();
        category.setParent(formCategoryId);
        List<FormCategory> children=formCategoryMapper.select(category);
        for (FormCategory formCategory : children) {
            if(checkFormReferenceAboutCategory(formCategory.getId())) return true;
        }
        
        //检查对所给分类的引用
        return checkFormReferenceAboutCategory(formCategoryId);
    }
    
    private boolean checkFormReferenceAboutCategory(Long formCategoryId){
        Form record=new Form();
        record.setCategory(formCategoryId);
        List<Form> forms=  formMapper.select(record);
        if(CollectionUtils.isEmpty(forms)) return false;
        else return true;
    }
}
