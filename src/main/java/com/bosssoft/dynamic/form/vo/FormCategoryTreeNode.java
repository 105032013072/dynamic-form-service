 package com.bosssoft.dynamic.form.vo;

import java.util.ArrayList;
import java.util.List;

/**
  * 表单分类树的节点
  * @author huangxw
  * @date 2020/01/08
  */
 public class FormCategoryTreeNode {
     
     private Long Id;

     private String label;
     
     private boolean  isLeaf;
     
     private Object data;
     
     private List<FormCategoryTreeNode> children=new ArrayList<FormCategoryTreeNode>();

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<FormCategoryTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<FormCategoryTreeNode> children) {
        this.children = children;
    }
     
    public void addChild(FormCategoryTreeNode childNode){
        this.children.add(childNode);
    }
     
     
}
