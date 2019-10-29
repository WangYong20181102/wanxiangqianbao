package com.jh.wxqb.ui.market;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.HandicapDetailAdapter;
import com.jh.wxqb.api.ServerInterface;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.CurrentPriceBean;
import com.jh.wxqb.bean.MarketDividendBottomBean;
import com.jh.wxqb.bean.MarketDividendTitleBean;
import com.jh.wxqb.bean.MeDividend;
import com.jh.wxqb.customview.NestedScrollWebView;
import com.jh.wxqb.ui.market.presenter.MarketPresenter;
import com.jh.wxqb.ui.market.view.MarketView;
import com.jh.wxqb.utils.Toasts;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 盘口详情
 */
public class HandicapDetailActivity extends BaseActivity implements MarketView {
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.swipe_recycle_view)
    SwipeMenuRecyclerView swipeRecycleView;
    @BindView(R.id.decorate_com_appbar)
    AppBarLayout decorateComAppbar;
    @BindView(R.id.webView)
    NestedScrollWebView webView;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.tv_sell)
    TextView tvSell;
    @BindView(R.id.tv_loading)
    TextView tvLoading;
    //折线图url
    private String appUrl = ServerInterface.BASE_URL + "kline/index.html#/";

    private MarketPresenter marketPresenter;
    private MarketDividendTitleBean.DataBean.ListBean listBean;
    //交易深度适配器
    private HandicapDetailAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_handicap_detail;
    }

    @Override
    protected void init() {
        //参数拼接
        if (MyApplication.getToken() != null) {
            appUrl += "?token=" + MyApplication.getToken().getAccess_token()
                    + "&webpath=" + ServerInterface.BASE_WEB_URL + "&platform=az" + "&lang=" + MyApplication.getLanuage();
        }
        marketPresenter = new MarketPresenter(this);
        initWebView();
        initRecyclerView();
        webView.loadUrl(appUrl);
        marketPresenter.dividendMarketDividend(3);//盘口数据
    }

    /**
     * 初始化webView
     */
    private void initWebView() {
        WebSettings settings = webView.getSettings();//获取设置对对象
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setBlockNetworkImage(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    webView.getSettings().setBlockNetworkImage(false);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                if (tvLoading != null){
                    tvLoading.setVisibility(View.GONE);
                }
            }
        });

    }

    /**
     * 初始化RecyclerView配置
     */
    public void initRecyclerView() {
        //是否开启加载更多
        swipeRecycleView.loadMoreFinish(false, false);
        //设置布局管理器
        swipeRecycleView.setLayoutManager(new LinearLayoutManager(this));
        //初始化适配器
        swipeRecycleView.setFocusableInTouchMode(false);
        swipeRecycleView.setItemAnimator(null);
        swipeRefresh.setOnRefreshListener(mRefreshListener);  //下拉刷新
        decorateComAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    swipeRefresh.setEnabled(true);
                } else {
                    swipeRefresh.setEnabled(false);
                }
            }
        });
        adapter = new HandicapDetailAdapter(this, listBean);
        swipeRecycleView.setAdapter(adapter);
    }

    //下拉刷新
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            marketPresenter.dividendMarketDividend(3);//盘口数据
            webView.loadUrl(appUrl);
        }
    };

    @Override
    public void myDividendSuccess(MeDividend result) {

    }

    @Override
    public void dividendMarketDividendSuccess(MarketDividendTitleBean result) {
        swipeRefresh.setRefreshing(false);  //停止刷新
        if (result.getData().getList() != null) {
            listBean = result.getData().getList();
            if (adapter == null) {
                adapter = new HandicapDetailAdapter(this, listBean);
                swipeRecycleView.setAdapter(adapter);
            } else {
                adapter.upDataList(listBean);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void dividendMarketTopDividendSuccess(MarketDividendBottomBean result) {

    }

    @Override
    public void purchaseOrderSuccess(BaseBean result) {

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

    @Override
    public void onViewFailureString(int statue, String message) {
        swipeRefresh.setRefreshing(false);  //停止刷新
        Toasts.showShort(message);
    }

    @Override
    public void onServerFailure(String e, int code) {

    }

    @OnClick({R.id.tv_buy, R.id.tv_sell})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_buy://买入
                EventBus.getDefault().post("backBuy");
                finish();
                break;
            case R.id.tv_sell://卖出
                EventBus.getDefault().post("backSell");
                finish();
                break;
        }
    }
}
