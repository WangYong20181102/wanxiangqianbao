package com.jh.wxqb.ui.home.login;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.bean.AgreementBean;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.GetTimeBean;
import com.jh.wxqb.customview.CountDownTimerUtils;
import com.jh.wxqb.ui.home.login.presenter.LoginPresenter;
import com.jh.wxqb.ui.home.login.view.LoginView;
import com.jh.wxqb.ui.message.MessagePresenter;
import com.jh.wxqb.ui.message.MessageView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.Toasts;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记密码
 */
public class ForgetPwdActivity extends BaseActivity implements MessageView, LoginView {

    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_pwd)
    EditText edPwd;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.iv_pwd)
    ImageView ivPwd;
    @BindView(R.id.tv_code)
    TextView tvCode;

    private boolean isShowPwd = false;  //表示是否显示密码
    private MessagePresenter messagePresenter;
    private LoginPresenter loginPresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void init() {
        messagePresenter = new MessagePresenter(this);
        loginPresenter = new LoginPresenter(this);
    }

    @OnClick({R.id.tv_code, R.id.ll_is_show_pwd, R.id.tv_confirm, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                if (!checkEdit(edPhone)) {
                    return;
                }
                StringUtil.Closekeyboard(this);
                showWaitDialog();
                messagePresenter.getMessageCode(edPhone.getText().toString());
                break;
            case R.id.ll_is_show_pwd:
                if (isShowPwd) {
                    ivPwd.setImageResource(R.drawable.iv_no_pwd);
                    edPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    ivPwd.setImageResource(R.drawable.iv_yes_pwd);
                    edPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                isShowPwd = !isShowPwd;
                break;
            case R.id.tv_confirm:
                if (!checkEdit(edPhone) || !checkEdit(edPwd) || !checkEdit(edCode)) {
                    return;
                }
                StringUtil.Closekeyboard(this);
                showWaitDialog();
//                String userName = edAccount.getText().toString();
                String phone = edPhone.getText().toString();
                String code = edCode.getText().toString();
                String pwd = edPwd.getText().toString();
                loginPresenter.forgetLoginPwd(phone, pwd, code);
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }


    @Override
    public void forgetLoginPwdSuccess(BaseBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("forgetLoginPwdSuccess==>" + GsonUtil.GsonString(result));
        Toasts.showShort(result.getMessage());
        finish();
    }

    @Override
    public void getTimeSuccess(GetTimeBean result) {

    }

    @Override
    public void getMessageCodeSuccess(BaseBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        Toasts.showLong(result.getMessage());
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tvCode, 60000, 1000);
        mCountDownTimerUtils.start();
    }

    /**
     * 判读输入框格式
     */
    private boolean checkEdit(View v) {
        String str = ((TextView) v).getText().toString();
        switch (v.getId()) {
            case R.id.ed_phone:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_enter_mobile_number);
                    return false;
                }
                if (str.length() != 11) {
                    Toasts.showShort(R.string.incorrect_format_of_mobile_number);
                    return false;
                }
                break;
            case R.id.ed_pwd:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_input_user_pwd);
                    return false;
                }
                break;
            case R.id.ed_code:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_enter_verification_code);
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
    public void userLoginSuccess(AgreementBean result) {

    }

    @Override
    public void userRegisterSuccess(BaseBean result) {

    }

}
