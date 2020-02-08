package com.hongniu.freight.ui.holder.order;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.Group;

import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.ui.holder.order.helper.OrderHelper;
import com.hongniu.freight.ui.holder.order.helper.OrderUtils;
import com.hongniu.freight.ui.holder.order.helper.control.OrderButtonClickListener;

import java.util.Map;

/**
 * 作者：  on 2020/2/6.
 */
public class OrderBaseHolder extends BaseHolder<OrderInfoBean> {

    private OrderButtonClickListener orderButtonClickListener;

    public OrderBaseHolder(Context context, ViewGroup parent, int layoutID ) {
        super(context, parent, layoutID);
    }

    public void setOrderButtonClickListener(OrderButtonClickListener orderButtonClickListener) {
        this.orderButtonClickListener = orderButtonClickListener;
    }

    @Override
    public void initView(View itemView, int position, OrderInfoBean data) {
        super.initView(itemView, position, data);

    }
    protected  void addButton(Group bottom_group, ViewGroup ll_bt, final OrderInfoBean data, Map<String, Integer> status) {
        ll_bt.removeAllViews();
        if (CollectionUtils.isEmpty(status)) {
            bottom_group.setVisibility(View.GONE);
        } else {
            bottom_group.setVisibility(View.VISIBLE);
            for (final String s : status.keySet()) {
                TextView button = OrderUtils.getButton(bottom_group.getContext(), status.get(s), s);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (orderButtonClickListener!=null){
                            new CustomOrderButtonClick(orderButtonClickListener).performClick(s,data);
                        }
                    }
                });
                ll_bt.addView(button);
            }
        }
    }
}
