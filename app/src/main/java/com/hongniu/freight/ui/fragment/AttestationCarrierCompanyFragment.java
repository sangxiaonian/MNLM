package com.hongniu.freight.ui.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.imgload.ImageLoader;
import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.utils.permission.PermissionUtils;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.UpImgData;
import com.hongniu.freight.entity.VerifyCompanyParams;
import com.hongniu.freight.entity.VerifyInfoBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.Utils;
import com.hongniu.freight.widget.ImageInforView;
import com.hongniu.thirdlibrary.picture.PictureClient;
import com.hongniu.thirdlibrary.picture.utils.PicUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 作者：  on 2020/2/24.
 * 公司承运人身份认证第二部
 */
@Route(path = ArouterParamApp.fragment_attestation_carrier_company)
public class AttestationCarrierCompanyFragment extends AttestationBaseFragment implements View.OnClickListener, ItemTextView.OnCenterChangeListener {

    private ItemTextView item_company_name;//姓名
    private ItemTextView item_company_address;//姓名
    private ItemTextView item_name;//姓名
    private ItemTextView item_id_card;//身份证号码

    private ItemTextView item_phone;//电话号码
    private ItemTextView item_email;//邮箱
    private ImageInforView img_driver;//邮箱
    private ImageInforView img_business_license;//营业执照

    private UpImgData driverInfo;//道路运输许可证
    private UpImgData qualificationInfo;//营业执照
    private int isDriver;//道路运输许可证是否上传完成
    private int isqualification;//挂靠协议否上传完成

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_attestation_carrier_company, null);
        item_company_name = inflate.findViewById(R.id.item_company_name);
        item_company_address = inflate.findViewById(R.id.item_company_address);
        item_name = inflate.findViewById(R.id.item_name);
        item_id_card = inflate.findViewById(R.id.item_id_card);
        item_phone = inflate.findViewById(R.id.item_phone);
        item_email = inflate.findViewById(R.id.item_email);
        bt_sum = inflate.findViewById(R.id.bt_sum);
        img_driver = inflate.findViewById(R.id.img_driver);
        img_business_license = inflate.findViewById(R.id.img_business_license);
        root = inflate;
        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        img_driver.setOnClickListener(this);
        img_business_license.setOnClickListener(this);
        bt_sum.setOnClickListener(this);
        item_company_name.setOnCenterChangeListener(this);
        item_company_address.setOnCenterChangeListener(this);
        item_name.setOnCenterChangeListener(this);
        item_id_card.setOnCenterChangeListener(this);
        item_phone.setOnCenterChangeListener(this);
        item_email.setOnCenterChangeListener(this);
    }

    @Override
    protected void initInfo(VerifyInfoBean verifyInfoBean) {
        VerifyCompanyParams identity = verifyInfoBean.getCompanyCarrier();
        //身份认证信息
        if (identity != null) {
            item_company_address.setTextCenter(identity.getCompanyAddress());
            item_company_name.setTextCenter(identity.getCompanyName());
            item_email.setTextCenter(identity.getContactEmail());
            item_name.setTextCenter(identity.getCompanyName());
            item_id_card.setTextCenter(identity.getIdnumber());
            item_phone.setTextCenter(identity.getContactMobile());
            img_business_license.setImageInfo(identity.getBusinessLicenseImageUrl());
            img_driver.setImageInfo(identity.getRoadTransportPermitImageUrl());
            if (!TextUtils.isEmpty(identity.getRoadTransportPermitImageUrl())) {
                driverInfo = new UpImgData();
                driverInfo.setAbsolutePath(identity.getRoadTransportPermitImageUrl());
                driverInfo.setPath(identity.getRoadTransportPermitImageUrl());
            }
            if (!TextUtils.isEmpty(identity.getBusinessLicenseImageUrl())) {
                qualificationInfo = new UpImgData();
                qualificationInfo.setPath(identity.getBusinessLicenseImageUrl());
                qualificationInfo.setAbsolutePath(identity.getBusinessLicenseImageUrl());
            }
        }
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_driver) {
            //            道路运输许可证
            startPhoto(new OnResultCallbackListener() {
                @Override
                public void onResult(List<LocalMedia> result) {
                    if (!CollectionUtils.isEmpty(result)) {
                        check(false);
                        String path = PicUtils.getPath(result.get(0));
                        img_driver.setImageInfo(path);
                        HttpAppFactory.upImage(13,
                                path
                                , null
                        )
                                .subscribe(new BaseObserver<UpImgData>(null) {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        super.onSubscribe(d);
                                        isDriver = 1;
                                    }

                                    @Override
                                    public void onNext(UpImgData result) {
                                        super.onNext(result);
                                        isDriver = 2;
                                        check(false);
                                        driverInfo = result;
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        super.onError(e);
                                        isDriver = 3;
                                    }
                                });

                    }

                }
            });
        } else if (v.getId() == R.id.img_business_license) {
            //            ToastUtils.getInstance().show("营业执照");
            startPhoto(new OnResultCallbackListener() {
                @Override
                public void onResult(List<LocalMedia> result) {
                    check(false);
                    if (!CollectionUtils.isEmpty(result)) {
                        String path = PicUtils.getPath(result.get(0));
                        img_business_license.setImageInfo(path);
                        HttpAppFactory.upImage(7,
                                path
                                , null
                        )
                                .subscribe(new BaseObserver<UpImgData>(null) {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        super.onSubscribe(d);
                                        isqualification = 1;
                                    }

                                    @Override
                                    public void onNext(UpImgData result) {
                                        super.onNext(result);
                                        qualificationInfo = result;
                                        isqualification = 2;
                                        check(false);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        super.onError(e);
                                        isqualification = 3;
                                    }
                                });

                    }

                }
            });
        } else if (v.getId() == R.id.bt_sum) {
            if (check(true)) {
                VerifyCompanyParams params = new VerifyCompanyParams();
                params.setCompanyAddress(item_company_address.getTextCenter());
                params.setCompanyName(item_company_name.getTextCenter());
                params.setContactName(item_name.getTextCenter());
                params.setContactEmail(item_email.getTextCenter());
                params.setContactMobile(item_phone.getTextCenter());
                params.setBusinessLicenseImageUrl(qualificationInfo.getPath());
                params.setRoadTransportPermitImageUrl(driverInfo.getPath());
                params.setContactMobile(item_phone.getTextCenter());
                params.setIdnumber(item_id_card.getTextCenter());
                params.setName(item_name.getTextCenter());

                HttpAppFactory.verifyCarrierCompany(params)
                        .subscribe(new NetObserver<String>(this) {
                            @Override
                            public void doOnSuccess(String s) {
                                super.doOnSuccess(s);
                                ArouterUtils.getInstance().builder(ArouterParamApp.activity_attestation_face)
                                        .navigation(getContext());
                            }
                        });

            }
        }
    }

    private void startPhoto(OnResultCallbackListener listener) {
        PermissionUtils.applyCamera(getActivity(), new PermissionUtils.onApplyPermission() {
            @Override
            public void hasPermission() {
                new PictureClient()
                        .startPhoto(AttestationCarrierCompanyFragment.this, 1, null, listener);
            }

            @Override
            public void noPermission() {

            }
        });
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

        if (isDriver == 0) {
            if (showAlert) {
                ToastUtils.getInstance().show("请上传道路运输许可证");
            }
            return false;
        } else if (isDriver == 1) {
            if (showAlert) {
                ToastUtils.getInstance().show("道路运输许可证上传中,请稍后");
            }
            return false;
        } else if (isDriver == 3) {
            if (showAlert) {
                ToastUtils.getInstance().show("道路运输许可证上传失败,请重试");
            }
            return false;
        }
        if (isqualification == 0) {
            if (showAlert) {
                ToastUtils.getInstance().show("请上传营业执照");
            }
            return false;
        } else if (isqualification == 1) {
            if (showAlert) {
                ToastUtils.getInstance().show("营业执照上传中,请稍后");
            }
            return false;
        } else if (isqualification == 3) {
            if (showAlert) {
                ToastUtils.getInstance().show("营业执照上传失败,请重试");
            }
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

    @Override
    public void onCenterChange(String msg) {
        check(false);
    }


}
