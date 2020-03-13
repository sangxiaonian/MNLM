package com.fy.companylibrary.config;

/**
 * 作者：  on 2019/10/29.
 */
public class Param {


    public static final String debugUrl = "http://47.104.130.110:9080/";
//    public static final String debugUrl = "http://d08e08b1.ngrok.io//wlhyapi/";
    public static final String releaseUrl = "https://pmls.ehousechina.com/";
    public static final long IMAGESIZE = 1024 * 1024 * 5;
    public static final float INSURANCE = 0.00015f;//保费计算费率

    public static String baseUrl;


    /**
     * 华夏银行签约流程
     */
    public static final String HUAXIA ="https://api.hongniudai.cn/static/huaxia_process.html";

    //App本身秘钥
    public static final String key = "85274113a1ce1c39";
    public static final String AppKey = "c33fbf23b76246bf8ee4a3d00b621e03";
    public static final String AppSecret = "a7735d245d4241ff9f94-a3ecf7b5fedc";

    public static final int PAGE_SIZE = 20;
    public static final int SUCCESS_FLAG = 200;//返回数据成功的标记
    public static final String TRAN = "TRAN";
    public static final String TYPE = "TYPE";
    public static final String ABOUT_US ="https://api.hongniudai.cn/static/html/service.html" ;
   public static final String  hongniu_agreement = "https://api.hongniudai.cn/" + "static/html/service.html";
   public static final String  insurance_notify = "https://api.hongniudai.cn/" + "static/html/notice.html";
   public static final String  insurance_polic = "https://api.hongniudai.cn/" + "static/html/insurance_2009.html";
   public static final String  hongniu_user_guide =   "https://api.hongniudai.cn/static/html/usermanual.html";

    /**
     * 确认到达时候，距离目的地的最小距离 单位 千米
     */
    public static final int ENTRY_MIN = 5000;
    public static String LOGIN="login";//登录信息
    public static String MY="my";//个人信息
}
