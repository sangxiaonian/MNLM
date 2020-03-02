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
     * @param role
     */
    @Override
    public void initData(Role role) {
        mode.saveRole(role);
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

    /**
     * 切换当前状态
     *
     * @param position
     * @param selected
     */
    @Override
    public void switchStatus(int position, String selected) {
        mode.saveStatus(position);
    }
}
