package com.hongniu.freight.mode;

import com.hongniu.freight.config.OrderButtonConfig;
import com.hongniu.freight.config.Status;
import com.hongniu.freight.control.OrderDetailControl;
import com.hongniu.freight.entity.OrderInfoBean;

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
    public int getStatus() {
        return infoBean == null ? 0 : infoBean.getStatus();
    }

    /**
     * 获取底部按钮文案
     * @return
     */
    @Override
    public String[] getButtonMsg() {
        int status = getStatus();
        String[] result = new String[2];
        if (status == Status.WAITE_PAY.getStatus()) {//待支付
            result[0] = OrderButtonConfig.ORDER_CANCEL;//取消订单
            result[1] = OrderButtonConfig.PAY;//继续支付
        } else if (status == Status.WAITE_DEPART_NO_INSURANCE.getStatus()) {//待发车
            result[1] = OrderButtonConfig.PAY_INSURANCE;//购买保险
        }else if (status == Status.WAITE_RECEIVING_ORDER.getStatus()) {//待接单
            result[0] = OrderButtonConfig.ORDER_CANCEL;//取消订单
        } else if (status == Status.WAITE_PAY_BALANCE.getStatus()) {//待支付差额
            result[1] = OrderButtonConfig.PAY_BALANCE;//待支付差额
        } else if (status == Status.FIND_CAR.getStatus()) {//找车中
        } else if (status == Status.IN_TRANSIT.getStatus()) {//运输中
            result[1] = OrderButtonConfig.QUERY_TRACK;//查看轨迹
        }

        return result;
    }


}
