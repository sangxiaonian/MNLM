package com.hongniu.freight.mode;

import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.net.interceptor.FileProgressRequestBody;
import com.hongniu.freight.control.CarAddModifyControl;
import com.hongniu.freight.entity.CarInfoBean;
import com.hongniu.freight.entity.CarTypeBean;
import com.hongniu.freight.entity.UpImgData;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.Utils;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/3/3.
 */
public class CarAddModifyMod implements CarAddModifyControl.ICarAddModifyMode {
    private List<CarTypeBean> carTypeBeans;
    private int carTypeIndex = -1;
    private UpImgData positivePic;
    private UpImgData minusPic;
    private CarInfoBean infoBean;//外部传入的信息

    public CarAddModifyMod() {
        this.carTypeBeans = new ArrayList<>();
    }

    @Override
    public void saveInfo(CarInfoBean infoBean) {
        this.infoBean=infoBean;
        if (infoBean!=null){
            minusPic=new UpImgData();
            positivePic=new UpImgData();
            minusPic.setAbsolutePath(infoBean.getFullBackVImageUrl());
            minusPic.setPath(infoBean.getBackVImageUrl());
            positivePic.setAbsolutePath(infoBean.getFullFaceVImageUrl());
            positivePic.setPath(infoBean.getFaceVImageUrl());
        }
    }

    /**
     * 查询车辆类型
     *
     * @return
     */
    @Override
    public Observable<CommonBean<List<CarTypeBean>>> queryCarTypes() {
        return HttpAppFactory.queryCarTypeList()
                ;

    }

    @Override
    public void saveCarTypes(List<CarTypeBean> carTypeBeans) {
        this.carTypeBeans  .clear();
        if (carTypeBeans!=null){
            this.carTypeBeans.addAll(carTypeBeans);
        }
    }


    /**
     * 切换车辆类型
     *
     * @param options1
     */
    @Override
    public void onSwitchCarType(int options1) {
        this.carTypeIndex = options1;
    }

    @Override
    public List<CarTypeBean> getCarTypes() {
        return carTypeBeans;
    }

    /**
     * 上传图片
     *
     * @param localMedia
     * @return
     */
    @Override
    public Observable<UpImgData> upImage(LocalMedia localMedia) {
        return HttpAppFactory.upImage(12, Utils.getPath(localMedia), new FileProgressRequestBody.ProgressListener() {
            @Override
            public void transferred(long current, long totle) {

            }
        });
    }

    /**
     * 储存驾驶证主页数据
     *
     * @param result
     */
    @Override
    public void savePositivePic(UpImgData result) {
        this.positivePic = result;
    }

    /**
     * 储存驾驶证副页数据
     *
     * @param result
     */
    @Override
    public void saveMinusPic(UpImgData result) {
        this.minusPic = result;
    }

    @Override
    public UpImgData getMinusPic() {
        return minusPic;
    }

    @Override
    public UpImgData getPositivePic() {
        return positivePic;
    }

    /**
     * 新增修改车辆信息
     *
     * @param infoBean
     * @return
     */
    @Override
    public Observable<CommonBean<Object>> createCar(CarInfoBean infoBean) {
        if (carTypeBeans.size()>carTypeIndex&&carTypeIndex>=0) {
            infoBean.setCarTypeId(carTypeBeans.get(carTypeIndex).getId());
        }
        if (positivePic!=null){
            infoBean.setFaceVImageUrl(positivePic.getPath());
        }
        if (minusPic!=null){
            infoBean.setBackVImageUrl(minusPic.getPath());
        }
        if (this.infoBean!=null){
            infoBean.setId(this.infoBean.getId());
        }
       return HttpAppFactory.createCar(infoBean);

    }

    @Override
    public boolean enable() {
        return infoBean == null || infoBean.getIdentityStatus() == 5;
    }

    /**
     * 删除车辆
     * @return
     */
    @Override
    public Observable<CommonBean<Object>> deleted() {
     return  HttpAppFactory.deletedCar(infoBean==null?"":infoBean.getId());
    }
}
