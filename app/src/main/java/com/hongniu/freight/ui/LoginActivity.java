package com.hongniu.freight.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.widget.editext.SearchTextWatcher;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.InfoUtils;
import com.hongniu.freight.utils.Utils;

import io.reactivex.disposables.Disposable;

/**
 * @data 2020/2/23
 * @Author PING
 * @Description 登录页面
 */
@Route(path = ArouterParamApp.activity_login)
public class LoginActivity extends CompanyBaseActivity implements View.OnClickListener {
    private final static int time = 60;
    private boolean isRetry;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (currentTime > 1) {
                currentTime--;
                tv_sms.setText(currentTime + "秒后再试");
                handler.sendEmptyMessageDelayed(0, 1000);
            } else {
                setTvSmsEnable(true);
            }
        }
    };
    private int currentTime;
    private EditText et_phone;
    private EditText et_sms;
    private TextView tv_sms;
    private TextView bt_sum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setWhitToolBar("");
        initView();
        initData();
        initListener();
        setTvSmsEnable(false);
        check(false);
    }

    @Override
    protected void initView() {
        super.initView();
        et_phone = findViewById(R.id.et_phone);
        et_sms = findViewById(R.id.et_sms);
        tv_sms = findViewById(R.id.tv_sms);
        bt_sum = findViewById(R.id.bt_sum);
    }

    @Override
    protected void initListener() {
        super.initListener();
        et_phone.addTextChangedListener(new SearchTextWatcher(new SearchTextWatcher.SearchTextChangeListener() {
            @Override
            public void onSearchTextChange(String msg) {
                if (msg.length() == 11) {
                    setTvSmsEnable(true);
                }
                check(false);
            }
        }));
        et_sms.addTextChangedListener(new SearchTextWatcher(new SearchTextWatcher.SearchTextChangeListener() {
            @Override
            public void onSearchTextChange(String msg) {
                check(false);
            }
        }));
        bt_sum.setOnClickListener(this);
    }

    private boolean check(boolean showAlert) {
        Utils.setButton(bt_sum, false);
        String phone = et_phone.getText().toString().trim();
        String sms = et_sms.getText().toString().trim();
        if (phone.length() < 11) {
            if (showAlert) {
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.getInstance().show("请输入手机号");
                } else {
                    ToastUtils.getInstance().show("请输入正确的手机号");

                }
            }
            return false;
        }
        if (TextUtils.isEmpty(sms)) {
            if (showAlert) {
                ToastUtils.getInstance().show(et_sms.getHint().toString());
            }
            return false;
        }

        Utils.setButton(bt_sum, true);

        return true;
    }


    private void setTvSmsEnable(boolean enable) {
        enable = enable && et_phone.getText().toString().trim().length() == 11;
        tv_sms.setOnClickListener(enable ? this : null);
        tv_sms.setTextColor(getResources().getColor(enable ? R.color.color_of_040000 : R.color.color_of_999999));
        tv_sms.setText(isRetry ? "再次获取验证码" : "获取验证码");
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_sms) {
            currentTime = time;
            setTvSmsEnable(false);
            isRetry = true;
            handler.sendEmptyMessage(0);
            String phone = et_phone.getText().toString().trim();
            HttpAppFactory.getSms(phone)
                    .subscribe(new NetObserver<String>(this) {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }
                    });
        } else if (v.getId() == R.id.bt_sum) {
            if (check(true)) {

                HttpAppFactory.login(et_phone.getText().toString().trim(),
                        et_sms.getText().toString().trim()
                )
                        .subscribe(new NetObserver<LoginInfo>(this) {
                            @Override
                            public void doOnSuccess(LoginInfo loginInfo) {
                                super.doOnSuccess(loginInfo);
                                int role = InfoUtils.getState(loginInfo);
                                if (role==0){
                                    //尚未认证
                                    ArouterUtils.getInstance().builder(ArouterParamApp.activity_attestation_select_role)
                                            .navigation(mContext);
                                }else if (loginInfo.getIsRealname()==0){
                                    ArouterUtils.getInstance().builder(ArouterParamApp.activity_attestation_face)
                                            .navigation(mContext);
                                }else {
                                    //已有认证角色
                                    ArouterUtils.getInstance().builder(ArouterParamApp.activity_main)
                                            .navigation(mContext);
                                }

                                finish();
                            }
                        });
            }
        }
    }
}
