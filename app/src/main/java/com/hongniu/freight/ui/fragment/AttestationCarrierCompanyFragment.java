package com.hongniu.freight.ui.fragment;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;

/**
 * 作者：  on 2020/2/24.
 * 个人托运人身份认证第二部
 */
@Route(path = ArouterParamApp.fragment_attestation_carrier_company)
public class AttestationCarrierCompanyFragment extends CompanyBaseFragment {



    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_attestation_carrier_company, null);
        return inflate;
    }
}
