package com.jh.wxqb.api;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/6/9 0009.
 */

public interface ServerApi {


    //手机注册
    @POST(ServerInterface.USER_REGISTER)
    @FormUrlEncoded
    Call<String> userRegister(@FieldMap Map<String, String> map);

    //获取验证码
    @GET(ServerInterface.GET_MESSAGE_CODE)
    Call<String> getMessageCode(@Query("mobile") String mobile);

    //登录
    @POST(ServerInterface.USER_LOGIN)
    @FormUrlEncoded
    Call<String> userLogin(@FieldMap Map<String, String> map);

    //版本更新
    @GET(ServerInterface.VERSION_UPDATE)
    Call<String> versionUpdate();

    //获取用户信息
    @GET(ServerInterface.GET_USER_INFO)
    Call<String> getUserInfo();

    //我的買入
    @GET(ServerInterface.MY_DIVIDEND)
    Call<String> myDividend(@Query("pageNum") int pageNum, @Query("type") int type);

    //停止買入
    @POST(ServerInterface.STOP_DIVIDEND)
    @FormUrlEncoded
    Call<String> stopDividend(@FieldMap Map<String, String> map);

    //撤消挂卖/停止排队
    @POST(ServerInterface.STOP_QUEUING)
    @FormUrlEncoded
    Call<String> stopQueuing(@FieldMap Map<String, String> map);

    //忘记密码
    @PUT(ServerInterface.FORGET_LOGIN_PWD)
    Call<String> forgetLoginPwd(@Query("mobile") String mobile, @Query("newOnePwd") String newOnePwd,  @Query("smsCode") String smsCode);

    //修改登录/交易密码
    @PUT(ServerInterface.UDP_LOGIN_PWD)
    Call<String> udpLoginPwd(@Query("type") int type, @Query("newOnePwd") String newOnePwd,
                             @Query("newTwoPwd") String newTwoPwd, @Query("smsCode") String smsCode, @Query("mobile") String mobile);

    //绑定账户邮箱
    @POST(ServerInterface.BANDING_EMAIL)
    @FormUrlEncoded
    Call<String> bandingEmail(@FieldMap Map<String, String> map);

    //我的團隊
    @GET(ServerInterface.MY_TEAM)
    Call<String> myTeam(@Query("pageNum") int pageNum);

    //查询用户上传的头像
    @GET(ServerInterface.SEL_USER_IMAGE)
    Call<String> selUserImage();

    //上传用户头像
    @Multipart
    @POST(ServerInterface.UPLOAD_USER_IMAGE)
    Call<String> uploadUserImage(@Part("type") int type, @Part MultipartBody.Part file);

    //新闻咨询详情
    @GET(ServerInterface.NEWS_INFO)
    Call<String> newsInfo(@Query("id") String id);

    //新闻咨询列表
    @GET(ServerInterface.NEWS_LIST)
    Call<String> newsList(@Query("type") String type, @Query("pageNum") int pageNum);

    //我的消息列表
    @GET(ServerInterface.MY_MESSAGE)
    Call<String> myMessage(@Query("pageNum") int pageNum);

    //我的消息详情
    @GET(ServerInterface.MY_MESSAGE_DETAILS)
    Call<String> myMessageDetails(@Query("id") String id);

    //保存用户反馈
    @Multipart
    @POST(ServerInterface.SAVE_USER_FEEDBACK)
    Call<String> saveUserFeedback(@Part() List<MultipartBody.Part> file, @Part("content") String content);

    //用户反馈列表
    @GET(ServerInterface.USER_FEEDBACK_LIST)
    Call<String> userFeedbackList(@Query("pageNum") int pageNum);

    //查询用户反馈详情
    @GET(ServerInterface.QUERY_USER_FEEDBACK)
    Call<String> queryUserFeedback(@Query("id") String id);

    //获取财务明细类型
    @GET(ServerInterface.FINANCIAL_DETAILS_TYPE)
    Call<String> financialDetailsType();

    //获取财务明细列表
    @GET(ServerInterface.GET_FINANCIAL_DETAILS)
    Call<String> getFinancialDetails(@Query("pageNum") int pageNum, @Query("type") int type,@Query("coinTypeId") int coinTypeId);
    //資產管理
    @GET(ServerInterface.GET_QUERYACCOUNTASSETS)
    Call<String> getQueryaccountassets(@Query("pageNum") int pageNum, @Query("type") int type);
    //提币
    @POST(ServerInterface.WITHDRAW_MONEY)
    @FormUrlEncoded
    Call<String> withdrawMoney(@FieldMap Map<String, String> map);

    //買入市場--買入顶部列表
    @GET(ServerInterface.DIVIDEND_MARKET_TOP_DIVIDEND)
    Call<String> dividendMarketDividend(@Query("type") int type);

    //買入市場--買入底部列表
    @GET(ServerInterface.DIVIDEND_MARKET_BOTTOM_DIVIDEND)
    Call<String> dividendMarketTopDividend(@Query("pageNum") int pageNum,@Query("type") int type,@Query("direction") int direction);

    //购买订单
    @POST(ServerInterface.PURCHASE_ORDER)
    @FormUrlEncoded
    Call<String> purchaseOrder(@FieldMap Map<String, String> map);

    //買入
    @POST(ServerInterface.SELL_OUT)
    @FormUrlEncoded
    Call<String> sellOut(@FieldMap Map<String, String> map);

    //卖出
    @POST(ServerInterface.MARKET_SELL)
    @FormUrlEncoded
    Call<String> marketSell(@FieldMap Map<String, String> map);

    //获取以太币和子币的价格
    @POST(ServerInterface.GET_CURRENT_PRICE)
    @FormUrlEncoded
    Call<String> getCurrentPrice(@FieldMap Map<String, String> map);

    //获取邮箱验证码
    @GET(ServerInterface.GET_EMAIL_CODE)
    Call<String> getEmailCode(@Query("email") String email);

    //获取安全标示
    @GET(ServerInterface.SAFETY_MARKING)
    Call<String> safetyMarking();

    //获取token失效时间
    @GET(ServerInterface.GET_TIME)
    Call<String> getTime();
    //获取币种价格
    @GET(ServerInterface.GET_QUERYCOINPRICE)
    Call<String> getQuerycoinprice();
    //币种兑换
    @POST(ServerInterface.GET_SAVECHANGEINFO)
    @FormUrlEncoded
    Call<String> getSavechangeinfo(@FieldMap Map<String, String> map);
    //新闻资讯
    @GET(ServerInterface.GET_NOTICE)
    Call<String> getNewMessageData(@Query("type")int type);
    //折线图
    @GET(ServerInterface.GET_ECHARTS)
    Call<String> getLineFigureData();


}
