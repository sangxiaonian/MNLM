package com.fy.companylibrary.net.interceptor;

import android.text.TextUtils;

import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.fy.companylibrary.config.Param;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：  on 2019/10/30.
 * 添加请求头
 */
public class CookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
//        String cookies = SharedPreferencesUtils.getInstance().getString(Param.Cookie);
//        if (!TextUtils.isEmpty(cookies)) {
//            requestBuilder.addHeader(Param.Cookie,cookies);
//        }
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }


}
