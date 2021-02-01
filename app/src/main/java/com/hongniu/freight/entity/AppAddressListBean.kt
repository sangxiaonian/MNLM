package com.hongniu.freight.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * 作者：  on 2021/2/1.
 * 地址列表数据
 */
data class AppAddressListBean(

       var id:String?,//	true	number	地址表id
       var userId:String?,//	true	number	用户id
       var startPlaceInfo:String?,//	false	string	开始地描述     查询开始地 忽略目的地
       var startPlaceLon:Double?,//	false	number	开始地经度
       var startPlaceLat:Double?,//	false	number	开始地纬度
       var destinationInfo:String?,//	false	string	目的地描述     查询目的地 忽略开始地
       var destinationLon:Double?,//	false	number	目的地经度
       var destinationLat:Double?,//	false	number	目的地纬度
       var companyAccountId:String?,//	false	number	所属公司Id
       var shipperName:String?,//	true	string	发货人姓名
       var shipperMobile:String?,//	true	string	发货人手机号码
       var receiverName:String?,//	true	string	收货人姓名
       var receiverMobile:String?,//	true	string	收货人手机号码


) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readString(),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(userId)
        parcel.writeString(startPlaceInfo)
        parcel.writeValue(startPlaceLon)
        parcel.writeValue(startPlaceLat)
        parcel.writeString(destinationInfo)
        parcel.writeValue(destinationLon)
        parcel.writeValue(destinationLat)
        parcel.writeString(companyAccountId)
        parcel.writeString(shipperName)
        parcel.writeString(shipperMobile)
        parcel.writeString(receiverName)
        parcel.writeString(receiverMobile)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AppAddressListBean> {
        override fun createFromParcel(parcel: Parcel): AppAddressListBean {
            return AppAddressListBean(parcel)
        }

        override fun newArray(size: Int): Array<AppAddressListBean?> {
            return arrayOfNulls(size)
        }
    }

}
