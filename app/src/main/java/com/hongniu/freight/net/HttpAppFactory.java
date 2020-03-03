package com.hongniu.freight.net;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.fy.androidlibrary.net.rx.RxUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.net.CompanyClient;
import com.fy.companylibrary.net.interceptor.FileProgressRequestBody;
import com.hongniu.freight.entity.InsuranceInfoBean;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.freight.entity.OrderCrateParams;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.entity.QueryOrderListBean;
import com.hongniu.freight.entity.QuerySmsParams;
import com.hongniu.freight.entity.UpImgData;
import com.hongniu.freight.entity.VerifyCarrierPersonParams;
import com.hongniu.freight.entity.VerifyInfoBean;
import com.hongniu.freight.entity.VerifyInfoParams;
import com.hongniu.freight.entity.VerifyTokenBeans;
import com.hongniu.freight.utils.InfoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 作者： ${PING} on 2018/8/13.
 * <p>
 * 登录使用的App
 */
public class HttpAppFactory {


    /**
     * 获取验证码
     *
     * @param phone
     * @return
     */
    public static Observable<CommonBean<String>> getSms(String phone) {
        QuerySmsParams params = new QuerySmsParams();
        params.setMobile(phone);
        params.setCode(ConvertUtils.MD5(phone + Param.key));
        return CompanyClient.getInstance().creatService(AppService.class)
                .getSmsCode(params)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());

    }

    /**
     * 获取验证码
     *
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
                        if (loginInfoCommonBean.getCode() == Param.SUCCESS_FLAG) {
                            InfoUtils.saveLoginInfo(loginInfoCommonBean.getData());
                        }
                        return loginInfoCommonBean;
                    }
                })
                .compose(RxUtils.<CommonBean<LoginInfo>>getSchedulersObservableTransformer());

    }

    /**
     * 查询个人信息
     *
     * @return
     */
    public static Observable<CommonBean<PersonInfor>> queryMyInfo() {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryMyInfo()
                .map(new Function<CommonBean<PersonInfor>, CommonBean<PersonInfor>>() {
                    @Override
                    public CommonBean<PersonInfor> apply(CommonBean<PersonInfor> loginInfoCommonBean) throws Exception {
                        if (loginInfoCommonBean.getCode() == Param.SUCCESS_FLAG) {
                            InfoUtils.saveMyInfo(loginInfoCommonBean.getData());
                        }
                        return loginInfoCommonBean;
                    }
                })
                .compose(RxUtils.<CommonBean<PersonInfor>>getSchedulersObservableTransformer());

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

    /**
     * 查询个人信息
     *
     * @return
     */
    public static Observable<CommonBean<PersonInfor>> queryIdentityCert(int userType) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryMyInfo()
                .map(new Function<CommonBean<PersonInfor>, CommonBean<PersonInfor>>() {
                    @Override
                    public CommonBean<PersonInfor> apply(CommonBean<PersonInfor> loginInfoCommonBean) throws Exception {
                        if (loginInfoCommonBean.getCode() == Param.SUCCESS_FLAG) {
                            InfoUtils.saveMyInfo(loginInfoCommonBean.getData());
                        }
                        return loginInfoCommonBean;
                    }
                })
                .compose(RxUtils.<CommonBean<PersonInfor>>getSchedulersObservableTransformer());

    }

    /**
     * 获取实名认证token
     *
     * @return 获取实名认证token
     */
    public static Observable<CommonBean<VerifyTokenBeans>> getVerifyToken() {
        return CompanyClient.getInstance().creatService(AppService.class)
                .getVerifyToken()
                .compose(RxUtils.<CommonBean<VerifyTokenBeans>>getSchedulersObservableTransformer());

    }

    /**
     * @param params
     * @return
     * @data 2020/3/1
     * @Author PING
     * @Description 个人托运人身份认证
     */
    public static Observable<CommonBean<String>> verifyCarrierPerson(VerifyCarrierPersonParams params) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .verifyCarrierPerson(params)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer());
    }

    /**
     * @return
     * @data 2020/3/1
     * @Author PING
     * @Description 个人托运人身份认证
     */
    public static Observable<CommonBean<VerifyInfoBean>> queryVerifyCarrierPerson() {
        VerifyInfoParams params = new VerifyInfoParams();
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryVerifyCarrierPerson()
                .compose(RxUtils.<CommonBean<VerifyInfoBean>>getSchedulersObservableTransformer());
    }

    /**
     * 查询被保险人列表
     *
     * @return
     */
    public static Observable<CommonBean<PageBean<InsuranceInfoBean>>> queryInsuranceList() {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryInsuranceList()
                .compose(RxUtils.<CommonBean<PageBean<InsuranceInfoBean>>>getSchedulersObservableTransformer());
    }

    /**
     * 查询被保险人列表
     *
     * @param param
     * @return
     */
    public static Observable<CommonBean<OrderInfoBean>> createOrder(OrderCrateParams param) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .createOrder(param)
                .compose(RxUtils.<CommonBean<OrderInfoBean>>getSchedulersObservableTransformer());
    }

    /**
     * 查询被保险人列表
     *
     * @param param
     * @return
     */
    public static Observable<CommonBean<PageBean<OrderInfoBean>>> queryOrderList(QueryOrderListBean param) {
        return CompanyClient.getInstance().creatService(AppService.class)
                .queryOrderList(param)
                .compose(RxUtils.<CommonBean<PageBean<OrderInfoBean>>>getSchedulersObservableTransformer());
    }


    /**
     * 上传图片
     *
     * @param type             文件分类：
     *                         1-货单
     *                         2-回单
     *                         7-企业营业执照
     *                         8-身份证图片
     *                         12-驾驶证行驶证图片
     *                         13-道路运输许可证图片
     *                         14-挂靠协议图片
     *                         15-司机从业资格证
     *                         16-意见反馈图片
     * @param path             路径
     * @param progressListener 上传进度监听
     * @return
     */
    public static Observable<UpImgData> upImage(int type, String path, FileProgressRequestBody.ProgressListener progressListener) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        FileProgressRequestBody filePart = new FileProgressRequestBody(requestBody, progressListener);
        builder.addFormDataPart("file", file.getName(), filePart);

        builder.addFormDataPart("classify", String.valueOf(type));
        return CompanyClient.getInstance().creatService(AppService.class)
                .upLoadImage(builder.build())
                .map(new Function<CommonBean<List<UpImgData>>, UpImgData>() {
                    @Override
                    public UpImgData apply(CommonBean<List<UpImgData>> listCommonBean) throws Exception {
                        return listCommonBean.getData().get(0);
                    }
                })
                .compose(RxUtils.getSchedulersObservableTransformer())
                ;
    }
}
