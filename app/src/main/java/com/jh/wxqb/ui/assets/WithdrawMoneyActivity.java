package com.jh.wxqb.ui.assets;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.CoreKeys;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.bean.AssetManagementBean;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.CoinPricesBean;
import com.jh.wxqb.bean.FinancialDetailsBean;
import com.jh.wxqb.bean.SafetyMarkingBean;
import com.jh.wxqb.customview.CancelOrOkDialog;
import com.jh.wxqb.customview.CountDownTimerUtils;
import com.jh.wxqb.customview.CustomTitle;
import com.jh.wxqb.customview.WithdrawMoneyDialog;
import com.jh.wxqb.ui.assets.presenter.AssestPresenter;
import com.jh.wxqb.ui.assets.view.AssetsView;
import com.jh.wxqb.ui.home.ScanningCodeActivity;
import com.jh.wxqb.ui.me.FinancialDetailsActivity;
import com.jh.wxqb.ui.me.UdpPwdActivity;
import com.jh.wxqb.ui.message.MessagePresenter;
import com.jh.wxqb.ui.message.MessageView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.ArithUtils;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.Toasts;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 提币
 */
public class WithdrawMoneyActivity extends BaseActivity implements AssetsView, MessageView {

    @BindView(R.id.ed_target_address)
    EditText edTargetAddress;
    @BindView(R.id.ed_amount_of_money)
    EditText edAmountOfMoney;
    @BindView(R.id.ed_pay_pwd)
    EditText edPayPwd;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.tv_assets)
    TextView tvAssets;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.image_icon)
    ImageView imageIcon;
    @BindView(R.id.tv_currency)
    TextView tvCurrency;
    @BindView(R.id.image_right)
    ImageView imageRight;
    @BindView(R.id.tv_bottom_activation)
    TextView tvBottomActivation;
    @BindView(R.id.tv_bottom_prompt)
    TextView tvBottomPrompt;
    @BindView(R.id.tv_coin_num)
    TextView tvCoinNum;
    @BindView(R.id.tv_erc20)
    TextView tvErc20;

    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private AssestPresenter assestPresenter;
    private MessagePresenter messagePresenter;
    private String ident;
    private AssetManagementBean.DataBean.AccountAssetsBean assetsBean;

    @Override
    protected int getLayout() {
        return R.layout.activity_withdraw_money;
    }

    @Override
    protected void init() {
        initIntent();
        EventBus.getDefault().register(this);
        messagePresenter = new MessagePresenter(this);
        assestPresenter = new AssestPresenter(this);
        setTitleListener(R.id.tv_title);
        initView();
        assestPresenter.safetyMarking();
    }

    private void initIntent() {
        assetsBean = (AssetManagementBean.DataBean.AccountAssetsBean) getIntent().getSerializableExtra(AssetManagementBean.class.getName());
    }

    private void initView() {
        tvAssets.setText(assetsBean.getActiveAssets() + "");
        tvErc20.setVisibility(View.GONE);
        switch (assetsBean.getBizCurrencyTypeId()) {
            case 1:
                tvCurrency.setText("ETH");
                imageIcon.setImageResource(R.drawable.icon_small_eth);
                imageRight.setImageResource(R.drawable.icon_eth_right);
                tvBottomActivation.setText("ETH提币手续费固定为0.001");
                tvCoinNum.setVisibility(View.GONE);
                tvBottomPrompt.setVisibility(View.GONE);
                break;
            case 2:
                tvCurrency.setText("TGM");
                imageIcon.setImageResource(R.drawable.iv_small_tgm);
                imageRight.setImageResource(R.drawable.iv_tgm_right);
                tvBottomActivation.setText("TGM提币手续费：小于1000枚固定为5枚，大于1000枚千分之五");
                tvCoinNum.setVisibility(View.VISIBLE);
                tvErc20.setVisibility(View.VISIBLE);
                tvCoinNum.setText("最小提币数量：30枚");
                tvBottomPrompt.setVisibility(View.GONE);
                break;
            case 3:
                tvCurrency.setText("USDT");
                imageIcon.setImageResource(R.drawable.icon_small_usdt);
                imageRight.setImageResource(R.drawable.icon_usdt_right);
                tvBottomActivation.setText("USDT提币手续费固定为2USDT");
                tvCoinNum.setVisibility(View.VISIBLE);
                tvCoinNum.setText("最小提币数量：5枚");
                tvErc20.setVisibility(View.VISIBLE);
                tvBottomPrompt.setVisibility(View.VISIBLE);
                tvBottomPrompt.setText("因每个交易所的最小充值金额不同，请您提USDT的时候，务必注意查看交易所充值的最小金额是多少，否则在交易所会出现无法上账的情况。");
                break;
            case 4:
                tvCurrency.setText("HT");
                imageIcon.setImageResource(R.drawable.iv_small_ht);
                imageRight.setImageResource(R.drawable.iv_ht_right);
                tvCoinNum.setVisibility(View.GONE);
                tvBottomPrompt.setVisibility(View.GONE);
                break;
            case 5:
                tvCurrency.setText("OKB");
                imageIcon.setImageResource(R.drawable.iv_small_okb);
                imageRight.setImageResource(R.drawable.iv_okb_right);
                tvCoinNum.setVisibility(View.GONE);
                tvBottomPrompt.setVisibility(View.GONE);
                break;
            case 6:
                tvCurrency.setText("BNB");
                imageIcon.setImageResource(R.drawable.iv_small_bnb);
                imageRight.setImageResource(R.drawable.iv_bnb_right);
                tvCoinNum.setVisibility(View.GONE);
                tvBottomPrompt.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    protected void toRight() {
        super.toRight();
        Intent intent = new Intent(this, FinancialDetailsActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.ll_scan, R.id.tv_get_code, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_scan:
                getPermission();
                break;
            case R.id.tv_get_code:
                if (MyApplication.getUserBean() != null) {
                    StringUtil.Closekeyboard(this);
                    showWaitDialog();
                    messagePresenter.getMessageCode(MyApplication.getUserBean().getPhone());
                }
                break;
            case R.id.tv_confirm:
                if (MyApplication.getUserBean() != null) {
                    if (MyApplication.getUserBean().getIsHasTradePwd()) {
                        if (!checkEdit(edTargetAddress) || !checkEdit(edAmountOfMoney) || !checkEdit(edPayPwd) || !checkEdit(edCode)) {
                            return;
                        }
                        String turnNum = edAmountOfMoney.getText().toString().trim();
                        switch (assetsBean.getBizCurrencyTypeId()) {
                            case 2:
                                if (Double.parseDouble(turnNum) < 30) {
                                    Toasts.showShort("最小提币数量30枚");
                                    return;
                                }
                                break;
                            case 3:
                                if (Double.parseDouble(turnNum) < 5) {
                                    Toasts.showShort("最小提币数量5枚");
                                    return;
                                }
                                break;
                        }

                        //判断可用余额是否为0、转出数量是否大于可用余额
                        if (new BigDecimal(turnNum).compareTo(new BigDecimal(assetsBean.getActiveAssets())) > 0 || assetsBean.getActiveAssets() == 0) {
                            Toasts.showShort("余额不足");
                            return;
                        }
                        StringUtil.Closekeyboard(this);
                        showWaitDialog();
                        map = new HashMap<>();
                        map.put("site", edTargetAddress.getText().toString());
                        map.put("number", turnNum);
                        map.put("pwd", edPayPwd.getText().toString());
                        map.put("smsCode", edCode.getText().toString());
                        map.put("type", "2");
                        map.put("coinTypeId", String.valueOf(assetsBean.getBizCurrencyTypeId()));
                        map.put("ident", ident);
                        if (MyApplication.getUserBean() != null) {
                            map.put("mobile", MyApplication.getUserBean().getPhone());
                        }
                        LogUtils.e("ident==>" + ident);
                        assestPresenter.withdrawMoney(map);
                    } else {
                        CancelOrOkDialog cancelOrOkDialog = new CancelOrOkDialog(this, R.string.please_set_the_transaction_password_first) {
                            @Override
                            public void ok() {
                                super.ok();
                                Intent intent = new Intent(WithdrawMoneyActivity.this, UdpPwdActivity.class);
                                intent.putExtra("type", "pay");
                                startActivity(intent);
                                dismiss();
                            }
                        };
                        cancelOrOkDialog.show();
                    }
                }
                break;
        }
    }

    //获取权限
    private void getPermission() {
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //已经打开权限
            Intent intent = new Intent(this, ScanningCodeActivity.class);
            startActivityForResult(intent, CoreKeys.RESULT_CODE);
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用权限", 1, permissions);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            Intent intent = new Intent(this, ScanningCodeActivity.class);
            startActivityForResult(intent, CoreKeys.RESULT_CODE);
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
    public void withdrawMoneySuccess(BaseBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("withdrawMoneySuccess===>" + GsonUtil.GsonString(result));
        tvAssets.setText(ArithUtils.sub(assetsBean.getActiveAssets(), Double.parseDouble(edAmountOfMoney.getText().toString().trim())) + "");
        edAmountOfMoney.setText(null);
        edPayPwd.setText(null);
        edCode.setText(null);
        EventBus.getDefault().post("udpHome");
        EventBus.getDefault().post("udpAssestData");
        WithdrawMoneyDialog dialog = new WithdrawMoneyDialog(this, true);
        dialog.setTitle(R.string.currency_success);
        dialog.setTips(R.string.open_des_sel_withdraw_money);
        dialog.show();
    }

    @Override
    public void safetyMarkingSuccess(SafetyMarkingBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("safetyMarkingSuccess===>" + GsonUtil.GsonString(result));
        ident = result.getData().getIdent();
    }

    @Override
    public void coinPricesSuccess(CoinPricesBean result) {

    }

    @Override
    public void coinRechangeSuccess(BaseBean result) {

    }

    @Subscribe
    public void udpWithdrawMoney(String udpWithdrawMoney) {
        if (!isFinishing()) {
            if (udpWithdrawMoney.equals("udpWithdrawMoney")) {
                initView();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CoreKeys.RESULT_CODE && resultCode == CoreKeys.RESULT_CODE) {
            String address = data.getStringExtra("data");
            if (address.contains("zhuanzhang")) {
                String[] zhuanzhangs = address.split("zhuanzhang");
                if (zhuanzhangs.length == 2) {
                    if (StringUtil.isCheckWalletAddress(zhuanzhangs[0])) {
                        edTargetAddress.setText(zhuanzhangs[0]);
                    } else {
                        Toasts.showShort(R.string.please_scan_the_correct_code);
                    }
                    if (StringUtil.isCheckNumber(zhuanzhangs[1])) {
                        edAmountOfMoney.setText(zhuanzhangs[1]);
                    } else {
                        Toasts.showShort(R.string.please_scan_the_correct_code);
                    }
                }
            } else if (StringUtil.isCheckWalletAddress(address)) {
                edTargetAddress.setText(address);
            } else {
                Toasts.showShort(R.string.please_scan_the_correct_code);
            }
        }
    }


    /**
     * 判读输入框格式
     */
    private boolean checkEdit(View v) {
        String str = ((TextView) v).getText().toString();
        switch (v.getId()) {
            case R.id.ed_target_address:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_input_target_address);
                    return false;
                }
                break;
            case R.id.ed_amount_of_money:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_input_amount_of_money);
                    return false;
                }
                break;
            case R.id.ed_pay_pwd:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_input_pay_pwd);
                    return false;
                }
                break;
            case R.id.ed_code:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_input_code);
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
        AgainLoginUtil.againLogin(this, statue);
        WithdrawMoneyDialog dialog = new WithdrawMoneyDialog(this, false);
        dialog.setTitle(R.string.currency_error);
        dialog.setTips(message);
        dialog.show();
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getFinancialDetailsSuccess(FinancialDetailsBean result) {

    }

    @Override
    public void getAssetManagementSuccess(AssetManagementBean result) {

    }
}
