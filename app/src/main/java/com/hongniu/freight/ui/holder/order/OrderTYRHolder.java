package com.hongniu.freight.ui.holder.order;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.Group;

import com.fy.androidlibrary.utils.CommonUtils;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.ui.holder.order.helper.OrderHelper;

/**
 * 作者：  on 2020/2/6.
 * 托运人
 *
 */
  class OrderTYRHolder extends OrderBaseHolder {

    public OrderTYRHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_tyr_order);
    }

    @Override
    public void initView(View itemView, int position, OrderInfoBean data) {
        super.initView(itemView, position, data);
        TextView tvTitle = itemView.findViewById(R.id.tv_title);
        TextView tv_time = itemView.findViewById(R.id.tv_time);
        TextView tv_tag = itemView.findViewById(R.id.tv_tag);
        TextView tv_start_address = itemView.findViewById(R.id.tv_start_address);
        TextView tv_end_address = itemView.findViewById(R.id.tv_end_address);
        ViewGroup ll_bt = itemView.findViewById(R.id.ll_bt);
        Group bottom_group = itemView.findViewById(R.id.bottom_group);

        OrderHelper helper = new OrderHelper(Role.SHIPPER_COMPANY);

        CommonUtils.setText(tv_time,data.getCreateTime());
        CommonUtils.setText(tv_start_address,data.getStartPlaceInfo());
        CommonUtils.setText(tv_end_address,data.getDestinationInfo());
        tv_tag.setText(RoleOrder.SHIPPER.getName());
        tvTitle.setText(helper.getStatus(data.getStatus()));

        //控制底部button
        addButton(bottom_group, ll_bt, data, helper.getButtons(data.getStatus()));

    }


}
