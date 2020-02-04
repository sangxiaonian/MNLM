package com.hongniu.freight.presenter;

import com.hongniu.freight.control.OrderDetailControl;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.mode.OrderDetailMode;

/**
 * 作者：  on 2020/2/8.
 */
public class OrderDetailPresenter implements OrderDetailControl.IOrderDetailPresenter {
    OrderDetailControl.IOrderDetailView view;
    OrderDetailControl.IOrderDetailMode mode;

    public OrderDetailPresenter(OrderDetailControl.IOrderDetailView view) {
        this.view = view;
        mode=new OrderDetailMode();
    }

    /**
     * 初始化所有的数据
     *
     * @param infoBean
     */
    @Override
    public void initInfo(OrderInfoBean infoBean) {
        mode.saveInfo(infoBean);
        view.showOrderState(infoBean);
        view.showOrderAddressInfo(infoBean);
        view.initDriverInfo(infoBean);
        view.showOrderDetail(infoBean);
        view.showCarInfo(infoBean);
        view.showButton(infoBean);
    }
}
