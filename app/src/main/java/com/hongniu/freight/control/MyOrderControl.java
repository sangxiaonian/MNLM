package com.hongniu.freight.control;

import com.fy.androidlibrary.widget.recycle.adapter.SelectAdapter;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.entity.OrderInfoBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/7.
 */
public class MyOrderControl {
    public interface IMyOrderView{
        /**
         * 初始化标题
         * @param titles
         */
        void initTitles(List<SelectAdapter.SelectInfoBean<String>> titles);
    }
    public interface IMyOrderPresenter{
        /**
         * 初始化数据
         */
        void initData();
        /**
         * 获取当前角色类型
         * @return
         */
        Role getType();


        /**
         * 查询当前订单
         * @param currentPage
         * @return
         */
        Observable<CommonBean<PageBean<OrderInfoBean>>> queryOrder(int currentPage);
    }
    public interface IMyOrderMode{
        /**
         * 获取当前角色类型
         * @return
         */
        Role getRole();

        /**
         * 获取头部选择标题
         * @return
         */
        List<SelectAdapter.SelectInfoBean<String>> getTitles();


        /**
         * 查询当前订单
         * @param currentPage
         * @return
         */
        Observable<CommonBean<PageBean<OrderInfoBean>>> queryOrder(int currentPage);
    }
}
