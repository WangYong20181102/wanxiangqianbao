package com.jh.wxqb.ui.assets;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.AssetManagementAdapter;
import com.jh.wxqb.adapter.FreezeAssetAdapter;
import com.jh.wxqb.base.BaseFragment;
import com.jh.wxqb.base.MainActivity;
import com.jh.wxqb.bean.AssetManagementBean;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.CoinPricesBean;
import com.jh.wxqb.bean.FinancialDetailsBean;
import com.jh.wxqb.bean.SafetyMarkingBean;
import com.jh.wxqb.customview.DefineLoadMoreView;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 冻结资产
 */

public class FreezeAssetsFragment extends BaseFragment implements AssetsView {
    @BindView(R.id.shop_recy)
    SwipeMenuRecyclerView shopRecy;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    Unbinder unbinder;
    private View view;
    int pageIndex = 1;
    boolean isClear = true;
    private FreezeAssetAdapter adapter;
    private List<AssetManagementBean.DataBean.AccountAssetsBean> financialDetailsBeen = new ArrayList<>();
    private AssestPresenter assestPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_freeze_assets, container, false);
            EventBus.getDefault().register(this);
            unbinder = ButterKnife.bind(this, view);
            assestPresenter = new AssestPresenter(this);
            initRecyclerView();
            assestPresenter.getQueryaccountassets(pageIndex, 7);
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
        adapter = new FreezeAssetAdapter(mContext, financialDetailsBeen);
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

    }

    @Override
    public void coinRechangeSuccess(BaseBean result) {

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
}
