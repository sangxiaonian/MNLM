package com.hongniu.freight.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.utils.InfoUtils;

public class SplashActivity extends CompanyBaseActivity {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            LoginInfo loginInfo = InfoUtils.getLoginInfo();
            PersonInfor myInfo = InfoUtils.getMyInfo();
            if (loginInfo != null) {
                if (InfoUtils.getRole(loginInfo) == Role.UNKNOW && InfoUtils.getRole(myInfo) == Role.UNKNOW) {
                    ArouterUtils.getInstance().builder(ArouterParamApp.activity_attestation_select_role)
                            .navigation(mContext);
                } else if (loginInfo.getIsRealname() != 1 &&(myInfo != null && myInfo.getIsRealname() != 1)) {
                    ArouterUtils.getInstance().builder(ArouterParamApp.activity_attestation_face)
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
