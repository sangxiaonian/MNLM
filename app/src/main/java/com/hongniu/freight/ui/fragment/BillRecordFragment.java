package com.hongniu.freight.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.ui.RefrushFragmet;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.BillInfoBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.ui.holder.EmptyHolder;
import com.hongniu.freight.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/12.
 * <p>
 * 账单余额明细和带入张明细数据
 */
@Route(path = ArouterParamApp.fragment_bill_month)
public class BillRecordFragment extends RefrushFragmet<BillInfoBean> {

    private int type ;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        type=getBundle().getInt(Param.TYPE,0);
        queryData(true);
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_bill_month, null);
        return inflate;

    }

    /**
     * 获取空数据
     *
     * @param parent
     * @return
     */
    @Override
    protected PeakHolder getEmptyHolder(ViewGroup parent) {
        return new EmptyHolder(mContext,parent);
    }

    @Override
    protected Observable<CommonBean<PageBean<BillInfoBean>>> getListDatas() {
        CommonBean<PageBean<BillInfoBean>> common = new CommonBean<>();
        common.setCode(200);
        PageBean<BillInfoBean> pageBean = new PageBean<>();
        common.setData(pageBean);
        List<BillInfoBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BillInfoBean orderInfoBean = new BillInfoBean();
            list.add(orderInfoBean);
        }
        pageBean.setData(list);
        return Observable.just(common);
    }

    @Override
    protected XAdapter<BillInfoBean> getAdapter(List<BillInfoBean> datas) {
        return new XAdapter<BillInfoBean>(mContext, datas) {
            @Override
            public BaseHolder<BillInfoBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<BillInfoBean>(context, parent, R.layout.item_bill) {
                    @Override
                    public void initView(View itemView, int position, BillInfoBean data) {
                        super.initView(itemView, position, data);
                        TextView tvCount = itemView.findViewById(R.id.tv_count);
                        TextView tvStatus = itemView.findViewById(R.id.tv_statute);
                        TextView tv_des = itemView.findViewById(R.id.tv_des);
                        TextView tv_time = itemView.findViewById(R.id.tv_time);
                        tvStatus.setText("订单运费收入");
                        tvCount.setText("20000");
                        tv_des.setText("订单号 HN292928383322012");
                        tv_time.setText("收款时间 2019-02-02 10:11:23");


                    }
                };
            }
        };
    }
}
