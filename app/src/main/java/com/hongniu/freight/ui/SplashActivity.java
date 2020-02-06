package com.hongniu.freight.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;

public class SplashActivity extends CompanyBaseActivity {

    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ArouterUtils.getInstance().builder(ArouterParamApp.activity_main)
                    .navigation(mContext);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessageDelayed(0,500);
        setWhitToolBar("");
    }
}
