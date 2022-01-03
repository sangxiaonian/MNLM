package com.hongniu.freight.manager

import android.content.Context
import com.fy.androidlibrary.utils.JLog
import com.fy.androidlibrary.utils.SharedPreferencesUtils
import com.fy.companylibrary.config.Param
import com.google.gson.Gson
import com.hongniu.freight.R
import com.hongniu.freight.entity.UmenToken
import com.hongniu.freight.receiver.MyPushReceiver
import com.hongniu.thirdlibrary.chact.ChactHelper
import com.hongniu.thirdlibrary.manager.PrivacyManger
import com.hongniu.thirdlibrary.pay.wechat.WeChatAppPay
import com.hongniu.thirdlibrary.push.NotificationUtils
import com.hongniu.thirdlibrary.push.client.PushClient
import com.hongniu.thirdlibrary.push.client.PushUmeng
import com.hongniu.thirdlibrary.push.inter.PlushRegisterListener
import com.hongniu.thirdlibrary.verify.VerifyClient
import com.umeng.message.entity.UMessage
import org.greenrobot.eventbus.EventBus

/**
 *@data  2021/12/12$
 *@Author PING
 *@Description
 *
 *由于改版，需要对部分第三方做延迟初始化等操作，此处进行统一管理
 */
object ThirdManager {

    fun init(context: Context, isDebug: Boolean) {
        val isAgree = PrivacyManger.isAgreePrivacy()

        //初始化微信
        WeChatAppPay.getInstance().init("wx10d4b234bc2e64dc")
        registerUM(context, isAgree)
        registerRong(context, isAgree)
        verifyClient(context, isAgree, isDebug)

    }


    private fun registerUM(context: Context, isAgree: Boolean) {
        val plushClient = PushClient.getClient()

        val pushUmeng = PushUmeng()

        plushClient.setPlush(pushUmeng)
        plushClient.setPlushRegisterListener(object : PlushRegisterListener {
            override fun onSuccess(data: String) {
                JLog.d("推送注册成功:$data")
                SharedPreferencesUtils.getInstance().putString(Param.UMENGTOKEN, data)
                EventBus.getDefault().postSticky(UmenToken())
            }

            override fun onFailure(code: String, errorReson: String) {
                JLog.e("推送注册失败:$code :  $errorReson")
            }
        })
        NotificationUtils.getInstance().setReceiver(MyPushReceiver::class.java)
        plushClient.setPlushDealWithMessageListener { context, message ->
            JLog.i("接收到推送信息：$message")
            if (message is UMessage) {
                val msg = message
                NotificationUtils
                    .getInstance() //                                .setSound(R.raw.notify_sound)//自定义声音
                    .setIcon(R.mipmap.ic_launcher)
                    .setReceiver(MyPushReceiver::class.java)
                    .showNotification(
                        context,
                        if (msg.ticker == null) "新消息来了" else msg.ticker,
                        if (msg.title == null) "新消息来了" else msg.title,
                        if (msg.text == null) "新消息来了" else msg.text,
                        Gson().toJson(message)
                    )
            }
        }
        plushClient.init(context, isAgree)
    }

    private fun registerRong(context: Context, isAgree: Boolean) {
        ChactHelper.getHelper()
            .setIsAgree(isAgree)
            .initHelper(
                context.applicationContext,
                context.getString(R.string.rong_app_key)

            ) //未读消息监听
            .setUnReadCountListener { count: Int ->
                EventBus.getDefault().postSticky(count)
            }
    }

    private fun verifyClient(context: Context, isAgree: Boolean, isDebug: Boolean) {
        if (!isAgree){
            return
        }
        // 初始化实人认证 SDK
        VerifyClient.getInstance()
            .setAppID(context.getString(R.string.verify_appID))
            .setSecret(context.getString(R.string.verify_secret))
            .setSDKlicense(context.getString(R.string.verify_SDKlicense))
            .initClient(isDebug)
    }
}