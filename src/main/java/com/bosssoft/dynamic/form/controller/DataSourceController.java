package com.bosssoft.dynamic.form.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.bosssoft.dynamic.form.domain.DataSourceInfo;
import com.bosssoft.dynamic.form.dto.DataSourceMetaDataDTO;
import com.bosssoft.dynamic.form.exception.ApplicationException;
import com.bosssoft.dynamic.form.model.DataSourceElement;
import com.bosssoft.dynamic.form.service.DataSourceService;
import com.bosssoft.dynamic.form.vo.DBConnectionTestResult;
import com.bosssoft.dynamic.form.vo.FieldMeta;
import com.bosssoft.dynamic.form.vo.ResultPageData;
import com.bosssoft.dynamic.form.vo.TableMeta;

/**
 * 数据源管理器
 * 
 * @author huangxw
 * @date 2020/01/08
 */
@RestController
@RequestMapping("/data-source")
public class DataSourceController {

    private static Logger log = LoggerFactory.getLogger(DataSourceController.class);

    @Autowired
    private DataSourceService dataSourceService;

    @GetMapping(value = "/{dataSourceInfoId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DataSourceInfo getDataSourceInfoById(@PathVariable(name = "dataSourceInfoId") Long dataSourceInfoId) {
        return dataSourceService.getDataSourceInfoById(dataSourceInfoId);
    }

    /**
     * 分页查询数据源信息
     * 
     * @param dataSourcePageDTO
     * @return
     */
    @PostMapping(value = "/page-query", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultPageData<DataSourceInfo> pageDataSource(@RequestBody(required = false) DataSourceInfo dataSourceInfo,
        @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize) {
        return dataSourceService.pageDataSourceInfo(dataSourceInfo, pageNum, pageSize);
    }

    /**
     * 新增数据源信息
     * 
     * @param dataSourceInfo
     * @return
     */
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DataSourceInfo saveDataSourceInfo(@RequestBody DataSourceInfo dataSourceInfo) {
        return dataSourceService.saveDataSourceInfo(dataSourceInfo);

    }

    @GetMapping(value = "/delete-verification/{dataSourceInfoId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean deleteVerif(@PathVariable(name = "dataSourceInfoId") Long dataSourceInfoId) {
        return !dataSourceService.existReferenceAboutDataSource(dataSourceInfoId);
    }

    /**
     * 删除单个数据源信息
     */
    @DeleteMapping(value = "/{dataSourceInfoId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Long deleteDataSourceInfo(@PathVariable(name = "dataSourceInfoId") Long dataSourceInfoId) {
        dataSourceService.deleteDataSourceInfoById(dataSourceInfoId);
        return dataSourceInfoId;
    }

    /**
     * 批量删除数据源信息
     * 
     * @return
     */
    @PostMapping(value = "/batch-delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Long> batchDeleteDataSourceInfo(@RequestBody List<Long> dataSourceInfoIds) {
        for (Long id : dataSourceInfoIds) {
            dataSourceService.deleteDataSourceInfoById(id);
        }
        return dataSourceInfoIds;
    }

    /**
     * 修改数据源信息
     * 
     * @param dataSourceInfo
     * @return
     */
    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DataSourceInfo modifyDataSourceInfo(@RequestBody DataSourceInfo dataSourceInfo) {
        return dataSourceService.modifyDataSourceInfo(dataSourceInfo);

    }

    /**
     * 获取支持的数据源类型信息
     * 
     * @return
     */
    @GetMapping(value = "/supported", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<DataSourceElement> getSupportDataSources() {
        return dataSourceService.getSupportDataSources();
    }

    /**
     * 数据源连接测试
     * 
     * @return
     */
    @PostMapping(value = "/connection-test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DBConnectionTestResult dataSourceConnectionTest(@RequestBody DataSourceInfo dataSourceInfo) {
        return dataSourceService.dataSourceConnectionTest(dataSourceInfo);
    }

    /**
     * 获取表元数据
     * 
     * @param dataSourceInfoId
     * @param dataSourceMetaDataDTO
     * @return
     * @throws ApplicationException
     */
    @PostMapping(value = "/{dataSourceInfoId}/tablemetas", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<TableMeta> getTableMetas(@PathVariable(name = "dataSourceInfoId") Long dataSourceInfoId,
        @RequestBody(required = false) DataSourceMetaDataDTO dataSourceMetaDataDTO) throws ApplicationException {
        return dataSourceService.getTableMetas(dataSourceInfoId, dataSourceMetaDataDTO);
    }

    /**
     * 获取表字段元数据
     * 
     * @param dataSourceInfoId
     * @param tableName
     * @param dataSourceMetaDataDTO
     * @return
     * @throws ApplicationException
     */
    @PostMapping(value = "/{dataSourceInfoId}/{tableName}/fieldmetas", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<FieldMeta> getFieldMetas(@PathVariable(name = "dataSourceInfoId") Long dataSourceInfoId,
        @PathVariable(name = "tableName") String tableName, @RequestBody(required = false) DataSourceMetaDataDTO dataSourceMetaDataDTO)
        throws ApplicationException {
        return dataSourceService.getFieldMetas(dataSourceInfoId, tableName, dataSourceMetaDataDTO);
    }

}
