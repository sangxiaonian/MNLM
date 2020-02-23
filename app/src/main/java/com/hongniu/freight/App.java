package com.hongniu.freight;

import com.fy.androidlibrary.net.OkHttp;
import com.fy.baselibrary.BaseApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.CompanyClient;
import com.hongniu.freight.net.interceptor.HeardInterceptor;

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
        CompanyClient.getInstance()
                .addInterceptor(new HeardInterceptor(this))
                .addInterceptor(OkHttp.getLogInterceptor())//添加log日志
                .setBaseUrl(Param.baseUrl);
    }
}
