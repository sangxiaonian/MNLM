package com.hongniu.freight.mode;

import android.text.TextUtils;

import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.hongniu.freight.App;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.config.Status;
import com.hongniu.freight.control.MyOrderControl;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.QueryOrderListBean;
import com.hongniu.freight.net.HttpAppFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/7.
 */
public class MyOrderMode implements MyOrderControl.IMyOrderMode {

    private RoleOrder role;
    private List<String> titles;
    private int statusIndex;

    public MyOrderMode() {
        titles = new ArrayList<>();

    }

    @Override
    public void saveRole(RoleOrder role) {
        this.role=role;
    }

    @Override
    public void saveStatus(int position) {
        this.statusIndex =position;
    }

    /**
     * 获取当前角色类型
     *
     * @return
     */
    @Override
    public RoleOrder getRole() {
        return role;
    }

    /**
     * 获取头部选择标题
     *
     * @return
     */
    @Override
    public List<String> getTitles() {
        titles.clear();
        String[] stringArray = {};
        if (role == RoleOrder.SHIPPER ) {
            stringArray = App.app.getResources().getStringArray(R.array.shipper_status);
        } else if (role == RoleOrder.DRIVER) {
            stringArray = App.app.getResources().getStringArray(R.array.driver_status);
        } else if (role == RoleOrder.CARRIER ) {
            stringArray = App.app.getResources().getStringArray(R.array.carrier_status);
        }
        titles.addAll(Arrays.asList(stringArray));
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
        QueryOrderListBean bean=new QueryOrderListBean();
        bean.setPageSize(Param.PAGE_SIZE);
        bean.setPageNum(currentPage);
        bean.setUserType(role.getType());
        for (Status value : Status.values()) {
            if (TextUtils.equals(value.getName(),titles.get(statusIndex))){
                bean.setStatus(value.getStatus()+"");
                break;
            }
        }
       return HttpAppFactory.queryOrderList(bean);
    }
}
