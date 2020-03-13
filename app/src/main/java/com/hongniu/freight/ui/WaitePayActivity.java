package com.hongniu.freight.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.event.BusFactory;
import com.fy.androidlibrary.event.IBus;
import com.fy.androidlibrary.imgload.ImageLoader;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.config.PayType;
import com.hongniu.freight.entity.OrderStatusBean;
import com.hongniu.freight.entity.QueryPayInfoParams;
import com.hongniu.freight.net.HttpAppFactory;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @data 2018/10/29
 * @Author PING
 * @Description 支付界面
 */
@Route(path = ArouterParamApp.activity_waite_pay)
public class WaitePayActivity extends CompanyBaseActivity {

    private ImageView img;
    private Subscription sub;
    QueryPayInfoParams payInfoParams;//支付信息
    private PayType payType;//当前支付类型
    private int type;//付款类型  	支付业务类型(1订单支付2补款运费支付3补购保险支付)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waite_pay);
        setWhitToolBar("等待付款");
        initView();
        initData();
        query();
    }

    @Override
    protected void initView() {
        super.initView();
        img = findViewById(R.id.img);
        ImageLoader.getLoader().load(this, img, R.raw.loading);
    }

    @Override
    protected void initData() {
        super.initData();

        payInfoParams = getIntent().getParcelableExtra(Param.TRAN);
        if (payInfoParams == null) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("未获取到支付信息");
            finish();
            return;
        }
        type=payInfoParams.getPaybusiness();

        for (PayType value : PayType.values()) {
            if (value.getPayType() == payInfoParams.getPayType()) {
                payType = value;
                break;
            }
        }


//      开始支付
        startPay();
    }

    private void startPay() {
        switch (payType) {
            case WEICHAT://微信支付
                ToastUtils.getInstance().show("微信支付");
                break;
            case UNIONPAY://银联支付
                ToastUtils.getInstance().show("银联支付");
                break;
            case ALIPAY://支付宝支付
                ToastUtils.getInstance().show("支付包支付");
                break;

            case BALANCE://余额支付
            case COMPANY://企业支付
                break;
            default:
        }
    }


//    //此处为接收回调的结果,由于改为查询，因此此处仅仅接受支付失败或者取消的情况
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(PayResult event) {
//        if (event.code != PayResult.SUCCESS) {
//            Intent mIntent = new Intent();
//            mIntent.putExtra("payResult", event.code);
//            mIntent.putExtra("payResultDes", event.ms);
//            // 设置结果，并进行传送
//            setResult(Activity.RESULT_OK, mIntent);
//            finish();
//        }
//
//    }
//
//    //此处为接收成功的情况
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(PaySucess event) {
//        img.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent mIntent = new Intent();
//                mIntent.putExtra("payResult", PayResult.SUCCESS);
//                mIntent.putExtra("payResultDes", "");
//                // 设置结果，并进行传送
//                setResult(Activity.RESULT_OK, mIntent);
//                finish();
//            }
//        }, 3000);
//
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusFactory.getBus().unregister(this);
        if (sub != null) {
            sub.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (disposable != null) {
            disposable.dispose();
        }
        if (sub != null) {
            sub.cancel();
        }
    }


    public void query() {
        Flowable.range(0, 100)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        SystemClock.sleep(3000);
                        return integer;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        sub = s;
                        s.request(1);
                    }

                    @Override
                    public void onNext(Integer aLong) {

                        queryOrder();

                    }


                    @Override
                    public void onError(Throwable t) {
                        if (sub != null) {
                            sub.request(1);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    /**
     * 查询订单
     */
    private void queryOrder() {
        HttpAppFactory.queryOrderStatus(payInfoParams.getOrderId())

                .subscribe(new NetObserver<OrderStatusBean>(null) {

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        disposable = d;
                    }

                    @Override
                    public void doOnSuccess(OrderStatusBean data) {
                        boolean success=false;
                        if (type==1){
                            //运费支付
                          success=data.getFreightStatus() == 1;
                        }else if (type==2){
                            //补差额
                            success=data.getBalanceFreightStatus()==1;
                        }else if (type==3){
                            success=data.getPayPolicyState()==1;
                        }
                        if (success){
                            //订单支付成功
                            // 设置结果，并进行传送
                            setResult(Activity.RESULT_OK);
                            finish();
                        }else {
                            sub.request(1);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (sub != null) {
                            sub.request(1);
                        }
                    }
                });
    }


    public static class PaySucess implements IBus.IEvent {
    }

}
