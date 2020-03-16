package com.hongniu.freight.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.fy.androidlibrary.event.BusFactory;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.JLog;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.fy.companylibrary.utils.PermissionUtils;
import com.hongniu.freight.BuildConfig;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.Event;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.ui.fragment.ChactListFragment;
import com.hongniu.freight.ui.fragment.HomeFragment;
import com.hongniu.freight.utils.InfoUtils;
import com.hongniu.freight.utils.LoactionUpUtils;
import com.hongniu.thirdlibrary.chact.ChactHelper;
import com.hongniu.thirdlibrary.chact.UserInfor;
import com.hongniu.thirdlibrary.chact.control.ChactControl;
import com.hongniu.thirdlibrary.chact.control.OnGetUserInforListener;
import com.hongniu.thirdlibrary.map.LoactionUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.rong.imkit.RongIM;

@Route(path = ArouterParamApp.activity_main)
public class MainActivity extends CompanyBaseActivity implements View.OnClickListener, ChactControl.OnConnectListener, AMapLocationListener, ChactControl.OnReceiveUnReadCountListener {

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
    private LoactionUtils loaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWhitToolBar("");
        initView();
        initData();
        initListener();
        tab1.performClick();
        loaction = LoactionUtils.getInstance();
        loaction.init(this);
        loaction.setListener(this);
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
                JLog.i(InfoUtils.getLoginInfo().getId());
                ChactHelper.getHelper().startPriver(mContext, "277", "测试名称");
//                ArouterUtils.getInstance().builder(ArouterParamsMatch.activity_match_estimate_order)
//                        .navigation(mContext);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ChactHelper.getHelper().disConnectState()) {

            ChactHelper.getHelper().connect(mContext, InfoUtils.getLoginInfo().getRongToken(), this);
            ChactHelper.getHelper().setUnReadCountListener(this);
        }
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
                if (InfoUtils.getState(InfoUtils.getMyInfo()) == 4) {
                    ArouterUtils.getInstance().builder(ArouterParamApp.activity_order_create)
                            .navigation();
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER)
                            .show("身份认证暂未成功，请联系客服");
                }
                break;
        }
    }

    private int lastID;

    private void changeTabeState(int id) {
        if (lastID == id) {
            return;
        }
        lastID = id;
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
                    messageFragment = new ChactListFragment();
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


    /**
     * 连接成功
     *
     * @param userID
     */
    @Override
    public void onConnectSuccess(String userID) {
        ChactHelper.getHelper().setUseInfor(new OnGetUserInforListener() {
            @Override
            public Observable<UserInfor> onGetUserInfor(String usrID) {
                return HttpAppFactory.queryRongInfor(usrID)
                        .map(new Function<CommonBean<UserInfor>, UserInfor>() {
                            @Override
                            public UserInfor apply(CommonBean<UserInfor> userInforCommonBean) throws Exception {
                                return userInforCommonBean.getData();
                            }
                        })
                        ;
            }
        });
        HttpAppFactory.queryRongInfor(userID)
                .subscribe(new NetObserver<UserInfor>(null) {
                    @Override
                    public void doOnSuccess(UserInfor data) {

                        ChactHelper.getHelper().refreshUserInfoCache(userID, data);
                    }
                });

        RongIM.getInstance().setMessageAttachedUserInfo(true);
        BusFactory.getBus().post(new Event.UpChactFragment());
    }

    /**
     * 连接错误
     *
     * @param errorCode
     * @param errorMsg
     */
    @Override
    public void onConnectError(int errorCode, String errorMsg) {
        if (errorCode == 31010) {//不是异地登录
            ToastUtils.getInstance().show("异地登录");
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        JLog.v("定位成功");
        //地理位置发生改变
        if (upLoactionUtils != null) {
            upLoactionUtils.add(aMapLocation.getLatitude(), aMapLocation.getLongitude(), aMapLocation.getTime(), aMapLocation.getSpeed(), aMapLocation.getBearing());
        }
    }

    @Override
    protected boolean isUseEventBus() {
        return true;
    }

    private LoactionUpUtils upLoactionUtils;//上传位置信息

    //开始或停止记录用户位置信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartLoactionMessage(final Event.UpLoactionEvent event) {
        if (event != null) {
            //如果有正在运输中的订单，则此时获取到用户的位置信息
            if (event.start) {//开始记录数据
                PermissionUtils.applyMap(this, new PermissionUtils.onApplyPermission() {
                    @Override
                    public void hasPermission() {
                        if (TextUtils.isEmpty(event.cardID)) {
                            loaction.setInterval(1000);
                        } else {
                            loaction.startLoaction();
                        }
                        //首次创建位置信息收集数据
                        if (upLoactionUtils == null || TextUtils.isEmpty(upLoactionUtils.getCarID())) {
                            if (!DeviceUtils.isOpenGps(mContext)) {
                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("为了更准确的记录您的轨迹信息，请打开GPS");
                            }
                            upLoactionUtils = new LoactionUpUtils();
                            upLoactionUtils.setOrderInfor(event.orderID, event.cardID, event.destinationLatitude, event.destinationLongitude);
                            //更新位置信息收起器
                        } else if (!upLoactionUtils.getCarID().equals(event.cardID)) {
                            upLoactionUtils.onDestroy();
                            if (!DeviceUtils.isOpenGps(mContext)) {
                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("为了更准确的记录您的轨迹信息，请打开GPS");
                            }
                            upLoactionUtils = new LoactionUpUtils();
                            upLoactionUtils.setOrderInfor(event.orderID, event.cardID, event.destinationLatitude, event.destinationLongitude);
                        }
                        JLog.i("开始发车");
                    }

                    @Override
                    public void noPermission() {

                    }
                });

            } else {
                if (upLoactionUtils != null) {
                    upLoactionUtils.onDestroy();
                }
                if (loaction != null) {
                    loaction.stopLoaction();
                }

            }
        }
    }

    /**
     * 有未读消息时候
     *
     * @param count 未读消息
     */
    @Override
    public void onReceiveUnRead(int count) {
        String msg = "";
        if (count > 99) {
            msg = "99+";
        } else if (count > 0) {
            msg = count + "";
        }
        tv_unread.setVisibility(TextUtils.isEmpty(msg) ? View.GONE : View.VISIBLE);
        tv_unread.setText(msg);
    }
}
