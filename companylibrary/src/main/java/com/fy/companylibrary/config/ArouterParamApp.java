package com.fy.companylibrary.config;

/**
 * 作者：  on 2019/10/30.
 * module 中使用到的Arouter 路径
 * <p>
 * 命名时注意参考已有命名方式，/module/路径
 * <p>
 * 为了避免重复路径以 module+activity/fragment开头 ，注意不要有重复，重复会引起异常，难以排查
 * <p>
 * 新建Module时候，需要重新创建一个配置，要求和此配置类似
 */
public class ArouterParamApp {

    public static final String fragment_home = "/app/fragment_home";
    public static final String fragment_home_type = "/app/fragment_home_type";
    public static final String activity_main = "/app/activity_main";
    public static final String activity_my_order = "/app/activity_my_order";
    public static final String fragment_my_order = "/app/fragment_my_order";
    /**
     * 个人中心
     */
    public static final String fragment_person_center = "/app/fragment_person_center";
    /**
     * 搜索订单
     */
    public static final String activity_search_order = "/app/activity_search_order";
    /**
     * 首页订单数据
     */
    public static final String fragment_order_home = "/app/fragment_order_home";
    /**
     * 订单详情也
     */
    public static final String activity_order_detail = "/app/activity_order_detail";

    public static final String activity_h5 = "/app/activity_h5";

    /**
     * 关于我们
     */
    public static final String activity_about_us = "/app/activity_about_us";
    /**
     * 我的车辆信息
     */
    public static final String activity_my_car_list = "/app/activity_my_car_list";
   /**
     * 我要接单
     */
    public static final String activity_order_center = "/app/activity_order_center";
    /**
     * 接单中心
     */
    public static final String activity_order_receiving = "/app/activity_order_receiving";

}
