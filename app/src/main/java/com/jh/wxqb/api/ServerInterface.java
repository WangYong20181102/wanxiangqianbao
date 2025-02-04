package com.jh.wxqb.api;


import com.jh.wxqb.base.MyApplication;

public class ServerInterface {

    public static final int SUCCESS = 8008;
    public static final int LOGIN_INVALID = 9009;
    public static final int TOKEN_INVALID = 401;


    //van nex mall
    public static final String BASE_VAN_NEX_MALL = "http://www.vannexmall.com";


    //服务器常量类
//    public static final String BASE_URL = "http://192.168.101.123:80/";//本地环境
//    public static final String BASE_WEB_URL = "http://192.168.101.123:80";   //本地环境 首页Web路径   服务器路径去掉最后
//    public static final String BASE_Q_CARD = "http://192.168.101.123:80";   //推荐二维码


    public static final String BASE_URL = "https://www.qbmap.com/";  //正式环境
    public static final String BASE_WEB_URL = "https://www.qbmap.com";   //正式环境 首页Web路径   服务器路径去掉最后
    public static final String BASE_Q_CARD = "https://www.qbmap.com";   //推荐二维码


//    public static final String BASE_URL = "https://test.tgmmix.com:80/";  //测试环境18825572827   15767847174  13714173670 13266761427
//    public static final String BASE_WEB_URL = "https://test.tgmmix.com:80";   //测试环境 首页Web路径   服务器路径去掉最后
//    public static final String BASE_Q_CARD = "https://test.tgmmix.com:80";   //推荐二维码


    //我要推荐二维码链接路径
    public static final String BASE_WEB_REGISTER_URL = BASE_Q_CARD +
            "/kline/index.html#/register?webpath=" + BASE_Q_CARD + "&lang=" + MyApplication.getLanuage() + "&tjrId=";
    //手机注册
    public static final String USER_REGISTER = "register.do";

    //获取短信验证码
    public static final String GET_MESSAGE_CODE = "captcha/sms";

    //登录
//    public static final String USER_LOGIN = "authentication/form";
    public static final String USER_LOGIN = "authentication/mobile";

    //版本更新
    public static final String VERSION_UPDATE = "app/endpoint/android";

    //获取用户信息
    public static final String GET_USER_INFO = "userInfo";

    //交易记录
    public static final String MY_DIVIDEND = "queryMyCommission";

    //停止買入
    public static final String STOP_DIVIDEND = "transaction/stopInterest";

    //撤消挂卖/停止排队
    public static final String STOP_QUEUING = "transaction/cancelSellOut";

    //忘记密码
    public static final String FORGET_LOGIN_PWD = "loginPassword";

    //修改登录/交易密码
    public static final String UDP_LOGIN_PWD = "upLogPwd";

    //绑定账户邮箱
    public static final String BANDING_EMAIL = "updateEmail";

    //我的團隊
//    public static final String MY_TEAM = "rTeamInfo";
    public static final String MY_TEAM = "myTeamInfo";

    //查询用户上传的头像
    public static final String SEL_USER_IMAGE = "imgAddress";

    //上传用户头像
    public static final String UPLOAD_USER_IMAGE = "upload";

    //新闻咨询详情
    public static final String NEWS_INFO = "noticeContent";

    //新闻咨询列表
    public static final String NEWS_LIST = "notice";

    //我的消息列表
    public static final String MY_MESSAGE = "sysMessageInfo";

    //我的消息详情
    public static final String MY_MESSAGE_DETAILS = "querySysMessage";

    //保存用户反馈
    public static final String SAVE_USER_FEEDBACK = "message";

    //用户反馈列表
    public static final String USER_FEEDBACK_LIST = "messageInfo";

    //查询用户反馈详情
    public static final String QUERY_USER_FEEDBACK = "queryMessage";

    //获取财务明细类型
    public static final String FINANCIAL_DETAILS_TYPE = "getType";

    //获取财务明细列表
    public static final String GET_FINANCIAL_DETAILS = "queryLog";
    //資產管理
    public static final String GET_QUERYACCOUNTASSETS = "queryAccountAssets";

    //提币
    public static final String WITHDRAW_MONEY = "mentionMoney";

    //買入市場--買入顶部列表
//    public static final String DIVIDEND_MARKET_TOP_DIVIDEND = "queryCommission";
    public static final String DIVIDEND_MARKET_TOP_DIVIDEND = "queryLatestSaleCommission";

    //買入市場--買入底部列表
    public static final String DIVIDEND_MARKET_BOTTOM_DIVIDEND = "queryCommission";

    //购买订单
    public static final String PURCHASE_ORDER = "transaction/purchaseInsert";

    //買入
    public static final String SELL_OUT = "transaction/purchase";

    //卖出
    public static final String MARKET_SELL = "transaction/sellOut";

    //获取以太币和子币的价格
    public static final String GET_CURRENT_PRICE = "transaction/estimate";

    //获取邮箱验证码
    public static final String GET_EMAIL_CODE = "captcha/email/code";

    //获取安全标示
    public static final String SAFETY_MARKING = "safety";

    //获取token失效时间
    public static final String GET_TIME = "parameter/getTokenInvalidTime";
    //获取币种价格
    public static final String GET_QUERYCOINPRICE = "queryCoinPrice";
    //币种兑换
    public static final String GET_SAVECHANGEINFO = "saveChangeInfo";
    //k线图
    public static final String GET_ECHARTS = "getEcharts";
    //新闻资讯
    public static final String GET_NOTICE = "notice";
    //行情
    public static final String GET_QUOTES = "getQuotesData";


}
