package com.hongniu.freight.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.entity.VerifyIdNumIdentityBean;
import com.hongniu.freight.entity.VerifyInfoBean;
import com.hongniu.freight.net.HttpAppFactory;

/**
 * 作者：  on 2020/3/4.
 */
public class AttestationBaseFragment extends CompanyBaseFragment {

    protected boolean canNext = true;
    protected TextView bt_sum;

    @Override
    protected void initData() {
        super.initData();
        if (getBundle() != null) {
            canNext = getBundle().getBoolean(Param.TRAN);
        }
        if (bt_sum != null) {
            bt_sum.setVisibility(canNext ? View.VISIBLE : View.GONE);
        }

        query();
    }

    protected void query() {

        HttpAppFactory.queryVerifyCarrierPerson()
                .subscribe(new NetObserver<VerifyInfoBean>(this) {
                    @Override
                    public void doOnSuccess(VerifyInfoBean verifyInfoBean) {
                        super.doOnSuccess(verifyInfoBean);
                        initInfo(verifyInfoBean);

                    }
                });
    }

    //初始化数据
    protected void initInfo(VerifyInfoBean verifyInfoBean) {
    }
}
