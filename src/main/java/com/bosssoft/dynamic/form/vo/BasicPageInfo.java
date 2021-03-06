package com.bosssoft.dynamic.form.vo;

public class BasicPageInfo {
    /**
     * 默认当前页
     */
    public static final Integer DEFAULE_CURRENTPAGE = 1;
    /**
     * 默认页大小
     */
    public static final Integer DEFAULE_PAGESIZE = 10;

    /**
     * 当前页
     */
    private Integer currentPage = DEFAULE_CURRENTPAGE;
    /**
     * 每页大小
     */
    private Integer pageSize = DEFAULE_PAGESIZE;
    /**
     * 总条数
     */
    private Long totalRecord;

    public BasicPageInfo() {}

    public BasicPageInfo(Integer currentPage, Integer pageSize) {
        super();
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public BasicPageInfo(Integer currentPage, Integer pageSize, Long totalRecord) {
        super();
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage <= 0 ? DEFAULE_CURRENTPAGE : currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize <= 0 ? DEFAULE_PAGESIZE : pageSize;
    }

    public Long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Long totalRecord) {
        this.totalRecord = totalRecord;
    }
}
