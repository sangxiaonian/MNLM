package com.hongniu.thirdlibrary.push.client;

import android.content.Context;

import com.hongniu.thirdlibrary.push.inter.PlushDealWithMessageListener;
import com.hongniu.thirdlibrary.push.inter.PlushRegisterListener;


/**
 * 作者： ${PING} on 2018/6/26.
 */

public class PushClient implements IPush {

    IPush plush;

    public static  PushClient client;

    public static  PushClient getClient() {
        if (client == null) {
            synchronized ( PushClient.class) {
                if (client == null) {
                    client = new  PushClient();
                }
            }
        }
        return client;
    }

    private PushClient() {
    }

    public void setPlush(IPush plush) {
        this.plush = plush;
    }

    public void setPlush(Context context) {
        this.plush = new PushUmeng(context);

    }

    /**
     * 注册推送结果监听
     *
     * @param registerListener
     */
    @Override
    public void setPlushRegisterListener(PlushRegisterListener registerListener) {
        if (plush != null) {
            plush.setPlushRegisterListener(registerListener);
        }
    }

    /**
     * 处理接收到推送消息监听
     *
     * @param dealWithMessageListener 处理接收到推送消息监听
     */
    @Override
    public void setPlushDealWithMessageListener(PlushDealWithMessageListener dealWithMessageListener) {
        if (plush != null) {
            plush.setPlushDealWithMessageListener(dealWithMessageListener);
        }
    }

    /**
     * 统计消息点击打开
     *
     * @param context
     * @param message
     */
    @Override
    public void trackMsgClick(Context context, Object message) {
        if (plush != null) {
            plush.trackMsgClick(context, message);
        }
    }

    /**
     * 统计消息忽略
     *
     * @param context
     * @param message
     */
    @Override
    public void trackMsgDismissed(Context context, Object message) {
        if (plush != null) {
            plush.trackMsgDismissed(context, message);
        }
    }

    @Override
    public void onAppStart(Context context) {
        if (plush != null) {
            plush.onAppStart(context);
        }
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
