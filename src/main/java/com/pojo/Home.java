package com.pojo;

/**
 * 首页信息维护
 * @Author: feng
 * @Date: 2019/9/25 9:56
 */
public class Home {
    private int hId;//主键
    private String title;//主标题
    private String subtitle;//副标题
    private String titlePicture;//标题图片
    private String hurl;//跳转地址
    private String hType;//类型
    private String hStatus;//状态
    private String chineseEnglish;//所属中英文
    private String pubDate;//发布时间
    private String createDate;//创建时间
    private String createBy;//创建人
    private String updateDate;//修改时间
    private String updateBy;//修改人
    private String sremark;//备注
    private String titFileName;//标题图片真实名称
    private String titFileNameSf;//服务器上存储的名称
    private String titFilePath;//标题图片存储路径

    public Home() {
    }

    public Home(int hId, String title, String subtitle, String titlePicture, String hurl, String hType, String hStatus, String chineseEnglish, String pubDate, String createDate, String createBy, String updateDate, String updateBy, String sremark, String titFileName, String titFileNameSf, String titFilePath) {
        this.hId = hId;
        this.title = title;
        this.subtitle = subtitle;
        this.titlePicture = titlePicture;
        this.hurl = hurl;
        this.hType = hType;
        this.hStatus = hStatus;
        this.chineseEnglish = chineseEnglish;
        this.pubDate = pubDate;
        this.createDate = createDate;
        this.createBy = createBy;
        this.updateDate = updateDate;
        this.updateBy = updateBy;
        this.sremark = sremark;
        this.titFileName = titFileName;
        this.titFileNameSf = titFileNameSf;
        this.titFilePath = titFilePath;
    }

    public int gethId() {
        return hId;
    }

    public void sethId(int hId) {
        this.hId = hId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitlePicture() {
        return titlePicture;
    }

    public void setTitlePicture(String titlePicture) {
        this.titlePicture = titlePicture;
    }

    public String getHurl() {
        return hurl;
    }

    public void setHurl(String hurl) {
        this.hurl = hurl;
    }

    public String gethType() {
        return hType;
    }

    public void sethType(String hType) {
        this.hType = hType;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public String getChineseEnglish() {
        return chineseEnglish;
    }

    public void setChineseEnglish(String chineseEnglish) {
        this.chineseEnglish = chineseEnglish;
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

    public String getTitFileName() {
        return titFileName;
    }

    public void setTitFileName(String titFileName) {
        this.titFileName = titFileName;
    }

    public String getTitFileNameSf() {
        return titFileNameSf;
    }

    public void setTitFileNameSf(String titFileNameSf) {
        this.titFileNameSf = titFileNameSf;
    }

    public String getTitFilePath() {
        return titFilePath;
    }

    public void setTitFilePath(String titFilePath) {
        this.titFilePath = titFilePath;
    }
}
