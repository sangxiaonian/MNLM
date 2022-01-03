package com.hongniu.thirdlibrary.push.client;

import android.content.Context;

import com.fy.androidlibrary.utils.DeviceUtils;
import com.hongniu.thirdlibrary.push.inter.PlushDealWithMessageListener;
import com.hongniu.thirdlibrary.push.inter.PlushRegisterListener;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.api.UPushRegisterCallback;
import com.umeng.message.entity.UMessage;


/**
 * 作者： ${PING} on 2018/6/26.
 * 友盟推送
 */

public class PushUmeng implements IPush<UMessage> {


    private PushAgent mPushAgent;


    private PlushRegisterListener registerListener;
    private PlushDealWithMessageListener<UMessage> dealWithMessageListener;
    private boolean isAgree;
    private int notifyId;

    /**
     * 只能在Application中使用
     *
     */
    public PushUmeng() {}

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

    public void init(final Context context, boolean isAgreed) {
        UMConfigure.setLogEnabled(true);
        //预初始化
        preInit(context);
        this.isAgree = isAgreed;
        if (!isAgreed) {
            return;
        }
        boolean isMainProcess = UMUtils.isMainProgress(context);
        if (isMainProcess) {
            //启动优化：建议在子线程中执行初始化
            new Thread(new Runnable() {
                @Override
                public void run() {
                    configUmeng(context.getApplicationContext());
                }
            }).start();
        } else {
            //若不是主进程（":channel"结尾的进程），直接初始化sdk，不可在子线程中执行
            configUmeng(context.getApplicationContext());
        }
    }

    private void preInit(Context context) {
        //解决厂商通知点击时乱码等问题
        PushAgent.setup(context, PushConstants.APP_KEY, PushConstants.MESSAGE_SECRET);
        UMConfigure.preInit(context, PushConstants.APP_KEY, PushConstants.CHANNEL);
    }

    private void configUmeng(Context context) {
        UMConfigure.init(context, PushConstants.APP_KEY, PushConstants.CHANNEL,
                UMConfigure.DEVICE_TYPE_PHONE, PushConstants.MESSAGE_SECRET);
        mPushAgent = PushAgent.getInstance(context);
        //自定义消息处理
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public void dealWithNotificationMessage(Context context, UMessage msg) {
                notifyId++;
                if (dealWithMessageListener != null) {
                    dealWithMessageListener.dealMessage(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);
        mPushAgent.register(new UPushRegisterCallback() {

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

        registerRomChannel(context);
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
