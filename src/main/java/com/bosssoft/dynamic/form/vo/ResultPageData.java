package com.bosssoft.dynamic.form.vo;

import java.util.Collection;
import java.util.List;

import com.bosssoft.platform.microservice.framework.common.data.Page;

/**
 * 分页查询，包含list和pageInfo信息
 */
public class ResultPageData<T> extends BasicPageInfo implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4472971178808408400L;

    public ResultPageData() {}

    /*public ResultPageData(List<T> list, BasicPageInfo pageInfo) {
        this.list = list;
        super.setPageSize(pageInfo.getPageSize());
        super.setCurrentPage(pageInfo.getCurrentPage());
        super.setTotalRecord(pageInfo.getTotalRecord());
    }*/

    public ResultPageData(Page<T> page) {
        this.list = page.getResult();
        super.setPageSize(page.getPageSize());
        super.setCurrentPage(page.getPageNum());
        super.setTotalRecord(page.getTotal());
    }

    public ResultPageData(List<T> list) {
        if (list instanceof Page) {
            Page<T> page = (Page<T>)list;
            super.setPageSize(page.getPageSize());
            super.setTotalRecord(page.getTotal());
            super.setCurrentPage(page.getPageNum());
            this.list = page;
        } else if (list instanceof Collection) {
            super.setTotalRecord((long)list.size());
            super.setPageSize(list.size());
            super.setCurrentPage(1);
            this.list = list;
        }
    }

    /**
     * 分页数据
     */
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
