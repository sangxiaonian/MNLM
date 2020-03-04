package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/4.
 */
public class OrderNumberInfoBean {
   private int  userOrderNum;//	true	number	托运人订单数量
   private int  ownerOrderNum;//	true	number	承运人订单数量
   private int  driverOrderNum;//	true	number	司机订单数量
   private int  driverTransOrderNum;//	true	number	司机正在运输订单数量

    public int getUserOrderNum() {
        return userOrderNum;
    }

    public void setUserOrderNum(int userOrderNum) {
        this.userOrderNum = userOrderNum;
    }

    public int getOwnerOrderNum() {
        return ownerOrderNum;
    }

    public void setOwnerOrderNum(int ownerOrderNum) {
        this.ownerOrderNum = ownerOrderNum;
    }

    public int getDriverOrderNum() {
        return driverOrderNum;
    }

    public void setDriverOrderNum(int driverOrderNum) {
        this.driverOrderNum = driverOrderNum;
    }

    public int getDriverTransOrderNum() {
        return driverTransOrderNum;
    }

    public void setDriverTransOrderNum(int driverTransOrderNum) {
        this.driverTransOrderNum = driverTransOrderNum;
    }
}
