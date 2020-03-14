package com.hongniu.freight.mode;

import android.text.TextUtils;

import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.control.HomeControl;
import com.hongniu.freight.entity.HomeInfoBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.OrderNumberInfoBean;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.InfoUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/6.
 */
public class HomeFramentMode implements HomeControl.IHomeFragmentMode {

    private   Role role=Role.SHIPPER_COMPANY;
    private RoleOrder roleOrder;
    private PersonInfor personInfo;
    private boolean isShowBalance;
    private List<OrderInfoBean> list;

    public HomeFramentMode() {
        personInfo = InfoUtils.getMyInfo();
        roleOrder=RoleOrder.CARRIER;
        list=new ArrayList<>();
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
        return role;
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
        role=InfoUtils.getRole(personInfo);
        if (role==Role.SHIPPER_PERSONAL||role==Role.SHIPPER_COMPANY){
            roleOrder =RoleOrder.SHIPPER;
        }else   if (role==Role.CARRIER_COMPANY||role==Role.CARRIER_PERSONAL){
            roleOrder =RoleOrder.CARRIER;
        }else   if (role==Role.DRIVER){
            roleOrder =RoleOrder.DRIVER;
        }else {
            roleOrder =RoleOrder.DRIVER;
        }
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

    /**
     * 查询订单数量
     * @return
     */
    @Override
    public Observable<CommonBean<PageBean<OrderInfoBean>>> queryOrderList() {
      return  HttpAppFactory
              .queryOwnerOrderList(1,3);
    }

    @Override
    public RoleOrder getRoleOrder() {
        return roleOrder;
    }

    @Override
    public void saveOrderList(List list) {
        this.list.clear();
        if (!CollectionUtils.isEmpty(list)){
            this.list.addAll(list);
        }
    }

    @Override
    public List<OrderInfoBean> getOrderList() {
return list;
    }


}
