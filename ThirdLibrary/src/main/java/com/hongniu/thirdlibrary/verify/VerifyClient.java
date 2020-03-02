package com.hongniu.thirdlibrary.verify;

import android.content.Context;

import com.alibaba.security.rp.RPSDK;
import com.fy.androidlibrary.utils.JLog;

/**
 * 作者：  on 2020/3/1.
 * 实名认证
 */
public class VerifyClient {

    private static class InnerClass{
        private static VerifyClient client=new VerifyClient();
    }


    public static VerifyClient getInstance(){
        return InnerClass.client;
    }

    public void initClient(Context context){
        // 初始化实人认证 SDK
        RPSDK.initialize(context);
    }

    public void startVerify(Context context, String token, final OnVerifyListener listener){
        RPSDK.start(token, context, new RPSDK.RPCompletedListener() {
            @Override
            public void onAuditResult(RPSDK.AUDIT audit, String code) {
                JLog.i(audit + "  "+code);
                if (audit == RPSDK.AUDIT.AUDIT_PASS) {
                    // 认证通过。建议接入方调用实人认证服务端接口DescribeVerifyResult来获取最终的认证状态，并以此为准进行业务上的判断和处理
                    // do something
                    listener.onVerifySuccess();
                } else if(audit == RPSDK.AUDIT.AUDIT_FAIL) {
                    // 认证不通过。建议接入方调用实人认证服务端接口DescribeVerifyResult来获取最终的认证状态，并以此为准进行业务上的判断和处理
                    listener.onVerifyFail();
                } else if(audit == RPSDK.AUDIT.AUDIT_NOT) {
                    // 未认证，具体原因可通过code来区分（code取值参见下方表格），通常是用户主动退出或者姓名身份证号实名校验不匹配等原因，导致未完成认证流程
                    listener.onVerifyCancel();
                }
            }
        });
    }

    public interface OnVerifyListener{
        /**
         * 认证成功
         */
        void onVerifySuccess();

        /**
         * 认证失败
         */
        void onVerifyFail();

        /**
         * 取消认证
         */
        void onVerifyCancel();
    }

}
