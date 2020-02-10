package com.hongniu.freight.ui;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;
import com.hongniu.freight.ui.fragment.HomeFragment;

@Route(path = ArouterParamApp.activity_main)
public class MainActivity extends CompanyBaseActivity implements View.OnClickListener {

    private View home;
    private View order;
    private View add;
    private View chat;
    private View my;

    private CompanyBaseFragment currentFragment, homeFragment,orderFragment,chatFragment,myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWhitToolBar("");
        initView();
        initData();
        initListener();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, new HomeFragment())
                .commit();
    }

    @Override
    protected void initView() {
        super.initView();
        home = findViewById(R.id.home);
        order = findViewById(R.id.order);
        add = findViewById(R.id.add);
        chat = findViewById(R.id.chat);
        my = findViewById(R.id.my);
    }

    @Override
    protected void initListener() {
        super.initListener();
        home.setOnClickListener(this);
        order.setOnClickListener(this);
        add.setOnClickListener(this);
        chat.setOnClickListener(this);
        my.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.home) {
            if (homeFragment==null) {
                homeFragment=new HomeFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (currentFragment!=null){
                    transaction.hide(currentFragment);
                }
                transaction
                        .add(R.id.content,homeFragment)
                        .commit();
                currentFragment=homeFragment;
            }else {
                switchFragment(homeFragment);
            }
        } else if (v.getId() == R.id.order) {
            if (orderFragment==null) {
                orderFragment=new HomeFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (currentFragment!=null){
                    transaction.hide(currentFragment);
                }
                transaction
                        .add(R.id.content,orderFragment)
                        .commit();
                currentFragment=orderFragment;
            }else {
                switchFragment(orderFragment);
            }

        } else if (v.getId() == R.id.add) {
            ToastUtils.getInstance().show("添加数据");
        } else if (v.getId() == R.id.chat) {
            if (chatFragment==null) {
                chatFragment=new HomeFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (currentFragment!=null){
                    transaction.hide(currentFragment);
                }
                transaction
                        .add(R.id.content,chatFragment)
                        .commit();
                currentFragment=chatFragment;
            }else {
                switchFragment(chatFragment);
            }
        } else if (v.getId() == R.id.my) {
            if (myFragment==null) {
                myFragment= (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_person_center).navigation();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (currentFragment!=null){
                    transaction.hide(currentFragment);
                }
                transaction
                        .add(R.id.content,myFragment)
                        .commit();
                currentFragment=myFragment;
            }else {
                switchFragment(myFragment);
            }
        }


    }

    private void switchFragment(CompanyBaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment!=null){
            transaction.hide(currentFragment);
        }
        transaction.show(fragment).commit();
        currentFragment=fragment;
    }
}
