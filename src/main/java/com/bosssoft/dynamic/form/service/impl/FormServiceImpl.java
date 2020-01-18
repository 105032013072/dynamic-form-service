package com.bosssoft.dynamic.form.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bosssoft.dynamic.form.domain.Form;
import com.bosssoft.dynamic.form.domain.FormField;
import com.bosssoft.dynamic.form.domain.FormModel;
import com.bosssoft.dynamic.form.domain.SystemConfig;
import com.bosssoft.dynamic.form.dto.FormExtendDTO;
import com.bosssoft.dynamic.form.dto.FormModelExtendDTO;
import com.bosssoft.dynamic.form.exception.ApplicationException;
import com.bosssoft.dynamic.form.exception.Error;
import com.bosssoft.dynamic.form.exception.ErrorType;
import com.bosssoft.dynamic.form.mapper.FormMapper;
import com.bosssoft.dynamic.form.model.BasicConstant;
import com.bosssoft.dynamic.form.model.SystemConfigConstant;
import com.bosssoft.dynamic.form.service.FormFieldService;
import com.bosssoft.dynamic.form.service.FormModelService;
import com.bosssoft.dynamic.form.service.FormService;
import com.bosssoft.dynamic.form.service.SystemConfigService;
import com.bosssoft.dynamic.form.spi.UserService;
import com.bosssoft.dynamic.form.util.DynamicFormUtil;
import com.bosssoft.dynamic.form.vo.BasicPageInfo;
import com.bosssoft.dynamic.form.vo.ResultPageData;
import com.bosssoft.platform.microservice.framework.common.data.Page;
import com.bosssoft.platform.microservice.framework.dal.pagination.PageHelper;

@Service
public class FormServiceImpl implements FormService {

    private static Logger log = LoggerFactory.getLogger(FormServiceImpl.class);

    @Autowired
    private FormMapper formMapper;

    @Autowired
    private FormModelService formModelService;

    @Autowired
    private FormFieldService formFieldService;
    
    @Autowired
    private SystemConfigService systemConfigService;
    
    @Autowired
    private UserService userService;

    @Override
    public Form getFormByFormId(Long formId) {

        return formMapper.selectByPrimaryKey(formId);
    }

    @Override
    public ResultPageData<Form> pageForm(Form search, Integer pageNum, Integer pageSize) {
        if(pageNum==null) pageNum=BasicPageInfo.DEFAULE_CURRENTPAGE;
        if(pageSize==null) pageSize=BasicPageInfo.DEFAULE_PAGESIZE;
        if(search==null) search=new Form();
        
        Page<Form> page = PageHelper.startPage(pageNum, pageSize);
        formMapper.selectFormByCondition(search);
        return new ResultPageData<>(page);
    }

    @Override
    public Form saveForm(FormExtendDTO formExtendDTO, Long templateFormId) throws ApplicationException {
        String templateContext = getTemplateContent(templateFormId);
        
        //获取模板表单的表单域信息
        if(templateFormId!=null){
            FormExtendDTO templateFormExtend=getFormModelAndFieldInfo(templateFormId);
            formExtendDTO.setFormModels(templateFormExtend.getFormModels());
        }
        
        return saveForm(formExtendDTO, templateContext);
    }

    /**
     * 获取模板文件内容
     * 
     * @param templateId
     * @return
     * @throws ApplicationException
     * @throws IOException
     */
    private String getTemplateContent(Long templateFormId) throws ApplicationException {
        String content = "";
        if (templateFormId == null) {
            // TODO 从空模板文件中读取
            StringBuffer buffer = new StringBuffer();
            buffer.append("<template></template>").append(System.lineSeparator());
            buffer.append("<style></style>").append(System.lineSeparator());
            buffer.append("<script></script>").append(System.lineSeparator());
            content = buffer.toString();
        } else {
            // 从表单模板文件中读取
            Form templateForm = getFormByFormId(templateFormId);
            try {
                content = FileUtils.readFileToString(new File(templateForm.getPath()), BasicConstant.ENCODING_UTF8);
            } catch (IOException e) {
                log.error(e.toString());
                throw new ApplicationException(new Error(ErrorType.BUSINESS_ERROR.getCode(), "读取表单模板文件内容失败"), e);
            }
        }
        return content;
    }

    @Override
    public Form saveForm(FormExtendDTO formExtendDTO, String templateContext) throws ApplicationException {
        // 生成vue文件
        if(formExtendDTO.getTemplate()==null){
            formExtendDTO.setTemplate(false);
        }
        File file = createVueFile(formExtendDTO, templateContext);

        // 保存表单基础数据
        formExtendDTO.setId(DynamicFormUtil.generateId());
        formExtendDTO.setCreationTime(DynamicFormUtil.getCurrentTimeStr());
        formExtendDTO.setCreationUser(userService.getCurrentUserId());
        formExtendDTO.setPath(file.getPath());
        formMapper.insertSelective(formExtendDTO);
        
        for (FormModelExtendDTO formModelExtendDTO : formExtendDTO.getFormModels()) {
            formModelExtendDTO.setFormId(formExtendDTO.getId());
            formModelService.saveFormModel(formModelExtendDTO);
        }
        return formExtendDTO;
    }

    private File createVueFile(FormExtendDTO formExtendDTO, String context) throws ApplicationException {
        String vueFilePath = getVueFilePath(formExtendDTO);
        File file = new File(vueFilePath);
        try {
            FileUtils.writeStringToFile(file, context, BasicConstant.ENCODING_UTF8, false);
        } catch (IOException e) {
            log.error(e.toString());
            throw new ApplicationException(new Error(ErrorType.BUSINESS_ERROR.getCode(), "创建表单文件失败"), e);
        }
        return file;

    }

    /**
     * 获取表单vue文件的路径
     * 
     * @param form
     * @return
     */
    private String getVueFilePath(Form form) {
        // 路径规则：系统配置表单存储路径\template|instance\分类路径\表单code.vue|表单code.mobile.vue

        SystemConfig formSaveRootPathConfig =
            systemConfigService.getSystemConfigByCode(SystemConfigConstant.CODE_FORM_PATH);
        StringBuffer pathBuffer = new StringBuffer(formSaveRootPathConfig.getValue()).append(File.separator);

        if (form.getTemplate()) {
            pathBuffer.append("template").append(File.separator);
        } else {
            pathBuffer.append("instance").append(File.separator);
        }

        pathBuffer.append(form.getCategory()).append(File.separator);
        pathBuffer.append(form.getFormCode());

        if (BasicConstant.FORM_TYPE_MOBILE.equals(form.getType())) {
            pathBuffer.append(".mobile.vue");
        } else if (BasicConstant.FORM_TYPE_PC.equals(form.getType())) {
            pathBuffer.append(".vue");
        }

        return pathBuffer.toString();

    }

    @Override
    @Transactional
    public void deleteForm(Long formId) {
        formModelService.deleteFormModelByFormId(formId);
        
        //获取表单文件路径
        String filePath= formMapper.selectByPrimaryKey(formId).getPath();
        
        // 删除表单
        formMapper.deleteByPrimaryKey(formId);
        
        //删除表单文件
        FileUtils.deleteQuietly(new File(filePath));
    }

    @Override
    @Transactional
    public void modifyForm(FormExtendDTO formExtendDTO) throws ApplicationException {
        // 先删除该表单下 旧的表单域数据
        formModelService.deleteFormModelByFormId(formExtendDTO.getId());

        // 新增表单域
        for (FormModelExtendDTO formModelExtend : formExtendDTO.getFormModels()) {
            formModelService.saveFormModel(formModelExtend);
        }
        
        
        //更新vue文件
        String filePath=formExtendDTO.getPath();
        if(StringUtils.isEmpty(filePath)){
            filePath=formMapper.selectByPrimaryKey(formExtendDTO.getId()).getPath();
        }
        try {
            FileUtils.writeStringToFile(new File(filePath), formExtendDTO.getFormContext(), BasicConstant.ENCODING_UTF8, false);
        } catch (IOException e) {
             log.error(e.toString());
             throw new ApplicationException(new Error(ErrorType.BUSINESS_ERROR.getCode(), "更新表单文件失败"), e);
        }
    }

    @Override
    public String getFormVueFileContext(Long formId) throws ApplicationException {
        Form form=formMapper.selectByPrimaryKey(formId);
        String context="";
        try {
            context= FileUtils.readFileToString(new File(form.getPath()), BasicConstant.ENCODING_UTF8);
        } catch (IOException e) {
            log.error(e.toString());
            throw new ApplicationException(new Error(ErrorType.BUSINESS_ERROR.getCode(), "读取表单文件失败"), e);
        }
         return context;
    }

    @Override
    public FormExtendDTO getFormModelAndFieldInfo(Long formId) {
        FormExtendDTO formExtendDTO=new FormExtendDTO(formMapper.selectByPrimaryKey(formId));
        
        //获取表单下的表单域
        List<FormModel> formModels=formModelService.getFormModelByFormId(formId);
        List<FormModelExtendDTO> formModelExtendDTOs=new ArrayList<>();
       
        for (FormModel formModel : formModels) {
            //获取表单域下的表单子段
            List<FormField> formFields=formFieldService.getFormFieldsByModelId(formModel.getId());
            FormModelExtendDTO formModelExtendDTO=new FormModelExtendDTO(formModel);
            formModelExtendDTO.setFields(formFields);
            formModelExtendDTOs.add(formModelExtendDTO);
        } 
        formExtendDTO.setFormModels(formModelExtendDTOs);
        
        return formExtendDTO;
    }

}
