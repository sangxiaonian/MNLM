package com.hongniu.freight.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.BuildConfig;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.manager.ThirdManager;
import com.hongniu.freight.utils.InfoUtils;
import com.hongniu.freight.widget.dialog.RuleAlertDialog;
import com.hongniu.thirdlibrary.manager.PrivacyManger;

public class SplashActivity extends CompanyBaseActivity implements RuleAlertDialog.IDialog {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            LoginInfo loginInfo = InfoUtils.getLoginInfo();
            PersonInfor myInfo = InfoUtils.getMyInfo();
            if (loginInfo != null) {
                ArouterUtils.getInstance().builder(ArouterParamApp.activity_main)
                        .navigation(mContext);
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
        setWhitToolBar("");
        boolean rule = PrivacyManger.INSTANCE.isAgreePrivacy();
        if (rule) {
            handler.sendEmptyMessageDelayed(0, 500);

        } else {
            new RuleAlertDialog(this, this).show();
        }
    }

    @Override
    public void onClickReportAlert(boolean isPositive) {
        PrivacyManger.INSTANCE.setAgreePrivacy(true);
        ThirdManager.INSTANCE.init(this, BuildConfig.DEBUG);
        handler.sendEmptyMessage(0);
    }
}
