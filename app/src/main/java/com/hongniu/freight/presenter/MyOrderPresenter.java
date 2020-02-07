package com.hongniu.freight.presenter;

import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.control.MyOrderControl;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.mode.MyOrderMode;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/7.
 */
public class MyOrderPresenter implements MyOrderControl.IMyOrderPresenter {
    MyOrderControl.IMyOrderView view;
    MyOrderControl.IMyOrderMode mode;

    public MyOrderPresenter(MyOrderControl.IMyOrderView view) {
        this.view = view;
        mode = new MyOrderMode();
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        view.initTitles(mode.getTitles());
    }

    /**
     * 获取当前角色类型
     *
     * @return
     */
    @Override
    public Role getType() {
        return mode.getRole();
    }

    /**
     * 查询当前订单
     *
     * @param currentPage
     * @return
     */
    @Override
    public Observable<CommonBean<PageBean<OrderInfoBean>>> queryOrder(int currentPage) {
        return mode.queryOrder(currentPage);
    }
}
