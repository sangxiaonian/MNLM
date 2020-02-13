package com.hongniu.freight.ui.holder.order.helper;

import com.hongniu.freight.config.OrderButtonConfig;
import com.hongniu.freight.config.Status;
import com.hongniu.freight.ui.holder.order.helper.control.HelperControl;

import java.util.Map;
import java.util.TreeMap;

/**
 * 作者：  on 2020/2/8.
 * 托运人数据，托运人有平台和个人两种昂区别
 */
public class TYRHelper implements HelperControl {

    private int type;//0个人 1平台

    public TYRHelper(int type) {
        this.type = type;
    }



    @Override
    public String getStatus(int status) {
        return OrderUtils.getStatus(status);
    }
    /**
     * 根据状态，获取到指定荒唐的按钮
     *
     * @param status
     * @return
     */
    @Override
    public Map<String, Integer> getButtons(int status) {
        Map<String, Integer> map = type==0?getPersonalMap(status):getPlatformMap(status);
        return map;
    }

    private Map<String, Integer> getPersonalMap(int status) {
        Map<String,Integer> map=new TreeMap<>();
        if (status == Status.WAITE_PAY.getStatus()) {
//            result = "待支付";
            map.put(OrderButtonConfig.ORDER_CANCEL,0);
            map.put(OrderButtonConfig.PAY,1);
        } else if (status == Status.WAITE_RECEIVING_ORDER.getStatus()) {
//            result = "待接单";
            //不显示订单
            map.put(OrderButtonConfig.ORDER_CANCEL,0);
        } else if (status == Status.WAITE_PAY_BALANCE.getStatus()) {
//            result = "差额支付中";
            map.put(OrderButtonConfig.PAY_BALANCE,1);
        } else if (status == Status.WAITE_CAR.getStatus()) {
//            result = "待派车";
            //不显示订单
        } else if (status == Status.FIND_CAR.getStatus()) {
//            result = "找车中";
        } else if (status == Status.WAITE_DEPART_NO_INSURANCE.getStatus()) {
//            result = "待发车(未买保险)";
            map.put(OrderButtonConfig.PAY_INSURANCE,1);
        } else if (status == Status.WAITE_DEPART_INSURANCE.getStatus()) {
//            result = "待发车(已买保险)";
            map.put(OrderButtonConfig.CHECK_INSURANCE,0);

        } else if (status == Status.IN_TRANSIT.getStatus()) {
//            result = "运输中";
            map.put(OrderButtonConfig.CHECK_INSURANCE,0);
            map.put(OrderButtonConfig.QUERY_TRACK,1);
        } else if (status == Status.ARRIVE.getStatus()) {
//            result = "已到达";
//            不显示订单
            map.put(OrderButtonConfig.CHECK_INSURANCE,0);
            map.put(OrderButtonConfig.ENTRY_RECEIVE,1);
        } else if (status == Status.RECEIVING.getStatus()) {
//            result = "已收货";
            map.put(OrderButtonConfig.CHECK_INSURANCE,0);
            map.put(OrderButtonConfig.EVALUATE,1);
        } else {
//            result = "未知状态";
        }
        return map;
    }
    private Map<String, Integer> getPlatformMap(int status) {
        Map<String,Integer> map=new TreeMap<>();
        if (status == Status.WAITE_PAY.getStatus()) {
//            result = "待支付";
            map.put(OrderButtonConfig.ORDER_CANCEL,0);
            map.put(OrderButtonConfig.PAY,1);
        } else if (status == Status.WAITE_RECEIVING_ORDER.getStatus()) {
//            result = "待接单";
            //不显示订单

        } else if (status == Status.WAITE_PAY_BALANCE.getStatus()) {
//            result = "差额支付中";
        } else if (status == Status.WAITE_CAR.getStatus()) {
//            result = "待派车";
            map.put(OrderButtonConfig.SEND_ORDER,1);
            map.put(OrderButtonConfig.FIND_CAR,0);
        } else if (status == Status.FIND_CAR.getStatus()) {
//            result = "找车中";
            map.put(OrderButtonConfig.SEND_ORDER,1);
        } else if (status == Status.WAITE_DEPART_NO_INSURANCE.getStatus()) {
//            result = "待发车(未买保险)";
            map.put(OrderButtonConfig.PAY_INSURANCE,1);
            map.put(OrderButtonConfig.RE_SEND_CARD,1);
        } else if (status == Status.WAITE_DEPART_INSURANCE.getStatus()) {
//            result = "待发车(已买保险)";
            map.put(OrderButtonConfig.CHECK_INSURANCE,0);

        } else if (status == Status.IN_TRANSIT.getStatus()) {
//            result = "运输中";
            map.put(OrderButtonConfig.CHECK_INSURANCE,0);
            map.put(OrderButtonConfig.QUERY_TRACK,1);
        } else if (status == Status.ARRIVE.getStatus()) {
//            result = "已到达";
//            不显示订单
            map.put(OrderButtonConfig.CHECK_INSURANCE,0);
            map.put(OrderButtonConfig.ENTRY_RECEIVE,1);
        } else if (status == Status.RECEIVING.getStatus()) {
//            result = "已收货";
            map.put(OrderButtonConfig.CHECK_INSURANCE,0);
            map.put(OrderButtonConfig.EVALUATE,1);
        } else {
//            result = "未知状态";
        }
        return map;
    }
}
