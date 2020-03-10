package com.hongniu.freight.presenter;

import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.companylibrary.net.NetObserver;
import com.hongniu.freight.control.OrderCreateControl;
import com.hongniu.freight.entity.InsuranceInfoBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.TranMapBean;
import com.hongniu.freight.mode.OrderCreateMode;

import java.util.List;

/**
 * 作者：  on 2020/2/17.
 */
public class OrderCreatePresenter implements OrderCreateControl.IOrderCreatePresenter {
    OrderCreateControl.IOrderCreateView view;
    OrderCreateControl.IOrderCreateMode mode;

    public OrderCreatePresenter(OrderCreateControl.IOrderCreateView view) {
        this.view = view;
        mode=new OrderCreateMode();
    }

    /**
     * @param result 发货地址
     */
    @Override
    public void saveStartInfo(TranMapBean result) {
        mode.saveStartInfo(result);
    }

    /**
     * @param result 收货地址
     */
    @Override
    public void saveEndInfo(TranMapBean result) {
        mode.saveEndInfo(result);

    }

    /**
     * 显示发货时间
     * @param listener
     */
    @Override
    public void showStartTime(TaskControl.OnTaskListener listener) {
        mode.getTimeInfo()
            .subscribe(new BaseObserver<Integer>(listener){
                @Override
                public void onNext(Integer result) {
                    super.onNext(result);
                    view.showTimePicker(mode.getDays(),mode.getHours(),mode.getMinutes());
                }
            })
        ;
    }

    /**
     * 切换当前年月日
     *
     * @param options1 年月日
     * @param options2  小时
     * @param options3 分钟
     */
    @Override
    public void onChangeTime(int options1, int options2, int options3) {
        List<String> days = mode.getDays();
        List<List<String>> hours = mode.getHours();
        List<List<List<String>>> minutes = mode.getMinutes();
        String time;
        if (options1 == 0 && options2 == 0) {
            time = hours.get(options1).get(options2);
            mode.saveStartTime(null);
        } else {
           time=String.format("%s%s%s", days.get(options1), hours.get(options1).get(options2), minutes.get(options1).get(options2).get(options3));
        }
        view.showTime(time);
    }

    /**
     * 切换是否购买保险
     */
    @Override
    public void onSwitchIsInsurance() {
        boolean isInsurance = !mode.getIsInsurance();
        mode.saveIsInsurance(isInsurance);
        view.switchInsurance(isInsurance);
    }

    /**
     * 显示支付方式dialog
     */
    @Override
    public void showPayDialog() {
        mode.getPayWaysInfo();
        view.showPayDialog(mode.getPayType(),mode.getPayWaysInfo());
    }

    /**
     * 切换支付方式
     *
     * @param payType 支付方式
     */
    @Override
    public void switchPayWay(int payType) {
        mode.savePayType(payType);
        String currentPayType = mode.getPayWaysInfo().get(payType);
        view.showPayType(payType,currentPayType);
    }

    /**
     * 展示所有被保险人信息
     * @param listener
     */
    @Override
    public void showInsuranceInfo(TaskControl.OnTaskListener listener) {

        mode.getAllInsuranceInfos()
            .subscribe(new NetObserver<List<InsuranceInfoBean>>(listener){
                @Override
                public void doOnSuccess(List<InsuranceInfoBean> inforBeans) {
                    super.doOnSuccess(inforBeans);
                        view.showInsuranceDialog(inforBeans);
                }
            })
        ;
    }

    /**
     * 切换被保险人信息
     *
     * @param position
     * @param def
     */
    @Override
    public void onChangeInsuranceInfo(int position, InsuranceInfoBean def) {
       mode.onChangeInsuranceInfo(position,def);
    }

    /**
     * 创建订单
     * @param listener
     */
    @Override
    public void createOrder(TaskControl.OnTaskListener listener) {
        view.getParams(mode.getParams());
        mode.createOrder()
            .subscribe(new NetObserver<OrderInfoBean>(listener){
                @Override
                public void doOnSuccess(OrderInfoBean o) {
                    super.doOnSuccess(o);
                    view.finishSuccess(o);
                }
            })
        ;
    }

    /**
     * 查询保费
     *
     * @param msg
     */
    @Override
    public void searchInsruancePrice(String msg) {
        mode.queryInsurancePrice(msg)
                .subscribe(new NetObserver<String>(null){
                    @Override
                    public void doOnSuccess(String s) {
                        super.doOnSuccess(s);
                        view.showInsurancePrice(String.format("保费%s元",s));

                    }
                });

    }

}
