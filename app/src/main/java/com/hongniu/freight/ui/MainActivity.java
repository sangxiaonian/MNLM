package com.hongniu.freight.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.third.push.NotificationUtils;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.google.gson.Gson;
import com.hongniu.freight.BuildConfig;
import com.hongniu.freight.R;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.ui.fragment.HomeFragment;
import com.hongniu.thirdlibrary.map.LoactionUtils;

@Route(path = ArouterParamApp.activity_main)
public class MainActivity extends CompanyBaseActivity implements View.OnClickListener {

    private TextView demo;


    ViewGroup tab1;
    ViewGroup tab2;
    ViewGroup tab3;
    ViewGroup tab4;
    ViewGroup tab5;

    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView tv_unread;

    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;
    ImageView img5;


    CompanyBaseFragment homeFragment, orderFragment, messageFragment, meFragment, currentFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWhitToolBar("");
        initView();
        initData();
        initListener();
        tab1.performClick();
    }

    @Override
    protected void initView() {
        super.initView();
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        tab4 = findViewById(R.id.tab4);
        tab5 = findViewById(R.id.tab5);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv_unread = findViewById(R.id.tv_unread);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        demo = findViewById(R.id.demo);

    }

    @Override
    protected void initData() {
        super.initData();
        tv_unread.setVisibility(View.GONE);

        demo.setVisibility(BuildConfig.DEBUG ? View.VISIBLE : View.GONE);


    }
    @Override
    protected void initListener() {
        super.initListener();
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
        tab5.setOnClickListener(this);
        demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ArouterUtils.getInstance().builder(ArouterParamsMatch.activity_match_estimate_order)
//                        .navigation(mContext);
            }
        });

    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tab1:
            case R.id.tab2:
            case R.id.tab4:
            case R.id.tab5:
                changeTabeState(v.getId());
                break;
            case R.id.tab3:
                ArouterUtils.getInstance().builder(ArouterParamApp.activity_order_create)
                        .navigation();  break;
        }
    }

    private int lastID;
    private void changeTabeState(int id) {
        if (lastID==id){
            return;
        }
        lastID=id;
        tv1.setTextColor(getResources().getColor(id == R.id.tab1 ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        tv2.setTextColor(getResources().getColor(id == R.id.tab2 ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        tv4.setTextColor(getResources().getColor(id == R.id.tab4 ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        tv5.setTextColor(getResources().getColor(id == R.id.tab5 ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        img1.setImageResource(id == R.id.tab1 ? R.mipmap.icon_home_selected_46 : R.mipmap.icon_home_unselected_46);
        img2.setImageResource(id == R.id.tab2 ? R.mipmap.icon_gz_selected_46 : R.mipmap.icon_gz_unselected_46);
        img4.setImageResource(id == R.id.tab4 ? R.mipmap.icon_message_selected_46 : R.mipmap.icon_message_unselected_46);
        img5.setImageResource(id == R.id.tab5 ? R.mipmap.icon_me_selected_46 : R.mipmap.icon_me_unselected_46);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }
        switch (id) {
            case R.id.tab1:

                if (homeFragment == null) {

                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.content, homeFragment);
                } else {
                    fragmentTransaction.show(homeFragment);
                }
                currentFragment = homeFragment;
                break;
            case R.id.tab2:
                if (orderFragment == null) {
                    orderFragment = (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_order_home).navigation(mContext);
                    fragmentTransaction.add(R.id.content, orderFragment);
                } else {
                    fragmentTransaction.show(orderFragment);
                }
                currentFragment = orderFragment;
                break;

            case R.id.tab4:

                if (messageFragment == null) {
                    messageFragment = new CompanyBaseFragment();
                    fragmentTransaction.add(R.id.content, messageFragment);
                } else {
                    fragmentTransaction.show(messageFragment);
                }

                currentFragment = messageFragment;

                break;
            case R.id.tab5:
                if (meFragment == null) {
                    meFragment = (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_person_center).navigation(mContext);
                    fragmentTransaction.add(R.id.content, meFragment);
                } else {
                    fragmentTransaction.show(meFragment);
                }

                currentFragment = meFragment;

                break;

        }

        fragmentTransaction.commitAllowingStateLoss();


    }



}
