package com.hongniu.freight.control;

import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.companylibrary.entity.CommonBean;
import com.hongniu.freight.config.Status;
import com.hongniu.freight.entity.OrderInfoBean;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/8.
 */
public class OrderDetailControl {

    public interface IOrderDetailView{
        /**
         * 显示订单状态和编号
         * @param status
         * @param orderInfo
         */
        void showOrderState(String status, String orderInfo);

        /**
         * 显示收货发货地址信息
         * @param infoBean
         */
        void showOrderAddressInfo(OrderInfoBean infoBean);

        /**
         * 初始化司机信息
         * @param infoBean
         */
        void initDriverInfo(OrderInfoBean infoBean);

        /**
         * 显示订单详情数据
         * @param infoBean
         */
        void showOrderDetail(OrderInfoBean infoBean);

        /**
         * 显示车辆信息
         * @param infoBean
         */
        void showCarInfo(OrderInfoBean infoBean);

        /**
         * 更改底部按钮数据
         * @param infoBean
         */
        void showButton(String[] infoBean);

        /**
         * 拨打电话
         * @param mobile
         */
        void statCall(String mobile);
    }
    public interface IOrderDetailPresenter{
        /**
         * 初始化所有的数据
         * @param infoBean
         */
        void initInfo(OrderInfoBean infoBean);

        /**
         * 查询订单详情
         * @param listener
         */
        void queryDetail(TaskControl.OnTaskListener listener);

        /**
         * 联系发货人
         */
        void contactStart();

        /**
         * 联系收货人
         */
        void contactEnd();

        /**
         * 联系承运人
         */
        void contactOwner();
    }
    public interface IOrderDetailMode{
        /**
         * 储存订单页面传入的数据
         */
        void saveInfo(OrderInfoBean infoBean);

        /**
         *
         * @return 订单状态
         */
        Status getStatus();

        /**
         * 获取底部按钮文案
         * @return
         */
        String[] getButtonMsg();

        /**
         * 查询订单详情
         * @return
         */
        Observable<CommonBean<OrderInfoBean>> queryOrderDetail();

        /**
         * 获取订单信息
         * @return
         */
        OrderInfoBean getOrderInfo();
    }
}
