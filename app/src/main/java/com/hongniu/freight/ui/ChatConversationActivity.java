package com.hongniu.freight.ui;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.InfoUtils;
import com.hongniu.freight.widget.DialogComment;
import com.hongniu.freight.widget.dialog.inter.DialogControl;
import com.hongniu.thirdlibrary.chact.ChactHelper;
import com.hongniu.thirdlibrary.chact.UserInfor;


/**
 * 单聊界面
 */
public class ChatConversationActivity extends CompanyBaseActivity {

    private UserInfor userInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_conversation);
        setToolbarSrcRight(R.mipmap.phone);
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInfor != null) {
                    new DialogComment.Builder()
                            .setDialogTitle(userInfor.getContact())
                            .setDialogContent(userInfor.getMobile())
                            .setBtLeft("取消")
                            .setBtRight("拨打")
                            .setRightClickListener(new DialogComment.OnButtonRightClickListener() {
                                @Override
                                public void onRightClick(View view, Dialog dialog) {
                                    dialog.dismiss();
                                    CommonUtils.call(mContext, userInfor.getMobile());
                                }
                            })
                            .creatDialog(mContext)
                            .show();
                } else {
                    ToastUtils.getInstance().show("正在获取信息，请稍后");
                }
            }
        });


        Uri data = getIntent().getData();
        if (data != null  ) {
            String title = data.getQueryParameter("title");
            setWhitToolBar( !TextUtils.isEmpty(title)?title:"聊天");
        } else {
            setWhitToolBar(data.getQueryParameter("聊天"));
        }

        final String userId = data.getQueryParameter("targetId");
        if (userId != null) {
            HttpAppFactory.queryRongInfor(userId)
                    .subscribe(new NetObserver<UserInfor>(null) {
                        @Override
                        public void doOnSuccess(UserInfor data) {
                            if (data != null) {
                                userInfor = data;
                                ChactHelper.getHelper().refreshUserInfoCache(userId, data);
                                StringBuilder builder = new StringBuilder();
                                if (!TextUtils.isEmpty(data.getContact())) {
                                    builder.append(data.getContact()).append("\t\t");

                                }
                                builder.append(data.getMobile());
                                final String name = builder.toString();
                                setWhitToolBar(name);
                            }
                        }
                    });

        }

        //更改自己的用户名信息
        LoginInfo loginInfor = InfoUtils.getLoginInfo();
        if (loginInfor != null && loginInfor.getId() != null) {
            final String id = loginInfor.getId();
            HttpAppFactory.queryRongInfor(loginInfor.getId())
                    .subscribe(new NetObserver<UserInfor>(null) {
                        @Override
                        public void doOnSuccess(UserInfor data) {
                            ChactHelper.getHelper().refreshUserInfoCache(id, data);
                        }
                    });
        }

    }
}
