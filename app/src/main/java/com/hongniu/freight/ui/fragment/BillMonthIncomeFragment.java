package com.hongniu.freight.ui.fragment;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.widget.autoline.AutoLineLayout;
import com.fy.androidlibrary.widget.autoline.helper.AutoLayoutHelper;
import com.fy.androidlibrary.widget.autoline.inter.AutoSingleSelectListener;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;
import com.hongniu.freight.ui.adapter.AutoTagSingleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：  on 2020/2/12.
 * 月账单收入数据
 */
@Route(path = ArouterParamApp.fragment_bill_month_income)
public class BillMonthIncomeFragment extends CompanyBaseFragment {
    private TextView tvExpendNumber;
    private TextView tvCount;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_bill_month_income, null);
        tvExpendNumber = inflate.findViewById(R.id.tv_expend_number);
        tvCount = inflate.findViewById(R.id.tv_count);
        return inflate;

    }

    @Override
    protected void initData() {
        super.initData();
        tvCount.setText("1000");
        tvExpendNumber.setText(getNumberSpan("10"));
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        CompanyBaseFragment trackFragment = (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_bill_month_child).navigation();
        transaction.add(R.id.content, trackFragment);
        Bundle bundle = new Bundle();
        bundle.putInt(Param.TYPE, 2);
        trackFragment.setBundle(bundle);
        transaction.commit();

    }


    private SpannableStringBuilder getNumberSpan(String count) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("共支出")
                .append(count)
                .append("笔,合计");
        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_of_fa8c16))
                , 3, 3 + count.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return builder;

    }


}
