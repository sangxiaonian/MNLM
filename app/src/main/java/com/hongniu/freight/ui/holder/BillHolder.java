package com.hongniu.freight.ui.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.BillInfoListBean;

/**
 * 作者：  on 2020/2/13.
 */
public class BillHolder extends BaseHolder<BillInfoListBean> {

    public BillHolder(Context context, ViewGroup parent ) {
        super(context, parent, R.layout.item_bill);

    }

    @Override
    public void initView(View itemView, int position, BillInfoListBean data) {
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
}
