package com.hongniu.freight.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;

/**
 * 作者：  on 2020/2/24.
 * 司机身份认证
 */
@Route(path = ArouterParamApp.fragment_attestation_driver)
public class AttestationDriverFragment extends CompanyBaseFragment {
    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_attestation_driver, null);
        return inflate;
    }
}
