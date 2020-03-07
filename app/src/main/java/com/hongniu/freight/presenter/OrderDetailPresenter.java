package com.hongniu.freight.presenter;

import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.companylibrary.net.NetObserver;
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
        view.showOrderState(mode.getStatus().getName(),String.format("订单编号\t %s",infoBean.getOrderNum()));
        view.showOrderAddressInfo(infoBean);
        view.initDriverInfo(infoBean);
        view.showOrderDetail(infoBean);
        view.showCarInfo(infoBean);
        view.showButton(mode.getButtonMsg());
    }

    /**
     * 查询订单详情
     * @param listener
     */
    @Override
    public void queryDetail(TaskControl.OnTaskListener listener) {
        mode.queryOrderDetail()
            .subscribe(new NetObserver<OrderInfoBean>(listener){
                @Override
                public void doOnSuccess(OrderInfoBean infoBean) {
                    super.doOnSuccess(infoBean);
                    initInfo(infoBean);

                }
            })
        ;
    }

    /**
     * 联系发货人
     */
    @Override
    public void contactStart() {
        view.statCall(mode.getOrderInfo().getShipperMobile());
    }

    /**
     * 联系收货人
     */
    @Override
    public void contactEnd() {
        view.statCall(mode.getOrderInfo().getReceiverMobile());
    }

    /**
     * 联系承运人
     */
    @Override
    public void contactOwner() {
        view.statCall(mode.getOrderInfo().getOwnerMobile());
    }
}
