package com.hongniu.freight.mode;

import com.hongniu.freight.config.Role;
import com.hongniu.freight.control.HomeControl;
import com.hongniu.freight.entity.HomeInfoBean;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/6.
 */
public class HomeFramentMode implements HomeControl.IHomeFragmentMode {

    private Role role;//获取当前类型0 托运人 1 承运人 2 司机

    public HomeFramentMode() {
    }

    /**
     * 查询首页数据
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
}
