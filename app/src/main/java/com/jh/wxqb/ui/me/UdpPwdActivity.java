package com.jh.wxqb.ui.me;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.jh.wxqb.ui.message.MessagePresenter;
import com.jh.wxqb.ui.message.MessageView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.SharedPreferencesUtil;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.Toasts;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改登录密码/支付密码/设置支付密码
 */
public class UdpPwdActivity extends BaseActivity implements MessageView, MeView {


    @BindView(R.id.tv_title)
    CustomTitle tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ed_pwd)
    EditText edPwd;
    @BindView(R.id.iv_new_pwd)
    ImageView ivNewPwd;
    @BindView(R.id.ed_confirm_pwd)
    EditText edConfirmPwd;
    @BindView(R.id.iv_confirm_new_pwd)
    ImageView ivConfirmNewPwd;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    private boolean isShowNewPwd = false;  //表示是否显示新密码
    private boolean isShowConfirmNewPwd = false;  //表示是否显示确认密码
    private int actionType; // 1为修改登录密码,2为修改交易密码
    private int toastsType; // 1为修改登录密码,2为修改交易密码,3设置交易密码
    private MessagePresenter messagePresenter;
    private MePresenter mePresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_udp_pwd;
    }

    @Override
    protected void init() {
        messagePresenter = new MessagePresenter(this);
        mePresenter = new MePresenter(this);
        initView();
    }

    public void initView() {
        String type = getIntent().getStringExtra("type");
        switch (type) {
            case "login":
                tvTitle.setTitleText(R.string.udp_login_pwd);
                actionType = 1;
                toastsType = 1;
                break;
            case "pay":
                edPwd.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                edConfirmPwd.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                actionType = 2;
                if (MyApplication.getUserBean() != null) {
                    if (MyApplication.getUserBean().getIsHasTradePwd()) {
                        tvTitle.setTitleText(R.string.udp_pay_pwd);
                        toastsType = 2;
                    } else {
                        tvTitle.setTitleText(R.string.set_transaction_password);
                        toastsType = 3;
                    }
                }
                break;
        }
        if (MyApplication.getUserBean() != null) {
            tvName.setText(MyApplication.getUserBean().getUserName());
            tvPhone.setText(MyApplication.getUserBean().getPhone());
        }
    }

    @OnClick({R.id.ll_new_pwd, R.id.ll_confirm_new_pwd, R.id.tv_get_code, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_new_pwd:
                if (isShowNewPwd) {
                    ivNewPwd.setImageResource(R.drawable.iv_no_pwd);
                    edPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    ivNewPwd.setImageResource(R.drawable.iv_yes_pwd);
                    edPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                isShowNewPwd = !isShowNewPwd;
                break;
            case R.id.ll_confirm_new_pwd:
                if (isShowConfirmNewPwd) {
                    ivConfirmNewPwd.setImageResource(R.drawable.iv_no_pwd);
                    edConfirmPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    ivConfirmNewPwd.setImageResource(R.drawable.iv_yes_pwd);
                    edConfirmPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                isShowConfirmNewPwd = !isShowConfirmNewPwd;
                break;
            case R.id.tv_get_code:
                if (MyApplication.getUserBean() != null) {
                    StringUtil.Closekeyboard(this);
                    showWaitDialog();
                    messagePresenter.getMessageCode(MyApplication.getUserBean().getPhone());
                }
                break;
            case R.id.tv_confirm:
                //校验验证码    || !checkEdit(edCode)
                if(!checkEdit(edPwd) || !checkEdit(edConfirmPwd) || !checkEdit(edCode)){
                    return;
                }
                StringUtil.Closekeyboard(this);
                showWaitDialog();
                if(MyApplication.getUserBean()!=null){
                mePresenter.udpLoginPwd(actionType,edPwd.getText().toString(),
                        edConfirmPwd.getText().toString(),edCode.getText().toString(),MyApplication.getUserBean().getPhone());
                }
                break;
        }
    }


    @Override
    public void getMessageCodeSuccess(BaseBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        Toasts.showLong(result.getMessage());
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tvGetCode, 60000, 1000);
        mCountDownTimerUtils.start();
    }


    @Override
    public void udpLoginPwdSuccess(BaseBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("udpLoginPwdSuccess==>"+ GsonUtil.GsonString(result));
        switch (toastsType) {
            case 1:
                Toasts.showLong(R.string.modify_login_password_successfully);
                AgainLoginUtil.againLogin(this,9009);
                break;
            case 2:
                Toasts.showLong(R.string.modify_transaction_password_successfully);
                SharedPreferencesUtil.setPrefInt(this,"FirstVerification",0);
                break;
            case 3:
                Toasts.showLong(R.string.set_transaction_password_successfully);
                SharedPreferencesUtil.setPrefInt(this,"FirstVerification",0);
                break;
        }
        EventBus.getDefault().post("udpHome");
        finish();
    }

    @Override
    public void bandingEmailSuccess(BaseBean result) {

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
    public void getEmailCodeSuccess(BaseBean result) {

    }


    /**
     * 判读输入框格式
     */
    private boolean checkEdit(View v) {
        String str = ((TextView) v).getText().toString();
        switch (v.getId()) {
            case R.id.ed_pwd:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_input_user_pwd);
                    return false;
                }
                break;
            case R.id.ed_confirm_pwd:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_input_the_password_again);
                    return false;
                }
                if (!edPwd.getText().toString().equals(edConfirmPwd.getText().toString())) {
                    Toasts.showShort(R.string.the_two_input_password_is_inconsistent);
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

}
