package com.jh.wxqb.ui.market;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.CurrentEntrustmentAdapter;
import com.jh.wxqb.base.BaseActivity;
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
import com.jh.wxqb.utils.Toasts;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 交易記錄
 */
public class CurrentEntrustmentActivity extends BaseActivity implements View.OnClickListener, MarketView {


    @BindView(R.id.shop_recy)
    SwipeMenuRecyclerView shop_recy;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.ll_sel_type)
    LinearLayout llSelType;

    int type = 3;  // 1.買入 2.卖出 3.所有
    int pageIndex = 1;
    boolean isClear = true;
    private CurrentEntrustmentAdapter adapter;
    private List<MeDividend.DataBean.ListBean> meDividendBean = new ArrayList<>();
    private PopupWindow optionWindow;
    private MarketPresenter marketPresenter;
    private boolean iMove;//屏幕滑动距离
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected int getLayout() {
        return R.layout.activity_current_entrustment;
    }


    @Override
    protected void init() {
        marketPresenter = new MarketPresenter(this);
        marketPresenter.myDividend(pageIndex, 3);
        initRecyclerView();
    }


    /**
     * 初始化RecyclerView配置
     */
    public void initRecyclerView() {
        //是否开启加载更多
        shop_recy.loadMoreFinish(false, true);
        //设置布局管理器
        mLinearLayoutManager = new LinearLayoutManager(this);
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
        adapter = new CurrentEntrustmentAdapter(this, meDividendBean);
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
                                marketPresenter.myDividend(pageIndex, type);
                                if (shop_recy != null) {
                                    shop_recy.loadMoreFinish(false, true);
                                }
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
                    marketPresenter.myDividend(pageIndex, type);
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
                    marketPresenter.myDividend(pageIndex, type);
                    sw_refresh.setRefreshing(false);  //停止刷新
                }
            });

        }
    };


    @Override
    public void myDividendSuccess(MeDividend result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("myDividendSuccess==>" + GsonUtil.GsonString(result));
        if (result.getData().getList().size() == 0) {
            shop_recy.loadMoreFinish(false, false);
        }
        if (isClear) {
            meDividendBean.clear();
        }
        meDividendBean.addAll(result.getData().getList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void dividendMarketDividendSuccess(MarketDividendTitleBean result) {

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

    @OnClick(R.id.ll_sel_type)
    public void onViewClicked() {
        selBusinessType(llSelType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_all:
                optionWindow.dismiss();
                ivImg.setImageResource(R.drawable.iv_down);
                tvType.setText(R.string.all);
                selType(3);
                break;
            case R.id.tv_dividend:
                optionWindow.dismiss();
                ivImg.setImageResource(R.drawable.iv_down);
                tvType.setText(R.string.dividend);
                selType(1);
                break;
            case R.id.tv_sell:
                optionWindow.dismiss();
                ivImg.setImageResource(R.drawable.iv_down);
                tvType.setText(R.string.sell);
                selType(2);
                break;
        }
    }

    public void selType(int type) {
        pageIndex = 1;
        isClear = true;
        this.type = type;
        marketPresenter.myDividend(pageIndex, type);
    }

    /**
     * 选择买卖订单类型类型
     */
    @SuppressLint("WrongConstant")
    private void selBusinessType(LinearLayout view) {
        ivImg.setImageResource(R.drawable.iv_up);
        View contentView = LayoutInflater.from(this).inflate(R.layout.item_business_type, null);
        TextView tvAll = contentView.findViewById(R.id.tv_all);
        TextView tvDividend = contentView.findViewById(R.id.tv_dividend);
        TextView tvSell = contentView.findViewById(R.id.tv_sell);
        tvAll.setOnClickListener(this);
        tvDividend.setOnClickListener(this);
        tvSell.setOnClickListener(this);
        optionWindow = new PopupWindow(contentView, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);
        optionWindow.setTouchable(true);
        optionWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));   //设置背景
        optionWindow.setOutsideTouchable(true);
        optionWindow.showAsDropDown(view, -15, 0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();    //将背景颜色参数重新设置
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        optionWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        optionWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        optionWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);   //从底部弹出
        optionWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
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

}
