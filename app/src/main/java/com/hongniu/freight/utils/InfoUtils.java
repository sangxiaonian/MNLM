package com.hongniu.freight.utils;

import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.fy.companylibrary.config.Param;
import com.google.gson.Gson;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.entity.BaseAttestationInfo;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.freight.entity.PersonInfor;

/**
 * 作者：  on 2020/2/23.
 * 个人信息相关
 */
public class InfoUtils {
    /**
     * 获取登录信息
     *
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

    /**
     * 获取登录信息
     */
    public static void saveLoginInfo(LoginInfo loginInfo) {
        if (loginInfo == null) {
            return;
        }
        SharedPreferencesUtils.getInstance().putString(Param.LOGIN, new Gson().toJson(loginInfo));

    }

    /**
     * 获取个人信息
     */
    public static void saveMyInfo(PersonInfor loginInfo) {
        if (loginInfo == null) {
            return;
        }
        SharedPreferencesUtils.getInstance().putString(Param.MY, new Gson().toJson(loginInfo));

    }

    /**
     * 获取个人信息
     *
     * @return
     */
    public static PersonInfor getMyInfo() {
        String string = SharedPreferencesUtils.getInstance().getString(Param.MY);
        PersonInfor myInfo = null;
        try {
            myInfo = new Gson().fromJson(string, PersonInfor.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myInfo;
    }

    /**
     * 退出登录
     */
    public static void loginOut() {
        SharedPreferencesUtils.getInstance().clear();
    }


    /**
     * 获取当前认证状态
     *
     * @return
     */
    public static int getState(BaseAttestationInfo info) {
        int status = 0;
        if (info.isStaff()) {
            //平台员工
            status = info.getIsStaffStatus();
        } else if (info.getIsDriver() == 1) {
            //如果是司机
            status = info.getIsDriverStatus();
        } else if (info.getIsPersonCarrier() == 1) {
            //个人承运人
            status = info.getIsPersonCarrierStatus();
        } else if (info.getIsCompanyCarrier() == 1) {
            //公司承运人
            status = info.getIsCompanyCarrierStatus();
        } else if (info.getIsCompanyShipper() == 1) {
            //公司托运人
            status = info.getIsCompanyShipperStatus();
        } else if (info.getIsPersonShipper() == 1) {
            //个人托运人
            status = info.getIsCompanyShipperStatus();
        }
        return status;
    }
    /**
     * 获取当前认证状态
     *
     * @return -1 未认证角色
     */
    public static int getRole(BaseAttestationInfo info) {
        int status ;
        if (info==null){
            return -1;
        }
        if (info.isStaff()) {
            //平台员工
           status=0;
        } else if (info.getIsDriver() == 1) {
            //如果是司机
            status=1;
        } else if (info.getIsPersonCarrier() == 1) {
            //个人承运人
            status=2;
        } else if (info.getIsCompanyCarrier() == 1) {
            //公司承运人
            status=3;
        } else if (info.getIsCompanyShipper() == 1) {
            //公司托运人
            status=4;
        } else if (info.getIsPersonShipper() == 1) {
            //个人托运人
            status=5;
        }else {
            status=-1;
        }
        return status;
    }

}
