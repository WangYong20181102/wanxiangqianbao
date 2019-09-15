package com.jh.wxqb.ui.me;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.FinancialDetailsBean;
import com.jh.wxqb.bean.FinancialDetailsTypeBean;
import com.jh.wxqb.bean.MyTeamBean;
import com.jh.wxqb.bean.UserImage;
import com.jh.wxqb.customview.CountDownTimerUtils;
import com.jh.wxqb.customview.CustomTitle;
import com.jh.wxqb.ui.me.presenter.MePresenter;
import com.jh.wxqb.ui.me.view.MeView;
import com.jh.wxqb.ui.message.MessageView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.Toasts;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 绑定账户
 */
public class BindingAccountActivity extends BaseActivity implements MeView, MessageView {


    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.tv_title)
    CustomTitle tvTitle;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    private MePresenter mePresenter;
    private String type;

    @Override
    protected int getLayout() {
        return R.layout.activity_binding_account;
    }

    @Override
    protected void init() {
        mePresenter = new MePresenter(this);
        initView();
    }

    private void initView() {
        if (MyApplication.getUserBean() != null) {
            if (MyApplication.getUserBean().getEmail() != null && !MyApplication.getUserBean().getEmail().equals("")) {
                tvTitle.setTitleText(R.string.modify_binding_account);
                type = "2";
            } else {
                tvTitle.setTitleText(R.string.binding_account);
                type = "1";
            }
        }
    }

    @OnClick({R.id.tv_get_code, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                if (!checkEdit(edEmail)) {
                    return;
                }
                StringUtil.Closekeyboard(this);
                showWaitDialog();
                mePresenter.getEmailCode(edEmail.getText().toString());
                break;
            case R.id.tv_confirm:
                if (!checkEdit(edEmail) || !checkEdit(edCode)) {
                    return;
                }
                StringUtil.Closekeyboard(this);
                showWaitDialog();
                map = new HashMap<>();
                map.put("type", type);
                map.put("email", edEmail.getText().toString());
                map.put("smsCode", edCode.getText().toString());
                mePresenter.bandingEmail(map);
                break;
        }
    }

    /**
     * 判读输入框格式
     */
    private boolean checkEdit(View v) {
        String str = ((TextView) v).getText().toString();
        switch (v.getId()) {
            case R.id.ed_email:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_input_the_email);
                    return false;
                }
                if (!StringUtil.isEmail(str)) {
                    Toasts.showShort(R.string.error_in_email_format);
                    return false;
                }
                break;
            case R.id.ed_code:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.enter_code);
                    return false;
                }
                break;
        }
        return true;
    }

    @Override
    public void getEmailCodeSuccess(BaseBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        Toasts.showLong(result.getMessage());
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tvGetCode, 60000, 1000);
        mCountDownTimerUtils.start();
    }

    @Override
    public void bandingEmailSuccess(BaseBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("bandingEmailSuccess==>" + GsonUtil.GsonString(result));
        Toasts.showShort(result.getMessage());
        EventBus.getDefault().post("udpHome");
        finish();
    }


    @Override
    public void getMessageCodeSuccess(BaseBean result) {

    }


    @Override
    public void myTeamSuccess(MyTeamBean result) {

    }

    @Override
    public void selUserImageSuccess(UserImage result) {

    }

    @Override
    public void uploadUserImageSuccess(BaseBean result) {

    }

    @Override
    public void financialDetailsTypeSuccess(FinancialDetailsTypeBean result) {

    }

    @Override
    public void getFinancialDetailsSuccess(FinancialDetailsBean result) {

    }

    @Override
    public void onViewFailureString(int statue, String message) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        AgainLoginUtil.againLogin(this, statue);
        Toasts.showShort(message);
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
    public void udpLoginPwdSuccess(BaseBean result) {

    }

}
