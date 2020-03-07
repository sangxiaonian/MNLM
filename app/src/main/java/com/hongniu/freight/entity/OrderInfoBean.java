package com.hongniu.freight.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：  on 2020/2/5.
 * 订单详情/列表数据
 */
public class OrderInfoBean implements Parcelable {

    private int status;
    private String id;//	true	number	订单id
    private String orderNum;//	true	number	订单号
    private String createTime;//	true	string	下单时间
    private String startPlaceInfo;//	true	string	发货地
    private String destinationInfo;//	true	string	收货地

    private String shipperName;//	true	string	发货人姓名
    private String shipperMobile;//	true	string	发货人手机号码
    private String receiverName;//	true	string	收货人姓名
    private String receiverMobile;//	true	string	收货人手机号码
    private String departureTime;//	false	string	发货时间 null表示立即发货
    private String goodName;//	true	string	货物名称
    private String goodVolume;//	true	number	货物体积(立方)
    private String goodWeight;//	true	number	货物重量(千克)
    private double money;//	true	number	运费
    private int insurance;//	true	number	是否购买保险 1表示是 0表示否
    private double policyMoney;//	false	number	保险支付金额
    private String insureUsername;//	false	string	被保险人
    private String insureIdnumber;//	false	string	被保险人证件号码
    private String carNum;//	false	string	车牌号码
    private String ownerName;//	false	stringstring	承运人姓名
    private String ownerMobile;//	false		承运人手机


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStartPlaceInfo() {
        return startPlaceInfo;
    }

    public void setStartPlaceInfo(String startPlaceInfo) {
        this.startPlaceInfo = startPlaceInfo;
    }

    public String getDestinationInfo() {
        return destinationInfo;
    }

    public void setDestinationInfo(String destinationInfo) {
        this.destinationInfo = destinationInfo;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getShipperMobile() {
        return shipperMobile;
    }

    public void setShipperMobile(String shipperMobile) {
        this.shipperMobile = shipperMobile;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodVolume() {
        return goodVolume;
    }

    public void setGoodVolume(String goodVolume) {
        this.goodVolume = goodVolume;
    }

    public String getGoodWeight() {
        return goodWeight;
    }

    public void setGoodWeight(String goodWeight) {
        this.goodWeight = goodWeight;
    }


    public int getInsurance() {
        return insurance;
    }

    public void setInsurance(int insurance) {
        this.insurance = insurance;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getPolicyMoney() {
        return policyMoney;
    }

    public void setPolicyMoney(double policyMoney) {
        this.policyMoney = policyMoney;
    }

    public String getInsureUsername() {
        return insureUsername;
    }

    public void setInsureUsername(String insureUsername) {
        this.insureUsername = insureUsername;
    }

    public String getInsureIdnumber() {
        return insureIdnumber;
    }

    public void setInsureIdnumber(String insureIdnumber) {
        this.insureIdnumber = insureIdnumber;
    }

    public String getCarNum() {
        return carNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerMobile() {
        return ownerMobile;
    }

    public void setOwnerMobile(String ownerMobile) {
        this.ownerMobile = ownerMobile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public OrderInfoBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.id);
        dest.writeString(this.createTime);
        dest.writeString(this.startPlaceInfo);
        dest.writeString(this.destinationInfo);
        dest.writeString(this.shipperName);
        dest.writeString(this.shipperMobile);
        dest.writeString(this.receiverName);
        dest.writeString(this.receiverMobile);
        dest.writeString(this.departureTime);
        dest.writeString(this.goodName);
        dest.writeString(this.goodVolume);
        dest.writeString(this.goodWeight);
        dest.writeDouble(this.money);
        dest.writeInt(this.insurance);
        dest.writeDouble(this.policyMoney);
        dest.writeString(this.insureUsername);
        dest.writeString(this.insureIdnumber);
        dest.writeString(this.carNum);
        dest.writeString(this.ownerName);
        dest.writeString(this.ownerMobile);
    }

    protected OrderInfoBean(Parcel in) {
        this.status = in.readInt();
        this.id = in.readString();
        this.createTime = in.readString();
        this.startPlaceInfo = in.readString();
        this.destinationInfo = in.readString();
        this.shipperName = in.readString();
        this.shipperMobile = in.readString();
        this.receiverName = in.readString();
        this.receiverMobile = in.readString();
        this.departureTime = in.readString();
        this.goodName = in.readString();
        this.goodVolume = in.readString();
        this.goodWeight = in.readString();
        this.money = in.readDouble();
        this.insurance = in.readInt();
        this.policyMoney = in.readDouble();
        this.insureUsername = in.readString();
        this.insureIdnumber = in.readString();
        this.carNum = in.readString();
        this.ownerName = in.readString();
        this.ownerMobile = in.readString();
    }

    public static final Creator<OrderInfoBean> CREATOR = new Creator<OrderInfoBean>() {
        @Override
        public OrderInfoBean createFromParcel(Parcel source) {
            return new OrderInfoBean(source);
        }

        @Override
        public OrderInfoBean[] newArray(int size) {
            return new OrderInfoBean[size];
        }
    };
}
