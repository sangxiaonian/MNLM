package com.hongniu.freight.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.InfoUtils;


/**
 * @data 2018/10/26
 * @Author PING
 * @Description 忘记/设置支付密码界面
 */
@Route(path = ArouterParamApp.activity_forget_pass)
public class ForgetPassActivity extends CompanyBaseActivity implements View.OnClickListener {

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


    private ItemTextView itemPhone;
    private ItemTextView itemSms;
    private ItemTextView itemPass;
    private ItemTextView itemNewPass;
    private TextView tv_sms;
    private Button buSum;

    private int currentTime;


    /**
     * 0 1 支付密码 2设置登录密码
     */
    private int type;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        phone = getIntent().getStringExtra(Param.TRAN);
        type = getIntent().getIntExtra(Param.TYPE, 0);

        if (TextUtils.isEmpty(phone) && InfoUtils.getMyInfo() != null) {
            phone = InfoUtils.getMyInfo().getMobile();
        }


        initView();
        initData();
        initListener();
        setTvSmsEnable(true);
    }

    @Override
    protected void initView() {
        super.initView();
        itemPhone = findViewById(R.id.item_phone);
        itemSms = findViewById(R.id.item_sms);
        itemPass = findViewById(R.id.item_pass);
        itemNewPass = findViewById(R.id.item_entry_pass);
        tv_sms = findViewById(R.id.bt_sms);
        buSum = findViewById(R.id.bt_sum);
    }

    @Override
    protected void initData() {
        super.initData();


        if (type == 0) {
            setWhitToolBar("忘记支付/提现密码");
            itemPhone.setTextCenter(phone);
            itemPhone.setEnabled(false);
            itemPass.setTextCenterHide("请输入六位数字新密码");
            itemNewPass.setTextCenterHide("请再次输入六位数字密码");
        } else if (type == 1) {
            setWhitToolBar("设置木牛流马支付/提现密码");
            itemPhone.setTextCenter(phone);
            itemPhone.setEnabled(false);
            itemPass.setTextCenterHide("请输入六位数字新密码");
            itemNewPass.setTextCenterHide("请再次输入六位数字密码");
        } else if (type == 2) {
            setWhitToolBar("设置登录密码");

            itemPhone.setTextCenter(phone);
            itemNewPass.setMaxLength(32);
            itemPass.setMaxLength(32);
            itemNewPass.setCenterType(5);
            itemPass.setCenterType(5);
            itemPhone.setEnabled(TextUtils.isEmpty(phone));
            itemPhone.setEditable(TextUtils.isEmpty(phone));
            itemPass.setTextCenterHide("请输入密码");
            itemNewPass.setTextCenterHide("请再次输入密码");
        } else {
            setWhitToolBar("设置密码");
            ToastUtils.getInstance().show("类型错误");
        }

//        setWhitToolBar(type == 0 ? "忘记支付/提现密码" : "设置木牛流马支付/提现密码");


    }

    @Override
    protected void initListener() {
        super.initListener();
        buSum.setOnClickListener(this);
        tv_sms.setOnClickListener(this);

    }

    private void setTvSmsEnable(boolean enable) {
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
        if (v.getId() == R.id.bt_sms) {
            startCountTime();
            if (TextUtils.isEmpty(itemPhone.getTextCenter())) {
                ToastUtils.getInstance().show(itemPhone.getTextCenterHide());
                return;
            }
            HttpAppFactory.getSms(itemPhone.getTextCenter())
                    .subscribe(new NetObserver<String>(null) {
                        @Override
                        public void doOnSuccess(String data) {
                        }
                    });


        } else if (v.getId() == R.id.bt_sum) {

            if (check()) {
                String passWord = itemPass.getTextCenter();
                if (type == 0 || type == 1) {
                    setPayPassword(passWord);
                } else {
                    setLoginPassword(passWord);
                }

            }


        }
    }

    /**
     * 设置登录密码
     *
     * @param passWord
     */
    private void setLoginPassword(String passWord) {
        HttpAppFactory
                .setLoginPass(itemPhone.getTextCenter(), itemSms.getTextCenter(), passWord)
                .subscribe(new NetObserver<String>(this) {
                    @Override
                    public void doOnSuccess(String s) {
                        super.doOnSuccess(s);
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                        finish();
                    }
                });
    }

    private void setPayPassword(String trim) {
        HttpAppFactory
                .upPassword(ConvertUtils.MD5(trim), itemSms.getTextCenter())
                .subscribe(new NetObserver<Object>(this) {
                    @Override
                    public void doOnSuccess(Object data) {
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show(type == 0 ? "修改密码成功" : "设置密码成功");
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
    }

    private void getResultMsg() {

    }

    private boolean check() {
        if (TextUtils.isEmpty(itemPhone.getTextCenter())) {
            ToastUtils.getInstance().show(itemPhone.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemSms.getTextCenter())) {
            ToastUtils.getInstance().show(itemSms.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemPass.getTextCenter())) {
            ToastUtils.getInstance().show(itemPass.getTextCenterHide());
            return false;
        }

        if (itemPass.getTextCenter().toString().trim().length() < 6) {
            ToastUtils.getInstance().show(itemPass.getTextCenterHide());
            return false;
        }

        if (TextUtils.isEmpty(itemNewPass.getTextCenter())) {
            ToastUtils.getInstance().show(itemNewPass.getTextCenterHide());
            return false;
        }


        if (!TextUtils.equals(itemPass.getTextCenter(), itemNewPass.getTextCenter())) {
            ToastUtils.getInstance().show("两次密码输入不一致");
            return false;
        }
        return true;

    }

    private void startCountTime() {
        currentTime = time;
        setTvSmsEnable(false);
        isRetry = true;
        handler.sendEmptyMessage(0);

    }
}
