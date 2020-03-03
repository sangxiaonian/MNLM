package com.hongniu.freight.net;

import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.google.gson.JsonObject;
import com.hongniu.freight.entity.CarInfoBean;
import com.hongniu.freight.entity.CarTypeBean;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.freight.entity.InsuranceInfoBean;
import com.hongniu.freight.entity.OrderCrateParams;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.PageParams;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.entity.QueryOrderListBean;
import com.hongniu.freight.entity.QuerySmsParams;
import com.hongniu.freight.entity.UpImgData;
import com.hongniu.freight.entity.VerifyCarrierPersonParams;
import com.hongniu.freight.entity.VerifyInfoBean;
import com.hongniu.freight.entity.VerifyTokenBeans;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者：  on 2020/2/23.
 */
public interface AppService {

    /**
     * 发送验证码
     *
     * @param params
     * @return
     */
    @POST("wlhyapi/api/login/getcheckcode")
    Observable<CommonBean<String>> getSmsCode(@Body QuerySmsParams params);

    /**
     * 发送验证码
     *
     * @param params
     * @return
     */
    @POST("wlhyapi/api/login/login")
    Observable<CommonBean<LoginInfo>> login(@Body QuerySmsParams params);

    /**
     * 查询个人资料
     *
     * @return
     */
    @POST("wlhyapi/api/user/finduserinfo")
    Observable<CommonBean<PersonInfor>> queryMyInfo();

    /**
     * 查询用户认证资料
     *
     * @return
     */
    @POST("wlhyapi/api/user/finduserinfo")
    Observable<CommonBean<PersonInfor>> queryIdentityCert();

    /**
     * 获取实名认证token
     *
     * @return
     */
    @POST("wlhyapi/api/aliRPBasic/getVerifyToken")
    Observable<CommonBean<VerifyTokenBeans>> getVerifyToken();
    /**
     * 上传实名认证结果
     *
     * @return
     * @param json
     */
    @POST("wlhyapi/api/aliRPBasic/uploadResult")
    Observable<CommonBean<Object>> uploadResult(@Body JsonObject json);

    /**
     * 个人托运人身份认证
     *
     * @param params
     * @return
     */
    @POST("wlhyapi/api/identityAuthen/personShipperAuth")
    Observable<CommonBean<String>> verifyCarrierPerson(@Body VerifyCarrierPersonParams params);

    /**
     * 个人托运人身份认证信息
     *
     * @return
     */
    @POST("wlhyapi/api/identityAuthen/queryIdentityCert")
    Observable<CommonBean<VerifyInfoBean>> queryVerifyCarrierPerson();

    /**
     * 被保险人列表
     *
     * @return
     */
    @POST("wlhyapi/api/userinsured/list")
    Observable<CommonBean<PageBean<InsuranceInfoBean>>> queryInsuranceList();

    /**
     * 创建订单
     *
     * @return
     */
    @POST("wlhyapi/api/deliveryOrder/add")
    Observable<CommonBean<OrderInfoBean>> createOrder(@Body OrderCrateParams params);

    /**
     * 查询订单列表
     *
     * @return
     */
    @POST("wlhyapi/api/deliveryOrder/queryPage")
    Observable<CommonBean<PageBean<OrderInfoBean>>> queryOrderList(@Body QueryOrderListBean params);


    /**
     * 上传图片
     *
     * @return
     */
    @POST("wlhyapi/api/file/uploadFiles")
    Observable<CommonBean<List<UpImgData>>> upLoadImage(@Body MultipartBody multipartBody);

    /**
     * 我的车辆列表
     *
     * @return
     */
    @POST("wlhyapi/api/car/selectpagecar")
    Observable<CommonBean<PageBean<CarInfoBean>>> queryCarList(@Body PageParams pageParams);
 /**
     * 我的车辆列表
     *
     * @return
     */
    @POST("wlhyapi/api/car/vehicletype")
    Observable<CommonBean<List<CarTypeBean>>> queryCarTypeList( );
 /**
     * 新增修改车辆
     *
     * @return
     */
    @POST("wlhyapi/api/car/savecar")
    Observable<CommonBean<Object>> createCar(@Body CarInfoBean infoBean );

}
