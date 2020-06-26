package com.hongniu.thirdlibrary.push.client;

import android.app.Application;
import android.content.Context;

import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.JLog;
import com.hongniu.thirdlibrary.push.NotificationUtils;
import com.hongniu.thirdlibrary.push.config.PushConfig;
import com.hongniu.thirdlibrary.push.inter.PlushDealWithMessageListener;
import com.hongniu.thirdlibrary.push.inter.PlushRegisterListener;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;




/**
 * 作者： ${PING} on 2018/6/26.
 * 友盟推送
 */

public class PushUmeng implements IPush<UMessage> {


    private final PushAgent mPushAgent;


    private PlushRegisterListener registerListener;
    private PlushDealWithMessageListener<UMessage> dealWithMessageListener;

    /**
     * 只能在Application中使用
     *
     * @param context
     */
    public PushUmeng(Context context,String appKey,String pushSercet) {
//        UMConfigure.init(context, PushConfig.appKey, DeviceUtils.getDeviceBrand(), UMConfigure.DEVICE_TYPE_PHONE, PushConfig.pushSercet);
        UMConfigure.init(context, appKey, DeviceUtils.getDeviceBrand(), UMConfigure.DEVICE_TYPE_PHONE, pushSercet);
        mPushAgent = PushAgent.getInstance(context);
        //注册rom渠道
        registerRomChannel(context);
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                if (registerListener != null) {
                    registerListener.onSuccess(deviceToken);
                }
            }

            @Override
            public void onFailure(String s, String s1) {
                if (registerListener != null) {
                    registerListener.onFailure(s, s1);
                }
            }
        });
        //自定义消息处理
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public void dealWithNotificationMessage(Context context, UMessage msg) {
                if (dealWithMessageListener != null) {
                    dealWithMessageListener.dealMessage(context, msg);
                } else {
                    NotificationUtils.getInstance().showNotification(context
                            , msg.ticker == null ? "新消息来了" : msg.ticker
                            , msg.title == null ? "新消息来了" : msg.title
                            , msg.text == null ? "新消息来了" : msg.text
                            , msg.custom
                    );
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);
    }

    private void registerRomChannel(Context context) {
        String deviceBrand = DeviceUtils.getDeviceBrand();
//        if (deviceBrand.equalsIgnoreCase("Huawei") || deviceBrand.equalsIgnoreCase("honor")) {//如果是华为手机
//            //注册推送服务，每次调用register方法都会回调该接口
//            HuaWeiRegister.register((Application) context);
//            JLog.i(deviceBrand);
//        } else if (deviceBrand.equalsIgnoreCase("Xiaomi")) {//小米手机渠道
//            MiPushRegistar.register(context, PushConfig.xiaomi_id, PushConfig.xiaomi_key);
//        } else if (deviceBrand.equalsIgnoreCase("meizu")) {//魅族手机渠道{
////            MeizuRegister.register(context, meizu_id, meizu_key);
//        } else if (deviceBrand.equalsIgnoreCase("vivo")) {//VIVO手机渠道{
//            //TODO 清单文件中尚未填写key和appid没有
//            VivoRegister.register(context);
//        } else if (deviceBrand.equalsIgnoreCase("oppo")) {//oppo{
//            //TODO 尚未进行测试，目前是测试key，上线之后更改为正式
//            OppoRegister.register(context, PushConfig.oppo_appkey, PushConfig.oppo_appsecret);
//        }

    }

    /**
     * 注册推送结果监听
     *
     * @param registerListener
     */
    @Override
    public void setPlushRegisterListener(PlushRegisterListener registerListener) {
        this.registerListener = registerListener;
    }


    /**
     * 处理接收到推送消息监听
     *
     * @param dealWithMessageListener 处理接收到推送消息监听
     */
    @Override
    public void setPlushDealWithMessageListener(PlushDealWithMessageListener dealWithMessageListener) {
        this.dealWithMessageListener = dealWithMessageListener;
    }

    /**
     * 点击统计自定义消息
     *
     * @param context
     * @param msg
     */
    @Override
    public void trackMsgClick(Context context, UMessage msg) {
        UTrack.getInstance(context).trackMsgClick(msg);
    }

    /**
     * 点击忽略统计自定义消息
     *
     * @param context
     * @param msg
     */
    @Override
    public void trackMsgDismissed(Context context, UMessage msg) {
        UTrack.getInstance(context).trackMsgDismissed(msg);
    }

    @Override
    public void onAppStart(Context context) {
        PushAgent.getInstance(context).onAppStart();
    }

    /**
     * 判断是否支持当前推送模式
     *
     * @param context
     * @return
     */
    @Override
    public boolean isSupport(Context context) {
        return true;
    }

}
