package com.hongniu.freight.ui.fragment;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.imgload.ImageLoader;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.H5Config;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.InfoUtils;
import com.hongniu.freight.widget.DialogComment;

import io.reactivex.disposables.Disposable;

/**
 * 作者：  on 2020/2/10.
 * 个人中心
 */
@Route(path = ArouterParamApp.fragment_person_center)
public class PersonCenterFragment extends CompanyBaseFragment implements View.OnClickListener {

    private ImageView img_heard;//头像
    private ImageView icon_eyes;//隐藏余额
    private TextView tv_name;//账号
    private TextView tv_role;//角色认证
    private TextView tv_count;//余额
    private ViewGroup ll_identification;//身份认证
    private ViewGroup ll_car;//我的车辆
    private ViewGroup ll_feedback;//意见反馈
    private ViewGroup ll_service;//联系客服
    private ViewGroup ll_about_us;//关于我们
    private ViewGroup ll_quit;//推出账号
    private ViewGroup ll_order_center;//接单中心
    private ViewGroup shadow;//我的金库

    private boolean hideBalance = true;//是否隐藏余额
    private PersonInfor personInfor;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_person_center, null);
        img_heard = inflate.findViewById(R.id.img_heard);
        icon_eyes = inflate.findViewById(R.id.icon_eyes);
        tv_name = inflate.findViewById(R.id.tv_name);
        tv_role = inflate.findViewById(R.id.tv_role);
        tv_count = inflate.findViewById(R.id.tv_count);
        ll_identification = inflate.findViewById(R.id.ll_identification);
        ll_car = inflate.findViewById(R.id.ll_car);
        ll_feedback = inflate.findViewById(R.id.ll_feedback);
        ll_service = inflate.findViewById(R.id.ll_service);
        ll_about_us = inflate.findViewById(R.id.ll_about_us);
        ll_quit = inflate.findViewById(R.id.ll_quit);
        ll_order_center = inflate.findViewById(R.id.ll_order_center);
        shadow = inflate.findViewById(R.id.shadow);
        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
        initInfo(InfoUtils.getMyInfo());
        switchBalance(hideBalance);
    }

    @Override
    public void onStart() {
        super.onStart();
        queryInfo();
    }

    private boolean isFirst=true;
    @Override
    public void onTaskStart(Disposable d) {
        if (isFirst) {
            super.onTaskStart(d);
        }

    }

    @Override
    protected void initListener() {
        super.initListener();
        icon_eyes.setOnClickListener(this);
        ll_identification.setOnClickListener(this);
        ll_car.setOnClickListener(this);
        ll_feedback.setOnClickListener(this);
        ll_service.setOnClickListener(this);
        ll_about_us.setOnClickListener(this);
        ll_quit.setOnClickListener(this);
        ll_order_center.setOnClickListener(this);
        shadow.setOnClickListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            queryInfo();
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.icon_eyes) {
            switchBalance(!hideBalance);
        } else if (v.getId() == R.id.ll_identification) {
            ToastUtils.getInstance().show("ll_identification");
        } else if (v.getId() == R.id.ll_car) {
            ArouterUtils.getInstance().builder(ArouterParamApp.activity_my_car_list).navigation(mContext);
        } else if (v.getId() == R.id.ll_feedback) {
            ToastUtils.getInstance().show("ll_feedback");
        } else if (v.getId() == R.id.ll_service) {
            new DialogComment.Builder()
                    .setBtLeft("取消")
                    .setBtRight("拨打")
                    .setDialogTitle(getString(R.string.service))
                    .setRightClickListener(new DialogComment.OnButtonRightClickListener() {
                        @Override
                        public void onRightClick(View view, Dialog dialog) {
                            CommonUtils.call(mContext, getString(R.string.service));
                            dialog.dismiss();
                        }
                    })
                    .creatDialog(mContext)
                    .show();

        } else if (v.getId() == R.id.ll_about_us) {
            ArouterUtils.getInstance().builder(ArouterParamApp.activity_about_us).navigation(mContext);

        } else if (v.getId() == R.id.ll_quit) {
            new DialogComment.Builder()
                    .setBtLeft("取消")
                    .setBtRight("确定")
                    .setDialogTitle("确定要退出登录吗？")
                    .setRightClickListener(new DialogComment.OnButtonRightClickListener() {
                        @Override
                        public void onRightClick(View view, Dialog dialog) {
                            dialog.dismiss();
                            InfoUtils.loginOut();
                            ArouterUtils.getInstance().builder(ArouterParamApp.activity_login)
                                    .navigation(mContext);
                            getActivity().finish();
                        }
                    })
                    .creatDialog(mContext)
                    .show();
        } else if (v.getId() == R.id.ll_order_center) {
            ArouterUtils.getInstance()
                    .builder(ArouterParamApp.activity_order_receiving)
                    .navigation(mContext);

        } else if (v.getId() == R.id.shadow) {
            ArouterUtils.getInstance()
                    .builder(ArouterParamApp.activity_my_coffers)
                    .navigation(mContext);

        }
    }

    private void queryInfo() {
        HttpAppFactory.queryMyInfo()
                .subscribe(new NetObserver<PersonInfor>(this) {
                    @Override
                    public void doOnSuccess(PersonInfor personInfor) {
                        super.doOnSuccess(personInfor);
                        initInfo(personInfor);
                        isFirst=false;
                    }


                });
    }

    private void initInfo(PersonInfor personInfor) {
        if (personInfor == null) {
            return;
        }
        this.personInfor = personInfor;
        ImageLoader.getLoader().loadHeaed(mContext, img_heard, personInfor.getLogoPath());
        CommonUtils.setText(tv_name, personInfor.getMobile());
        String roleName = InfoUtils.getRoleName(personInfor);

        String stateName = InfoUtils.getStateName(InfoUtils.getState(personInfor));
        if (!TextUtils.isEmpty(stateName)) {
            roleName += "(" + stateName + ")";
        }
        CommonUtils.setText(tv_role, roleName);
        switchBalance(hideBalance);
    }

    private void switchBalance(boolean hideBalance) {
        this.hideBalance = hideBalance;
        tv_count.setText(hideBalance ? "******" : personInfor == null||TextUtils.isEmpty(personInfor.getTotalBalance()) ? "0" : personInfor.getTotalBalance());
        icon_eyes.setImageResource(hideBalance ? R.mipmap.attention_forbid : R.mipmap.attention);
    }
}
