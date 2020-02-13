package com.hongniu.freight.ui;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.widget.editext.PointLengthFilter;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;

/**
 * @data 2020/2/13
 * @Author PING
 * @Description 余额提现
 */
@Route(path = ArouterParamApp.activity_balance_pending)
public class BalancePendingActivity extends CompanyBaseActivity implements View.OnClickListener {

    private TextView tv_blank;//银行卡信息
    private EditText et_count;//提现金额
    private TextView tv_pending_all;//全部提现
    private TextView tv_count;//余额
    private TextView tv_pending;//提现

    private String all = "1000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_pending);
        setWhitToolBar("余额提现");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        tv_blank = findViewById(R.id.tv_blank);
        et_count = findViewById(R.id.et_count);
        tv_pending_all = findViewById(R.id.tv_pending_all);
        tv_count = findViewById(R.id.tv_count);
        tv_pending = findViewById(R.id.tv_pending);
    }

    @Override
    protected void initData() {
        super.initData();
        tv_blank.setText(String.format("%s（%s）", "招商银行", "4586"));
        tv_count.setText(String.format("账户余额¥%s", all));
        et_count.setFilters(new InputFilter[]{new PointLengthFilter(2)});
    }

    @Override
    protected void initListener() {
        super.initListener();
        tv_pending_all.setOnClickListener(this);
        tv_pending.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_pending_all) {
            et_count.setText(all);
            et_count.setSelection(et_count.getText().toString().length());
        } else if (v.getId() == R.id.tv_pending) {
            ToastUtils.getInstance().show("提现");
        }
    }
}
