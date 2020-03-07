package com.hongniu.thirdlibrary.pay.ali;

import android.app.Activity;

import com.alipay.sdk.app.PayTask;
import com.fy.androidlibrary.net.rx.RxUtils;
import com.fy.androidlibrary.toast.ToastUtils;
import com.hongniu.thirdlibrary.pay.entity.PayInfoBean;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 作者： ${PING} on 2018/10/17.
 */
public class AliPay {

    private boolean isDebug;

    public void pay(final Activity activity, PayInfoBean bean) {
        Observable.just(bean)
                .map(new Function<PayInfoBean, Map<String, String>>() {
                    @Override
                    public Map<String, String> apply(PayInfoBean bean) throws Exception {
                        PayTask alipay = new PayTask(activity);
                        Map<String, String> result = alipay.payV2(bean.getOrderInfo(), true);
                        return result;
                    }
                })
                .compose(RxUtils.<Map<String, String>>getSchedulersObservableTransformer())
                .subscribe(new Observer<Map<String, String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Map<String, String> stringStringMap) {

                        // 判断resultStatus 为9000则代表支付成功
                        if (stringStringMap != null && "9000".equals(stringStringMap.get("resultStatus"))) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            ToastUtils.getInstance().show("支付成功");
                        } else {
                            ToastUtils.getInstance().show("支付失败");
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtils.getInstance().show("支付发生异常");
                    }

                    @Override
                    public void onComplete() {

                    }
                })
        ;
    }


}
