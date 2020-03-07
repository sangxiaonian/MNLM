package com.hongniu.freight.ui.holder.order;

import android.content.Context;

import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.ui.holder.order.helper.control.OrderButtonClickListener;

/**
 * 作者：  on 2020/2/8.
 */
public class XOrderButtonClcik implements OrderButtonClickListener {
    private Context mContext;

    public XOrderButtonClcik(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 继续付款
     *
     * @param bean
     */
    @Override
    public void onPayClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("继续付款");
        ArouterUtils.getInstance()
                .builder(ArouterParamApp.activity_pay)
                .withString(Param.TRAN,bean.getId())
                .navigation(mContext);
    }

    /**
     * 取消订单
     *
     * @param bean
     */
    @Override
    public void onOrderCancleClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("取消订单");

    }

    /**
     * 差额支付
     *
     * @param bean
     */
    @Override
    public void onPayBalanceClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("差额支付");

    }

    /**
     * 购买保险
     *
     * @param bean
     */
    @Override
    public void onPayInsuranceClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("购买保险");

    }

    /**
     * 查看保单
     *
     * @param bean
     */
    @Override
    public void onCheckInsuranceClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("查看保单");

    }

    /**
     * 查看轨迹
     *
     * @param bean
     */
    @Override
    public void onCheckTrackClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("查看轨迹");

    }

    /**
     * 确认收货
     *
     * @param bean
     */
    @Override
    public void onEntryReceiveClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("确认收货");

    }

    /**
     * 确认接单
     *
     * @param bean
     */
    @Override
    public void onEntryReceiveOrderClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("确认接单");

    }

    /**
     * 评价
     *
     * @param bean
     */
    @Override
    public void onEvaluateClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("评价");

    }

    /**
     * 立即派单
     *
     * @param bean
     */
    @Override
    public void onSendOrderClick(OrderInfoBean bean) {
//        ToastUtils.getInstance().show("立即派单");
        ArouterUtils.getInstance().builder(ArouterParamApp.activity_assign_order)
                .navigation(mContext);
    }

    /**
     * 发布找车
     *
     * @param bean
     */
    @Override
    public void onFindCarClick(OrderInfoBean bean) {
//        ToastUtils.getInstance().show("发布找车");
        ArouterUtils.getInstance().builder(ArouterParamApp.activity_find_car)
                .navigation(mContext);

    }

    /**
     * 重新派单
     *
     * @param bean
     */
    @Override
    public void onReSendOrderClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("重新派单");

    }

    /**
     * 我要接单
     *
     * @param bean
     */
    @Override
    public void onReceiveOrderClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("我要接单");

    }

    /**
     * 开始发车
     *
     * @param bean
     */
    @Override
    public void onStartCarClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("开始发车");

    }

    /**
     * 确认到达
     *
     * @param bean
     */
    @Override
    public void onEntryArriveClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("确认到达");

    }

    /**
     * 查看路线
     *
     * @param bean
     */
    @Override
    public void onQueryPathClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("查看路线");

    }
}
