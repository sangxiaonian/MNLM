package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/1.
 */
public class VerifyTokenBeans {
   private int isAuthen;//	true	number	是否已识别(0 未识别 1 已识别)
   private String token;//	true	string	认证token

    public int getIsAuthen() {
        return isAuthen;
    }

    public void setIsAuthen(int isAuthen) {
        this.isAuthen = isAuthen;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
