package com.hongniu.freight.ui.holder.order;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.Group;

import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.ui.holder.order.helper.OrderHelper;

/**
 * 作者：  on 2020/2/6.
 */
public class OrderCYRHolder extends OrderBaseHolder {

    public OrderCYRHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_cyr_order);
    }

    @Override
    public void initView(View itemView, int position, OrderInfoBean data) {
        super.initView(itemView, position, data);
        TextView tvTitle = itemView.findViewById(R.id.tv_title);
        TextView tv_time = itemView.findViewById(R.id.tv_time);
        TextView tv_tag = itemView.findViewById(R.id.tv_tag);
        TextView tv_start_address = itemView.findViewById(R.id.tv_start_address);
        TextView tv_end_address = itemView.findViewById(R.id.tv_end_address);
        Group bottom_group = itemView.findViewById(R.id.bottom_group);
        ViewGroup ll_bt = itemView.findViewById(R.id.ll_bt);

        OrderHelper helper = new OrderHelper(Role.CARRIER_COMPANY);

        tv_time.setText("2019-04-02 09:00");
        tvTitle.setText("指定火车类型");
        tv_start_address.setText("青海省黄南藏族自治州尖扎县银霄大道355号");
        tv_end_address.setText("广东省河源市连平县工业路801号");
        //控制底部button
        addButton(bottom_group, ll_bt, data,helper.getButtons(data.getStatus()));

    }

}
