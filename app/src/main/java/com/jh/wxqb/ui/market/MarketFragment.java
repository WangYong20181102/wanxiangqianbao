package com.jh.wxqb.ui.market;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.MarketDividendAdapter;
import com.jh.wxqb.base.BaseFragment;
import com.jh.wxqb.base.MainActivity;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.CurrentPriceBean;
import com.jh.wxqb.bean.MarketDividendBottomBean;
import com.jh.wxqb.bean.MarketDividendTitleBean;
import com.jh.wxqb.bean.MeDividend;
import com.jh.wxqb.customview.DefineLoadMoreView;
import com.jh.wxqb.customview.WithdrawMoneyDialog;
import com.jh.wxqb.ui.market.presenter.MarketPresenter;
import com.jh.wxqb.ui.market.view.MarketView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.Toasts;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 買入市場
 */
public class MarketFragment extends BaseFragment implements MarketView {

    @BindView(R.id.tv_dividend)
    TextView tvDividend;
    @BindView(R.id.view_dividend)
    View viewDividend;
    @BindView(R.id.tv_sell)
    TextView tvSell;
    @BindView(R.id.view_sell)
    View viewSell;
    private View view;
    @BindViews({R.id.tv_dividend, R.id.tv_sell})
    List<TextView> allTitle;
    @BindViews({R.id.view_dividend, R.id.view_sell})
    List<View> allView;
    @BindView(R.id.shop_recy)
    SwipeMenuRecyclerView shop_recy;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    private MarketDividendAdapter adapter;
    private MarketPresenter marketPresenter;
    private CurrentPriceBean currentPriceBean;
    private MarketDividendTitleBean.DataBean.ListBean buyListBeen;
    private List<MarketDividendBottomBean.DataBean.ListBean> listBeen = new ArrayList<>();
    private int type = 1;
    private String viewType = "dividend";   //dividend：買入  sell：卖出
    private String transactionType = "1";   //  挂单方向 1：買入  2：卖出
    private String coinType = "1";   //  资产类型 1：活动ETH  2：重构PKB  3：買入PKB
    private String selActiveType = "1";  //选择買入类型文字记录   1活动ETH  2重购PKB  3：買入PKB
    private String assetTypeId = "1";  //记录 買入选择类型   委托数量输入框计算预计获得
    private String isUdpTypeText="0";  //标识当前买卖是否修改  0不修改  1修改

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_market, container, false);
            ButterKnife.bind(this, view);
            EventBus.getDefault().register(this);
            marketPresenter = new MarketPresenter(this);
            initRecyclerView();
        }
        return view;
    }

    @OnClick({R.id.ll_current_entrustment, R.id.ll_active_assets, R.id.ll_repurchase_assets})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_current_entrustment:
                intent = new Intent(mContext, CurrentEntrustmentActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_active_assets:
                isUdpTypeText="0";
                selectTitle(0);
                map.put("coinType", coinType);
                map.put("transactionType", transactionType);
                marketPresenter.getCurrentPrice(map);
                marketPresenter.dividendMarketTopDividend(1, 2, 1);
                break;
            case R.id.ll_repurchase_assets:
                isUdpTypeText="0";
                selectTitle(1);
                map = new HashMap<>();
                map.put("transactionType", "2");
                marketPresenter.getCurrentPrice(map);
                marketPresenter.dividendMarketTopDividend(1, 2, 2);
                break;
        }
    }

    //选择标题
    public void selectTitle(int index) {
        clearViewAndTitle();
        switch (index) {
            case 0:
                allTitle.get(index).setTextColor(getResources().getColor(R.color.color_02c289));
                break;
            case 1:
                allTitle.get(index).setTextColor(getResources().getColor(R.color.color_d6734b));
                break;
        }
        allView.get(index).setVisibility(View.VISIBLE);
        switch (index) {
            case 0:
                allTitle.get(index).setTextColor(getResources().getColor(R.color.color_02c289));
                break;
            case 1:
                allTitle.get(index).setTextColor(getResources().getColor(R.color.color_d6734b));
                break;
        }
        switch (index) {
            case 0:
                viewType = "dividend";
                break;
            case 1:
                viewType = "sell";
                break;
        }
        adapter = new MarketDividendAdapter(mContext, getActivity(), viewType, buyListBeen, listBeen, currentPriceBean,
                selActiveType, assetTypeId,type,isUdpTypeText);
        shop_recy.setAdapter(adapter);  //设置适配器
    }

    //初始化标题
    public void clearViewAndTitle() {
        for (TextView text : allTitle) {
            text.setTextColor(Color.WHITE);
        }
        for (View view : allView) {
            view.setVisibility(View.GONE);
        }
    }


    @Subscribe
    public void selAssets(String selAssets) {
        switch (selAssets) {
            case "selAssets":
                marketPresenter.dividendMarketTopDividend(1, 2, type);
                marketPresenter.dividendMarketDividend(3);
                map = new HashMap<>();
                switch (viewType) {
                    case "dividend":
                        map.put("coinType", coinType);
                        map.put("transactionType", transactionType);
                        break;
                    case "sell":
                        map.put("transactionType", "2");
                        break;
                }
                marketPresenter.getCurrentPrice(map);
                break;
            case "udpMarketData":
                viewType="dividend";
                selectTitle(0);
                marketPresenter=new MarketPresenter(this);
                marketPresenter.dividendMarketTopDividend(1, 2, type);
                marketPresenter=new MarketPresenter(this);
                marketPresenter.dividendMarketDividend(3);
                map = new HashMap<>();
                switch (viewType) {
                    case "dividend":
                        map.put("coinType", coinType);
                        map.put("transactionType", transactionType);
                        break;
                    case "sell":
                        map.put("transactionType", "2");
                        break;
                }
                marketPresenter.getCurrentPrice(map);
                break;
            case "udpCurrentBusinessList":
                marketPresenter.dividendMarketTopDividend(1, 2, type);
                break;
            case "udpPurchaseOrder":
                map = new HashMap<>();
                switch (viewType) {
                    case "dividend":
                        map.put("coinType", coinType);
                        map.put("transactionType", transactionType);
                        break;
                    case "sell":
                        map.put("transactionType", "2");
                        break;
                }
                marketPresenter.getCurrentPrice(map);
                break;
        }
    }

    @Subscribe
    public void udpBusiness(Intent intent) {
        String type = intent.getStringExtra("type");
        LogUtils.e("type==>" + type);
        switch (type) {
            case "udpBusiness":
                this.type = intent.getIntExtra("typeNum", 0);
                isUdpTypeText = intent.getStringExtra("isUdpTypeText");
                marketPresenter.dividendMarketTopDividend(1, 2, this.type);
                break;
            case "udpCurrentPrice":
                coinType = intent.getStringExtra("coinType");
                selActiveType = intent.getStringExtra("coinType");
                assetTypeId = intent.getStringExtra("assetTypeId");
                map = new HashMap<>();
                map.put("coinType", coinType);
                map.put("transactionType", transactionType);
                marketPresenter.getCurrentPrice(map);
                break;
            case "openDividends":
                LogUtils.e("当前价格==>" + intent.getStringExtra("currentPrice"));
                ((MainActivity) mContext).showWaitDialog();
                map = new HashMap<>();
                map.put("number", intent.getStringExtra("number"));
                map.put("tradePassword", intent.getStringExtra("tradePassword"));
                map.put("currentPrice", intent.getStringExtra("currentPrice"));
                map.put("assetTypeId", intent.getStringExtra("assetTypeId"));
                map.put("acountTransaction", intent.getStringExtra("acountTransaction"));
                marketPresenter.sellOut(map);
                break;
            case "marketSell":
                ((MainActivity) mContext).showWaitDialog();
                map = new HashMap<>();
                map.put("number", intent.getStringExtra("number"));
                map.put("tradePassword", intent.getStringExtra("tradePassword"));
                map.put("currentPrice", intent.getStringExtra("currentPrice"));
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
        sw_refresh.setEnabled(false);
        //设置布局管理器
        shop_recy.setLayoutManager(new LinearLayoutManager(mContext));

        // 自定义的核心就是DefineLoadMoreView类。
        DefineLoadMoreView loadMoreView = new DefineLoadMoreView(mContext);
        shop_recy.addFooterView(loadMoreView); // 添加为Footer。
        shop_recy.setLoadMoreView(loadMoreView); // 设置LoadMoreView更新监听。
        sw_refresh.setOnRefreshListener(mRefreshListener);  //下拉刷新
        //初始化适配器
        adapter = new MarketDividendAdapter(mContext, getActivity(), viewType, buyListBeen,
                listBeen, currentPriceBean, selActiveType, assetTypeId,type,isUdpTypeText);
        shop_recy.setAdapter(adapter);  //设置适配器
    }

    //下拉刷新
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            shop_recy.post(new Runnable() {
                @Override
                public void run() {
//                    marketPresenter.dividendMarketTopDividend(1, 2, type);
//                    marketPresenter.dividendMarketDividend(3);
//                    map = new HashMap<>();
//                    map.put("coinType", coinType);
//                    map.put("transactionType", transactionType);
//                    marketPresenter.getCurrentPrice(map);
//                    sw_refresh.setRefreshing(false);  //停止刷新
                }
            });
        }
    };


    @Override
    public void dividendMarketDividendSuccess(MarketDividendTitleBean result) {
        if (((MainActivity) mContext).isFinishing()) {
            return;
        }
        LogUtils.e("dividendMarketDividendSuccess==>" + GsonUtil.GsonString(result));
        buyListBeen = null;
        buyListBeen = result.getData().getList();
        adapter = new MarketDividendAdapter(mContext, getActivity(), "dividend", buyListBeen, listBeen,
                currentPriceBean, selActiveType, assetTypeId,type,isUdpTypeText);
        shop_recy.setAdapter(adapter);  //设置适配器
    }

    @Override
    public void dividendMarketTopDividendSuccess(MarketDividendBottomBean result) {
        if (((MainActivity) mContext).isFinishing()) {
            return;
        }
        LogUtils.e("dividendMarketTopDividendSuccess==>" + GsonUtil.GsonString(result));
        if (result.getData().getList().size() == 0) {
            shop_recy.loadMoreFinish(false, false);
        }
        listBeen.clear();
        listBeen.addAll(result.getData().getList());
        adapter = new MarketDividendAdapter(mContext, getActivity(), viewType, buyListBeen, listBeen,
                currentPriceBean, selActiveType, assetTypeId,type,isUdpTypeText);
        shop_recy.setAdapter(adapter);  //设置适配器
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void getCurrentPriceSuccess(CurrentPriceBean result) {
        if (((MainActivity) mContext).isFinishing()) {
            return;
        }
        LogUtils.e("getCurrentPriceSuccess==>" + GsonUtil.GsonString(result));

        if(result.getData()!=null){
            currentPriceBean = result;
        }else {
            map = new HashMap<>();
            switch (viewType) {
                case "dividend":
                    map.put("coinType", coinType);
                    map.put("transactionType", transactionType);
                    break;
                case "sell":
                    map.put("transactionType", "2");
                    break;
            }
            marketPresenter.getCurrentPrice(map);
        }
        adapter = new MarketDividendAdapter(mContext, getActivity(), viewType, buyListBeen, listBeen,
                currentPriceBean, selActiveType, assetTypeId,type,isUdpTypeText);
        shop_recy.setAdapter(adapter);  //设置适配器
    }

    @Override
    public void sellOutSuccess(BaseBean result) {
        if (((MainActivity) mContext).isFinishing()) {
            return;
        }
        ((MainActivity) mContext).dismissWaitDialog();
        LogUtils.e("purchaseOrderSuccess==>" + GsonUtil.GsonString(result));
        marketPresenter.dividendMarketTopDividend(1, 2, type);
        EventBus.getDefault().post("udpHome");
        WithdrawMoneyDialog dialog = new WithdrawMoneyDialog(mContext, true);
        dialog.setTitle(R.string.success);
        dialog.setTips(R.string.please_enter_my_dividend);
        dialog.show();
    }

    @Override
    public void marketSellSuccess(BaseBean result) {
        if (((MainActivity) mContext).isFinishing()) {
            return;
        }
        ((MainActivity) mContext).dismissWaitDialog();
        LogUtils.e("purchaseOrderSuccess==>" + GsonUtil.GsonString(result));
        marketPresenter.dividendMarketTopDividend(1, 2, type);
        EventBus.getDefault().post("udpHome");
        WithdrawMoneyDialog dialog = new WithdrawMoneyDialog(mContext, true);
        dialog.setTitle(R.string.listing_success);
        dialog.setTips(R.string.please_enter_my_dividend);
        dialog.show();
    }

    @Override
    public void purchaseOrderSuccess(BaseBean result) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewFailureString(int statue, String message) {
        if (((MainActivity) mContext).isFinishing()) {
            return;
        }
        ((MainActivity) mContext).dismissWaitDialog();
        AgainLoginUtil.againLogin(mContext, statue);
        Toasts.showShort(message);
    }

    @Override
    public void onServerFailure(String e, int code) {
        if (((MainActivity) mContext).isFinishing()) {
            return;
        }
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

}
