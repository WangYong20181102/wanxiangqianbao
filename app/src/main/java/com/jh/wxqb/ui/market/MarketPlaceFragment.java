package com.jh.wxqb.ui.market;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.MarketPlaceAdapter;
import com.jh.wxqb.base.BaseFragment;
import com.jh.wxqb.base.MainActivity;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.CurrentPriceBean;
import com.jh.wxqb.bean.MarketDividendBottomBean;
import com.jh.wxqb.bean.MarketDividendTitleBean;
import com.jh.wxqb.bean.MeDividend;
import com.jh.wxqb.customview.WithdrawMoneyDialog;
import com.jh.wxqb.ui.market.presenter.MarketPresenter;
import com.jh.wxqb.ui.market.view.MarketView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.SharedPreferencesUtil;
import com.jh.wxqb.utils.TimeUtil;
import com.jh.wxqb.utils.Toasts;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 交易市場
 */
public class MarketPlaceFragment extends BaseFragment implements MarketView {

    @BindView(R.id.tv_dividend)
    TextView tvDividend;
    @BindView(R.id.tv_sell)
    TextView tvSell;
    Unbinder unbinder;
    @BindView(R.id.ll_active_assets)
    RelativeLayout llActiveAssets;
    @BindView(R.id.ll_repurchase_assets)
    RelativeLayout llRepurchaseAssets;
    private View view;
    @BindViews({R.id.tv_dividend, R.id.tv_sell})
    List<TextView> allTitle;
    @BindView(R.id.shop_recy)
    SwipeMenuRecyclerView shop_recy;
    @BindView(R.id.tv_percentage_point)
    TextView tvPercentagePoint;
    private Context mContext;
    private MarketPlaceAdapter adapter;
    private CurrentPriceBean currentPriceBean;
    private MarketDividendTitleBean.DataBean.ListBean buyListBeen;
    private List<MarketDividendBottomBean.DataBean.ListBean> listBeen = new ArrayList<>();
    private int type = 0; //全部
    private String viewType = "dividend";   //dividend：買入  sell：卖出
    private Timer timer;
    private int count = 0;
    private MarketPresenter marketPresenter;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                timingHttpRequest();
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext();
        EventBus.getDefault().register(this);
        marketPresenter = new MarketPresenter(this);
        initRecyclerView();
        upTopBottomData(viewType);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_market_place, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 定时网络请求
     */
    private void timingHttpRequest() {
        if (adapter != null) {
            upTopBottomData(viewType);
            count++;
        }
    }

    /**
     * 更新盘口最近成交数据
     */
    private void upTopBottomData(String currentType) {
        map = new HashMap<>();
        switch (currentType) {
            case "dividend":
                map.put("coinType", "1");
                map.put("transactionType", "1");
                break;
            case "sell":
                map.put("transactionType", "2");
                break;
        }
        marketPresenter.dividendMarketTopDividend(1, 1, type);//底部最近成交数据
        marketPresenter.dividendMarketDividend(3);//盘口数据
        marketPresenter.getCurrentPrice(map);
    }

    @OnClick({ R.id.ll_active_assets, R.id.ll_repurchase_assets})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_active_assets:
                count++;
                selectTitle(0);
                break;
            case R.id.ll_repurchase_assets:
                count++;
                selectTitle(1);
                break;
        }
    }

    //选择标题
    public void selectTitle(int index) {
        if (timer == null) {
            startTimer();
        }
        clearViewAndTitle();
        switch (index) {
            case 0:
                viewType = "dividend";
                allTitle.get(index).setTextColor(Color.WHITE);
                llActiveAssets.setBackgroundResource(R.drawable.market_place_buy_bg);
                llRepurchaseAssets.setBackgroundResource(R.drawable.market_place_sell_buy_unselect);
                break;
            case 1:
                viewType = "sell";
                allTitle.get(index).setTextColor(Color.WHITE);
                llActiveAssets.setBackgroundResource(R.drawable.market_place_sell_buy_unselect);
                llRepurchaseAssets.setBackgroundResource(R.drawable.market_place_sell_bg);
                break;
        }
        if (adapter != null) {
            adapter.setCurrentPrice(currentPriceBean, viewType,1);
            adapter.notifyItemRangeChanged(0, 1);
        }
    }

    //初始化标题
    public void clearViewAndTitle() {
        for (TextView text : allTitle) {
            text.setTextColor(getResources().getColor(R.color.color_646482));
        }
    }


    @Subscribe
    public void selAssets(String selAssets) {
        switch (selAssets) {
            case "selAssets":
                upTopBottomData(viewType);
                break;
            case "udpCurrentBusinessList":
                marketPresenter.dividendMarketTopDividend(1, 1, type);
                break;
            case "udpPurchaseOrder":
                map = new HashMap<>();
                switch (viewType) {
                    case "dividend":
                        map.put("coinType", "1");
                        map.put("transactionType", "1");
                        break;
                    case "sell":
                        map.put("transactionType", "2");
                        break;
                }
                marketPresenter.getCurrentPrice(map);
                break;
            case "pauseTimer":
                onPause();
                break;
            case "resumeTimer":
                count++;
                onResume();
                break;
            case "backSell":
                count++;
                selectTitle(1);
                break;
            case "backBuy":
                count++;
                selectTitle(0);
                break;
        }
    }

    @Subscribe
    public void udpBusiness(Intent intent) {
        String type = intent.getStringExtra("type");
        LogUtils.e("type==>" + type);
        switch (type) {
            case "udpBusiness":
                marketPresenter.dividendMarketTopDividend(1, 1, this.type);
                break;
            case "udpCurrentPrice":
                map = new HashMap<>();
                map.put("coinType", "1");
                map.put("transactionType", "1");
                marketPresenter.getCurrentPrice(map);
                break;
            case "openDividends":
                LogUtils.e("当前价格==>" + intent.getStringExtra("currentPrice"));
                ((MainActivity) mContext).showWaitDialog();
                map = new HashMap<>();
                map.put("number", intent.getStringExtra("number"));
                map.put("tradePassword", intent.getStringExtra("tradePassword"));
                map.put("currentPrice", intent.getStringExtra("currentPrice"));
                map.put("pendingCurrencyId", intent.getStringExtra("pendingCurrencyId"));
                map.put("exchangeCurrencyId", intent.getStringExtra("exchangeCurrencyId"));
                map.put("acountTransaction", intent.getStringExtra("acountTransaction"));
                marketPresenter.sellOut(map);
                break;
            case "marketSell":
                ((MainActivity) mContext).showWaitDialog();
                map = new HashMap<>();
                map.put("number", intent.getStringExtra("number"));
                map.put("tradePassword", intent.getStringExtra("tradePassword"));
                map.put("currentPrice", intent.getStringExtra("currentPrice"));
                map.put("pendingCurrencyId", intent.getStringExtra("pendingCurrencyId"));
                map.put("exchangeCurrencyId", intent.getStringExtra("exchangeCurrencyId"));
                map.put("acountTransaction", intent.getStringExtra("acountTransaction"));
                marketPresenter.marketSell(map);
                break;
        }
    }

    /**
     * 初始化RecyclerView配置
     */
    public void initRecyclerView() {
        //是否开启加载更多
        shop_recy.loadMoreFinish(false, false);
        //设置布局管理器
        shop_recy.setLayoutManager(new LinearLayoutManager(mContext));
        //初始化适配器
        shop_recy.setFocusableInTouchMode(false);
        shop_recy.setItemAnimator(null);
        adapter = new MarketPlaceAdapter(mContext, viewType, buyListBeen, listBeen, currentPriceBean,0);
        shop_recy.setAdapter(adapter);  //设置适配器
    }

    @Override
    public void dividendMarketDividendSuccess(MarketDividendTitleBean result) {
//        if (((MainActivity) mContext).isFinishing()) {
//            return;
//        }
        LogUtils.e("dividendMarketDividendSuccess==>" + GsonUtil.GsonString(result));
        if (result.getData().getList() != null) {
            buyListBeen = null;
            buyListBeen = result.getData().getList();
            upDataAdapter();
        }
        //增长点
        if (!TextUtils.isEmpty(result.getData().getQuoteChange())) {
            String percentagePoint = result.getData().getQuoteChange();
            if (percentagePoint.contains("+")) {
                tvPercentagePoint.setBackgroundResource(R.drawable.market_rate);
                tvPercentagePoint.setTextColor(Color.parseColor("#03AD8F"));
            } else if (percentagePoint.contains("-")) {
                tvPercentagePoint.setBackgroundResource(R.drawable.market_rate_down);
                tvPercentagePoint.setTextColor(Color.parseColor("#D14B64"));
            } else {
                tvPercentagePoint.setBackgroundResource(R.drawable.market_rate_stop);
                tvPercentagePoint.setTextColor(Color.parseColor("#7E93A2"));
            }
            tvPercentagePoint.setText(percentagePoint);
        }
    }

    @Override
    public void dividendMarketTopDividendSuccess(MarketDividendBottomBean result) {
//        if (((MainActivity) mContext).isFinishing()) {
//            return;
//        }
        LogUtils.e("dividendMarketTopDividendSuccess==>" + GsonUtil.GsonString(result));
        MarketDividendBottomBean.DataBean data = result.getData();
        if (data.getList() != null) {
            if (result.getData().getList().size() == 0) {
                shop_recy.loadMoreFinish(false, false);
            }
            listBeen.clear();
            listBeen.addAll(result.getData().getList());
            upDataAdapter();
        }
    }

    /**
     * 更新数据
     */
    private void upDataAdapter() {
        if (adapter == null) {
            adapter = new MarketPlaceAdapter(mContext, viewType, buyListBeen, listBeen, currentPriceBean,0);
            shop_recy.setAdapter(adapter);  //设置适配器
        } else {
            adapter.updateList(mContext, viewType, buyListBeen, listBeen, currentPriceBean,0);
            //指定刷新，防止輸入框失去焦點
            int itemCount;
            if (listBeen.size() >= 5) {
                itemCount = 7;
            } else {
                itemCount = listBeen.size() + 1;
            }
            adapter.notifyItemRangeChanged(0, itemCount);
        }
    }

    @Override
    public void getCurrentPriceSuccess(CurrentPriceBean result) {
//        if (((MainActivity) mContext).isFinishing()) {
//            return;
//        }
        LogUtils.e("getCurrentPriceSuccess==>" + GsonUtil.GsonString(result));

        if (result.getData() != null) {
            currentPriceBean = result;
        } else {
            map = new HashMap<>();
            switch (viewType) {
                case "dividend":
                    map.put("coinType", "1");
                    map.put("transactionType", "1");
                    break;
                case "sell":
                    map.put("transactionType", "2");
                    break;
            }
            marketPresenter.getCurrentPrice(map);
            return;
        }
        if (adapter != null) {
            adapter.setCurrentPrice(currentPriceBean, viewType,0);
            adapter.notifyItemRangeChanged(0, 1);
        }
    }

    @Override
    public void sellOutSuccess(BaseBean result) {
//        if (((MainActivity) mContext).isFinishing()) {
//            return;
//        }
        if (SharedPreferencesUtil.getPrefInt(mContext, "FirstVerification", 0) == 0) {//第一次输入支付密码
            SharedPreferencesUtil.setPrefInt(mContext, "FirstVerification", 1);
            SharedPreferencesUtil.setSettingLong(getActivity(), "dateTime", System.currentTimeMillis());
        } else {
            String oldTime = TimeUtil.getTimeByLong(SharedPreferencesUtil.getPrefLong(getActivity(), "dateTime", System.currentTimeMillis()));
            if (TimeUtil.friendly_time(oldTime) >= 24) {
                SharedPreferencesUtil.setSettingLong(getActivity(), "dateTime", System.currentTimeMillis());
            }
        }
        ((MainActivity) mContext).dismissWaitDialog();
        LogUtils.e("purchaseOrderSuccess==>" + GsonUtil.GsonString(result));
        EventBus.getDefault().post("udpHome");
        upTopBottomData(viewType);
        WithdrawMoneyDialog dialog = new WithdrawMoneyDialog(mContext, true);
        dialog.setTitle(R.string.success);
        dialog.setTips(R.string.please_enter_my_dividend);
        dialog.show();
    }

    @Override
    public void marketSellSuccess(BaseBean result) {
//        if (((MainActivity) mContext).isFinishing()) {
//            return;
//        }
        if (SharedPreferencesUtil.getPrefInt(mContext, "FirstVerification", 0) == 0) {//第一次输入支付密码
            SharedPreferencesUtil.setPrefInt(mContext, "FirstVerification", 1);
            SharedPreferencesUtil.setSettingLong(getActivity(), "dateTime", System.currentTimeMillis());
        } else {
            String oldTime = TimeUtil.getTimeByLong(SharedPreferencesUtil.getPrefLong(getActivity(), "dateTime", System.currentTimeMillis()));
            if (TimeUtil.friendly_time(oldTime) >= 24) {
                SharedPreferencesUtil.setSettingLong(getActivity(), "dateTime", System.currentTimeMillis());
            }
        }
        ((MainActivity) mContext).dismissWaitDialog();
        LogUtils.e("purchaseOrderSuccess==>" + GsonUtil.GsonString(result));
        EventBus.getDefault().post("udpHome");
        upTopBottomData(viewType);
        WithdrawMoneyDialog dialog = new WithdrawMoneyDialog(mContext, true);
        dialog.setTitle(R.string.listing_success);
        dialog.setTips(R.string.please_enter_my_dividend);
        dialog.show();
    }

    @Override
    public void purchaseOrderSuccess(BaseBean result) {

    }

    @Override
    public void onViewFailureString(int statue, String message) {
//        if (((MainActivity) mContext).isFinishing()) {
//            return;
//        }
        ((MainActivity) mContext).dismissWaitDialog();
        AgainLoginUtil.againLogin(mContext, statue);
        if (message.contains("密码错误")) {
            SharedPreferencesUtil.setPrefInt(mContext, "FirstVerification", 0);
        }
        Toasts.showShort(message);
    }

    @Override
    public void onServerFailure(String e, int code) {
//        if (((MainActivity) mContext).isFinishing()) {
//            return;
//        }
        ((MainActivity) mContext).dismissWaitDialog();
        Toasts.showShort(e);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    @Override
    public void myDividendSuccess(MeDividend result) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        stopTimer();
        if (adapter != null) {
            adapter = null;
        }
        unbinder.unbind();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        count = 0;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (count > 0) {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            startTimer();
        }
    }

    private void startTimer() {
        //每5秒请求一次服务器
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }, 1000, 5000);
    }
}
