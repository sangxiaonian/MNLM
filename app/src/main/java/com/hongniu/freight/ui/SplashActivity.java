package com.hongniu.freight.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.utils.InfoUtils;

public class SplashActivity extends CompanyBaseActivity {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (InfoUtils.getLoginInfo() != null) {
                int role = InfoUtils.getRole(InfoUtils.getLoginInfo());
                if (role == -1 && InfoUtils.getRole(InfoUtils.getMyInfo()) == -1) {
                    ArouterUtils.getInstance().builder(ArouterParamApp.activity_attestation_select_role)
                            .navigation(mContext);
                } else {
                    ArouterUtils.getInstance().builder(ArouterParamApp.activity_main)
                            .navigation(mContext);
                }

            } else {
                ArouterUtils.getInstance().builder(ArouterParamApp.activity_login)
                        .navigation(mContext);
            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessageDelayed(0, 500);
        setWhitToolBar("");
    }
}
