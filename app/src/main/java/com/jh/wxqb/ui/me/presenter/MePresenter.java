package com.jh.wxqb.ui.me.presenter;


import com.jh.wxqb.api.ServerInterface;
import com.jh.wxqb.api.biz.Biz;
import com.jh.wxqb.api.biz.bizImpl.BizImpl;
import com.jh.wxqb.base.OnBaseListener;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.FinancialDetailsBean;
import com.jh.wxqb.bean.FinancialDetailsTypeBean;
import com.jh.wxqb.bean.MyTeamBean;
import com.jh.wxqb.bean.UserImage;
import com.jh.wxqb.ui.me.view.MeView;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2018/6/11 0011.
 */

public class MePresenter {

    private Biz biz;
    private MeView meView;

    public MePresenter(MeView meView) {
        this.meView = meView;
        biz = new BizImpl();
    }

    /**
     * 修改登录/交易密码
     */
    public void udpLoginPwd(int type, String newOnePwd, String newTwoPwd, String smsCode,String mobile) {
        biz.udpLoginPwd(type, newOnePwd, newTwoPwd, smsCode,mobile, new OnBaseListener() {

            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("udpLoginPwd==>" + GsonUtil.GsonString(result));
                    BaseBean baseBean = GsonUtil.GsonToBean(result, BaseBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        meView.udpLoginPwdSuccess(baseBean);
                    } else {
                        meView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    meView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                meView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 绑定账户邮箱
     */
    public void bandingEmail(Map<String, String> map) {
        biz.bandingEmail(map, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("bandingEmail==>" + GsonUtil.GsonString(result));
                    BaseBean baseBean = GsonUtil.GsonToBean(result, BaseBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        meView.bandingEmailSuccess(baseBean);
                    } else {
                        meView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    meView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                meView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 我的团队
     */
    public void myTeam(int pageNum) {
        biz.myTeam(pageNum, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("myTeam==>" + GsonUtil.GsonString(result));
                    MyTeamBean myTeamBean = GsonUtil.GsonToBean(result, MyTeamBean.class);
                    if (myTeamBean.getCode() == ServerInterface.SUCCESS) {
                        meView.myTeamSuccess(myTeamBean);
                    } else {
                        meView.onViewFailureString(myTeamBean.getCode(), myTeamBean.getMessage());
                    }
                } else {
                    meView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                meView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 查询用户上传的头像
     */
    public void selUserImage() {
        biz.selUserImage(new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("selUserImage==>" + GsonUtil.GsonString(result));
                    UserImage userImage = GsonUtil.GsonToBean(result, UserImage.class);
                    if (userImage.getCode() == ServerInterface.SUCCESS) {
                        meView.selUserImageSuccess(userImage);
                    } else {
                        meView.onViewFailureString(userImage.getCode(), userImage.getMessage());
                    }
                } else {
                    meView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                meView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 上传用户头像
     */
    public void uploadUserImage(int type, MultipartBody.Part file) {
        biz.uploadUserImage(type, file, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("uploadUserImage==>" + GsonUtil.GsonString(result));
                    BaseBean baseBean = GsonUtil.GsonToBean(result, BaseBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        meView.uploadUserImageSuccess(baseBean);
                    } else {
                        meView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    meView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                meView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 获取财务明细类型
     */
    public void financialDetailsType() {
        biz.financialDetailsType(new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("financialDetailsType==>" + GsonUtil.GsonString(result));
                    FinancialDetailsTypeBean financialDetailsTypeBean = GsonUtil.GsonToBean(result, FinancialDetailsTypeBean.class);
                    if (financialDetailsTypeBean.getCode() == ServerInterface.SUCCESS) {
                        meView.financialDetailsTypeSuccess(financialDetailsTypeBean);
                    } else {
                        meView.onViewFailureString(financialDetailsTypeBean.getCode(), financialDetailsTypeBean.getMessage());
                    }
                } else {
                    meView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                meView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 获取财务明细列表
     */
    public void getFinancialDetails(int pageNum, int type,int coinTypeId) {
        biz.getFinancialDetails(pageNum, type,coinTypeId, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("getFinancialDetails==>" + GsonUtil.GsonString(result));
                    FinancialDetailsBean financialDetailsBean = GsonUtil.GsonToBean(result, FinancialDetailsBean.class);
                    if (financialDetailsBean.getCode() == ServerInterface.SUCCESS) {
                        meView.getFinancialDetailsSuccess(financialDetailsBean);
                    } else {
                        meView.onViewFailureString(financialDetailsBean.getCode(), financialDetailsBean.getMessage());
                    }
                } else {
                    meView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                meView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 获取邮箱验证码
     */
    public void getEmailCode(String  email) {
        biz.getEmailCode(email, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("getEmailCode==>" + GsonUtil.GsonString(result));
                    BaseBean baseBean = GsonUtil.GsonToBean(result, BaseBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        meView.getEmailCodeSuccess(baseBean);
                    } else {
                        meView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    meView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                meView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 获取邮箱验证码
     */
    public void getTime() {
        biz.getTime( new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("getTime==>" + GsonUtil.GsonString(result));
                    BaseBean baseBean = GsonUtil.GsonToBean(result, BaseBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        meView.getEmailCodeSuccess(baseBean);
                    } else {
                        meView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    meView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                meView.onServerFailure(e, code);
            }
        });
    }

}
