package com.hongniu.freight.ui.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;
import com.hongniu.freight.utils.Utils;

/**
 * 作者：  on 2020/2/24.
 * 公司托运人身份认证第二部
 */
@Route(path = ArouterParamApp.fragment_attestation_shipper_company)
public class AttestationShipperCompanyFragment extends CompanyBaseFragment implements ItemTextView.OnCenterChangeListener, View.OnClickListener {

    private View root;
    private ItemTextView item_company_name;//企业名称
    private ItemTextView item_company_address;//企业地址
    private ItemTextView item_name;//姓名
    private ItemTextView item_phone;//联系人手机号
    private ItemTextView item_email;//邮箱
    private View ll_business_license;//挂靠协议
    private ImageView img_business_license;//营业执照
    private TextView bt_sum;//邮箱

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_attestation_shipper_company, null);
        root = inflate;
        item_company_name = inflate.findViewById(R.id.item_company_name);
        item_company_address = inflate.findViewById(R.id.item_company_address);
        item_name = inflate.findViewById(R.id.item_name);
        item_phone = inflate.findViewById(R.id.item_phone);
        item_email = inflate.findViewById(R.id.item_email);
        bt_sum = inflate.findViewById(R.id.bt_sum);
        ll_business_license = inflate.findViewById(R.id.ll_business_license);
        img_business_license = inflate.findViewById(R.id.img_business_license);
        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
        check(false);
    }

    @Override
    protected void initListener() {
        super.initListener();
        item_company_name.setOnCenterChangeListener(this);
        item_company_address.setOnCenterChangeListener(this);
        item_name.setOnCenterChangeListener(this);
        item_phone.setOnCenterChangeListener(this);
        item_email.setOnCenterChangeListener(this);
        bt_sum.setOnClickListener(this);
        ll_business_license.setOnClickListener(this);
    }

    private boolean check(boolean showAlert) {
        Utils.setButton(bt_sum, false);
        if (!check(item_company_name, showAlert)
                || !check(item_company_address, showAlert)
                || !check(item_name, showAlert)
                || !check(item_phone, showAlert)
                || !check(item_email, showAlert)

        ) {
            return false;
        }
        Utils.setButton(bt_sum, true);

        return true;
    }


    private boolean check(ItemTextView itemTextView, boolean showAlert) {
        if (TextUtils.isEmpty(itemTextView.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(itemTextView.getTextCenterHide());
            }
            return false;
        }

        return true;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_sum) {
            if (check(true)) {
                ToastUtils.getInstance().show("下一步");
            }
        } else  if (v.getId() == R.id.ll_business_license) {
            ToastUtils.getInstance().show("营业执照");

        }
    }

    @Override
    public void onCenterChange(String msg) {
        check(false);
    }
}
