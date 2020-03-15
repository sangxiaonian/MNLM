package com.hongniu.thirdlibrary.pay.unionpay;

import android.app.Activity;

import com.hongniu.thirdlibrary.pay.entity.PayInfoBean;
import com.unionpay.UPPayAssistEx;

/**
 * 作者： ${PING} on 2018/9/25.
 */
public class UnionPayClient {


    private boolean isDebug;

    public void pay(Activity activity, PayInfoBean bean) {
        String serverMode;
        if (isDebug) {
            serverMode = "01";//测试环境
        } else {
            serverMode = "00";//正式环境
        }
        UPPayAssistEx.startPay(activity, null, null, bean.getTn(), serverMode);
    }


}
