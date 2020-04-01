package com.hongniu.freight.presenter;

import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.NetObserver;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.control.OrderDetailControl;
import com.hongniu.freight.entity.AppInsuranceInfo;
import com.hongniu.freight.entity.H5Config;
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
     * @param roler
     */
    @Override
    public void initInfo(OrderInfoBean infoBean, RoleOrder roler) {
        mode.saveInfo(infoBean,roler);
        view.initClick(roler);
        view.showOrderState(mode.getStatusDes() ,String.format("订单编号\t %s",infoBean.getOrderNum()));
        view.showOrderAddressInfo(infoBean);
        view.initDriverInfo(infoBean,mode.isShowDriverInfo());
        view.showOrderDetail(infoBean,mode.isShowCargoPrice(),mode.isShowRealePrice());
        view.showCarInfo(infoBean,mode.isShowCarInfo());
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
                    initInfo(infoBean, mode.getRole());

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
    public void contactCarrier() {
        view.startChat(mode.getOrderInfo().getOwnerId(),mode.getOrderInfo().getOwnerName());
    }

    /**
     * 联系司机
     */
    @Override
    public void contactDriver() {
        view.startChat(mode.getOrderInfo().getDriverId(),mode.getOrderInfo().getDriverName());
    }

    /**
     * 点击按钮
     * @param i
     */
    @Override
    public void clickButton(int i) {
        String[] buttonMsg = mode.getButtonMsg();
        view.clickButton(buttonMsg[i],mode.getOrderInfo());
    }

    /**
     * 查看保单
     */
    @Override
    public void checkInsurance() {
        AppInsuranceInfo insurance = mode.getInsurance();
        if (insurance!=null) {
            view.checkInsurance(insurance);

        }else {
            view.showError("保单信息异常,请稍后再试");
        }
    }

    /**
     * 和发货人聊天
     */
    @Override
    public void chatStart() {
        //TODO 聊天,缺少ID

    }


}
