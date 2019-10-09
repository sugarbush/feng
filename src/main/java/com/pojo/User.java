package com.pojo;

/**
 * 用户信息管理
 * @author fengs
 */
public class User {
    private int uId; //主键
    private String uName; //真实名
    private String loginName; //登录名
    private String uPassword; //登录密码
    private String paperType; //证件类型
    private String paperCode; //证件号码
    private String uSex; //性别
    private String uRemark; //备注

    public User() {
    }

    public User(int uId, String uName, String loginName, String uPassword, String paperType, String paperCode, String uSex, String uRemark) {
        this.uId = uId;
        this.uName = uName;
        this.loginName = loginName;
        this.uPassword = uPassword;
        this.paperType = paperType;
        this.paperCode = paperCode;
        this.uSex = uSex;
        this.uRemark = uRemark;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    public String getuSex() {
        return uSex;
    }

    public void setuSex(String uSex) {
        this.uSex = uSex;
    }

    public String getuRemark() {
        return uRemark;
    }

    public void setuRemark(String uRemark) {
        this.uRemark = uRemark;
    }

}
