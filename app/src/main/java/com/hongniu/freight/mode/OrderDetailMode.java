package com.hongniu.freight.mode;

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
        this.infoBean=infoBean;
    }
}
