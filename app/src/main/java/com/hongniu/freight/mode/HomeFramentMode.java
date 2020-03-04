package com.hongniu.freight.mode;

import android.text.TextUtils;

import com.fy.companylibrary.entity.CommonBean;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.control.HomeControl;
import com.hongniu.freight.entity.HomeInfoBean;
import com.hongniu.freight.entity.OrderNumberInfoBean;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.InfoUtils;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/6.
 */
public class HomeFramentMode implements HomeControl.IHomeFragmentMode {

    private Role role;
    private PersonInfor personInfo;
    private boolean isShowBalance;

    public HomeFramentMode() {
        personInfo = InfoUtils.getMyInfo();
    }

    /**
     * 查询首页数据
     *
     * @return
     */
    @Override
    public Observable<HomeInfoBean> queryHomeInfo() {
        return Observable.just(new HomeInfoBean());

    }

    /**
     * @return 获取当前类型
     * 0 托运人
     * 1 承运人
     * 2 司机
     */
    @Override
    public Role getRole() {
        return InfoUtils.getRole(InfoUtils.getLoginInfo());
    }

    /**
     * 查询个信息
     *
     * @return
     */
    @Override
    public Observable<CommonBean<PersonInfor>> queryMyInfo() {
        return HttpAppFactory.queryMyInfo();
    }

    /**
     * 查询订单数量
     *
     * @return
     */
    @Override
    public Observable<CommonBean<OrderNumberInfoBean>> queryOrderNum() {
        return HttpAppFactory.queryOrderNumber();
    }

    /**
     * 储存个人信息
     *
     * @param data
     */
    @Override
    public void savePersonInfo(PersonInfor data) {
        this.personInfo = data==null?InfoUtils.getMyInfo():data;
    }

    /**
     * 查询订单信息
     */
    @Override
    public void queryOrderInfo() {
//        HttpAppFactory.queryOrderList();
    }

    @Override
    public PersonInfor getPersonInfo() {
        return personInfo==null?InfoUtils.getMyInfo():personInfo;
    }

    /**
     * 余额显示状态
     *
     * @return true 显示 false隐藏
     */
    @Override
    public boolean isShowBalance() {
        return isShowBalance;
    }

    /**
     * 切换余额显示状态
     *
     * @param balanceStatus
     * @return true 显示 false隐藏
     */
    @Override
    public void switchBalance(boolean balanceStatus) {
        this.isShowBalance=balanceStatus;
    }

    /**
     * 获取余额
     *
     * @return
     */
    @Override
    public String getBalanceTotle() {
       return TextUtils.isEmpty(getPersonInfo().getTotalBalance())?"0":getPersonInfo().getTotalBalance();
    }


}
