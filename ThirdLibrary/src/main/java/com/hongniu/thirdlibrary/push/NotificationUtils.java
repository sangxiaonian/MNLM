package com.hongniu.thirdlibrary.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


import com.hongniu.thirdlibrary.push.config.PushConfig;

import java.util.Random;



/**
 * 作者： ${PING} on 2018/3/7.
 */

public class NotificationUtils {

    private static NotificationUtils utils;
    private String CHANNEL_ID = "12654";
    private String CHANNEL_NORMAL_ID = "12655";
    private String CHANNEL_NAME = "消息通知";

    private Class receiver;
    private int notify_sound;
    private int icon;

    public NotificationUtils setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public static NotificationUtils getInstance() {

        if (utils == null) {
            synchronized (NotificationUtils.class) {
                if (utils == null) {
                    utils = new NotificationUtils();
                }
            }
        }
        return utils;
    }

    /**
     * 设置通知栏点击时候的广播
     * @param receiver
     * @return
     */
    public NotificationUtils setReceiver(Class receiver) {
        this.receiver = receiver;
        return this;
    }

    private PendingIntent getDeletedIntent(Context context, String msg) {
        Intent intent = new Intent();
        intent.setClass(context, receiver);
        intent.putExtra(PushConfig.msg, msg);
        intent.putExtra(PushConfig.OPEN, false);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getBroadcast(context, new Random().nextInt(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getClickIntent(Context context, String msg) {
        Intent intent = new Intent();
        intent.setClass(context, receiver);
        intent.putExtra(PushConfig.msg, msg);
        intent.putExtra(PushConfig.OPEN, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getBroadcast(context, new Random().nextInt(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void setCHANNEL_ID(String CHANNEL_ID) {
        this.CHANNEL_ID = CHANNEL_ID;
    }

    public void setCHANNEL_NORMAL_ID(String CHANNEL_NORMAL_ID) {
        this.CHANNEL_NORMAL_ID = CHANNEL_NORMAL_ID;
    }

    public void setCHANNEL_NAME(String CHANNEL_NAME) {
        this.CHANNEL_NAME = CHANNEL_NAME;
    }

    private int notifyId;
    /**
     * 展示通知栏信息
     *
     * @param context 上下文
     * @param ticker  通知栏描述
     * @param title   通知栏标题
     * @param text    通知栏内容
     * @param extra   点击事件额外数据,在点击事件时候处理，发送个相应的广播
     */
    public void showNotification(Context context, String ticker, String title, String text, String extra) {
        notifyId++;
        showNotification(context, notifyId, ticker, title, text, extra);
    }

    public void showNotification(Context context, int notifyId, String ticker, String title, String text, String extra) {

        if (icon==0){
            throw new RuntimeException("icon 不能为0，请传入正确的图标id");
        }else if (receiver==null){
            throw new RuntimeException("receiver 不能为null，请传入正确的BroadcastReceiver，建议继承 PushBroadcastReceiver");
        }


        NotificationCompat.Builder builder = creatNotificationBuider(context);
        NotificationManager notificationManager = getNotificationManager(context);
        builder.setTicker(ticker == null ? "新消息来了" : ticker);
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle(title == null ? "新消息来了" : title);
        //第二行内容 通常是通知正文
        builder.setContentText(text == null ? "新消息来了" : text);
        builder.setContentIntent(getClickIntent(context, extra))
                .setDeleteIntent(getDeletedIntent(context, extra));
        Notification notification = builder.build();
        if (showVoice()) {
            notification.sound = getSound(context);
        }
        notificationManager.notify(notifyId, notification);
    }


    private NotificationManager getNotificationManager(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null) {
            notificationManager.createNotificationChannel(creatChan(context, getCHANNEL_ID(), CHANNEL_NAME));
        }


        return notificationManager;
    }


    public String getCHANNEL_ID() {
        return showVoice() ? CHANNEL_ID : CHANNEL_NORMAL_ID;
    }

    private Uri getSound(Context context) {
        String uri = "android.resource://" + context.getPackageName() + "/" + notify_sound;
        return Uri.parse(uri);
    }


    private NotificationCompat.Builder creatNotificationBuider(Context context) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(context, getCHANNEL_ID());
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标

        if (showVoice()) {
            builder.setSound(getSound(context));
        } else {
            builder.setDefaults(Notification.DEFAULT_ALL);
        }
        //下拉显示的大图标
        builder
                .setSmallIcon(icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon));
        return builder;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel creatChan(Context context, String CHANNEL_ID, String CHANNEL_NAME) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        channel.setBypassDnd(true);    //设置绕过免打扰模式
        channel.canBypassDnd();       //检测是否绕过免打扰模式
        channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);//设置在锁屏界面上显示这条通知
        channel.setDescription("description of this notification");
        channel.setLightColor(Color.GREEN);
        channel.setName(CHANNEL_NAME);
        channel.setShowBadge(true);
        channel.setVibrationPattern(new long[]{100, 100});
        channel.enableVibration(true);

        if (showVoice()) {
            channel.setSound(getSound(context), Notification.AUDIO_ATTRIBUTES_DEFAULT);
        }

        return channel;
    }


    public boolean showVoice() {
        return notify_sound != 0;
    }


    /**
     * 设置是否使用自定义语音
     *
     * @param notify_sound
     * @return
     */
    public NotificationUtils setSound(int notify_sound) {
        this.notify_sound = notify_sound;
        return this;
    }
}
