package com.jh.wxqb.ui.me.mysendletter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.OrderProfitAdapter;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.MeDividend;
import com.jh.wxqb.customview.CancelOrOkDialog;
import com.jh.wxqb.ui.me.mysendletter.presenter.MySendLetterPresenter;
import com.jh.wxqb.ui.me.mysendletter.view.MySendLetterView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.TimeUtil;
import com.jh.wxqb.utils.Toasts;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单详情
 */
public class OrderInfoActivity extends BaseActivity implements MySendLetterView {


    @BindView(R.id.shop_recy)
    SwipeMenuRecyclerView shop_recy;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    int pageIndex = 1;
    boolean isClear = true;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_delegate_quantity)
    TextView tvDelegateQuantity;
    @BindView(R.id.tv_volume)
    TextView tvVolume;
    @BindView(R.id.tv_total_turnover)
    TextView tvTotalTurnover;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.tv_dividend_income)
    TextView tvDividendIncome;
    @BindView(R.id.tv_stop)
    TextView tvStop;
    @BindView(R.id.tv_entrust)
    TextView tvEntrust;

    private OrderProfitAdapter adapter;
    private MeDividend.DataBean.ListBean data;
    private int dividendState;  //1：停止買入   2：停止排队
    private MySendLetterPresenter presenter;
    private CancelOrOkDialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_order_info;
    }

    @Override
    protected void init() {
        presenter = new MySendLetterPresenter(this);
        initView();
        initRecyclerView();
    }

    //1買入中2.停止買入3.停止排队4.挂单中5.已成交6.已撤销7.排队中
    private void initView() {

        data = (MeDividend.DataBean.ListBean) getIntent().getSerializableExtra("data");
        tvDelegateQuantity.setText(StringUtil.subZeroAndDot(data.getAccountCommission().toPlainString()));
        tvVolume.setText(StringUtil.subZeroAndDot(data.getAcountTransaction().toPlainString()));
        tvTotalTurnover.setText(StringUtil.subZeroAndDot(data.getAmountTotal().toPlainString()));
        tvTime.setText(TimeUtil.getTime(data.getCreateDate()));
        tvRate.setText(data.getInterestRatio());
        tvDividendIncome.setText(StringUtil.subZeroAndDot(data.getDailyInterest().toPlainString()));

        switch (data.getAssetTypeId()) {
            case 1:
                tvEntrust.setText("TGM");
                break;
            case 2:
                tvEntrust.setText("USDT");
                break;
            case 3:
                tvEntrust.setText("USDT");
                break;
        }

        switch (data.getStatus()) {
            case 1:
                dividendState = 1;
                tvState.setText(R.string.the_dividend);
                tvStop.setVisibility(View.VISIBLE);
                tvStop.setText(R.string.stop_the_dividend);
                break;
            case 2:
                tvState.setText(R.string.stop_the_dividend);
                tvStop.setVisibility(View.GONE);
                break;
            case 3:
                tvState.setText(R.string.stop_queuing);
                tvStop.setVisibility(View.GONE);
                break;
            case 7:
                dividendState = 2;
                tvState.setText(R.string.queuing);
                tvStop.setVisibility(View.GONE);
//                tvStop.setText(R.string.stop_queuing);
                break;
        }
    }


    @OnClick(R.id.tv_stop)
    public void onViewClicked() {
        map = new HashMap<>();
        map.put("commissonId", String.valueOf(data.getId()));
        switch (dividendState) {
            case 1:
                dialog = new CancelOrOkDialog(this, R.string.confirmed_that_dividends) {
                    @Override
                    public void ok() {
                        super.ok();
                        showWaitDialog();
                        presenter.stopDividend(map);
                        dismiss();
                    }
                };
                dialog.show();
                break;
            case 2:
//                dialog = new CancelOrOkDialog(this, R.string.confirmed_that_stop_queuing) {
//                    @Override
//                    public void ok() {
//                        super.ok();
//                        showWaitDialog();
//                        presenter.stopQueuing(map);
//                        dismiss();
//                    }
//                };
//                dialog.show();
                break;
        }
    }

    /**
     * 初始化RecyclerView配置
     */
    public void initRecyclerView() {
        //是否开启加载更多
        shop_recy.loadMoreFinish(false, false);
        sw_refresh.setEnabled(false);
        //设置布局管理器
        shop_recy.setLayoutManager(new LinearLayoutManager(this));
        //初始化适配器
        adapter = new OrderProfitAdapter(this, data.getMemberInterests());
        shop_recy.setAdapter(adapter);  //设置适配器
    }

    @Override
    public void stopDividendSuccess(BaseBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("stopDividendSuccess==>" + GsonUtil.GsonString(result));
        Toasts.showShort(result.getMessage());
        EventBus.getDefault().post("udpDividendFragment");
        finish();
    }

    @Override
    public void stopQueuingSuccess(BaseBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("stopDividendSuccess==>" + GsonUtil.GsonString(result));
        Toasts.showShort(result.getMessage());
        EventBus.getDefault().post("udpDividendFragment");
        finish();
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

}
