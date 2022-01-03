package com.hongniu.thirdlibrary.chact;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.LruCache;

import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.JLog;
import com.hongniu.thirdlibrary.chact.control.ChactControl;
import com.hongniu.thirdlibrary.chact.control.OnGetUserInforListener;

import java.util.Objects;

import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * 作者： ${桑小年} on 2018/11/25.
 * 努力，为梦长留
 */
public class ChactHelper {

    OnGetUserInforListener listener;
    ChactControl.OnReceiveUnReadCountListener unReadCountListener;
    private String ownerID;
    private boolean isAgree;
    private boolean isInit;
    private String appKey;

    private static class Inner {
        private static ChactHelper helper = new ChactHelper();
    }

    public static ChactHelper getHelper() {
        return Inner.helper;
    }

    public ChactHelper setIsAgree(boolean isAgree) {
        this.isAgree = isAgree;
        return this;
    }

    /**
     * 初始化数据
     *
     * @param application
     * @param key
     */
    public ChactHelper initHelper(Context application, String key) {
        this.appKey = key;
        if (isAgree && application.getApplicationInfo().packageName.equals(getCurProcessName(application))) {
            RongIM.init(application);
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
     * @param context
     * @param token    用户自己的token
     * @param callback
     */
    public void connect(final Context context, String token, final RongIMClient.ConnectCallback callback) {
        if (!isAgree) {
            return;
        }
        if (!isInit) {
            initHelper(context, appKey);
        }
        JLog.i("融云token: " + token);
        if (context.getApplicationInfo().packageName.equals(getCurProcessName(context.getApplicationContext()))) {
            ownerID = null;
            JLog.d("开始连接服务器");
            RongIM.connect(token, new RongIMClient.ConnectCallback() {


                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    ownerID = userid;
                    if (callback != null) {
                        callback.onSuccess(userid);
                    }
                    JLog.d("初始化成功" + userid);
                }

                @Override
                public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {
                    JLog.e("初始化失败");

                    if (callback != null) {
                        callback.onError(connectionErrorCode);
                    }

                    if (connectionErrorCode.getValue() == 31010) {//不是异地登录
                        ToastUtils.getInstance().show("异地登录");
                    }
                    JLog.e("初始错误" + connectionErrorCode.getValue());
                }

                @Override
                public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {
                    callback.onDatabaseOpened(databaseOpenStatus);
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
     *
     * @return true 断开连接
     */
    public boolean disConnectState() {
        return !RongIM.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED);
    }


    /**
     * 开启器单聊
     *
     * @param context
     * @param userID
     * @param title
     */
    public void startPriver(Context context, String userID, String title) {
        if (Objects.equals(userID, ownerID)) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("您不能和自己对话");
        } else {
            if (userID != null) {
                RongIM.getInstance().startPrivateChat(context, userID, title == null ? "聊天" : title);
            } else {
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
