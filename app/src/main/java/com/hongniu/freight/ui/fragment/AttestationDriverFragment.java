package com.hongniu.freight.ui.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.imgload.ImageLoader;
import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.net.NetObserver;
import com.fy.androidlibrary.utils.permission.PermissionUtils;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.UpImgData;
import com.hongniu.freight.entity.VerifyPersonParams;
import com.hongniu.freight.entity.VerifyInfoBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.Utils;
import com.hongniu.thirdlibrary.picture.PictureClient;
import com.hongniu.thirdlibrary.picture.utils.PicUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 作者：  on 2020/2/24.
 * 司机身份认证
 */
@Route(path = ArouterParamApp.fragment_attestation_driver)
public class AttestationDriverFragment extends AttestationBaseFragment implements ItemTextView.OnCenterChangeListener, View.OnClickListener {
    private ItemTextView item_name;//姓名
    private ItemTextView item_id_card;//身份证号码
    private ItemTextView item_email;//邮箱
    private View ll_driver;//道路运输许可证
    private View ll_driver1;//道路运输许可证
    private View ll_qualification;//从业资格证
    private ImageView img_driver;//驾照
    private ImageView img_driver1;//驾照
    private ImageView img_qualification;//从业资格证

    private UpImgData driverInfo;//驾照
    private UpImgData driverInfo1;//驾照附页
    private UpImgData qualificationInfo;//从业资格证
    private int isDriver;//驾照是否上传完成
    private int isqualification;//从业资格证否上传完成
    private int isDriver1;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_attestation_driver, null);
        item_name = inflate.findViewById(R.id.item_name);
        img_driver1 = inflate.findViewById(R.id.img_driver1);
        item_id_card = inflate.findViewById(R.id.item_id_card);
        item_email = inflate.findViewById(R.id.item_email);
        bt_sum = inflate.findViewById(R.id.bt_sum);
        ll_driver = inflate.findViewById(R.id.ll_driver);
        ll_qualification = inflate.findViewById(R.id.ll_qualification);
        img_driver = inflate.findViewById(R.id.img_driver);
        img_qualification = inflate.findViewById(R.id.img_qualification);
        ll_driver1 = inflate.findViewById(R.id.ll_driver1);
        return inflate;
    }


    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initInfo(VerifyInfoBean verifyInfoBean) {
        VerifyPersonParams qcIdentity = verifyInfoBean.getQcIdentity();
        VerifyPersonParams idnumIdentity = verifyInfoBean.getDlIdentity();
        if (idnumIdentity != null) {
            item_email.setTextCenter(idnumIdentity.getEmail());
            item_id_card.setTextCenter(idnumIdentity.getIdnumber());
            item_name.setTextCenter(idnumIdentity.getName());
            ImageLoader.getLoader().load(mContext, img_driver, idnumIdentity.getFaceDLImageUrl());
            ImageLoader.getLoader().load(mContext, img_driver1, idnumIdentity.getBackDLImageUrl());
            ll_driver.setVisibility(View.GONE);
            ll_driver1.setVisibility(View.GONE);

        }
        if (qcIdentity!=null){
            ImageLoader.getLoader().load(mContext, img_qualification, qcIdentity.getQualificationCertificateImageUrl());
            ll_qualification.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        item_name.setOnCenterChangeListener(this);
        item_id_card.setOnCenterChangeListener(this);
        item_email.setOnCenterChangeListener(this);
        bt_sum.setOnClickListener(this);
        img_driver.setOnClickListener(this);
        img_driver1.setOnClickListener(this);
        img_qualification.setOnClickListener(this);
    }

    private boolean check(boolean showAlert) {
        Utils.setButton(bt_sum, false);
        if (!check(item_name, showAlert)
                || !check(item_id_card, showAlert)
                || !check(item_email, showAlert)

        ) {
            return false;
        }

        if (isDriver == 0) {
            if (showAlert) {
                ToastUtils.getInstance().show("请上驾照主页");
            }
            return false;
        } else if (isDriver == 1) {
            if (showAlert) {
                ToastUtils.getInstance().show("驾照主页上传中,请稍后");
            }
            return false;
        } else if (isDriver == 3) {
            if (showAlert) {
                ToastUtils.getInstance().show("驾照主页上传失败,请重试");
            }
            return false;
        }
        if (isDriver1 == 0) {
            if (showAlert) {
                ToastUtils.getInstance().show("请上驾照副页");
            }
            return false;
        } else if (isDriver1 == 1) {
            if (showAlert) {
                ToastUtils.getInstance().show("驾照副页上传中,请稍后");
            }
            return false;
        } else if (isDriver1 == 3) {
            if (showAlert) {
                ToastUtils.getInstance().show("驾照副页上传失败,请重试");
            }
            return false;
        }
        if (isqualification == 0) {
            if (showAlert) {
                ToastUtils.getInstance().show("请上传从业资格证");
            }
            return false;
        } else if (isqualification == 1) {
            if (showAlert) {
                ToastUtils.getInstance().show("从业资格证上传中,请稍后");
            }
            return false;
        } else if (isqualification == 3) {
            if (showAlert) {
                ToastUtils.getInstance().show("从业资格证上传失败,请重试");
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_sum) {
            if (check(true)) {
                VerifyPersonParams params = new VerifyPersonParams();
                params.setEmail(item_email.getTextCenter());
                params.setIdnumber(item_id_card.getTextCenter());
                params.setName(item_name.getTextCenter());
                params.setFaceDLImageUrl(driverInfo.getPath());
                params.setBackDLImageUrl(driverInfo1.getPath());
                params.setQualificationCertificateImageUrl(qualificationInfo.getPath());

                HttpAppFactory.verifyDriver(params)
                        .subscribe(new NetObserver<String>(this) {
                            @Override
                            public void doOnSuccess(String s) {
                                super.doOnSuccess(s);
                                ArouterUtils.getInstance().builder(ArouterParamApp.activity_attestation_face)
                                        .navigation(getContext());
                            }
                        });

            }
        } else if (v.getId() == R.id.img_driver) {
//            驾照
            startPhoto(new OnResultCallbackListener() {
                @Override
                public void onResult(List<LocalMedia> result) {
                    if (!CollectionUtils.isEmpty(result)) {
                        check(false);
                        ll_driver.setVisibility(View.GONE);
                        String path = PicUtils.getPath(result.get(0));
                        ImageLoader.getLoader().load(mContext, img_driver, path);
                        HttpAppFactory.upImage(12,
                                path
                                , null
                        )
                                .subscribe(new BaseObserver<UpImgData>(null) {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        super.onSubscribe(d);
                                        isDriver = 1;
                                        check(false);
                                    }

                                    @Override
                                    public void onNext(UpImgData result) {
                                        super.onNext(result);
                                        isDriver = 2;
                                        driverInfo = result;
                                        check(false);
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

        } else if (v.getId() == R.id.img_driver1) {
//            驾照
            startPhoto(new OnResultCallbackListener() {
                @Override
                public void onResult(List<LocalMedia> result) {
                    if (!CollectionUtils.isEmpty(result)) {
                        check(false);
                        ll_driver1.setVisibility(View.GONE);
                        String path = PicUtils.getPath(result.get(0));
                        ImageLoader.getLoader().load(mContext, img_driver1, path);
                        HttpAppFactory.upImage(12,
                                path
                                , null
                        )
                                .subscribe(new BaseObserver<UpImgData>(null) {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        super.onSubscribe(d);
                                        isDriver1 = 1;
                                        check(false);
                                    }

                                    @Override
                                    public void onNext(UpImgData result) {
                                        super.onNext(result);
                                        isDriver1 = 2;
                                        driverInfo1 = result;
                                        check(false);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        super.onError(e);
                                        isDriver1 = 3;
                                    }
                                });

                    }

                }
            });

        } else if (v.getId() == R.id.img_qualification) {
//            ToastUtils.getInstance().show("从业资格证");
            startPhoto(new OnResultCallbackListener() {
                @Override
                public void onResult(List<LocalMedia> result) {
                    check(false);
                    if (!CollectionUtils.isEmpty(result)) {
                        ll_qualification.setVisibility(View.GONE);
                        String path = PicUtils.getPath(result.get(0));
                        ImageLoader.getLoader().load(mContext, img_qualification, path);
                        HttpAppFactory.upImage(15,
                                path
                                , null
                        )
                                .subscribe(new BaseObserver<UpImgData>(null) {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        super.onSubscribe(d);
                                        isqualification = 1;
                                        check(false);
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
                                        check(false);
                                    }
                                });

                    }

                }
            });
        }
    }

    @Override
    public void onCenterChange(String msg) {
        check(false);
    }

    private void startPhoto(OnResultCallbackListener listener) {
        PermissionUtils.applyCamera(getActivity(), new PermissionUtils.onApplyPermission() {
            @Override
            public void hasPermission() {
                new PictureClient()
                        .startPhoto(AttestationDriverFragment.this, 1, null, listener);
            }

            @Override
            public void noPermission() {

            }
        });
    }
}
