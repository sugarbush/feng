package com.pojo;

/**
 * @Author: feng
 * @Date: 2019/9/18 14:27
 */
public class Sort {
    private int tId;//主键
    private String sortName;//分类名称
    private String sortSynopsis;//分类简介
    private String dStatus ;//状态
    private String sOrder;//顺序
    private String pubDate;//发布时间
    private String createDate;//创建时间
    private String createBy;//创建人
    private String updateDate;//修改时间
    private String updateBy;//修改人
    private String sremark;//备注

    public Sort() {
    }

    public Sort(int tId, String sortName, String sortSynopsis, String dStatus, String sOrder, String pubDate, String createDate, String createBy, String updateDate, String updateBy, String sremark) {
        this.tId = tId;
        this.sortName = sortName;
        this.sortSynopsis = sortSynopsis;
        this.dStatus = dStatus;
        this.sOrder = sOrder;
        this.pubDate = pubDate;
        this.createDate = createDate;
        this.createBy = createBy;
        this.updateDate = updateDate;
        this.updateBy = updateBy;
        this.sremark = sremark;
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortSynopsis() {
        return sortSynopsis;
    }

    public void setSortSynopsis(String sortSynopsis) {
        this.sortSynopsis = sortSynopsis;
    }

    public String getdStatus() {
        return dStatus;
    }

    public void setdStatus(String dStatus) {
        this.dStatus = dStatus;
    }

    public String getsOrder() {
        return sOrder;
    }

    public void setsOrder(String sOrder) {
        this.sOrder = sOrder;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getSremark() {
        return sremark;
    }

    public void setSremark(String sremark) {
        this.sremark = sremark;
    }
}
