package com.hongniu.freight.mode;

import com.fy.companylibrary.entity.CommonBean;
import com.hongniu.freight.config.OrderButtonConfig;
import com.hongniu.freight.config.Status;
import com.hongniu.freight.control.OrderDetailControl;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.net.HttpAppFactory;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/8.
 */
public class OrderDetailMode implements OrderDetailControl.IOrderDetailMode {
    private OrderInfoBean infoBean;

    /**
     * 储存订单页面传入的数据
     *
     * @param infoBean
     */
    @Override
    public void saveInfo(OrderInfoBean infoBean) {
        this.infoBean = infoBean;
    }


    @Override
    public Status getStatus() {
        int status = infoBean == null ? 0 : infoBean.getStatus();
        Status result = Status.UNKNOW;
        for (Status value : Status.values()) {
            if (value.getStatus()==status){
                result=value;
            }
        }
        return result;
    }

    /**
     * 获取底部按钮文案
     * @return
     */
    @Override
    public String[] getButtonMsg() {
        Status status = getStatus();
        String[] result = new String[2];
        if (status == Status.WAITE_PAY ) {//待支付
            result[0] = OrderButtonConfig.ORDER_CANCEL;//取消订单
            result[1] = OrderButtonConfig.PAY;//继续支付
        } else if (status == Status.WAITE_DEPART_NO_INSURANCE) {//待发车
            result[1] = OrderButtonConfig.PAY_INSURANCE;//购买保险
        }else if (status == Status.WAITE_RECEIVING_ORDER) {//待接单
            result[0] = OrderButtonConfig.ORDER_CANCEL;//取消订单
        } else if (status == Status.WAITE_PAY_BALANCE) {//待支付差额
            result[1] = OrderButtonConfig.PAY_BALANCE;//待支付差额
        } else if (status == Status.FIND_CAR) {//找车中
        } else if (status == Status.IN_TRANSIT) {//运输中
            result[1] = OrderButtonConfig.QUERY_TRACK;//查看轨迹
        }
        return result;
    }

    /**
     * 查询订单详情
     * @return
     */
    @Override
    public Observable<CommonBean<OrderInfoBean>> queryOrderDetail() {
       return HttpAppFactory.queryOrderDetail(infoBean.getId());
    }

    /**
     * 获取订单信息
     *
     * @return
     */
    @Override
    public OrderInfoBean getOrderInfo() {
        return infoBean;
    }


}
