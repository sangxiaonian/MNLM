package com.hongniu.freight.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：  on 2020/2/5.
 * 订单详情/列表数据
 */
public class OrderInfoBean implements Parcelable {

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public OrderInfoBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
    }

    protected OrderInfoBean(Parcel in) {
        this.status = in.readInt();
    }

    public static final Creator<OrderInfoBean> CREATOR = new Creator<OrderInfoBean>() {
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
