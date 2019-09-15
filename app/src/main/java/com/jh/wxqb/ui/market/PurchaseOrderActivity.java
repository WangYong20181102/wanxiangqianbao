package com.jh.wxqb.ui.market;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.CoreKeys;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.CurrentPriceBean;
import com.jh.wxqb.bean.MarketDividendBottomBean;
import com.jh.wxqb.bean.MarketDividendTitleBean;
import com.jh.wxqb.bean.MeDividend;
import com.jh.wxqb.customview.CancelOrOkDialog;
import com.jh.wxqb.ui.market.presenter.MarketPresenter;
import com.jh.wxqb.ui.market.view.MarketView;
import com.jh.wxqb.ui.me.UdpPwdActivity;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.TimeUtil;
import com.jh.wxqb.utils.Toasts;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 购买订单
 */
public class PurchaseOrderActivity extends BaseActivity implements MarketView {


    @BindView(R.id.tv_delegate_quantity)
    TextView tvDelegateQuantity;
    @BindView(R.id.tv_volume)
    TextView tvVolume;
    @BindView(R.id.tv_total_turnover)
    TextView tvTotalTurnover;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_current_quantity)
    TextView tvCurrentQuantity;
    @BindView(R.id.ed_pwd)
    EditText edPwd;
    private MarketDividendBottomBean.DataBean.ListBean bean;
    private MarketPresenter marketPresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_purchase_order;
    }

    @Override
    protected void init() {
        marketPresenter = new MarketPresenter(this);
        bean = (MarketDividendBottomBean.DataBean.ListBean) getIntent().getSerializableExtra("data");
        LogUtils.e("数据==>" + GsonUtil.GsonString(bean));
        tvDelegateQuantity.setText(StringUtil.subZeroAndDot(bean.getAccountCommission().toPlainString()));
        tvVolume.setText(StringUtil.subZeroAndDot(bean.getAcountTransaction().toPlainString()));
        tvTotalTurnover.setText(StringUtil.subZeroAndDot(bean.getAmountTotal().toPlainString()));
        tvTime.setText(TimeUtil.getTime(bean.getCreateDate()));
        if (MyApplication.getUserBean() != null) {
            tvCurrentQuantity.setText(StringUtil.subZeroAndDot(MyApplication.getUserBean().getActiveAssets().toPlainString()));
        }
    }

    @OnClick(R.id.tv_purchase_immediately)
    public void onViewClicked() {
        if (MyApplication.getUserBean() != null) {
            if (MyApplication.getUserBean().getIsHasTradePwd()) {

                if (!checkEdit(edPwd)) {
                    return;
                }
                StringUtil.Closekeyboard(this);
                showWaitDialog();
                map = new HashMap<>();
                map.put("commissionId", String.valueOf(bean.getId()));
                map.put("tradePassword", edPwd.getText().toString());
                marketPresenter.purchaseOrder(map);
            }else {
                CancelOrOkDialog cancelOrOkDialog = new CancelOrOkDialog(this, R.string.please_set_the_transaction_password_first) {
                    @Override
                    public void ok() {
                        super.ok();
                        Intent intent = new Intent(PurchaseOrderActivity.this, UdpPwdActivity.class);
                        intent.putExtra("type", "pay");
                        startActivity(intent);
                        dismiss();
                    }
                };
                cancelOrOkDialog.show();
            }
        }
    }


    @Override
    public void purchaseOrderSuccess(BaseBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("purchaseOrderSuccess===>" + GsonUtil.GsonString(result));
        Toasts.showShort(result.getMessage());
        EventBus.getDefault().post("udpHome");
        EventBus.getDefault().post("udpCurrentBusinessList");
        setResult(CoreKeys.RESULT_CODE);
        finish();
    }

    @Override
    public void getCurrentPriceSuccess(CurrentPriceBean result) {

    }

    @Override
    public void sellOutSuccess(BaseBean result) {

    }

    @Override
    public void marketSellSuccess(BaseBean result) {

    }


    /**
     * 判读输入框格式
     */
    private boolean checkEdit(View v) {
        String str = ((TextView) v).getText().toString();
        switch (v.getId()) {
            case R.id.ed_pwd:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_enter_the_tran_password);
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
    public void myDividendSuccess(MeDividend result) {

    }

    @Override
    public void dividendMarketDividendSuccess(MarketDividendTitleBean result) {

    }

    @Override
    public void dividendMarketTopDividendSuccess(MarketDividendBottomBean result) {

    }

}
