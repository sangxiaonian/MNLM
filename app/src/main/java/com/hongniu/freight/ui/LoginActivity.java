package com.hongniu.freight.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.net.error.NetException;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.widget.editext.SearchTextWatcher;
import com.fy.androidlibrary.widget.span.XClickableSpan;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.H5Config;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.freight.entity.UmenToken;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
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
    private boolean isSmsLogin=true;//true 短信验证码登录
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
    private TextView tv_title;
    private TextView bt_sum;
    private TextView tv_argument;
    private TextView tv_switch_login_way;
    private TextView tv_forget_password;


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
        switchLoginWay(true);
    }

    @Override
    protected void initView() {
        super.initView();
        tv_title = findViewById(R.id.tv_title);
        et_phone = findViewById(R.id.et_phone);
        et_sms = findViewById(R.id.et_sms);
        tv_sms = findViewById(R.id.tv_sms);
        bt_sum = findViewById(R.id.bt_sum);
        tv_argument = findViewById(R.id.tv_argument);
        tv_switch_login_way = findViewById(R.id.tv_switch_login_way);
        tv_forget_password = findViewById(R.id.tv_forget_password);
    }

    @Override
    protected void initData() {
        super.initData();
        SpannableStringBuilder builder = new SpannableStringBuilder("进入木牛流马代表你已同意");
        int start = builder.length();
        builder.append("《木牛流马许可协议》");
        int end = builder.length();
        XClickableSpan xClickableSpan = new XClickableSpan() {
            /**
             * Performs the click action associated with this span.
             *
             * @param widget
             */
            @Override
            public void onClick(@NonNull View widget) {
                H5Config h5Config = new H5Config("许可协议", Param.agreement, true);
                ArouterUtils.getInstance().builder(ArouterParamApp.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(mContext);

            }
        };
        xClickableSpan.setColor(getResources().getColor(R.color.color_of_3d59ae));
        builder.setSpan(xClickableSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_argument.setMovementMethod(LinkMovementMethod.getInstance());
        tv_argument.setText(builder);
    }

    @Override
    protected void initListener() {
        super.initListener();
        et_phone.addTextChangedListener(new SearchTextWatcher(new SearchTextWatcher.SearchTextChangeListener() {
            @Override
            public void onSearchTextChange(String msg) {
                if (!handler.hasMessages(0)) {
                    if (msg.length() == 11 ) {
                        setTvSmsEnable(true);
                    }else {
                        setTvSmsEnable(false);
                    }
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
        tv_switch_login_way.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
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
                String phone = et_phone.getText().toString().trim();
                String password = et_sms.getText().toString().trim();
                Observable<CommonBean<LoginInfo>> login;
                if (isSmsLogin) {
                   login = HttpAppFactory.login(phone,
                           password
                    );

                }else {
                    login = HttpAppFactory.loginWitPassWord(phone,
                            password
                    );
                }
                login
                        .subscribe(new NetObserver<LoginInfo>(this) {
                            @Override
                            public void doOnSuccess(LoginInfo loginInfo) {
                                super.doOnSuccess(loginInfo);
                                ArouterUtils.getInstance()
                                        .builder(ArouterParamApp.activity_main)
                                        .withBoolean(Param.TRAN, true)
                                        .navigation(mContext);
                                EventBus.getDefault().postSticky(new UmenToken());
                                finish();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                if (!isSmsLogin&&e instanceof NetException&&((NetException) e).getErrorCode()==306){
                                    jumpToSetPassword();
                                }
                            }
                        });
            }
        }else if (v.getId() == R.id.tv_switch_login_way) {
            //切换登录方式
            switchLoginWay(!isSmsLogin);
        }else if (v.getId() == R.id.tv_forget_password) {
             //忘记密码
            jumpToSetPassword();

        }
    }

    private void jumpToSetPassword() {
        ArouterUtils.getInstance().builder(ArouterParamApp.activity_forget_pass)
                .withInt(Param.TYPE, 2)
                .withString(Param.TRAN, et_phone.getText().toString().trim())
                .navigation(this, 2);
    }

    private void switchLoginWay(boolean isSmsLogin) {
        this.isSmsLogin=isSmsLogin;
        tv_title.setText(isSmsLogin?"手机号验证码登录":"账号密码登录");
        tv_switch_login_way.setText(!isSmsLogin?"手机号验证码登录":"账号密码登录");
        tv_forget_password.setVisibility(isSmsLogin?View.GONE:View.VISIBLE);
        tv_sms.setVisibility(isSmsLogin?View.VISIBLE:View.GONE);
        et_sms.setHint(isSmsLogin?"请输入验证码":"请输入密码");
        if (isSmsLogin){
            et_sms. setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            InputFilter[] inputFilters = {new InputFilter.LengthFilter(6)};
            et_sms.setFilters(inputFilters);
            et_sms.setInputType(InputType.TYPE_CLASS_PHONE);
            et_sms.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        }else {
            et_sms. setTransformationMethod(PasswordTransformationMethod.getInstance());
            InputFilter[] inputFilters = {new InputFilter.LengthFilter(32)};
            et_sms.setFilters(inputFilters);
            et_sms.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }

    }
}
