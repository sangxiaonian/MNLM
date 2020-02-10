package com.hongniu.freight.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.utils.PickerDialogUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @data 2020/2/7
 * @Author PING
 * @Description 我的订单页面
 */
@Route(path = ArouterParamApp.fragment_order_home)
public class HomeOrderFragment extends CompanyBaseFragment implements OnOptionsSelectListener, View.OnClickListener {

    private Role role=Role.SHIPPER;
    private TextView tvTitle;
    private TextView tvRole;
    private View ll_filtrate;
    private ArrayList<String> roles;
    private OptionsPickerView pickerView;

    private CompanyBaseFragment currentFragment,driverFragment,shipperFragment,carrierFragment;
    private View search;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_order_home, null);
        tvTitle = inflate.findViewById(R.id.tv_title);
        tvRole = inflate.findViewById(R.id.tv_role);
        ll_filtrate = inflate.findViewById(R.id.ll_filtrate);
        search = inflate.findViewById(R.id.search);

        return inflate;

    }






    @Override
    protected void initData() {
        super.initData();
        pickerView = PickerDialogUtils.initPickerDialog(mContext, this);

        roles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.roles)));
        pickerView.setPicker(roles);
        pickerView.setTitleText("选择身份类型");

        switchFragment(role);
        tvRole.setText(role.getName());

    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_filtrate.setOnClickListener(this);
        search.setOnClickListener(this);
    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        role=getRole(options1);
        switchFragment(role);
        tvRole.setText(role.getName());
    }

    private Role getRole(int options1) {
        Role role=Role.SHIPPER;
        if (options1==0){
            role= Role.SHIPPER;
        }else if (options1==1){
            role=Role.CARRIER;
        }else if (options1==2){
            role=Role.DRIVER;
        }
        return role;
    }

    private void switchFragment(Role role) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (currentFragment!=null){
            transaction.hide(currentFragment);
        }
        if (role==Role.SHIPPER){
            if (shipperFragment==null){
                shipperFragment= (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_my_order).navigation();
                Bundle bundle=new Bundle();
                bundle.putSerializable(Param.TRAN,role);
                shipperFragment.setBundle(bundle);
                transaction.add(R.id.content,shipperFragment);
            }else {
                transaction.show(shipperFragment);
            }
            currentFragment=shipperFragment;

        }else if (role==Role.CARRIER){
            if (carrierFragment==null){
                carrierFragment= (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_my_order).navigation();
                Bundle bundle=new Bundle();
                bundle.putSerializable(Param.TRAN,role);
                carrierFragment.setBundle(bundle);
                transaction.add(R.id.content,carrierFragment);
            }else {
                transaction.show(carrierFragment);
            }
            currentFragment=carrierFragment;
        }else if (role==Role.DRIVER){
            if (driverFragment==null){
                driverFragment= (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_my_order).navigation();
                Bundle bundle=new Bundle();
                bundle.putSerializable(Param.TRAN,role);
                driverFragment.setBundle(bundle);
                transaction.add(R.id.content,driverFragment);
            }else {
                transaction.show(driverFragment);
            }
            currentFragment=driverFragment;
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
        if (v.getId()==R.id.ll_filtrate){
            pickerView.setSelectOptions(role.getType());
            pickerView.show();
        }else if (v.getId()==R.id.search){
            ArouterUtils.getInstance().builder(ArouterParamApp.activity_search_order)
                    .withSerializable(Param.TRAN,role)
                    .navigation(mContext);
        }
    }
}
