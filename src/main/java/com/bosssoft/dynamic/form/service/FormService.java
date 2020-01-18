 package com.bosssoft.dynamic.form.service;

import com.bosssoft.dynamic.form.domain.Form;
import com.bosssoft.dynamic.form.dto.FormExtendDTO;
import com.bosssoft.dynamic.form.exception.ApplicationException;
import com.bosssoft.dynamic.form.vo.ResultPageData;

public interface FormService {

    /**
     * 根据Id获取表单
     * @param formId
     * @return
     */
     public Form getFormByFormId(Long formId);
     
     /**
      * 分页查询
      * @param search
      * @param pageNum
      * @param pageSize
      * @return
      */
     public ResultPageData<Form> pageForm(Form search,Integer pageNum, Integer pageSize);
     
     /**
      * 新增保单
      * @param formExtendDTO
      * @param templateId 模板Id
      * @return
      * @throws ApplicationException
      */
     public Form saveForm(FormExtendDTO formExtendDTO,Long templateId)throws ApplicationException;
     
     /**
      * 新增表单
      * @param formExtendDTO
      * @param templateContent 表单内容
      * @return
      * @throws ApplicationException
      */
     public Form saveForm(FormExtendDTO formExtendDTO,String templateContent)throws ApplicationException;
     
     /**
      * 删除表单
      * @param formId
      */
     public void deleteForm(Long formId);
     
     /**
      * 修改表单
      * @param formExtendDTO
      * @throws ApplicationException
      */
     public void modifyForm(FormExtendDTO formExtendDTO)throws ApplicationException;
     
     /**
      * 获取表单文件内容
      * @param formId
      * @return
      * @throws ApplicationException
      */
     public String getFormVueFileContext(Long formId)throws ApplicationException;
     
     /**
      * 获取表单域，表单字段信息
      * @param formId
      * @return
      */
     public FormExtendDTO getFormModelAndFieldInfo(Long formId);
}
