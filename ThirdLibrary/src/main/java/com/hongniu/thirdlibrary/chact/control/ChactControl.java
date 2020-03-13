package com.hongniu.thirdlibrary.chact.control;

/**
 * 作者： ${PING} on 2018/11/29.
 */
public class ChactControl {


    /**
     * 融云未读消息接受监听
     */
    public interface OnReceiveUnReadCountListener {

        /**
         * 有未读消息时候
         *
         * @param count 未读消息
         */
        void onReceiveUnRead(int count);
    }

    /**
     * 融云未读消息接受监听
     */
    public interface OnConnectListener {

        /**
         * 连接成功
         *
         */
        void onConnectSuccess(  String userID);

        /**
         * 连接错误
         *
         */
        void onConnectError(int errorCode, String errorMsg);

    }

}
