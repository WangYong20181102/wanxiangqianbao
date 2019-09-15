package com.jh.wxqb.ui.home.login.view;

import com.jh.wxqb.base.BaseView;
import com.jh.wxqb.bean.AgreementBean;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.GetTimeBean;

/**
 * Created by Administrator on 2018/5/10 0010.
 */

public interface LoginView extends BaseView {

    /**
     * 登录
     *
     * @param result
     */
    void userLoginSuccess(AgreementBean result);


    /**
     * 注册成功
     *
     * @param result
     */
    void userRegisterSuccess(BaseBean result);


    /**
     * 忘记密码
     *
     * @param result
     */
    void forgetLoginPwdSuccess(BaseBean result);


    /**
     * 获取token失效时间
     *
     * @param result
     */
    void getTimeSuccess(GetTimeBean result);


}
