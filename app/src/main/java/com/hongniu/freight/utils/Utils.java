package com.hongniu.freight.utils;

import android.view.View;

import com.amap.api.services.core.PoiItem;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/7.
 */
public class Utils {

    public static Observable<CommonBean<PageBean<OrderInfoBean>>> createDemoOrderInfo() {
        CommonBean<PageBean<OrderInfoBean>> common = new CommonBean<>();
        common.setCode(200);
        PageBean<OrderInfoBean> pageBean = new PageBean<>();
        common.setData(pageBean);
        List<OrderInfoBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OrderInfoBean orderInfoBean = new OrderInfoBean();
            orderInfoBean.setStatus(i);
            list.add(orderInfoBean);
        }
        pageBean.setList(list);
        return Observable.just(common);
    }


    /**
     * 获取托运人类别
     *
     * @return 0 个人 1平台
     */
    public static int getShipperType() {
        return 1;
    }

    public static void setButton(View button, boolean enable) {
        if (button == null) {
            return;
        }
        if (enable) {
            button.setAlpha(1f);
        } else {
            button.setAlpha(0.3f);

        }
    }

    /**
     * 对地址显示进行处理
     *
     * @param data
     */
    public static String dealPioPlace(PoiItem data) {
        String placeInfor = "";
        if (data != null && data.getProvinceName() != null) {
            if (data.getProvinceName().equals(data.getCityName())) {
                placeInfor = data.getProvinceName() + data.getAdName()
                        + data.getSnippet();
            } else {
                placeInfor = data.getProvinceName() + data.getCityName() + data.getAdName()
                        + data.getSnippet();
            }
        }
        return placeInfor;
    }


    public static String getPath(LocalMedia localMedia) {
        String path;
        if (DeviceUtils.getSdkVersion()>28){
            path=localMedia.getAndroidQToPath();
        }else {
            path=localMedia.getPath();
        }
        return path;
    }

    public static String getTitleTime() {
        int time = ConvertUtils.getTime();
        String current = "";
        if (time == 0) {
            current = "凌晨";
        } else if (time == 1) {
            current = "上午";
        } else if (time == 2) {
            current = "中午";
        } else if (time == 3) {
            current = "下午";
        } else if (time == 4) {
            current = "晚上";
        }
        return current;

    }
}
