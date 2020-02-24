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
 * 个人承运人身份认证第二部
 */
@Route(path = ArouterParamApp.fragment_attestation_shipper_personal)
public class AttestationShipperPersonalFragment extends CompanyBaseFragment implements ItemTextView.OnCenterChangeListener, View.OnClickListener {

    private View root;
    private ItemTextView item_name;//姓名
    private ItemTextView item_id_card;//身份证号码
    private ItemTextView item_email;//邮箱
    private View ll_driver;//道路运输许可证
    private View ll_qualification;//挂靠协议
    private ImageView img_driver;//道路运输许可证
    private ImageView img_qualification;//挂靠协议
    private TextView bt_sum;//邮箱

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_attestation_shipper_personal, null);
        root = inflate;
        item_name = inflate.findViewById(R.id.item_name);
        item_id_card = inflate.findViewById(R.id.item_id_card);
        item_email = inflate.findViewById(R.id.item_email);
        bt_sum = inflate.findViewById(R.id.bt_sum);
        ll_driver = inflate.findViewById(R.id.ll_driver);
        ll_qualification = inflate.findViewById(R.id.ll_qualification);
        img_driver = inflate.findViewById(R.id.img_driver);
        img_qualification = inflate.findViewById(R.id.img_qualification);
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
        item_name.setOnCenterChangeListener(this);
        item_id_card.setOnCenterChangeListener(this);
        item_email.setOnCenterChangeListener(this);
        bt_sum.setOnClickListener(this);
        ll_qualification.setOnClickListener(this);
        ll_driver.setOnClickListener(this);
    }

    private boolean check(boolean showAlert) {
        Utils.setButton(bt_sum, false);
        if (!check(item_name, showAlert)
                || !check(item_id_card, showAlert)
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
        } else if (v.getId() == R.id.ll_driver) {
            ToastUtils.getInstance().show("道路运输许可证");

        } else if (v.getId() == R.id.ll_qualification) {
            ToastUtils.getInstance().show("挂靠协议");

        }
    }

    @Override
    public void onCenterChange(String msg) {
        check(false);
    }
}
