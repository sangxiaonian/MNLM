package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/2/23.
 */
public class LoginInfo {

    private long id;//true	用户id
    private String mobile;//true	用户手机号
    private String openid;//false	微信的openid
    private String token;//false	本次登录的token，有有效期。
    private int type;//false	用户类型。无实际作用。
    private int state;//false	状态
    private String logo;//false	微信登录时用户图像的地址
    private String contact;//false	用户的联系方式
    private String nickname;//false	用户的微信昵称
    private int gender;//false	性别,1男,2女,0未知
    private String remark;//false	备注
    private String rongToken;//true	string	融云token
    private String logoPath;//true	string	绝对路径用于显示

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRongToken() {
        return rongToken;
    }

    public void setRongToken(String rongToken) {
        this.rongToken = rongToken;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
}
