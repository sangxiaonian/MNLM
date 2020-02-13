package com.hongniu.freight.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：  on 2020/2/10.
 */
public class CarInfoListBean implements Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public CarInfoListBean() {
    }

    protected CarInfoListBean(Parcel in) {
    }

    public static final Parcelable.Creator<CarInfoListBean> CREATOR = new Parcelable.Creator<CarInfoListBean>() {
        @Override
        public CarInfoListBean createFromParcel(Parcel source) {
            return new CarInfoListBean(source);
        }

        @Override
        public CarInfoListBean[] newArray(int size) {
            return new CarInfoListBean[size];
        }
    };
}
