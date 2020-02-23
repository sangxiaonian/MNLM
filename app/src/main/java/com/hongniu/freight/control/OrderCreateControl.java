package com.hongniu.freight.control;

import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.companylibrary.entity.CommonBean;
import com.hongniu.freight.entity.OrderInsuranceInforBean;
import com.hongniu.freight.entity.TranMapBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/17.
 */
public class OrderCreateControl {

    public interface IOrderCreateView {
        /**
         * 显示发货日期弹窗
         * @param days
         * @param hours
         * @param minutes
         */
        void showTimePicker(  List<String> days, List<List<String>> hours, List<List<List<String>>> minutes);

        /**
         * 显示发货日期
         * @param time
         */
        void showTime(String time);

        /**
         * 切换当前是否购买保险
         * @param isInsurance true 购买
         */
        void switchInsurance(boolean isInsurance);

        /**
         * 显示付款方式
         * @param payType
         * @param payWaysInfo
         */
        void showPayDialog(int payType, List<String> payWaysInfo);

        void showPayType(String currentPayType);

        /**
         * 显示选择被保险人信息弹窗
         * @param inforBeans 被保险人信息
         */
        void showInsuranceDialog(List<OrderInsuranceInforBean> inforBeans);
    }

    ;

    public interface IOrderCreatePresenter {
        /**
         * @param result 发货地址
         */
        void saveStartInfo(TranMapBean result);

        /**
         * @param result 收货地址
         */
        void saveEndInfo(TranMapBean result);

        /**
         * 显示发货时间
         * @param listener
         */
        void showStartTime(TaskControl.OnTaskListener listener);

        /**
         * 切换当前年月日
         * @param options1
         * @param options2
         * @param options3
         */
        void onChangeTime(int options1, int options2, int options3);

        /**
         * 切换是否购买保险
         */
        void onSwitchIsInsurance();

        /**
         * 显示支付方式dialog
         */
        void showPayDialog();

        /**
         * 切换支付方式
         * @param payType 支付方式
         */
        void switchPayWay(int payType);

        /**
         * 展示所有被保险人信息
         * @param listener
         */
        void showInsuranceInfo(TaskControl.OnTaskListener listener);
    }

    ;

    public interface IOrderCreateMode {
        /**
         * @param result 发货地址
         */
        void saveStartInfo(TranMapBean result);

        /**
         * @param result 收货地址
         */
        void saveEndInfo(TranMapBean result);

        /**
         * 获取发货时间年月日
         * @return
         */
        Observable<Integer> getTimeInfo();

        List<List<List<String>>> getMinutes();

        List<String> getDays();

        List<List<String>> getHours();


        /**
         * 是否购买保险
         * @return
         */
        boolean getIsInsurance();

        /**
         * 储存当前是是否购买保险
         * @param isInsurance true 是
         */
        void saveIsInsurance(boolean isInsurance);

        /**
         * 显示付款方式
         * @return
         */
        List<String> getPayWaysInfo();

        /**
         * 获取当前付款方式
         * @return 当前付款方式
         */
        int getPayType();
        /**
         * 储存当前付款方式
         */
        void savePayType(int payType);

        /**
         * 获取所有被保险人信息
         * @return
         */
        Observable<CommonBean<List<OrderInsuranceInforBean>>> getAllInsuranceInfos();
    }

    ;

}
