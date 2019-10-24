package com.jh.wxqb.ui.assets;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.EnjoyAssetAdapter;
import com.jh.wxqb.base.BaseFragment;
import com.jh.wxqb.base.MainActivity;
import com.jh.wxqb.bean.AssetManagementBean;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.CoinPricesBean;
import com.jh.wxqb.bean.FinancialDetailsBean;
import com.jh.wxqb.bean.SafetyMarkingBean;
import com.jh.wxqb.customview.DefineLoadMoreView;
import com.jh.wxqb.customview.RealTimeRedemptionDialog;
import com.jh.wxqb.ui.assets.presenter.AssestPresenter;
import com.jh.wxqb.ui.assets.view.AssetsView;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 优享资产
 */

public class EnjoyAssetsFragment extends BaseFragment implements AssetsView {
    @BindView(R.id.shop_recy)
    SwipeMenuRecyclerView shopRecy;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    Unbinder unbinder;
    private View view;
    int pageIndex = 1;
    boolean isClear = true;
    private EnjoyAssetAdapter adapter;
    private List<AssetManagementBean.DataBean.AccountAssetsBean> financialDetailsBeen = new ArrayList<>();
    private AssestPresenter assestPresenter;
    private CoinPricesBean coinPricesBean;
    private RealTimeRedemptionDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_enjoy_assets, container, false);
            EventBus.getDefault().register(this);
            unbinder = ButterKnife.bind(this, view);
            assestPresenter = new AssestPresenter(this);
            initRecyclerView();
            assestPresenter.getQueryaccountassets(pageIndex, 7);
            //获取币种实时价格
            assestPresenter.getCoinPrices();
        }
        return view;
    }

    /**
     * 初始化RecyclerView配置
     */
    public void initRecyclerView() {
        //是否开启加载更多
        shopRecy.loadMoreFinish(false, false);
        //设置布局管理器
        shopRecy.setLayoutManager(new LinearLayoutManager(mContext));

        // 自定义的核心就是DefineLoadMoreView类。
        DefineLoadMoreView loadMoreView = new DefineLoadMoreView(mContext);
        shopRecy.addFooterView(loadMoreView); // 添加为Footer。
        shopRecy.setLoadMoreView(loadMoreView); // 设置LoadMoreView更新监听。
        swRefresh.setOnRefreshListener(mRefreshListener);  //下拉刷新
        //初始化适配器
        adapter = new EnjoyAssetAdapter(mContext, financialDetailsBeen);
        shopRecy.setAdapter(adapter);  //设置适配器
    }

    //下拉刷新
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            shopRecy.post(new Runnable() {
                @Override
                public void run() {
//                    shopRecy.loadMoreFinish(false, true);
                    pageIndex = 1;
                    isClear = true;
                    assestPresenter.getQueryaccountassets(pageIndex, 7);
                    assestPresenter.getCoinPrices();
                    swRefresh.setRefreshing(false);  //停止刷新
                }
            });

        }
    };

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    @Override
    public void getFinancialDetailsSuccess(FinancialDetailsBean result) {

    }

    @Override
    public void getAssetManagementSuccess(AssetManagementBean result) {
        if (((MainActivity) mContext).isFinishing()) {
            return;
        }
        ((MainActivity) mContext).dismissWaitDialog();

        LogUtils.e("getFinancialDetailsSuccess==>" + GsonUtil.GsonString(result));
        if (result != null) {
            if (result.getData() != null) {
                if (result.getData().getAccountAssets() != null) {
                    if (result.getData().getAccountAssets().size() == 0) {
                        shopRecy.loadMoreFinish(false, false);
                    }
                }
            }
        }
        if (isClear) {
            financialDetailsBeen.clear();
        }
        if (result != null) {
            if (result.getData() != null) {
                if (result.getData().getAccountAssets() != null) {
                    financialDetailsBeen.addAll(result.getData().getAccountAssets());
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void withdrawMoneySuccess(BaseBean result) {

    }

    @Override
    public void safetyMarkingSuccess(SafetyMarkingBean result) {

    }

    @Override
    public void coinPricesSuccess(CoinPricesBean result) {
        LogUtils.e("coinPrices====" + GsonUtil.GsonString(result));
        coinPricesBean = result;
    }

    @Override
    public void coinRechangeSuccess(BaseBean result) {
        Toasts.showShort(result.getMessage());
        EventBus.getDefault().post("udpAssestData");
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

    @Subscribe
    public void selAssets(String selAssets) {
        switch (selAssets) {
            case "udpAssestData":
                assestPresenter.getQueryaccountassets(pageIndex, 7);
                assestPresenter.getCoinPrices();
                break;
            case "selAssets":
                assestPresenter.getQueryaccountassets(pageIndex, 7);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_change_click, R.id.rl_change_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_change_click://实时兑换
            case R.id.tv_change_click:
                if (coinPricesBean == null) {
                    Toasts.showShort("实时价格获取失败，请刷新当前页面");
                } else {
                    dialog = new RealTimeRedemptionDialog(getContext(), coinPricesBean, financialDetailsBeen) {
                        @Override
                        public void onSure(Map<String, String> params) {
                            assestPresenter.getSavechangeinfo(params);
                        }
                    };
                    dialog.show();
                }
                break;
        }
    }
}
