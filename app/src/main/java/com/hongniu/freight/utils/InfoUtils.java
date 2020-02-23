package com.hongniu.freight.utils;

import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.fy.companylibrary.config.Param;
import com.google.gson.Gson;
import com.hongniu.freight.entity.LoginInfo;

/**
 * 作者：  on 2020/2/23.
 * 个人信息相关
 */
public class InfoUtils {
    /**
     * 获取登录信息
     * @return
     */
    public static LoginInfo getLoginInfo() {
        String string = SharedPreferencesUtils.getInstance().getString(Param.LOGIN);
        LoginInfo loginInfo = null;
        try {
            loginInfo = new Gson().fromJson(string, LoginInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginInfo;
    }
}
