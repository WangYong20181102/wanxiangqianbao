package com.jh.wxqb.api.biz;

import com.jh.wxqb.base.OnBaseListener;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Part;

/**
 * Created by Administrator on 2018/6/9 0009.
 */
@SuppressWarnings("all")
public interface Biz {

    //注册
    void userRegister(Map<String, String> map, OnBaseListener listener);

    //获取短信验证码
    void getMessageCode(String mobile, OnBaseListener listener);

    //登录
    void userLogin(Map<String, String> map, OnBaseListener listener);

    //获取用户信息
    void getUserInfo(OnBaseListener listener);

    //我的買入
    void myDividend(int pageNum, int type, OnBaseListener listener);

    //停止買入
    void stopDividend(Map<String, String> map, OnBaseListener listener);

    //撤消挂卖/停止排队
    void stopQueuing(Map<String, String> map, OnBaseListener listener);

    //忘记密码
    void forgetLoginPwd(String mobile, String newOnePwd, String smsCode, OnBaseListener listener);

    //修改登录/交易密码
    void udpLoginPwd(int type, String newOnePwd, String newTwoPwd, String smsCode,String mobile, OnBaseListener listener);

    //绑定账户邮箱
    void bandingEmail(Map<String, String> map, OnBaseListener listener);

    //绑定账户邮箱
    void myTeam(int pageNum, OnBaseListener listener);

    //查询用户上传的头像
    void selUserImage(OnBaseListener listener);

    //上传用户头像
    void uploadUserImage(@Part("type") int type, @Part MultipartBody.Part file, OnBaseListener listener);

    //查看新闻详情
    void newsInfo(String id, OnBaseListener listener);

    //新闻咨询列表
    void newsList(String type,int pageNum, OnBaseListener listener);

    //我的消息列表
    void myMessage(int pageNum, OnBaseListener listener);

    //我的消息详情
    void myMessageDetails(String id, OnBaseListener listener);

    //保存用户反馈
    void saveUserFeedback(List<MultipartBody.Part> file, String content, OnBaseListener listener);

    //用户反馈列表
    void userFeedbackList(int pageNum, OnBaseListener listener);

    //查询用户反馈详情
    void queryUserFeedback(String id, OnBaseListener listener);

    //获取财务明细类型
    void financialDetailsType(OnBaseListener listener);

    //获取财务明细列表
    void getFinancialDetails(int pageNum,int type,int coinTypeId,OnBaseListener listener);
    //資產管理
    void getQueryaccountassets(int pageNum,int type,OnBaseListener listener);

    //提币
    void withdrawMoney(Map<String, String> map, OnBaseListener listener);

    //買入市場--買入顶部列表
    void dividendMarketDividend(int type,OnBaseListener listener);

    //買入市場--買入底部列表
    void dividendMarketTopDividend(int pageNum,int type,int direction,OnBaseListener listener);

    //购买订单
    void purchaseOrder(Map<String, String> map, OnBaseListener listener);

    //買入
    void sellOut(Map<String, String> map, OnBaseListener listener);

    //卖出
    void marketSell(Map<String, String> map, OnBaseListener listener);

    //获取以太币和子币的价格
    void getCurrentPrice(Map<String, String> map, OnBaseListener listener);

    //获取邮箱验证码
    void getEmailCode(String email, OnBaseListener listener);

    //获取安全标示
    void safetyMarking(OnBaseListener listener);

    //版本更新
    void versionUpdate(OnBaseListener listener);

    //获取token失效时间
    void getTime(OnBaseListener listener);
    //获取币种价格
    void getQuerycoinprice(OnBaseListener listener);
    //币种兑换
    void getSavechangeinfo(Map<String, String> map,OnBaseListener listener);



}
