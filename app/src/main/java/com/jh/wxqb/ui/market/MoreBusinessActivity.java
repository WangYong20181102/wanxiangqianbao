package com.jh.wxqb.ui.market;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.MoreBusinessAdapter;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.CoreKeys;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.CurrentPriceBean;
import com.jh.wxqb.bean.MarketDividendBottomBean;
import com.jh.wxqb.bean.MarketDividendTitleBean;
import com.jh.wxqb.bean.MeDividend;
import com.jh.wxqb.customview.DefineLoadMoreView;
import com.jh.wxqb.ui.market.presenter.MarketPresenter;
import com.jh.wxqb.ui.market.view.MarketView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.MoveDistanceUtils;
import com.jh.wxqb.utils.MyClicker;
import com.jh.wxqb.utils.Toasts;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 查看更多（最近成交）
 */
public class MoreBusinessActivity extends BaseActivity implements MarketView, MyClicker {

    @BindView(R.id.shop_recy)
    SwipeMenuRecyclerView shop_recy;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    int pageIndex = 1;
    boolean isClear = true;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.iv_business_down)
    ImageView ivBusinessDown;
    private boolean iMove;//屏幕滑动距离

    private MoreBusinessAdapter adapter;
    private List<MarketDividendBottomBean.DataBean.ListBean> listBeen = new ArrayList<>();
    private MarketPresenter marketPresenter;
    private int type = 0; //0.所有的 1.買入 2.卖出
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected int getLayout() {
        return R.layout.activity_more_business;
    }

    @Override
    protected void init() {
        showWaitDialog();
        marketPresenter = new MarketPresenter(this);
        marketPresenter.dividendMarketTopDividend(pageIndex, 1, type);
        initRecyclerView();
    }

    @Override
    public void myClick(View view, int type) {
        if (type == 0) {
            int position = (int) view.getTag();
            Intent intent = new Intent(this, PurchaseOrderActivity.class);
            intent.putExtra("data", listBeen.get(position));
            startActivityForResult(intent, CoreKeys.RESULT_CODE);
        }
    }

    /**
     * 初始化RecyclerView配置
     */
    public void initRecyclerView() {
        //是否开启加载更多
        shop_recy.loadMoreFinish(false, true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        shop_recy.setLayoutManager(mLinearLayoutManager);
        // 自定义的核心就是DefineLoadMoreView类。
        DefineLoadMoreView loadMoreView = new DefineLoadMoreView(this);
        shop_recy.addFooterView(loadMoreView); // 添加为Footer。
        shop_recy.setLoadMoreView(loadMoreView); // 设置LoadMoreView更新监听。
//        shop_recy.setLoadMoreListener(mLoadMoreListener);   //上拉加载更多
        shop_recy.addOnScrollListener(onScrollListener);
        sw_refresh.setOnRefreshListener(mRefreshListener);  //下拉刷新
        new MoveDistanceUtils().setOnMoveDistanceListener(shop_recy, new MoveDistanceUtils.OnMoveDistanceListener() {
            @Override
            public void onMoveDistance(boolean b) {
                iMove = b;
            }
        });


        //初始化适配器
        adapter = new MoreBusinessAdapter(this, listBeen, this);
        shop_recy.setAdapter(adapter);  //设置适配器
    }

    //滑动事件
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            //滑动加载更多
            super.onScrollStateChanged(recyclerView, newState);
            if (adapter != null) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLinearLayoutManager.findLastVisibleItemPosition() == adapter.getItemCount()) {
                    if (iMove) {
                        //加载更多
                        shop_recy.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isClear = false;
                                pageIndex = pageIndex + 1;
                                marketPresenter.dividendMarketTopDividend(pageIndex, 1, type);
                                shop_recy.loadMoreFinish(false, true);
                            }
                        }, 1000);
                    }
                }
            }

        }
    };

    //上拉加载更多
    private SwipeMenuRecyclerView.LoadMoreListener mLoadMoreListener = new SwipeMenuRecyclerView.LoadMoreListener() {
        @Override
        public void onLoadMore() {
            shop_recy.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isClear = false;
                    pageIndex = pageIndex + 1;
                    marketPresenter.dividendMarketTopDividend(pageIndex, 1, type);
                    shop_recy.loadMoreFinish(false, true);
                }
            }, 1000);
        }
    };

    //下拉刷新
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            shop_recy.post(new Runnable() {
                @Override
                public void run() {
                    shop_recy.loadMoreFinish(false, true);
                    pageIndex = 1;
                    isClear = true;
                    marketPresenter.dividendMarketTopDividend(pageIndex, 1, type);
                    sw_refresh.setRefreshing(false);  //停止刷新
                }
            });

        }
    };


    @Override
    public void dividendMarketTopDividendSuccess(MarketDividendBottomBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("dividendMarketTopDividendSuccess==>" + GsonUtil.GsonString(result));
        if (result.getData().getList().size() == 0) {
            shop_recy.loadMoreFinish(false, false);
        }
        if (isClear) {
            listBeen.clear();
        }
        listBeen.addAll(result.getData().getList());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CoreKeys.RESULT_CODE && resultCode == CoreKeys.RESULT_CODE) {
            pageIndex = 1;
            isClear = true;
            marketPresenter.dividendMarketTopDividend(pageIndex, 1, type);
        }
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

}
