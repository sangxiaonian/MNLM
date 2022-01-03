package com.hongniu.freight;

import android.content.Context;

import com.fy.androidlibrary.imgload.ImageLoader;
import com.fy.androidlibrary.net.OkHttp;
import com.fy.androidlibrary.utils.JLog;
import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.fy.baselibrary.BaseApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.CompanyClient;
import com.google.gson.Gson;
import com.hongniu.freight.entity.UmenToken;
import com.hongniu.freight.manager.ThirdManager;
import com.hongniu.freight.net.interceptor.HeardInterceptor;
import com.hongniu.freight.net.interceptor.LoginOutRespondInterceptor;
import com.hongniu.freight.receiver.MyPushReceiver;
import com.hongniu.thirdlibrary.chact.ChactHelper;
import com.hongniu.freight.huoyun.FreightClient;
import com.hongniu.thirdlibrary.pay.wechat.WeChatAppPay;
import com.hongniu.thirdlibrary.push.NotificationUtils;
import com.hongniu.thirdlibrary.push.client.PushClient;
import com.hongniu.thirdlibrary.push.client.PushUmeng;
import com.hongniu.thirdlibrary.push.inter.PlushDealWithMessageListener;
import com.hongniu.thirdlibrary.push.inter.PlushRegisterListener;
import com.hongniu.thirdlibrary.verify.VerifyClient;
import com.umeng.message.entity.UMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * 作者：  on 2020/2/5.
 */
public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Param.baseUrl = Param.debugUrl;
        } else {
            Param.baseUrl = Param.releaseUrl;
        }
        Param.baseUrl = Param.releaseUrl;
        ImageLoader.getLoader().headErrorImg(R.mipmap.icon_default_avatar_100);
        ImageLoader.getLoader().headPlaceholder(R.mipmap.icon_default_avatar_100);
        ImageLoader.getLoader().globalPlaceholder(R.drawable.ovl_5_f7f7f7);
        ImageLoader.getLoader().globalErrorImg(R.drawable.ovl_5_f7f7f7);

        CompanyClient.getInstance()
                .addInterceptor(new HeardInterceptor(this))
                .addInterceptor(new LoginOutRespondInterceptor(this))
                .addInterceptor(OkHttp.getLogInterceptor())//添加log日志
                .setBaseUrl(Param.baseUrl);

        ThirdManager.INSTANCE.init(this, BuildConfig.DEBUG);
        //保活
        FreightClient.getClient().startKeepLive(this,getString(R.string.app_name),"正在使用",R.mipmap.ic_launcher);


    }

}
