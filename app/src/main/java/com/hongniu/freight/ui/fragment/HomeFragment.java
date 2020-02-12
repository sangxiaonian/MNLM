package com.hongniu.freight.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.control.HomeControl;
import com.hongniu.freight.entity.HomeInfoBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.presenter.HomeFramentPresent;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：  on 2020/2/5.
 */
@Route(path = ArouterParamApp.fragment_home)
public class HomeFragment extends CompanyBaseFragment implements HomeControl.IHomeFragmentView, View.OnClickListener {
    private TextView tv_title;//标题
    private View search;//搜索
    private ImageView icon_eyes;//查看余额
    private TextView tv_count;//余额
    private TextView tv_count_tyr;//托运人订单数量
    private TextView tv_count_cyr;//承运人订单数量
    private TextView tv_count_driver;//司机订单数量
    private View view_chengyunren;//承运人
    private View view_tuoyunren;//托运人
    private View view_driver;//司机
    private View bt_learn_more;//了解更多
    private View shadow;//了解更多

    private boolean hideBalance=true;//是否隐藏余额

    private CompanyBaseFragment fragment;
    HomeControl.IHomeFragmentPresent present;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_home, null);
        tv_title = inflate.findViewById(R.id.tv_title);
        search = inflate.findViewById(R.id.search);
        icon_eyes = inflate.findViewById(R.id.icon_eyes);
        tv_count = inflate.findViewById(R.id.tv_count);
        tv_count_tyr = inflate.findViewById(R.id.tv_count_tyr);
        view_tuoyunren = inflate.findViewById(R.id.view_tuoyunren);
        tv_count_cyr = inflate.findViewById(R.id.tv_count_cyr);
        view_chengyunren = inflate.findViewById(R.id.view_chengyunren);
        tv_count_driver = inflate.findViewById(R.id.tv_count_driver);
        view_driver = inflate.findViewById(R.id.view_driver);
        bt_learn_more = inflate.findViewById(R.id.bt_learn_more);
        shadow = inflate.findViewById(R.id.shadow);
        fragment = new HomeTypeFragment();
        present = new HomeFramentPresent(this);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    @Override
    protected void initData() {
        super.initData();
        present.initDate(this);
        switchBalance(hideBalance);
    }

    @Override
    protected void initListener() {
        super.initListener();
        bt_learn_more.setOnClickListener(this);
        view_chengyunren.setOnClickListener(this);
        view_tuoyunren.setOnClickListener(this);
        view_driver.setOnClickListener(this);
        shadow.setOnClickListener(this);
        icon_eyes.setOnClickListener(this);


    }

    /**
     * 网络请求获取数据之后，显示数据
     *
     * @param result
     */
    @Override
    public void showInfo(HomeInfoBean result) {
        tv_title.setText(String.format("%s好，%s", getTitleTime(), "测试人"));
        tv_count_tyr.setText(getSpanner("1单"));
        tv_count_cyr.setText(getSpanner("2"));
        tv_count_driver.setText(getSpanner("3"));

    }

    private SpannableStringBuilder getSpanner(String s) {
        SpannableStringBuilder builder = new SpannableStringBuilder(s);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(13, true);
        builder.setSpan(sizeSpan, builder.length() - 1, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return builder;
    }

    private String getTitleTime() {
        int time = ConvertUtils.getTime();
        String current = "";
        if (time == 0) {
            current = "凌晨";
        } else if (time == 1) {
            current = "上午";
        } else if (time == 2) {
            current = "中午";
        } else if (time == 3) {
            current = "下午";
        } else if (time == 4) {
            current = "晚上";
        }
        return current;
    }

    /**
     * 显示订单信息
     *  @param infoBeans
     * @param type
     */
    @Override
    public void showOrderInfo(List<OrderInfoBean> infoBeans, Role type) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Param.TRAN, (ArrayList<? extends Parcelable>) infoBeans);
        bundle.putSerializable(Param.TYPE,type);
        FragmentTransaction fragmentTransaction = getChildFragmentManager()
                .beginTransaction();
        if (fragment == null) {
            fragment = new HomeTypeFragment();
            fragmentTransaction
                    .add(R.id.content, fragment)
            ;
        } else {
            if (CollectionUtils.isEmpty(infoBeans)) {
                fragmentTransaction
                        .hide(fragment)
                ;
            } else {
                fragmentTransaction
                        .show(fragment)
                ;
            }
        }
        fragmentTransaction.commit();
        fragment.setBundle(bundle);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_learn_more) {
            ToastUtils.getInstance().show("了解更多");
        } else if (v.getId() == R.id.view_chengyunren) {
            ArouterUtils.getInstance().builder(ArouterParamApp.activity_my_order)
                    .withSerializable(Param.TRAN,Role.CARRIER)
                    .navigation(mContext);
        } else if (v.getId() == R.id.view_tuoyunren) {
            ArouterUtils.getInstance().builder(ArouterParamApp.activity_my_order)
                    .withSerializable(Param.TRAN,Role.SHIPPER)
                    .navigation(mContext);
        } else if (v.getId() == R.id.view_driver) {
            ArouterUtils.getInstance().builder(ArouterParamApp.activity_my_order)
                    .withSerializable(Param.TRAN,Role.DRIVER)
                    .navigation(mContext);
        }else if (v.getId() == R.id.icon_eyes) {
           switchBalance(!hideBalance);
        }else if (v.getId() == R.id.shadow) {
            ArouterUtils.getInstance()
                    .builder(ArouterParamApp.activity_my_coffers)
                    .navigation(mContext);
        }


    }
    private void switchBalance(boolean hideBalance){
        this.hideBalance=hideBalance;
        tv_count.setText(hideBalance?"******":"1820");
        icon_eyes.setImageResource(hideBalance?R.mipmap.attention_forbid:R.mipmap.attention);
    }
}
