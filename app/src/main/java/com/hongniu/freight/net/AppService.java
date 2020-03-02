package com.hongniu.freight.net;

import com.fy.companylibrary.entity.CommonBean;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.entity.QuerySmsParams;
import com.hongniu.freight.entity.VerifyCarrierPersonParams;
import com.hongniu.freight.entity.VerifyInfoBean;
import com.hongniu.freight.entity.VerifyTokenBeans;

import io.reactivex.Observable;
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
    @POST("api/login/getcheckcode")
    Observable<CommonBean<String>> getSmsCode(@Body QuerySmsParams params);

    /**
     * 发送验证码
     *
     * @param params
     * @return
     */
    @POST("api/login/login")
    Observable<CommonBean<LoginInfo>> login(@Body QuerySmsParams params);

    /**
     * 查询个人资料
     *
     * @return
     */
    @POST("api/user/finduserinfo")
    Observable<CommonBean<PersonInfor>> queryMyInfo();
    /**
     * 查询用户认证资料
     *
     * @return
     */
    @POST("api/user/finduserinfo")
    Observable<CommonBean<PersonInfor>> queryIdentityCert();

   /**
     * 获取实名认证token
     *
     * @return
     */
    @POST("api/aliRPBasic/getVerifyToken")
    Observable<CommonBean<VerifyTokenBeans>> getVerifyToken();

 /**
     * 个人托运人身份认证
     *
     * @return
  * @param params
     */
    @POST("api/identityAuthen/personShipperAuth")
    Observable<CommonBean<String>> verifyCarrierPerson(@Body VerifyCarrierPersonParams params);
 /**
     * 个人托运人身份认证信息
     *
     * @return
  */
    @POST("api/identityAuthen/queryIdentityCert")
    Observable<CommonBean<VerifyInfoBean>> queryVerifyCarrierPerson();


}
