package com.hongniu.freight.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.net.error.NetException;
import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.widget.autoline.AutoLineLayout;
import com.fy.androidlibrary.widget.autoline.helper.AutoLayoutHelper;
import com.fy.androidlibrary.widget.autoline.helper.AutoTagAdapter;
import com.fy.androidlibrary.widget.autoline.inter.AutoSingleSelectListener;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.AccountDetailBean;
import com.hongniu.freight.entity.CoffersInfoBean;
import com.hongniu.freight.entity.QueryBindHuaInforsBean;
import com.hongniu.freight.entity.QueryVeriBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.ui.adapter.AutoTagSingleAdapter;
import com.hongniu.freight.widget.dialog.RechargeInforDialog;

import org.apache.http.params.HttpParams;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2020/2/12
 * @Author PING
 * @Description 我的金库
 */
@Route(path = ArouterParamApp.activity_my_coffers)
public class MyCoffersActivity extends CompanyBaseActivity implements AutoSingleSelectListener<String>, View.OnClickListener, RechargeInforDialog.OnEntryClickListener {
    AutoLineLayout autoLineLayout;//标签
    TextView tv_balance_of_account;//余额
    TextView tv_balance_of_unentry;//待入账金额
    TextView tv_recharge;//充值
    TextView tv_withdraw;//提现
    private AutoTagAdapter<String> tagAdapter;
    RechargeInforDialog inforDialog;

    private CompanyBaseFragment currentFragment, balanceFragment, waiteFragment;
    private boolean isV;//是否开通华夏银行子账户
    private QueryBindHuaInforsBean countInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coffers);
        setWhitToolBar("我的金库");
        setToolbarMsgRight("月账单");
        tvToolbarRight.setTextColor(getResources().getColor(R.color.color_of_666666));
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArouterUtils.getInstance().builder(ArouterParamApp.activity_bill_month)
                        .navigation(mContext);
            }
        });

        initView();
        initData();
        initListener();
        switchFragment(0);
        queryInfo();
    }

    private void queryInfo() {

        Observable.concat(
                HttpAppFactory.querySubAcc()//查询是否已经实名认证
                ,HttpAppFactory.queryAccountDetails()//查询开户信息
                ,HttpAppFactory.queryRechargeInfo()//查询华夏银行开户信息

        ).subscribe(new BaseObserver<CommonBean<? extends Object>>(this) {
            @Override
            public void onNext(CommonBean<?> result) {
                super.onNext(result);
                if (result.getCode() != 200) {
                    onError(new NetException(result.getCode(), result.getMsg()));
                } else {
                    Object data = result.getData();
                    if (data != null) {
                        if (data instanceof QueryVeriBean) {
                            isV = ((QueryVeriBean) data).isSubAccStatus();
                        } else if (data instanceof AccountDetailBean) {
                            tv_balance_of_account.setText(ConvertUtils.changeFloat(((AccountDetailBean) data).getAvailableBalance(),2));
                            tv_balance_of_unentry.setText(ConvertUtils.changeFloat(((AccountDetailBean) data).getTobeCreditedBalance(),2)+"元");
                        } else if (data instanceof QueryBindHuaInforsBean) {
                            countInfor = (QueryBindHuaInforsBean) data;
                        }
                    }
                }
            }


        });


        HttpAppFactory.queryAccountDetails()
                .subscribe(new NetObserver<AccountDetailBean>(null){
                    @Override
                    public void doOnSuccess(AccountDetailBean accountDetailBean) {
                        super.doOnSuccess(accountDetailBean);

                        tv_balance_of_account.setText(ConvertUtils.changeFloat(accountDetailBean.getAvailableBalance(),2));
                        tv_balance_of_unentry.setText(ConvertUtils.changeFloat(accountDetailBean.getTobeCreditedBalance(),2)+"元");
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        queryInfo();
    }

    @Override
    protected void initView() {
        super.initView();
        inforDialog = new RechargeInforDialog(this);
        autoLineLayout = findViewById(R.id.auto_layout);
        tv_balance_of_account = findViewById(R.id.tv_balance_of_account);
        tv_balance_of_unentry = findViewById(R.id.tv_balance_of_unentry);
        tv_recharge = findViewById(R.id.tv_recharge);
        tv_withdraw = findViewById(R.id.tv_withdraw);


    }

    @Override
    protected void initData() {
        super.initData();
        List<String> list = new ArrayList<>();
        list.add("余额明细");
        list.add("待入账明细");

        tagAdapter = new AutoTagSingleAdapter(mContext);
        tagAdapter.setDatas(list);
        tagAdapter.setSingleSelectedListener(this);
        tagAdapter.setSelectsPosition(0);
        AutoLayoutHelper helper = new AutoLayoutHelper();
        helper.setType(0);
        helper.sethGap(DeviceUtils.dip2px(mContext, 25));
        autoLineLayout.setLayoutHelper(helper);
        autoLineLayout.setAdapter(tagAdapter);

    }

    @Override
    protected void initListener() {
        super.initListener();
        tv_balance_of_account.setOnClickListener(this);
        tv_recharge.setOnClickListener(this);
        tv_withdraw.setOnClickListener(this);
        inforDialog.setClickListener(this);
    }

    @Override
    public void onAutoSingleSelected(int position, String s, boolean check) {

        switchFragment(position);

    }

    private void switchFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        if (position == 0) {
            if (balanceFragment == null) {
                balanceFragment = (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_bill_month).navigation();
                transaction.add(R.id.content, balanceFragment);
                Bundle bundle = new Bundle();
                bundle.putInt(Param.TYPE, 1);
                balanceFragment.setBundle(bundle);
            } else {
                transaction.show(balanceFragment);
            }

            currentFragment = balanceFragment;
        } else {
            if (waiteFragment == null) {
                waiteFragment = (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_bill_month).navigation();
                transaction.add(R.id.content, waiteFragment);
                Bundle bundle = new Bundle();
                bundle.putInt(Param.TYPE, 2);
                waiteFragment.setBundle(bundle);
            } else {
                transaction.show(waiteFragment);
            }

            currentFragment = waiteFragment;
        }
        transaction.commit();
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
         if (v.getId() == R.id.tv_balance_of_account) {
            ArouterUtils.getInstance().builder(ArouterParamApp.activity_balance)
                    .navigation(mContext);

        } if (R.id.tv_recharge == v.getId()) {
            if (check()){
                inforDialog.setInfor(countInfor);
                inforDialog.show();
            }

        } else if (R.id.tv_withdraw == v.getId()) {
            if (check()){
                ArouterUtils.getInstance().builder(ArouterParamApp.activity_balance_pending)
                        .navigation(mContext);
            }
        }
    }

    private boolean check(){
        boolean result=false;
        if (isV) {
            if (countInfor==null){
                //跳转到绑卡
                ArouterUtils
                        .getInstance()
                        .builder(ArouterParamApp.activity_bind_blank_card)
                        .navigation(this,1);
            }else {
                result=true;

            }
        } else {
            // TODO 实名认证界面
            ToastUtils.getInstance().show("尚未实名认证");
        }
        return result;
    }

    @Override
    public void onClickEntry(String msg) {
        if (countInfor != null) {
            CommonUtils.copyToPlate(mContext, countInfor.getOthBankPayeeSubAcc());
            ToastUtils.getInstance().show("复制账号成功");
        }
    }
}
