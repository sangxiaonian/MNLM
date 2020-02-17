package com.fy.baselibrary;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.fy.androidlibrary.net.rx.RxUtils;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.JLog;
import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.fy.baselibrary.refrush.RecycleFooter;
import com.fy.baselibrary.refrush.RecycleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * 作者：  on 2019/10/28.
 */
public class BaseApp extends MultiDexApplication {

    public static BaseApp app;

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new RecycleHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                RecycleFooter recycleFooter = new RecycleFooter(context);
                return recycleFooter;
            }
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesUtils.getInstance().initSharePreference(this);
        ToastUtils.getInstance().init(this);
        app = this;
//        Density.setDensity(this,375);
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            JLog.i("----------打印日志---------");
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        RxUtils.setRxJavaErrorHandler();
    }
}
