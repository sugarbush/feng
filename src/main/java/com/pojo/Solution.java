package com.pojo;

/**
 * @Author: feng
 * @Date: 2019/9/18 14:27
 */
public class Solution {
    private int sId;//主键
    private String title;//主标题
    private String subtitle;//副标题
    private String titlePicture;//标题图片
    private String titleContent;//标题内容
    private String pubDate;//发布时间
    private String createDate;//创建时间
    private String createBy;//创建人
    private String updateDate;//修改时间
    private String updateBy;//修改人
    private String sremark;//备注
    private String fileName;//文件上传路径
    private String titFileName;//标题图片真实名称
    private String titFileNameSf;//服务器上存储的名称
    private String titFilePath;//标题图片存储路径

    public Solution() {
    }

    public Solution(int sId, String title, String subtitle, String titlePicture, String titleContent, String pubDate, String createDate, String createBy, String updateDate, String updateBy, String sremark, String fileName, String titFileName, String titFilePath, String titFileNameSf) {
        this.sId = sId;
        this.title = title;
        this.subtitle = subtitle;
        this.titlePicture = titlePicture;
        this.titleContent = titleContent;
        this.pubDate = pubDate;
        this.createDate = createDate;
        this.createBy = createBy;
        this.updateDate = updateDate;
        this.updateBy = updateBy;
        this.sremark = sremark;
        this.fileName = fileName;
        this.titFileName = titFileName;
        this.titFilePath = titFilePath;
        this.titFileNameSf = titFileNameSf;
    }

    public int getSId() {
        return sId;
    }

    public void setSId(int sId) {
        this.sId = sId;
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

    public String getTitleContent() {
        return titleContent;
    }

    public void setTitleContent(String titleContent) {
        this.titleContent = titleContent;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public String getTitFileName() {
        return titFileName;
    }

    public void setTitFileName(String titFileName) {
        this.titFileName = titFileName;
    }

    public String getTitFilePath() {
        return titFilePath;
    }

    public void setTitFilePath(String titFilePath) {
        this.titFilePath = titFilePath;
    }

    public String getTitFileNameSf() {
        return titFileNameSf;
    }

    public void setTitFileNameSf(String titFileNameSf) {
        this.titFileNameSf = titFileNameSf;
    }
}
