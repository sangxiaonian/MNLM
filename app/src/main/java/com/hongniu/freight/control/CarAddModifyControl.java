package com.hongniu.freight.control;

import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.companylibrary.entity.CommonBean;
import com.hongniu.freight.entity.CarInfoBean;
import com.hongniu.freight.entity.CarTypeBean;
import com.hongniu.freight.entity.UpImgData;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/3/3.
 */
public class CarAddModifyControl {
    public interface ICarAddModifyView {

        /**
         * 初始化信息
         * @param infoBean
         * @param enable
         */
        void initInfo(CarInfoBean infoBean, boolean enable);
        /**
         * 根据新增修改改变头部信息
         * @param isADd
         */
        void showTitle(boolean isADd);
        /**
         * 显示选择车辆类型
         *
         * @param carTypeBeans
         */
        void showCarTypesDialog(List<CarTypeBean> carTypeBeans);

        /**
         * 显示车辆类型
         *
         * @param carType
         */
        void showCarType(String carType);

        /**
         * 显示图片
         * @param result
         */
        void showMinus(UpImgData result);

        /**
         * 显示图片
         * @param positivePic
         */
        void showPositive(UpImgData positivePic);

        /**
         * 新增完成
         */
        void finishWithSuccess();



    }

    public interface ICarAddModifyPresenter {
        /**
         * 储存外部传入的信息
         * @param infoBean
         */
        void saveInfo(CarInfoBean infoBean);
        /**
         * 点击修改车辆类型
         *
         * @param listener
         */
        void queryCarTypes(TaskControl.OnTaskListener listener);

        /**
         * 切换车辆类型
         *
         * @param options1
         */
        void onSwitchCarType(int options1);

        /**
         * 上传行驶证主页
         *
         * @param localMedia
         * @param listener
         */
        void upPositive(LocalMedia localMedia, TaskControl.OnTaskListener listener);

        /**
         * 上传行驶证副业
         *
         * @param localMedia
         * @param listener
         */
        void upMinus(LocalMedia localMedia, TaskControl.OnTaskListener listener);

        /**
         * 检查是否又驾驶证主页
         * @return
         */
        boolean checkPositive();
        /**
         * 检查是否又驾驶证副页
         * @return
         */
        boolean checkMinus();

        /**
         * 新增修改车辆信息
         * @param infoBean
         * @param listener
         */
        void createCar(CarInfoBean infoBean, TaskControl.OnTaskListener listener);


        void deleted(TaskControl.OnTaskListener listener);
    }

    public interface ICarAddModifyMode {
        void saveInfo(CarInfoBean infoBean);

        /**
         * 查询车辆类型
         *
         * @return
         */
        Observable<CommonBean<List<CarTypeBean>>> queryCarTypes();
        void saveCarTypes(List<CarTypeBean> carTypeBeans);

        /**
         * 切换车辆类型
         *
         * @param options1
         */
        void onSwitchCarType(int options1);

        List<CarTypeBean> getCarTypes();

        /**
         * 上传图片
         *
         * @param localMedia
         * @return
         */
        Observable<UpImgData> upImage(LocalMedia localMedia);

        /**
         * 储存驾驶证主页数据
         *
         * @param result
         */
        void savePositivePic(UpImgData result);

        /**
         * 储存驾驶证副页数据
         *
         * @param result
         */
        void saveMinusPic(UpImgData result);

        UpImgData getMinusPic();

        UpImgData getPositivePic();

        /**
         * 新增修改车辆信息
         * @param infoBean
         * @return
         */
        Observable<CommonBean<Object>> createCar(CarInfoBean infoBean);

        boolean enable();

        /**
         * 删除车辆
         * @return
         */
        Observable<CommonBean<Object>> deleted();
    }
}
