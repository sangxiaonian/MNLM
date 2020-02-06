package com.hongniu.freight.ui.holder;

import android.content.Context;
import android.view.ViewGroup;

import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.hongniu.freight.entity.OrderInfoBean;

/**
 * 作者：  on 2020/2/6.
 */
public class OrderHolderBuider {

    private int type;//0 托运人 1 承运人 2 司机
    private ViewGroup parent;
    private Context context;
    public OrderHolderBuider(Context context ) {
         this.context=context;
    }

    public OrderHolderBuider setType(int type) {
        this.type = type;
        return this;
    }

    public OrderHolderBuider setParent(ViewGroup parent) {
        this.parent = parent;
        return this;
    }

    public BaseHolder<OrderInfoBean> build(){
        BaseHolder<OrderInfoBean> holder;
        if (type==1){
            holder=new OrderCYRHolder(context,parent);
        }else if (type==2){
            holder=new OrderDriverHolder(context,parent);
        }else {
            holder=new OrderTYRHolder(context,parent);
        }
        return holder;
    }
}
