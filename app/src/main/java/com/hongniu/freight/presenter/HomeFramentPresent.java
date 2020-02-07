package com.hongniu.freight.presenter;

import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.androidlibrary.net.rx.BaseObserver;
import com.hongniu.freight.control.HomeControl;
import com.hongniu.freight.entity.HomeInfoBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.mode.HomeFramentMode;

import java.util.ArrayList;
import java.util.List;

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
        mode.queryHomeInfo()
            .subscribe(new BaseObserver<HomeInfoBean>(listener){
                @Override
                public void onNext(HomeInfoBean result) {
                    super.onNext(result);
                    view.showInfo(result);
                    List<OrderInfoBean> infoBeans=new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        infoBeans.add(new OrderInfoBean());
                    }
                    view.showOrderInfo(infoBeans,mode.getRole());
                }
            })
        ;
    }
}
