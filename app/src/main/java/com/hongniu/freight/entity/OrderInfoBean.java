package com.hongniu.freight.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：  on 2020/2/5.
 * 订单详情/列表数据
 */
public class OrderInfoBean implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public OrderInfoBean() {
    }

    protected OrderInfoBean(Parcel in) {
    }

    public static final Parcelable.Creator<OrderInfoBean> CREATOR = new Parcelable.Creator<OrderInfoBean>() {
        @Override
        public OrderInfoBean createFromParcel(Parcel source) {
            return new OrderInfoBean(source);
        }

        @Override
        public OrderInfoBean[] newArray(int size) {
            return new OrderInfoBean[size];
        }
    };
}
