package com.hongniu.freight.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;

/**
 * @data 2020/2/13
 * @Author PING
 * @Description 余额
 */
@Route(path = ArouterParamApp.activity_balance)
public class BalanceActivity extends CompanyBaseActivity implements View.OnClickListener {

    private TextView tv_balance;
    private TextView tv_balance_pending;
    private TextView tv_recharge;
    private TextView tv_withdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        setWhitToolBar("账户余额");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        tv_balance = findViewById(R.id.tv_balance);
        tv_balance_pending = findViewById(R.id.tv_balance_pending);
        tv_recharge = findViewById(R.id.tv_recharge);
        tv_withdraw = findViewById(R.id.tv_withdraw);
    }

    @Override
    protected void initData() {
        super.initData();
        tv_balance.setText("100");
        tv_balance_pending.setText(String.format("你有 %s 元入账后可提现", "100"));
    }

    @Override
    protected void initListener() {
        super.initListener();
        tv_recharge.setOnClickListener(this);
        tv_withdraw.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.tv_recharge == v.getId()) {
            ToastUtils.getInstance().show("充值");
        } else if (R.id.tv_withdraw == v.getId()) {
            ArouterUtils.getInstance().builder(ArouterParamApp.activity_balance_pending)
                    .navigation(mContext);
        }
    }
}
