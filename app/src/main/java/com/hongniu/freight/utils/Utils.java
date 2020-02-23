package com.hongniu.freight.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.amap.api.services.core.PoiItem;
import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.google.gson.Gson;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.freight.entity.OrderInfoBean;

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
        pageBean.setData(list);
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


}
