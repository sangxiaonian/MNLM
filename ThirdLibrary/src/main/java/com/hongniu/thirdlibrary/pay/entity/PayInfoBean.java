package com.hongniu.thirdlibrary.pay.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：  on 2020/3/7.
 * 支付相关的信息
 */
public class PayInfoBean {

    //------------------------微信支付相关数据---------------------------//
    private String timeStamp;//	true	string	时间
    private String nonceStr;//	true	string	随机数
    @SerializedName(value = "package", alternate = {"prepay_id"})
    private String prepay_id;//	true	string	prepay_id=XXXXXXX
    private String signType;//	true	string	加签类型
    private String paySign;//	true	string	签名
    private String prePayId;//	true	string	prepay_id (APP 支付时有值)
    private String partnerId;//	true	string	商户号 (APP 支付时有值)

    //------------------------支付宝支付相关数据---------------------------//
    private String code;//	true	string	支付宝返回code 00 成功 其他失败
    private String msg;//	true	string	支付宝返回msg
    private String orderInfo;//	true	string	支付宝返回的orderInfo
    private String orderNum;//	true	string	订单号

    //------------------------密码支付---------------------------//
    private String flowid;//	true	string	流水号flowid

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public String getSignType() {
        return signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public String getPrePayId() {
        return prePayId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public String getFlowid() {
        return flowid;
    }
}
