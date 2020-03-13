package com.hongniu.freight.mode;

import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.config.Status;
import com.hongniu.freight.control.OrderDetailControl;
import com.hongniu.freight.entity.AppInsuranceInfo;
import com.hongniu.freight.entity.H5Config;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.ui.holder.order.helper.OrderHelper;
import com.hongniu.freight.ui.holder.order.helper.control.HelperControl;

import java.util.Map;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/8.
 */
public class OrderDetailMode implements OrderDetailControl.IOrderDetailMode {
    private OrderInfoBean infoBean;
    private RoleOrder role;

    /**
     * 储存订单页面传入的数据
     *
     * @param infoBean
     * @param roler
     */
    @Override
    public void saveInfo(OrderInfoBean infoBean, RoleOrder roler) {
        this.infoBean = infoBean;
        if (roler==null){
            roler=RoleOrder.SHIPPER;
        }
        this.role=roler;

    }


    @Override
    public Status getStatus() {
        int status = infoBean == null ? 0 : infoBean.getStatus();
        Status result = Status.All;
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
        String[] result = new String[2];
        HelperControl helper=new OrderHelper(role)
                .setInsurance(infoBean.getInsurance()==1)
                .setStatus(getStatus().getStatus())
                ;
        Map<String, Integer> buttons = helper.getButtons(getStatus().getStatus());
        if (!CollectionUtils.isEmpty(buttons)){
            for (String s : buttons.keySet()) {
                Integer integer = buttons.get(s);
                if (integer !=null&&integer==0){
                    result[0]=s;
                }else {
                    result[1]=s;

                }
            }
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

    @Override
    public RoleOrder getRole() {
        return role;
    }

    /**
     * 设置是否显示司机信息
     *
     * @return true 显示
     */
    @Override
    public boolean isShowDriverInfo() {

        return
                getStatus()==Status.WAITE_DEPART_NO_INSURANCE
                || getStatus()==Status.IN_TRANSIT
                || getStatus()==Status.ARRIVE
                || getStatus()==Status.RECEIVING
                ;
    }

    /**
     * 设置是否显示车辆信息
     *
     * @return true 显示
     */
    @Override
    public boolean isShowCarInfo() {
      return   getStatus()==Status.WAITE_DEPART_NO_INSURANCE
                || getStatus()==Status.IN_TRANSIT
                || getStatus()==Status.ARRIVE
                || getStatus()==Status.RECEIVING
        ;
    }

    /**
     * 获取保单信息
     * @return
     */
    @Override
    public AppInsuranceInfo getInsurance() {
        AppInsuranceInfo orderCreatBean = null;
        try {
            orderCreatBean = new Gson().fromJson(infoBean.getPolicyInfo(), AppInsuranceInfo.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return orderCreatBean;
    }


}
