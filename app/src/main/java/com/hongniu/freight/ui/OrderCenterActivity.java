package com.hongniu.freight.ui;

import android.os.Bundle;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.ui.RefrushActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.ui.holder.order.OrderHolderBuider;
import com.hongniu.freight.ui.holder.order.XOrderButtonClick;
import com.hongniu.freight.utils.Utils;

import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2020/2/10
 * @Author PING
 * @Description 我要接单
 */
@Route(path = ArouterParamApp.activity_order_center)
public class OrderCenterActivity extends RefrushActivity<OrderInfoBean> implements XOrderButtonClick.NextStepListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_center);
        setWhitToolBar("我要接单");
        initView();
        initData();
        initListener();
    }

    /**
     * 获取空界面
     *
     * @param parent
     * @return
     */
    @Override
    protected PeakHolder getEmptyHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected Observable<CommonBean<PageBean<OrderInfoBean>>> getListDatas() {
        return Utils.createDemoOrderInfo();
    }

    @Override
    protected XAdapter<OrderInfoBean> getAdapter(List<OrderInfoBean> datas) {
        return new XAdapter<OrderInfoBean>(mContext, datas) {
            @Override
            public BaseHolder<OrderInfoBean> initHolder(ViewGroup parent, int viewType) {
                XOrderButtonClick xOrderButtonClick = new XOrderButtonClick(mContext);
                xOrderButtonClick.setNextStepListener(OrderCenterActivity.this::doUpdate);
                xOrderButtonClick.setType(RoleOrder.CARRIER);
                return new OrderHolderBuider(mContext)
                        .setParent(parent)
                        .setType(RoleOrder.CARRIER)
                        .setOrderButtonClickListener(xOrderButtonClick)
                        .build()
                        ;
            }
        };
    }

    /**
     * 再进行取消等操作完成之后,刷新界面
     */
    @Override
    public void doUpdate() {
        queryData(true);
    }
}
