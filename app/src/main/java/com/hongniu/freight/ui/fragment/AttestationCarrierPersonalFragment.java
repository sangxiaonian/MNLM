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
import com.fy.companylibrary.utils.PermissionUtils;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.UpImgData;
import com.hongniu.freight.entity.VerifyPersonParams;
import com.hongniu.freight.entity.VerifyIdNumIdentityBean;
import com.hongniu.freight.entity.VerifyInfoBean;
import com.hongniu.freight.entity.VerifyRtpIdentityBean;
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
 * 个人承运人身份认证第二部
 */
@Route(path = ArouterParamApp.fragment_attestation_carrier_personal)
public class AttestationCarrierPersonalFragment extends AttestationBaseFragment implements ItemTextView.OnCenterChangeListener, View.OnClickListener {

    private View root;
    private ItemTextView item_name;//姓名
    private ItemTextView item_id_card;//身份证号码
    private ItemTextView item_email;//邮箱
    private View ll_driver;//道路运输许可证
    private View ll_qualification;//挂靠协议
    private ImageView img_driver;//道路运输许可证
    private ImageView img_qualification;//挂靠协议

    private UpImgData driverInfo;//道路运输许可证
    private UpImgData qualificationInfo;//挂靠协议
    private int isDriver;//道路运输许可证是否上传完成
    private int isqualification;//挂靠协议否上传完成

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_attestation_carrier_personal, null);
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
    protected void initInfo(VerifyInfoBean verifyInfoBean) {
        VerifyIdNumIdentityBean idnumIdentity = verifyInfoBean.getIdnumIdentity();
        if (idnumIdentity != null) {
            item_email.setTextCenter(idnumIdentity.getEmail());
            item_id_card.setTextCenter(idnumIdentity.getIdnumber());
            item_name.setTextCenter(idnumIdentity.getName());
        }
        //道路运输许可证
        VerifyRtpIdentityBean rtpIdentity = verifyInfoBean.getRtpIdentity();
        if (rtpIdentity!=null){
            ll_driver.setVisibility(View.GONE);
            ImageLoader.getLoader().load(mContext,img_driver,rtpIdentity.getRoadTransportPermitImageUrl());
            driverInfo=new UpImgData();
            driverInfo.setAbsolutePath(rtpIdentity.getRoadTransportPermitImageUrl());
        }
        //挂靠协议
        VerifyRtpIdentityBean aaIdentity = verifyInfoBean.getAaIdentity();
        if (rtpIdentity!=null){
            ll_qualification.setVisibility(View.GONE);
            ImageLoader.getLoader().load(mContext,img_qualification,aaIdentity.getRoadTransportPermitImageUrl());
            qualificationInfo=new UpImgData();
            qualificationInfo.setAbsolutePath(rtpIdentity.getAffiliationAgreementImageUrl());
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

        if (isDriver==0){
            if (showAlert){
                ToastUtils.getInstance().show("请上传道路运输许可证");
            }
            return false;
        }else if (isDriver==1){
            if (showAlert){
                ToastUtils.getInstance().show("道路运输许可证上传中,请稍后");
            }
            return false;
        }else if   (isDriver==3){
            if (showAlert){
                ToastUtils.getInstance().show("道路运输许可证上传失败,请重试");
            }
            return false;
        }
        if (isqualification==0){
            if (showAlert){
                ToastUtils.getInstance().show("请上传挂靠协议");
            }
            return false;
        }else if (isqualification==1){
            if (showAlert){
                ToastUtils.getInstance().show("挂靠协议上传中,请稍后");
            }
            return false;
        }else if   (isqualification==3){
            if (showAlert){
                ToastUtils.getInstance().show("挂靠协议上传失败,请重试");
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
                VerifyPersonParams params=new VerifyPersonParams();
                params.setName(item_name.getTextCenter());
                params.setIdnumber(item_id_card.getTextCenter());
                params.setEmail(item_email.getTextCenter());
                params.setAffiliationAgreementImageUrl(driverInfo.getAbsolutePath());
                params.setRoadTransportPermitImageUrl(qualificationInfo.getAbsolutePath());
                HttpAppFactory.verifyCarrierPerson(params)
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
//            道路运输许可证
            startPhoto(new OnResultCallbackListener() {
                @Override
                public void onResult(List<LocalMedia> result) {
                    if (!CollectionUtils.isEmpty(result)){
                        check(false);
                        ll_driver.setVisibility(View.GONE);
                        String path = PicUtils.getPath(result.get(0));
                        ImageLoader.getLoader().load(mContext,img_driver,path);
                        HttpAppFactory.upImage(13,
                                path
                                , null
                        )
                                .subscribe(new BaseObserver<UpImgData>(null){
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        super.onSubscribe(d);
                                        isDriver=1;
                                    }

                                    @Override
                                    public void onNext(UpImgData result) {
                                        super.onNext(result);
                                        isDriver=2;
                                        driverInfo=result;
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        super.onError(e);
                                        isDriver=3;
                                    }
                                });

                    }

                }
            });

        } else if (v.getId() == R.id.img_qualification) {
//            ToastUtils.getInstance().show("挂靠协议");
            startPhoto(new OnResultCallbackListener() {
                @Override
                public void onResult(List<LocalMedia> result) {
                    check(false);
                    if (!CollectionUtils.isEmpty(result)){
                        ll_qualification.setVisibility(View.GONE);
                        String path = PicUtils.getPath(result.get(0));
                        ImageLoader.getLoader().load(mContext,img_qualification, path);
                        HttpAppFactory.upImage(14,
                                path
                                , null
                        )
                                .subscribe(new BaseObserver<UpImgData>(null){
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        super.onSubscribe(d);
                                        isqualification=1;
                                    }

                                    @Override
                                    public void onNext(UpImgData result) {
                                        super.onNext(result);
                                        qualificationInfo=result;
                                        isqualification=2;
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        super.onError(e);
                                        isqualification=3;
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

    private void startPhoto(OnResultCallbackListener listener){
        PermissionUtils.applyCamera(getActivity(), new PermissionUtils.onApplyPermission() {
            @Override
            public void hasPermission() {
                new PictureClient()
                        .startPhoto(AttestationCarrierPersonalFragment.this, 1, null,listener);
            }

            @Override
            public void noPermission() {

            }
        });
    }
}
