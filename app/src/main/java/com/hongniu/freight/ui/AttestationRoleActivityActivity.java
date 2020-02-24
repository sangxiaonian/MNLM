package com.hongniu.freight.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;

/**
 * @data 2020/2/24
 * @Author PING
 * @Description 身份认证页面
 */
@Route(path = ArouterParamApp.activity_attestation_role_activity)
public class AttestationRoleActivityActivity extends CompanyBaseActivity {

    private int role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attestation_role_activity);
        role = getIntent().getIntExtra(Param.TRAN, 0);
        setWhitToolBar(getTitle(role));
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        String route = null;
        if (role == 1) {
            route = ArouterParamApp.fragment_attestation_driver;
        } else if (role == 2) {
            route = ArouterParamApp.fragment_attestation_shipper_personal;
//            route = "个人承运人身份认证";
        } else if (role == 3) {
//            route = "公司承运人身份认证";
            route = ArouterParamApp.fragment_attestation_shipper_company;
        } else if (role == 4) {
//            route = "公司托运人身份认证";
            route = ArouterParamApp.fragment_attestation_carrier_company;
        } else if (role == 5) {
//            route = "个人托运人身份认证";
            route = ArouterParamApp.fragment_attestation_carrier_personal;
        }
        if (route!=null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, (Fragment) ArouterUtils.getInstance().builder(route).navigation())
                    .commit();
        }
    }

    private String getTitle(int role) {
        String title;
        if (role == 1) {
            title = "司机身份认证";
        } else if (role == 2) {
            title = "个人承运人身份认证";
        } else if (role == 3) {
            title = "公司承运人身份认证";
        } else if (role == 4) {
            title = "公司托运人身份认证";
        } else if (role == 5) {
            title = "个人托运人身份认证";
        } else {
            title = "身份认证";
        }
        return title;
    }

}
