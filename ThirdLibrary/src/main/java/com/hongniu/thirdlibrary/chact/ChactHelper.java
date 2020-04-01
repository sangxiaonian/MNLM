package com.hongniu.thirdlibrary.chact;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.LruCache;


import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.JLog;
import com.hongniu.thirdlibrary.chact.config.RongConfig;
import com.hongniu.thirdlibrary.chact.control.ChactControl;
import com.hongniu.thirdlibrary.chact.control.OnGetUserInforListener;

import java.util.Objects;

import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * 作者： ${桑小年} on 2018/11/25.
 * 努力，为梦长留
 */
public class ChactHelper {

    OnGetUserInforListener listener;
    ChactControl.OnReceiveUnReadCountListener unReadCountListener;
    private String ownerID;


    private static class Inner {
        private static ChactHelper helper = new ChactHelper();
    }

    public static ChactHelper getHelper() {
        return Inner.helper;
    }

    /**
     * 初始化数据
     *
     * @param application
     */
    public ChactHelper initHelper(Application application) {
        String deviceBrand = DeviceUtils.getDeviceBrand();
        if (application.getApplicationInfo().packageName.equals(getCurProcessName(application))) {
            RongIM.init(application);
//            if (deviceBrand.equalsIgnoreCase("Xiaomi")){
//                //小米推送配置
//                RongPushClient.registerMiPush(application, "2882303761517871354", "5731787151354");
//            }else if (deviceBrand.equalsIgnoreCase("huawei")){
//                //华为配置
//                RongPushClient.registerHWPush(application);
//            }


            RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                @Override
                public UserInfo getUserInfo(final String s) {
                    if (listener != null) {
                        listener.onGetUserInfor(s)
                                .subscribe(new BaseObserver<UserInfor>(null) {
                                    @Override
                                    public void onNext(UserInfor infor) {
                                        super.onNext(infor);
                                        refreshUserInfoCache(s, infor);
                                    }
                                });
                    }
                    return null;
                }
            }, true);


            RongIM.getInstance().addUnReadMessageCountChangedObserver(new IUnReadMessageObserver() {
                @Override
                public void onCountChanged(int i) {
                    if (unReadCountListener != null) {
                        unReadCountListener.onReceiveUnRead(i);
                    }
                }
            }, Conversation.ConversationType.PRIVATE);
        }
        return this;
    }


    /**
     *
     * @param context
     * @param token    用户自己的token
     * @param callback
     */
    public void connect(final Context context,String token,   final ChactControl.OnConnectListener callback) {
        JLog.i("融云token: "+token);
        if (context.getApplicationInfo().packageName.equals(getCurProcessName(context.getApplicationContext()))) {
            ownerID = null;
            JLog.d("开始连接服务器");
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    JLog.e("初始化失败");
                    if (callback != null) {
                        callback.onConnectError(-1,"初始化失败,请检查token是否正确");
                    }
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    ownerID = userid;
                    if (callback != null) {
                        callback.onConnectSuccess(userid);
                    }
                    JLog.d("初始化成功" + userid);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    if (callback != null) {
                        callback.onConnectError(errorCode.getValue(),errorCode.getMessage());
                    }

                    if (errorCode.getValue() == 31010) {//不是异地登录
                        ToastUtils.getInstance().show("异地登录");
                    }
                    JLog.e("初始错误" + errorCode.getValue());
                }
            });
        }
    }

    public void disConnect() {
        try {
            RongIM.getInstance().logout();
            RongIM.getInstance().disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断当前是否处于断开连接状态
     * @return true 断开连接
     */
    public boolean disConnectState(){
        return RongIM.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED);
    }


    /**
     * 开启器单聊
     *
     * @param context
     * @param userID
     * @param title
     */
    public void startPriver(Context context, String userID, String title) {
        if (Objects.equals(userID,ownerID) ) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("您不能和自己对话");
        } else {
            if (userID!=null){
                RongIM.getInstance().startPrivateChat(context, userID, title == null ? "聊天" : title);
            }else {
                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("用户ID为空");
            }
        }

    }

    /**
     * 设置获取用户信息
     */
    public ChactHelper setUseInfor(final OnGetUserInforListener listener) {
        this.listener = listener;
        return this;

    }


    public ChactHelper setUnReadCountListener(ChactControl.OnReceiveUnReadCountListener unReadCountListener) {
        this.unReadCountListener = unReadCountListener;
        return this;
    }

    /**
     * 刷新用户头像信息
     *
     * @param userID
     * @param infor
     */
    public void refreshUserInfoCache(String userID, UserInfor infor) {
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(infor.getContact())) {
            builder.append(infor.getContact()).append("\t\t");

        }
        builder.append(infor.getMobile());
        final String name = builder.toString();
        final Uri head = TextUtils.isEmpty(infor.getLogoPath()) ? null : Uri.parse(infor.getLogoPath());
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(userID, name, head));
    }


    public void put(String userId, UserInfor infor) {
        cache.put(userId, infor);

        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(infor.getContact())) {
            builder.append(infor.getContact()).append("\t\t");

        }
        builder.append(infor.getMobile());


        final String name = builder.toString();
        final Uri head = TextUtils.isEmpty(infor.getLogoPath()) ? null : Uri.parse(infor.getLogoPath());
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(userId, name, head));
    }

    private LruCache<String, UserInfor> cache = new LruCache<>(100);


}
