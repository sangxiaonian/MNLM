package com.hongniu.freight.mode;

import com.fy.androidlibrary.widget.recycle.adapter.SelectAdapter;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.hongniu.freight.App;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.control.MyOrderControl;
import com.hongniu.freight.entity.OrderInfoBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/7.
 */
public class MyOrderMode implements MyOrderControl.IMyOrderMode {

    private Role role;
    private List<SelectAdapter.SelectInfoBean<String>> titles;

    public MyOrderMode() {
        role = Role.SHIPPER;
        titles = new ArrayList<>();

    }

    /**
     * 获取当前角色类型
     *
     * @return
     */
    @Override
    public Role getRole() {
        return role;
    }

    /**
     * 获取头部选择标题
     *
     * @return
     */
    @Override
    public List<SelectAdapter.SelectInfoBean<String>> getTitles() {
        titles.clear();
        String[] stringArray = {};
        if (role == Role.SHIPPER) {
            stringArray = App.app.getResources().getStringArray(R.array.shipper_status);
        } else if (role == Role.DRIVER) {
            stringArray = App.app.getResources().getStringArray(R.array.driver_status);
        } else if (role == Role.CARRIER) {
            stringArray = App.app.getResources().getStringArray(R.array.carrier_status);
        }
        for (int i = 0; i < stringArray.length; i++) {
            SelectAdapter.SelectInfoBean<String> stringSelectInfoBean = new SelectAdapter.SelectInfoBean<>();
            stringSelectInfoBean.setT(stringArray[i]);
            titles.add(stringSelectInfoBean);
        }
        return titles;
    }

    /**
     * 查询当前订单
     *
     * @param currentPage
     * @return
     */
    @Override
    public Observable<CommonBean<PageBean<OrderInfoBean>>> queryOrder(int currentPage) {
        CommonBean<PageBean<OrderInfoBean>> common = new CommonBean<>();
        common.setCode(200);
        PageBean<OrderInfoBean> pageBean = new PageBean<>();
        common.setData(pageBean);
        List<OrderInfoBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new OrderInfoBean());
        }
        pageBean.setData(list);
        return Observable.just(common);
    }
}
