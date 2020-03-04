package com.hongniu.freight.control;

import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.companylibrary.entity.CommonBean;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.entity.HomeInfoBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.OrderNumberInfoBean;
import com.hongniu.freight.entity.PersonInfor;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/6.
 */
public class HomeControl {

    public interface IHomeFragmentView {

        /**
         * 显示订单信息
         * @param infoBeans
         * @param type
         */
        void showOrderInfo(List<OrderInfoBean> infoBeans, Role type);

        /**
         * 储存订单数量
         * @param data
         */
        void showOrderNum(OrderNumberInfoBean data);

        /**
         * 展示个人信息
         * @param personInfo
         */
        void showPersonInfo(PersonInfor personInfo);

        /**
         * 显示余额数据
         * @param showBalance true 显示
         * @param totalBalance 余额
         */
        void showBalance(boolean showBalance, String totalBalance);

        /**
         * 跳转到查看认证信息页面
         * @param role
         * @param personInfo
         */
        void jump2CheckState(Role role, PersonInfor personInfo);
    }
    public interface IHomeFragmentPresent {
        /**
         *
         * 初始化数据
         * @param listener
         */
        void initDate(TaskControl.OnTaskListener listener);

        /**
         * 切换是否隐藏数据
         */
        void switchBalance();

        /**
         * 更新认证状态
         * @param listener
         */
        void upDateState(TaskControl.OnTaskListener listener);

        /**
         * 查看认证信息
         */
        void checkStateInfo();
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

        /**
         * 查询个信息
         * @return
         */
        Observable<CommonBean<PersonInfor>> queryMyInfo();

        /**
         * 查询订单数量
         * @return
         */
        Observable<CommonBean<OrderNumberInfoBean>> queryOrderNum();

        /**
         * 储存个人信息
         * @param data
         */
        void savePersonInfo(PersonInfor data);


        /**
         * 查询订单信息
         */
        void queryOrderInfo();

        PersonInfor getPersonInfo();

        /**
         * 余额显示状态
         * @return true 显示 false隐藏
         */
        boolean isShowBalance();

        /**
         * 切换余额显示状态
         * @return true 显示 false隐藏
         */
        void switchBalance(boolean balanceStatus);

        /**
         * 获取余额
         * @return
         */
        String getBalanceTotle();
    }

}
