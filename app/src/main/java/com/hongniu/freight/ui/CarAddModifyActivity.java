package com.hongniu.freight.ui;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.fy.androidlibrary.imgload.ImageLoader;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;
import com.hongniu.freight.control.CarAddModifyControl;
import com.hongniu.freight.entity.CarInfoBean;
import com.hongniu.freight.entity.CarTypeBean;
import com.hongniu.freight.entity.UpImgData;
import com.hongniu.freight.presenter.CarAddModifyPresenter;
import com.hongniu.freight.utils.PickerDialogUtils;
import com.hongniu.freight.utils.Utils;
import com.hongniu.thirdlibrary.picture.PictureClient;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.List;

/**
 * @data 2020/3/3
 * @Author PING
 * @Description 新增修改车辆信息
 */
@Route(path = ArouterParamApp.activity_car_add_modify)
public class CarAddModifyActivity extends CompanyBaseActivity implements View.OnClickListener, CarAddModifyControl.ICarAddModifyView, OnOptionsSelectListener, ItemTextView.OnCenterChangeListener {

    private TextView btSum;
    private ImageView img_positive;//驾驶证正面
    private ImageView img_minus;//驾驶证副面
    private View ll_positive;//驾驶证副面
    private View ll_minus;//驾驶证副面
    private ItemTextView item_car_type;//车辆类型
    private ItemTextView item_car_band;//车辆品牌
    private ItemTextView item_car_number;//车牌号
    private ItemTextView item_car_name;//姓名
    private ItemTextView item_car_phone;//手机号

    CarAddModifyControl.ICarAddModifyPresenter presenter;
    private OptionsPickerView<CarTypeBean> pickerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_add_modify);
        setWhitToolBar("填写车辆信息");
        initView();
        initData();
        initListener();
        presenter = new CarAddModifyPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        img_positive = findViewById(R.id.img_positive);
        img_minus = findViewById(R.id.img_minus);
        ll_positive = findViewById(R.id.ll_positive);
        ll_minus = findViewById(R.id.ll_minus);
        item_car_type = findViewById(R.id.item_car_type);
        item_car_band = findViewById(R.id.item_car_band);
        item_car_number = findViewById(R.id.item_car_number);
        item_car_name = findViewById(R.id.item_car_name);
        item_car_phone = findViewById(R.id.item_car_phone);
        btSum = findViewById(R.id.bt_sum);
    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_positive.setOnClickListener(this);
        ll_minus.setOnClickListener(this);
        item_car_type.setOnClickListener(this);
        btSum.setOnClickListener(this);
        item_car_type.setOnCenterChangeListener(this);
        item_car_band.setOnCenterChangeListener(this);
        item_car_number.setOnCenterChangeListener(this);
        item_car_name.setOnCenterChangeListener(this);
        item_car_phone.setOnCenterChangeListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_positive) {
            ToastUtils.getInstance().show("行驶证正面");
            new PictureClient().startPhoto(this, 1, null, new OnResultCallbackListener() {
                @Override
                public void onResult(List<LocalMedia> result) {
                    LocalMedia media = result.get(0);
                    ImageLoader.getLoader().load(mContext, img_positive, Utils.getPath(media));
                    presenter.upPositive(media, CarAddModifyActivity.this);
                }
            });
        } else if (v.getId() == R.id.ll_minus) {
            new PictureClient().startPhoto(this, 1, null, new OnResultCallbackListener() {
                @Override
                public void onResult(List<LocalMedia> result) {
                    LocalMedia media = result.get(0);
                    ImageLoader.getLoader().load(mContext, img_minus, Utils.getPath(media));
                    presenter.upMinus(media, CarAddModifyActivity.this);
                }
            });
        } else if (v.getId() == R.id.item_car_type) {
//            ToastUtils.getInstance().show("车辆类型");
            presenter.queryCarTypes(this);
        } else if (v.getId() == R.id.bt_sum) {
//            ToastUtils.getInstance().show("车辆类型");
            if (check(true)) {

                CarInfoBean infoBean = new CarInfoBean();
                infoBean.setCarNumber(item_car_number.getTextCenter());
                infoBean.setVehicleModel(item_car_band.getTextCenter());
                infoBean.setName(item_car_name.getTextCenter());
                infoBean.setMobile(item_car_phone.getTextCenter());
                presenter.createCar(infoBean, this);
            }
        }
    }
    private boolean check(boolean showAlert) {
        Utils.setButton(btSum, false);
        if (!presenter.checkPositive()) {
            if (showAlert) {
                ToastUtils.getInstance().show("请上传行驶证主页信息");
            }
            return false;
        }
        if (!presenter.checkMinus()) {
            if (showAlert) {
                ToastUtils.getInstance().show("请上传行驶证副页信息");
            }
            return false;
        }

        if (TextUtils.isEmpty(item_car_type.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_car_type.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_car_band.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_car_band.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_car_number.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_car_number.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_car_name.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_car_name.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_car_phone.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_car_phone.getTextCenterHide());
            }
            return false;
        }
        Utils.setButton(btSum, true);
        return true;
    }

    /**
     * 显示选择车辆类型
     *
     * @param carTypeBeans
     */
    @Override
    public void showCarTypesDialog(List<CarTypeBean> carTypeBeans) {
        if (pickerView == null) {
            pickerView = PickerDialogUtils.creatPickerDialog(mContext, this).build();
            pickerView.setTitleText("选择车辆类型");
            pickerView.setPicker(carTypeBeans);
        }
        pickerView.show();
    }

    /**
     * 显示车辆类型
     *
     * @param carType
     */
    @Override
    public void showCarType(String carType) {
        item_car_type.setTextCenter(carType);
    }

    /**
     * 显示图片
     *
     * @param result
     */
    @Override
    public void showMinus(UpImgData result) {
        check(false);
        ll_minus.setVisibility(result == null ? View.VISIBLE : View.GONE);
        if (result == null) {
            img_minus.setImageDrawable(new ColorDrawable(getResources().getColor(R.color.color_of_f6f5f8)));
        } else {
            ImageLoader.getLoader().load(mContext, img_minus, result.getAbsolutePath());
        }
    }

    /**
     * 显示图片
     */
    @Override
    public void showPositive(UpImgData result) {
        check(false);

        ll_positive.setVisibility(result == null ? View.VISIBLE : View.GONE);
        if (result == null) {
            img_positive.setImageDrawable(new ColorDrawable(getResources().getColor(R.color.color_of_f6f5f8)));
        } else {
            ImageLoader.getLoader().load(mContext, img_positive, result.getAbsolutePath());
        }
    }

    /**
     * 新增完成
     */
    @Override
    public void finishWithSuccess() {
        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
        finish();
    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        presenter.onSwitchCarType(options1);
    }

    @Override
    public void onCenterChange(String msg) {
        check(false);
    }
}
