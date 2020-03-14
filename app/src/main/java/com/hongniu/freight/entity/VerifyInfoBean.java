package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/1.
 * 身份认证信息
 */
public class VerifyInfoBean {
   private String userId;//	true	string	用户id
   private VerifyIdNumIdentityBean idnumIdentity;//	false	json对象	身份证认证信息(为空则无数据)
   private VerifyRtpIdentityBean rtpIdentity;//	false	json对象	道路运输许可证认证信息(为空则无数据)
   private VerifyCompanyParams bLIdentity;//	false	json对象	公司营业执照认证信息(为空则无数据)
   private VerifyRtpIdentityBean aaIdentity;//	false	json对象	个人承运人挂靠协议认证信息(为空则无数据)
   private VerifyPersonParams qcIdentity;//	false	json对象	司机从业资格证认证信息(为空则无数据)
   private VerifyPersonParams dlIdentity;//	false	json对象	驾驶证认证信息(为空则无数据)

    public VerifyCompanyParams getbLIdentity() {
        return bLIdentity;
    }

    public void setbLIdentity(VerifyCompanyParams bLIdentity) {
        this.bLIdentity = bLIdentity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public VerifyIdNumIdentityBean getIdnumIdentity() {
        return idnumIdentity;
    }

    public void setIdnumIdentity(VerifyIdNumIdentityBean idnumIdentity) {
        this.idnumIdentity = idnumIdentity;
    }

    public VerifyRtpIdentityBean getRtpIdentity() {
        return rtpIdentity;
    }

    public void setRtpIdentity(VerifyRtpIdentityBean rtpIdentity) {
        this.rtpIdentity = rtpIdentity;
    }

    public VerifyRtpIdentityBean getAaIdentity() {
        return aaIdentity;
    }

    public void setAaIdentity(VerifyRtpIdentityBean aaIdentity) {
        this.aaIdentity = aaIdentity;
    }

    public VerifyPersonParams getQcIdentity() {
        return qcIdentity;
    }

    public void setQcIdentity(VerifyPersonParams qcIdentity) {
        this.qcIdentity = qcIdentity;
    }

    public VerifyPersonParams getDlIdentity() {
        return dlIdentity;
    }

    public void setDlIdentity(VerifyPersonParams dlIdentity) {
        this.dlIdentity = dlIdentity;
    }

}
