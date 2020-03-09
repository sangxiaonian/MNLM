package com.hongniu.freight.ui.holder.order;

import android.app.Activity;
import android.content.Context;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.NetObserver;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.ui.holder.order.helper.control.OrderButtonClickListener;
import com.hongniu.thirdlibrary.map.SingleLocation;
import com.hongniu.thirdlibrary.map.inter.OnLocationListener;
import com.hongniu.thirdlibrary.map.utils.MapConverUtils;

/**
 * 作者：  on 2020/2/8.
 */
public class XOrderButtonClick implements OrderButtonClickListener {
    private Context mContext;
    private TaskControl.OnTaskListener listener;
    private NextStepListener nextStepListener;


    public XOrderButtonClick(Context mContext) {
        this.mContext = mContext;
        if (mContext instanceof TaskControl.OnTaskListener) {
            listener = (TaskControl.OnTaskListener) mContext;
        }
        if (mContext instanceof NextStepListener) {
            nextStepListener = (NextStepListener) mContext;
        }
    }

    public void setNextStepListener(NextStepListener nextStepListener) {
        this.nextStepListener = nextStepListener;
    }

    public void seOnTaskListener(TaskControl.OnTaskListener listener) {
        this.listener = listener;
    }

    /**
     * 继续付款
     *
     * @param bean
     */
    @Override
    public void onPayClick(OrderInfoBean bean) {
        ArouterUtils.getInstance()
                .builder(ArouterParamApp.activity_pay)
                .withString(Param.TRAN, bean.getId())
                .navigation(mContext);
    }

    /**
     * 取消订单
     *
     * @param bean
     */
    @Override
    public void onOrderCancleClick(OrderInfoBean bean) {
//        ToastUtils.getInstance().show("取消订单");
        HttpAppFactory.orderCancel(bean.getId())
                .subscribe(new NetObserver<Object>(listener) {
                    @Override
                    public void doOnSuccess(Object o) {
                        super.doOnSuccess(o);
                        if (nextStepListener != null) {
                            nextStepListener.doUpdate();
                        }
                    }
                });
    }

    /**
     * 差额支付
     *
     * @param bean
     */
    @Override
    public void onPayBalanceClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("差额支付");
//TODO 差额支付
    }

    /**
     * 购买保险
     *
     * @param bean
     */
    @Override
    public void onPayInsuranceClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("购买保险");
//TODO 购买保险
    }

    /**
     * 查看保单
     *
     * @param bean
     */
    @Override
    public void onCheckInsuranceClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("查看保单");
//TODO 查看保单
    }

    /**
     * 查看轨迹
     *
     * @param bean
     */
    @Override
    public void onCheckTrackClick(OrderInfoBean bean) {
//        ToastUtils.getInstance().show("查看轨迹");
        ArouterUtils.getInstance().builder(ArouterParamApp.activity_map_check_path)
                .withParcelable(Param.TRAN, bean)
                .navigation((Activity) mContext, 1);
    }

    /**
     * 确认收货
     *
     * @param bean
     */
    @Override
    public void onEntryReceiveClick(OrderInfoBean bean) {
//        ToastUtils.getInstance().show("确认收货");
        HttpAppFactory.orderEntryReceive(bean.getId())
                .subscribe(new NetObserver<Object>(listener) {
                    @Override
                    public void doOnSuccess(Object o) {
                        super.doOnSuccess(o);
                        if (nextStepListener != null) {
                            nextStepListener.doUpdate();
                        }
                    }
                });
    }

    /**
     * 确认接单
     *
     * @param bean
     */
    @Override
    public void onEntryReceiveOrderClick(OrderInfoBean bean) {
//        ToastUtils.getInstance().show("确认接单");
        HttpAppFactory.orderReceivePlatform(bean.getId())
                .subscribe(new NetObserver<Object>(listener) {
                    @Override
                    public void doOnSuccess(Object o) {
                        super.doOnSuccess(o);
                        if (nextStepListener != null) {
                            nextStepListener.doUpdate();
                        }
                    }
                });
    }

    /**
     * 评价
     *
     * @param bean
     */
    @Override
    public void onEvaluateClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("评价");
        //TODO 评价
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
                .withString(Param.TRAN, bean.getId())
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
        ArouterUtils.getInstance()
                .builder(ArouterParamApp.activity_order_find_car)
                .withString(Param.TRAN, bean.getId())
                .navigation(mContext);

    }

    /**
     * 重新派单
     *
     * @param bean
     */
    @Override
    public void onReSendOrderClick(OrderInfoBean bean) {
//        ToastUtils.getInstance().show("重新派单");
        ArouterUtils.getInstance().builder(ArouterParamApp.activity_assign_order)
                .withString(Param.TRAN, bean.getId())
                .navigation(mContext);
    }

    /**
     * 我要接单
     *
     * @param bean
     */
    @Override
    public void onReceiveOrderClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("我要接单");
        //TODO 我要接单

    }

    /**
     * 开始发车
     *
     * @param bean
     */
    @Override
    public void onStartCarClick(OrderInfoBean bean) {
//        ToastUtils.getInstance().show("开始发车");

        SingleLocation location = new SingleLocation(mContext);
        location.setListener(new OnLocationListener() {
            @Override
            public void onStartLocation() {
                if (listener != null) {
                    listener.onTaskStart(null);
                }
            }

            @Override
            public void onSuccessLocation(double longitude, double latitude) {
                if (listener != null) {
                    listener.onTaskSuccess();
                }
                float distance = MapConverUtils.caculeDis(bean.getStartPlaceLat(), bean.getStartPlaceLon()
                        , latitude, longitude
                );
                if (distance > Param.ENTRY_MIN) {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("距离发货地点还有" + ConvertUtils.changeFloat(distance / 1000, 1) + "公里，无法开始发车");
                } else {
                    //开始发车
                    HttpAppFactory.orderStart(bean.getId())
                            .subscribe(new NetObserver<Object>(listener) {
                                @Override
                                public void doOnSuccess(Object o) {
                                    super.doOnSuccess(o);
                                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                                    if (nextStepListener != null) {
                                        nextStepListener.doUpdate();
                                    }
                                }
                            })
                    ;
                }

            }

            @Override
            public void onFailLocation(int errorCode, String errorInfo) {
                if (listener != null) {
                    listener.onTaskFail(new Exception(), errorCode, errorInfo);
                }
            }
        });
        location.startLoaction();


    }


    /**
     * 确认到达
     *
     * @param bean
     */
    @Override
    public void onEntryArriveClick(OrderInfoBean bean) {
        ToastUtils.getInstance().show("确认到达");
        SingleLocation location = new SingleLocation(mContext);
        location.setListener(new OnLocationListener() {
            @Override
            public void onStartLocation() {
                if (listener != null) {
                    listener.onTaskStart(null);
                }
            }

            @Override
            public void onSuccessLocation(double longitude, double latitude) {
                if (listener != null) {
                    listener.onTaskSuccess();
                }
                float distance = MapConverUtils.caculeDis(bean.getDestinationLat(), bean.getDestinationLon()
                        , latitude, longitude
                );
                if (distance > Param.ENTRY_MIN) {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("距离收货货地点还有" + ConvertUtils.changeFloat(distance / 1000, 1) + "公里，无法确认到达");
                } else {
                    //开始发车
                    HttpAppFactory.orderEnd(bean.getId())
                            .subscribe(new NetObserver<Object>(listener) {
                                @Override
                                public void doOnSuccess(Object o) {
                                    super.doOnSuccess(o);
                                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                                    if (nextStepListener != null) {
                                        nextStepListener.doUpdate();
                                    }
                                }
                            })
                    ;
                }

            }

            @Override
            public void onFailLocation(int errorCode, String errorInfo) {
                if (listener != null) {
                    listener.onTaskFail(new Exception(), errorCode, errorInfo);
                }
            }
        });
        location.startLoaction();

    }

    /**
     * 查看路线
     *
     * @param infoBean
     */
    @Override
    public void onQueryPathClick(OrderInfoBean infoBean) {
//        ToastUtils.getInstance().show("查看路线");
        Poi start = new Poi(infoBean.getStartPlaceInfo(), new LatLng(infoBean.getStartPlaceLat(), infoBean.getStartPlaceLon()), null);
        Poi end = new Poi(infoBean.getDestinationInfo(), new LatLng(infoBean.getDestinationLat(), infoBean.getDestinationLon()), null);

        AmapNaviParams amapNaviParams = new AmapNaviParams(start, null, end, AmapNaviType.DRIVER);
        amapNaviParams.setUseInnerVoice(true);

        AmapNaviPage.getInstance().showRouteActivity(mContext.getApplicationContext(),
                amapNaviParams, null);

    }

    public interface NextStepListener {
        /**
         * 再进行取消等操作完成之后,刷新界面
         */
        void doUpdate();
    }
}
