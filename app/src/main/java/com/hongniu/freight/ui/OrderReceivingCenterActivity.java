package com.hongniu.freight.ui;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;

/**
 * @data 2020/2/10
 * @Author PING
 * @Description 接单中心
 */
@Route(path = ArouterParamApp.activity_order_receiving)
public class OrderReceivingCenterActivity extends CompanyBaseActivity {

    private Role role;

    private CompanyBaseFragment shipperFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_receiving);
        role = Role.SHIPPER;
        setWhitToolBar("");
        setToolbarSrcRight(R.drawable.icon_search_434445);
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArouterUtils.getInstance().builder(ArouterParamApp.activity_search_order)
                        .withSerializable(Param.TRAN, role)
                        .navigation(mContext);
            }
        });
        shipperFragment = (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_my_order).navigation();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Param.TRAN, role);
        shipperFragment.setBundle(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, shipperFragment)
                .commit();

        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initData() {
        super.initData();


    }

    @Override
    protected void initListener() {
        super.initListener();
    }


}
