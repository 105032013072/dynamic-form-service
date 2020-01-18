package com.bosssoft.dynamic.form.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bosssoft.dynamic.form.domain.FormField;
import com.bosssoft.dynamic.form.domain.FormModel;
import com.bosssoft.dynamic.form.dto.FormModelExtendDTO;
import com.bosssoft.dynamic.form.mapper.FormModelMapper;
import com.bosssoft.dynamic.form.service.FormFieldService;
import com.bosssoft.dynamic.form.service.FormModelService;
import com.bosssoft.dynamic.form.spi.UserService;
import com.bosssoft.dynamic.form.util.DynamicFormUtil;

@Service
public class FormModelServiceImpl implements FormModelService {

    @Autowired
    private FormModelMapper formModelMapper;
    
    @Autowired
    private FormFieldService  formFieldService;
    
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void deleteFormModel(Long formModelId) {
        //删除表单字段
        formFieldService.deleteFormFieldByModelId(formModelId);

        // 删除表单域
        formModelMapper.deleteByPrimaryKey(formModelId);

    }

    @Override
    public void deleteFormModelByFormId(Long formId) {
        // 获取表单下的表单域
        List<FormModel> formModels = getFormModelByFormId(formId);

        for (FormModel formModel : formModels) {
            deleteFormModel(formModel.getId());
        }

    }

    @Override
    public List<FormModel> getFormModelByFormId(Long formId) {
        FormModel record = new FormModel();
        record.setFormId(formId);
        List<FormModel> formModels = formModelMapper.select(record);
        return formModels;
    }

    @Override
    public void saveFormModel(FormModelExtendDTO formModelExtendDTO) {
        /*if (formModelExtendDTO.getId() == null) {
            formModelExtendDTO.setId(DynamicFormUtil.generateId());
            formModelExtendDTO.setCreationTime(DynamicFormUtil.getCurrentTimeStr());
            formModelExtendDTO.setCreationUser(userService.getCurrentUserId());
        } else {
            formModelExtendDTO.setModifyTime(DynamicFormUtil.getCurrentTimeStr());
            formModelExtendDTO.setModifyUser(userService.getCurrentUserId());
        }*/
        formModelExtendDTO.setId(DynamicFormUtil.generateId());
        formModelExtendDTO.setCreationTime(DynamicFormUtil.getCurrentTimeStr());
        formModelExtendDTO.setCreationUser(userService.getCurrentUserId());
        formModelMapper.insertSelective(formModelExtendDTO);

        // 保存表单字段
        for (FormField formField : formModelExtendDTO.getFields()) {
            formField.setFormModelId(formModelExtendDTO.getId());
            formFieldService.saveFormField(formField);
        }

    }

}
