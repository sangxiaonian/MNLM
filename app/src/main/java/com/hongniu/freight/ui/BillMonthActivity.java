package com.hongniu.freight.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.widget.autoline.AutoLineLayout;
import com.fy.androidlibrary.widget.autoline.helper.AutoLayoutHelper;
import com.fy.androidlibrary.widget.autoline.inter.AutoSingleSelectListener;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;
import com.hongniu.freight.ui.adapter.AutoTagSingleAdapter;
import com.hongniu.freight.utils.PickerDialogUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @data 2020/2/12
 * @Author PING
 * @Description 月账单
 */
@Route(path = ArouterParamApp.activity_bill_month)
public class BillMonthActivity extends CompanyBaseActivity implements AutoSingleSelectListener<String>, OnTimeSelectListener, View.OnClickListener {

    private AutoTagSingleAdapter tagAdapter;
    private AutoLineLayout autoLineLayout;
    private ViewGroup ll_time;
    private TextView tv_time;
    private TimePickerView timePicker;
    private CompanyBaseFragment currentFragment, expendFragment, incomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_month);
        setWhitToolBar("");
        setToolbarSrcRight(R.drawable.icon_search_434445);
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArouterUtils.getInstance()
                        .builder(ArouterParamApp.activity_bill_search)
                        .navigation();
            }
        });
        initView();
        initData();
        initListener();
        switchFragment(0);
    }
    @Override
    protected void initView() {
        super.initView();
        autoLineLayout = findViewById(R.id.auto_layout);
        ll_time = findViewById(R.id.ll_time);
        tv_time = findViewById(R.id.tv_time);

        boolean[] type = new boolean[]{true, true, false, false, false, false};
         timePicker = PickerDialogUtils.initTimePicker(mContext, this, type);
    }


    @Override
    protected void initData() {
        super.initData();
        List<String> list = new ArrayList<>();
        list.add("支出");
        list.add("收入");

        tagAdapter = new AutoTagSingleAdapter(mContext);
        tagAdapter.setDatas(list);
        tagAdapter.setSingleSelectedListener(this);
        tagAdapter.setSelectsPosition(0);
        AutoLayoutHelper helper = new AutoLayoutHelper();
        helper.setType(0);
        helper.sethGap(DeviceUtils.dip2px(mContext, 25));
        autoLineLayout.setLayoutHelper(helper);
        autoLineLayout.setAdapter(tagAdapter);
        tv_time.setText(ConvertUtils.formatTime(new Date(),"yyyy年MM月"));
    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_time.setOnClickListener(this);
    }

    @Override
    public void onAutoSingleSelected(int position, String s, boolean check) {
        switchFragment(position);
    }
    private void switchFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment !=null){
            transaction.hide(currentFragment);
        }
        if (position==0){
            if (expendFragment ==null) {
                expendFragment = (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_bill_month_expend).navigation();
                transaction.add(R.id.content, expendFragment);
                Bundle bundle=new Bundle();
                bundle.putInt(Param.TYPE,position);
                expendFragment.setBundle(bundle);
            }else {
                transaction.show(expendFragment);
            }

            currentFragment= expendFragment;
        }else {
            if (incomeFragment ==null) {
                incomeFragment = (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_bill_month_income).navigation();
                transaction.add(R.id.content, incomeFragment);
                Bundle bundle=new Bundle();
                bundle.putInt(Param.TYPE,position);
                incomeFragment.setBundle(bundle);
            }else {
                transaction.show(incomeFragment);
            }

            currentFragment= incomeFragment;
        }
        transaction.commit();
    }


    @Override
    public void onTimeSelect(Date date, View v) {
        String time = ConvertUtils.formatTime(date, "yyyy年MM月");
        tv_time.setText(time);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.ll_time){
            timePicker.show();
        }
    }
}
