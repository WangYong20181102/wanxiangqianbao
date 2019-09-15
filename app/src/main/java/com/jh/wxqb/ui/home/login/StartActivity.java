package com.jh.wxqb.ui.home.login;

import android.content.Intent;

import com.jh.wxqb.R;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.MainActivity;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.bean.AgreementBean;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.GetTimeBean;
import com.jh.wxqb.ui.home.login.presenter.LoginPresenter;
import com.jh.wxqb.ui.home.login.view.LoginView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.TimeUtil;
import com.jh.wxqb.utils.Toasts;

import butterknife.OnClick;

/**
 * 启动页
 */
public class StartActivity extends BaseActivity implements LoginView {


    private LoginPresenter loginPresenter;
    private boolean isClick = true;  //标识当前时间用户是否可登陆

    @Override
    protected int getLayout() {
        return R.layout.activity_start;
    }

    @Override
    protected void init() {
//        showWaitDialog();
        loginPresenter = new LoginPresenter(this);
//        loginPresenter.getTime();
    }

    @OnClick(R.id.tv_login)
    public void onViewClicked() {
        if (isClick) {
            if (MyApplication.getToken() != null) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
            finish();
        } else {
//            Toasts.showShort();
        }
    }


    @Override
    public void getTimeSuccess(GetTimeBean result) {
        if (isFinishing()) {
            return;
        }
        LogUtils.e("getTimeSuccess==>" + GsonUtil.GsonString(result));
        String startTimes = TimeUtil.getDivisionSecond(result.getData().getTokenEffectiveTime());
        String endTimes = TimeUtil.getDivisionSecond(result.getData().getTokenInvalidTime());
        String currentTime = TimeUtil.getDivisionSecond(System.currentTimeMillis());
        LogUtils.e("开始时间==>"+startTimes);
        LogUtils.e("结束时间==>"+endTimes);
        LogUtils.e("当前时间==>"+currentTime);
        if(StringUtil.isBelong(currentTime,startTimes,endTimes)){
            isClick=false;
        }
    }

    @Override
    public void onViewFailureString(int statue, String message) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        Toasts.showShort(message);
        AgainLoginUtil.againLogin(this, statue);
    }

    @Override
    public void onServerFailure(String e, int code) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        Toasts.showShort(e);
    }

    @Override
    public void userLoginSuccess(AgreementBean result) {

    }

    @Override
    public void userRegisterSuccess(BaseBean result) {

    }

    @Override
    public void forgetLoginPwdSuccess(BaseBean result) {

    }

}
