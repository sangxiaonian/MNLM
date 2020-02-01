package com.fy.companylibrary.entity;

/**
 * 作者：  on 2019/10/30.
 *
 * 请求通用
 */
public class CommonBean<T> {

    //10：成功；20：逻辑错误；30：系统错误；50：登录错误；
    private int BFlag;

    //错误原因
    private String Msg;

    //具体信息
    private T TData;

    public int getBFlag() {
        return BFlag;
    }

    public void setBFlag(int BFlag) {
        this.BFlag = BFlag;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public T getTData() {
        return TData;
    }

    public void setTData(T TData) {
        this.TData = TData;
    }
}
