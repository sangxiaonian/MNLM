package com.hongniu.freight.presenter;

import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.net.NetObserver;
import com.hongniu.freight.control.HomeControl;
import com.hongniu.freight.entity.OrderNumberInfoBean;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.mode.HomeFramentMode;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;

/**
 * 作者：  on 2020/2/6.
 */
public class HomeFramentPresent implements HomeControl.IHomeFragmentPresent {

    HomeControl.IHomeFragmentView view;
    HomeControl.IHomeFragmentMode mode;

    public HomeFramentPresent(HomeControl.IHomeFragmentView view) {
        this.view = view;
        mode=new HomeFramentMode();
    }

    /**
     * 初始化数据
     * @param listener
     */
    @Override
    public void initDate(TaskControl.OnTaskListener listener) {
        Observable.zip(mode.queryMyInfo()
                , mode.queryOrderNum()
                , new BiFunction<CommonBean<PersonInfor>, CommonBean<OrderNumberInfoBean>, CommonBean<PersonInfor>>() {
                    @Override
                    public CommonBean<PersonInfor> apply(CommonBean<PersonInfor> personInforCommonBean, CommonBean<OrderNumberInfoBean> orderNumberInfoBeanCommonBean) throws Exception {
                        mode.savePersonInfo(personInforCommonBean.getData());
                        view.showPersonInfo(mode.getPersonInfo());
                        view.showOrderNum(orderNumberInfoBeanCommonBean.getData());
                        return personInforCommonBean;
                    }
                }

        )
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        view.showBalance(mode.isShowBalance(), mode.getBalanceTotle());
                        //查询订单数据
                    }
                })
            .subscribe(new NetObserver<PersonInfor>(listener))
        ;


    }

    /**
     * 切换是否隐藏数据
     */
    @Override
    public void switchBalance() {
        mode.switchBalance(!mode.isShowBalance());
        view.showBalance(mode.isShowBalance(), mode.getBalanceTotle());
    }

    /**
     * 更新认证状态
     * @param listener
     */
    @Override
    public void upDateState(TaskControl.OnTaskListener listener) {
        mode.queryMyInfo()
                .subscribe(new NetObserver<PersonInfor>(listener){
                    @Override
                    public void doOnSuccess(PersonInfor personInfor) {
                        super.doOnSuccess(personInfor);
                        view.showPersonInfo(mode.getPersonInfo());
                        view.showBalance(mode.isShowBalance(), mode.getBalanceTotle());
                    }
                });
    }

    /**
     * 查看认证信息
     */
    @Override
    public void checkStateInfo() {
        //跳转到查看信息
        view.jump2CheckState(mode.getRole(),mode.getPersonInfo());

    }
}
