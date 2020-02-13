package com.hongniu.freight.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;
import com.hongniu.freight.utils.PickerDialogUtils;
import com.hongniu.freight.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @data 2020/2/13
 * @Author PING
 * @Description 发布找车信息
 */
@Route(path = ArouterParamApp.activity_find_car)
public class FindCarActivity extends CompanyBaseActivity implements View.OnClickListener, OnOptionsSelectListener, ItemTextView.OnCenterChangeListener {

    private TextView bt_sum;
    private ItemTextView item_price;
    private ItemTextView item_car_type;

    private List<String> carTypes;
    private OptionsPickerView pickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_car);
        setWhitToolBar("");
        initView();
        initData();
        initListener();
        check(false);
    }

    @Override
    protected void initView() {
        super.initView();
        bt_sum = findViewById(R.id.bt_sum);
        item_price = findViewById(R.id.item_price);
        item_car_type = findViewById(R.id.item_car_type);
        pickerView = PickerDialogUtils.initPickerDialog(mContext, this);
    }

    @Override
    protected void initData() {
        super.initData();
        carTypes = new ArrayList<>();
        carTypes.add("特种车");
        carTypes.add("小火车");
        carTypes.add("工程车");
        carTypes.add("冷冻车");
        carTypes.add("大货车");
        pickerView.setPicker(carTypes);
    }

    @Override
    protected void initListener() {
        super.initListener();
        bt_sum.setOnClickListener(this);
        item_car_type.setOnClickListener(this);
        item_car_type.setOnCenterChangeListener(this);
        item_price.setOnCenterChangeListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Utils.setButton(bt_sum, true);

        if (v.getId() == R.id.bt_sum) {
            if (check(true)) {
                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                finish();
            }
        } else if (v.getId() == R.id.item_car_type) {
            pickerView.show();
        }

    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        item_car_type.setTextCenter(carTypes.get(options1));
    }

    private boolean check(boolean showAlert) {
        Utils.setButton(bt_sum, false);

        if (TextUtils.isEmpty(item_car_type.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_car_type.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_price.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_price.getTextCenterHide());
            }
            return false;
        }
        Utils.setButton(bt_sum, true);

        return true;
    }

    @Override
    public void onCenterChange(String msg) {
        check(false);
    }
}
