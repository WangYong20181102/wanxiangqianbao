package com.jh.wxqb.ui.home.login.presenter;


import com.jh.wxqb.api.ServerInterface;
import com.jh.wxqb.api.biz.Biz;
import com.jh.wxqb.api.biz.bizImpl.BizImpl;
import com.jh.wxqb.base.OnBaseListener;
import com.jh.wxqb.bean.AgreementBean;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.GetTimeBean;
import com.jh.wxqb.ui.home.login.view.LoginView;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/10 0010.
 */

public class LoginPresenter {

    private Biz biz;
    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        biz = new BizImpl();
    }

    /**
     * 登录
     */
    public void userLogin(Map<String, String> map) {
        biz.userLogin(map, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("userLogin==>" + GsonUtil.GsonString(result));
                    AgreementBean baseBean = GsonUtil.GsonToBean(result, AgreementBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        loginView.userLoginSuccess(baseBean);
                    } else {
                        loginView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    loginView.onServerFailure("服务器繁忙", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                loginView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 注册
     */
    public void userRegister(Map<String, String> map) {
        biz.userRegister(map, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("forgetPayPwd==>" + GsonUtil.GsonString(result));
                    BaseBean baseBean = GsonUtil.GsonToBean(result, BaseBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        loginView.userRegisterSuccess(baseBean);
                    } else {
                        loginView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    loginView.onServerFailure("服务器繁忙", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                loginView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 忘记密码
     */
    public void forgetLoginPwd(String mobile, String newOnePwd, String username, String smsCode) {
        biz.forgetLoginPwd(mobile, newOnePwd, username, smsCode, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("forgetLoginPwd==>" + GsonUtil.GsonString(result));
                    BaseBean baseBean = GsonUtil.GsonToBean(result, BaseBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        loginView.forgetLoginPwdSuccess(baseBean);
                    } else {
                        loginView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    loginView.onServerFailure("服务器繁忙", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                loginView.onServerFailure(e, code);
            }
        });
    }


    /**
     * 获取token失效时间
     */
    public void getTime() {
        biz.getTime(new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("getTime==>" + GsonUtil.GsonString(result));
                    GetTimeBean getTimeBean = GsonUtil.GsonToBean(result, GetTimeBean.class);
                    if (getTimeBean.getCode() == ServerInterface.SUCCESS) {
                        loginView.getTimeSuccess(getTimeBean);
                    } else {
                        loginView.onViewFailureString(getTimeBean.getCode(), getTimeBean.getMessage());
                    }
                } else {
                    loginView.onServerFailure("服务器繁忙", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                loginView.onServerFailure(e, code);
            }
        });
    }


}
