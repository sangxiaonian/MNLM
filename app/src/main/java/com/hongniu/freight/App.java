package com.hongniu.freight;

import com.fy.androidlibrary.imgload.ImageLoader;
import com.fy.androidlibrary.net.OkHttp;
import com.fy.baselibrary.BaseApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.CompanyClient;
import com.hongniu.freight.net.interceptor.HeardInterceptor;
import com.hongniu.freight.net.interceptor.LoginOutRespondInterceptor;
import com.hongniu.thirdlibrary.chact.ChactHelper;
import com.hongniu.thirdlibrary.map.SingleLocation;
import com.hongniu.thirdlibrary.verify.VerifyClient;

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

        ImageLoader.getLoader().headErrorImg(R.mipmap.icon_default_avatar_100);
        ImageLoader.getLoader().headPlaceholder(R.mipmap.icon_default_avatar_100);

        CompanyClient.getInstance()
                .addInterceptor(new HeardInterceptor(this))
                .addInterceptor(new LoginOutRespondInterceptor(this))
                .addInterceptor(OkHttp.getLogInterceptor())//添加log日志
                .setBaseUrl(Param.baseUrl);

        // 初始化实人认证 SDK
        VerifyClient.getInstance().initClient(this);

        //融云
        ChactHelper.getHelper().initHelper(this);
    }
}
