package com.bosssoft.dynamic.form.controller;

import org.apache.commons.lang.StringUtils;
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

import com.bosssoft.dynamic.form.domain.Form;
import com.bosssoft.dynamic.form.dto.FormExtendDTO;
import com.bosssoft.dynamic.form.exception.ApplicationException;
import com.bosssoft.dynamic.form.service.FormService;
import com.bosssoft.dynamic.form.vo.ResultPageData;

/**
 * 表单管理器
 * 
 * @author huangxw
 * @date 2020/01/09
 */
@RestController
@RequestMapping("/form")
public class FormController {

    @Autowired
    private FormService formService;

    @GetMapping(value="/test",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String test(){
        return "test";
    }
    
    /**
     * 分页查询
     * 
     * @param formPageDTO
     * @return
     */
    @PostMapping(value = "/page-query", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultPageData<Form> pageForm(@RequestBody(required = false) Form form,@RequestParam(required = false) Integer pageNum,@RequestParam(required = false) Integer pageSize) {
        
        return formService.pageForm(form, pageNum, pageSize);
    }

    /**
     * 新增表单
     * 
     * @param formExtendDTO
     * @return
     * @throws ApplicationException
     */
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Form saveForm(@RequestBody FormExtendDTO formExtendDTO) throws ApplicationException {
        if (StringUtils.isNotEmpty(formExtendDTO.getFormContext())) {
            return formService.saveForm(formExtendDTO, formExtendDTO.getFormContext());
        } else {
            return formService.saveForm(formExtendDTO, formExtendDTO.getTemplateFormId());
        }
    }

    /**
     * 删除表单
     * 
     * @return
     */
    @DeleteMapping(value = "/{formId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Long deleteForm(@PathVariable(value = "formId") Long formId) {
        formService.deleteForm(formId);
        return formId;
    }

    /**
     * 修改表单
     * 
     * @return
     * @throws ApplicationException
     */
    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Form modifyForm(@RequestBody FormExtendDTO formExtendDTO) throws ApplicationException {
        formService.modifyForm(formExtendDTO);
        return formExtendDTO;
    }
    
    /**
     * 获取表单文件内容
     * @param formId
     * @return
     * @throws ApplicationException 
     */
    @GetMapping(value="/{formId}/file-context",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getFormVueFileContext(@PathVariable(value = "formId") Long formId) throws ApplicationException{
        return formService.getFormVueFileContext(formId);
    }
    
    @GetMapping(value="/{formId}/model-field",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public FormExtendDTO getFormModelAndFieldInfo(@PathVariable(value = "formId") Long formId){
        return formService.getFormModelAndFieldInfo(formId);
    }
}
