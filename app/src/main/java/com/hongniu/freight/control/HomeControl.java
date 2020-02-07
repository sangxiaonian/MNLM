package com.hongniu.freight.control;

import com.fy.androidlibrary.net.listener.TaskControl;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.entity.HomeInfoBean;
import com.hongniu.freight.entity.OrderInfoBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/6.
 */
public class HomeControl {

    public interface IHomeFragmentView {
        /**
         *  网络请求获取数据之后，显示数据
         * @param result
         */
        void showInfo(HomeInfoBean result);

        /**
         * 显示订单信息
         * @param infoBeans
         * @param type
         */
        void showOrderInfo(List<OrderInfoBean> infoBeans, Role type);
    }
    public interface IHomeFragmentPresent {
        /**
         *
         * 初始化数据
         * @param listener
         */
        void initDate(TaskControl.OnTaskListener listener);
    }
    public interface IHomeFragmentMode {
        /**
         * 查询首页数据
         * @return
         */
        Observable<HomeInfoBean> queryHomeInfo();

        /**
         *
         * @return 获取当前类型 0 1 2
         */
        Role getRole();
    }

}
