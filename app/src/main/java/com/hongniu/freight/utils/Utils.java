package com.hongniu.freight.utils;

import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.hongniu.freight.entity.OrderInfoBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/7.
 */
public class Utils {

    public static Observable<CommonBean<PageBean<OrderInfoBean>>> createDemoOrderInfo(){
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





}
