package com.hongniu.freight.config;

/**
 * 作者：  on 2020/2/8.
 * 订单状态数据
 */
public enum Status {

    WAITE_PAY(0,"待支付"),
    WAITE_RECEIVING_ORDER(1,"待接单"),
    WAITE_PAY_BALANCE(2,"差额支付中"),
    WAITE_CAR(3,"待派车"),
    FIND_CAR(4,"找车中"),
    WAITE_DEPART_NO_INSURANCE(5,"待发车(未买保险)"),
    WAITE_DEPART_INSURANCE(6,"待发车(已买保险)"),
    IN_TRANSIT(7,"运输中"),
    ARRIVE(8,"已到达"),
    RECEIVING(9,"已收货"),
    ;

    int status;
    String name;

    Status(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
