package com.hongniu.freight.net;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.fy.androidlibrary.net.rx.RxUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.net.CompanyClient;
import com.google.gson.Gson;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.freight.entity.QuerySmsParams;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 作者： ${PING} on 2018/8/13.
 * <p>
 * 登录使用的App
 */
public class HttpAppFactory {


    /**
     * 获取验证码
     * @param phone
     * @return
     */
    public static Observable<CommonBean<String>> getSms(String phone) {
        QuerySmsParams params = new QuerySmsParams();
        params.setMobile(phone);
        params.setCode(ConvertUtils.MD5(phone+ Param.key));
        return CompanyClient.getInstance().creatService(AppService.class)
                .getSmsCode(params)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());

    } /**
     * 获取验证码
     * @param phone
     * @return
     */
    public static Observable<CommonBean<LoginInfo>> login(String phone, String sms) {
        QuerySmsParams params = new QuerySmsParams();
        params.setMobile(phone);
        params.setCheckCode(sms);
        return CompanyClient.getInstance().creatService(AppService.class)
                .login(params)
                .map(new Function<CommonBean<LoginInfo>, CommonBean<LoginInfo>>() {
                    @Override
                    public CommonBean<LoginInfo> apply(CommonBean<LoginInfo> loginInfoCommonBean) throws Exception {
                        if (loginInfoCommonBean.getCode()==Param.SUCCESS_FLAG){
                            SharedPreferencesUtils.getInstance().putString(Param.LOGIN,new Gson().toJson(loginInfoCommonBean.getData()));
                        }
                        return loginInfoCommonBean;
                    }
                })
                .compose(RxUtils.<CommonBean<LoginInfo>>getSchedulersObservableTransformer());

    }



        /**
         * 高德地图搜索PIO
         *
         * @param poiSearch
         */
    public static Observable<CommonBean<PageBean<PoiItem>>> searchPio(PoiSearch poiSearch) {
        return Observable.just(poiSearch)
                .map(new Function<PoiSearch, PoiResult>() {

                    public PoiResult apply(PoiSearch poiSearch) throws Exception {
                        return poiSearch.searchPOI();
                    }
                })
                .map(new Function<PoiResult, ArrayList<PoiItem>>() {
                    @Override
                    public ArrayList<PoiItem> apply(PoiResult poiResult) throws Exception {


                        return poiResult.getPois();
                    }
                })
                .map(new Function<ArrayList<PoiItem>, CommonBean<PageBean<PoiItem>>>() {
                    @Override
                    public CommonBean<PageBean<PoiItem>> apply(ArrayList<PoiItem> poiItems) throws Exception {
                        CommonBean<PageBean<PoiItem>> bean = new CommonBean<>();
                        PageBean<PoiItem> pageBean = new PageBean<>();
                        pageBean.setData(poiItems);
                        bean.setCode(200);
                        bean.setData(pageBean);
                        return bean;
                    }
                })
                .compose(RxUtils.<CommonBean<PageBean<PoiItem>>>getSchedulersObservableTransformer())
                ;


    }

}
