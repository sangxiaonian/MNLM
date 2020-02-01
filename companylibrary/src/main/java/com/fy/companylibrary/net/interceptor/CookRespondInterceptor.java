package com.fy.companylibrary.net.interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.fy.androidlibrary.utils.JLog;
import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.fy.companylibrary.config.Param;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 作者：  on 2019/10/30.
 *
 * 根据返回的数据，保存请求头中的cook
 */
public class CookRespondInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());
//        String cook = originalResponse.header("Set-Cookie");
//        if (!TextUtils.isEmpty(cook)){
//            SharedPreferencesUtils.getInstance().putString(Param.Cookie,cook.split(";")[0]);
//        }
        return originalResponse;

    }
}
