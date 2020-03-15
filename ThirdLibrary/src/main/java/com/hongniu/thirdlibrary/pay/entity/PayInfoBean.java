package com.hongniu.thirdlibrary.pay.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：  on 2020/3/7.
 * 支付相关的信息
 */
public class PayInfoBean implements Parcelable {

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
    private String tn;

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
    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.timeStamp);
        dest.writeString(this.nonceStr);
        dest.writeString(this.prepay_id);
        dest.writeString(this.signType);
        dest.writeString(this.paySign);
        dest.writeString(this.prePayId);
        dest.writeString(this.partnerId);
        dest.writeString(this.code);
        dest.writeString(this.msg);
        dest.writeString(this.orderInfo);
        dest.writeString(this.orderNum);
        dest.writeString(this.flowid);
    }

    public PayInfoBean() {
    }

    protected PayInfoBean(Parcel in) {
        this.timeStamp = in.readString();
        this.nonceStr = in.readString();
        this.prepay_id = in.readString();
        this.signType = in.readString();
        this.paySign = in.readString();
        this.prePayId = in.readString();
        this.partnerId = in.readString();
        this.code = in.readString();
        this.msg = in.readString();
        this.orderInfo = in.readString();
        this.orderNum = in.readString();
        this.flowid = in.readString();
    }

    public static final Creator<PayInfoBean> CREATOR = new Creator<PayInfoBean>() {
        @Override
        public PayInfoBean createFromParcel(Parcel source) {
            return new PayInfoBean(source);
        }

        @Override
        public PayInfoBean[] newArray(int size) {
            return new PayInfoBean[size];
        }
    };


}
