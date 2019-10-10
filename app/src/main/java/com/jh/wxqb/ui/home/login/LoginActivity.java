package com.jh.wxqb.ui.home.login;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.jh.wxqb.utils.Toasts;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.ed_user_name)
    EditText edUserName;
    @BindView(R.id.ed_user_pwd)
    EditText edUserPwd;
    @BindView(R.id.iv_pwd)
    ImageView ivPwd;

    private boolean isShowPwd = false;  //表示是否显示密码
    private LoginPresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        presenter = new LoginPresenter(this);
        if (MyApplication.getToken() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @OnClick({R.id.ll_is_show_pwd, R.id.tv_login, R.id.ll_forget_pwd,R.id.ll_register})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_is_show_pwd:
                if (isShowPwd) {
                    ivPwd.setImageResource(R.drawable.iv_no_pwd);
                    edUserPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    ivPwd.setImageResource(R.drawable.iv_yes_pwd);
                    edUserPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                isShowPwd = !isShowPwd;
                break;
            case R.id.tv_login:
                if (!checkEdit(edUserName) || !checkEdit(edUserPwd)) {
                    return;
                }
                StringUtil.Closekeyboard(this);
                showWaitDialog();
                String username = edUserName.getText().toString();
                String userpwd = edUserPwd.getText().toString();
                LogUtils.e("username==>" + username + "\tuserpwd==>" + userpwd);
                map = new HashMap<>();
                map.put("username", username);
                map.put("password", userpwd);
                presenter.userLogin(map);
                break;
            case R.id.ll_forget_pwd:
                intent = new Intent(this, ForgetPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_register:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void userLoginSuccess(AgreementBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("userLoginSuccess==>" + GsonUtil.GsonString(result));
        LogUtils.e("请求获取Token==>" + GsonUtil.GsonString(result.getData().getToken()));
        MyApplication.setToken(null);
        MyApplication.setToken(result.getData().getToken());
        LogUtils.e("本地Token==>" + GsonUtil.GsonString(MyApplication.getToken()));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 判读输入框格式
     */
    private boolean checkEdit(View v) {
        String str = ((TextView) v).getText().toString();
        switch (v.getId()) {
            case R.id.ed_user_name:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_enter_user_name);
                    return false;
                }
                break;
            case R.id.ed_user_pwd:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_enter_user_pwd);
                    return false;
                }
                break;
        }
        return true;
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
    public void userRegisterSuccess(BaseBean result) {

    }

    @Override
    public void forgetLoginPwdSuccess(BaseBean result) {

    }

    @Override
    public void getTimeSuccess(GetTimeBean result) {

    }
}
