package com.hongniu.freight;

import android.app.Application;

import com.fy.baselibrary.BaseApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.CompanyClient;

/**
 * 作者：  on 2020/2/5.
 */
public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG){
            Param.baseUrl=Param.debugUrl;
        }else {
            Param.baseUrl=Param.releaseUrl;

        }
        CompanyClient.getInstance().setBaseUrl(Param.baseUrl);
    }
}
