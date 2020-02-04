package com.hongniu.freight.control;

import com.hongniu.freight.entity.OrderInfoBean;

/**
 * 作者：  on 2020/2/8.
 */
public class OrderDetailControl {

    public interface IOrderDetailView{
        /**
         * 显示订单状态和编号
         * @param infoBean
         */
        void showOrderState(OrderInfoBean infoBean);

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
        void showButton(OrderInfoBean infoBean);
    }
    public interface IOrderDetailPresenter{
        /**
         * 初始化所有的数据
         * @param infoBean
         */
        void initInfo(OrderInfoBean infoBean);
    }
    public interface IOrderDetailMode{
        /**
         * 储存订单页面传入的数据
         */
        void saveInfo(OrderInfoBean infoBean);
    }
}
