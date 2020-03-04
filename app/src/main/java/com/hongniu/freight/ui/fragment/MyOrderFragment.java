package com.hongniu.freight.ui.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.widget.recycle.adapter.SelectAdapter;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.androidlibrary.widget.recycle.utils.XLineDivider;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.ui.RefrushFragmet;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.control.MyOrderControl;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.presenter.MyOrderPresenter;
import com.hongniu.freight.ui.holder.order.OrderHolderBuider;
import com.hongniu.freight.ui.holder.order.XOrderButtonClcik;

import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2020/2/7
 * @Author PING
 * @Description 我的订单页面
 */
@Route(path = ArouterParamApp.fragment_my_order)
public class MyOrderFragment extends RefrushFragmet<OrderInfoBean> implements MyOrderControl.IMyOrderView, SelectAdapter.SingleSelectedListener<String> {

    private RecyclerView rvTitle;
    MyOrderControl.IMyOrderPresenter presenter;
    private SelectAdapter<String> selectAdapter;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_my_order, null);
        rvTitle = inflate.findViewById(R.id.rv_title);
        return inflate;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RoleOrder role = (RoleOrder) getBundle().getSerializable(Param.TRAN);
        presenter.initData(role);
        queryData(true);

    }

    @Override
    protected void initData() {
        super.initData();
        presenter = new MyOrderPresenter(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        rvTitle.setLayoutManager(manager);
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
        return presenter.queryOrder(currentPage);

    }

    @Override
    protected XAdapter<OrderInfoBean> getAdapter(List<OrderInfoBean> datas) {
        return new XAdapter<OrderInfoBean>(mContext, datas) {
            @Override
            public BaseHolder<OrderInfoBean> initHolder(ViewGroup parent, int viewType) {
                return new OrderHolderBuider(mContext)
                        .setParent(parent)
                        .setType(presenter.getType())
                        .setOrderButtonClickListener(new XOrderButtonClcik(mContext))
                        .build()
                        ;
            }
        };
    }

    /**
     * 初始化标题
     *
     * @param titles
     */
    @Override
    public void initTitles(List<String> titles) {
        if (selectAdapter==null) {
            selectAdapter = new SelectAdapter<String>(mContext, rvTitle) {
                @Override
                public void initView(View itemView, int position, String s, boolean select) {
                    TextView tvTitle = itemView.findViewById(R.id.tv_title);
                    View flag= itemView.findViewById(R.id.view_flag);
                    tvTitle.setText(s);
                    tvTitle.setTextColor(getResources().getColor(select?R.color.color_of_040000:R.color.color_of_999999));
                    tvTitle .setTypeface(Typeface.defaultFromStyle(select?Typeface.BOLD:Typeface.NORMAL));
                    flag.setBackgroundResource( select?R.drawable.ovl_2_e50000:0);
                }
            };
            selectAdapter.setCanEmpty(false);
            selectAdapter.setSingle(true);
            selectAdapter.setSingleSelectedListener(this);
            selectAdapter.setItemLayoutID(R.layout.item_title);
            rvTitle.setAdapter(selectAdapter);
            XLineDivider tagLine = new XLineDivider()
                    .setmOrientation(LinearLayoutManager.HORIZONTAL)
                    .setmDividerWidth(DeviceUtils.dip2px(mContext, 20))
                    .setDividerColor(Color.TRANSPARENT)
                    .setHeadGap(DeviceUtils.dip2px(mContext, 25), DeviceUtils.dip2px(mContext, 1))
                    .hideLast(false);
            rvTitle.addItemDecoration(tagLine);
        }
        selectAdapter.notifyAllItem(titles,0);
    }

    /**
     * 单选时候
     *
     * @param position
     * @param selected
     * @param check
     */
    @Override
    public void onSingleSelected(int position, String selected, boolean check) {
        presenter.switchStatus(position,selected);
        queryData(true,true);
    }
}
