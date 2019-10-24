package com.jh.wxqb.ui.assets;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.AssetManagementAdapter;
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
import com.jh.wxqb.utils.MyClicker;
import com.jh.wxqb.utils.Toasts;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 活動資產
 */
public class ActiveManagementFragment extends BaseFragment implements MyClicker, AssetsView {


    private View view;
    @BindView(R.id.shop_recy)
    SwipeMenuRecyclerView shop_recy;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    protected RecyclerView.ItemDecoration mItemDecoration;  //Item之间的间距
    int pageIndex = 1;
    boolean isClear = true;
    private AssetManagementAdapter adapter;
    private List<AssetManagementBean.DataBean.AccountAssetsBean> financialDetailsBeen = new ArrayList<>();
    private AssestPresenter assestPresenter;
    private OnEquivalentAssetsListener onEquivalentAssetsListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_assets_management, container, false);
            ButterKnife.bind(this, view);
            EventBus.getDefault().register(this);
            assestPresenter = new AssestPresenter(this);
            initRecyclerView();
            assestPresenter.getQueryaccountassets(pageIndex, 7);
        }
        return view;
    }


//    @OnClick({R.id.tv_withdraw_money, R.id.tv_tran})
//    public void onViewClicked(View view) {
//        Intent intent;
//        switch (view.getId()) {
//            case R.id.tv_withdraw_money:
//                intent = new Intent(mContext, WithdrawMoneyActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.tv_tran:
//                intent = new Intent(mContext, PunchingMoneyActivity.class);
//                startActivity(intent);
//                break;
//        }
//    }

    /**
     * 初始化RecyclerView配置
     */
    public void initRecyclerView() {
        //是否开启加载更多
        shop_recy.loadMoreFinish(false, false);
        //设置布局管理器
        shop_recy.setLayoutManager(new LinearLayoutManager(mContext));

        // 自定义的核心就是DefineLoadMoreView类。
        DefineLoadMoreView loadMoreView = new DefineLoadMoreView(mContext);
        shop_recy.addFooterView(loadMoreView); // 添加为Footer。
        shop_recy.setLoadMoreView(loadMoreView); // 设置LoadMoreView更新监听。
        sw_refresh.setOnRefreshListener(mRefreshListener);  //下拉刷新
//        mItemDecoration = createItemDecoration(); //获取ItemDecoration对象
//        shop_recy.addItemDecoration(mItemDecoration); //添加每个Item之间的间距
        //初始化适配器
        adapter = new AssetManagementAdapter(mContext, financialDetailsBeen);
        shop_recy.setAdapter(adapter);  //设置适配器
    }


    //每个Item之间的间距
    protected RecyclerView.ItemDecoration createItemDecoration() {
        //颜色  宽度  高度
        return new DefaultItemDecoration(Color.rgb(243, 243, 243), WindowManager.LayoutParams.MATCH_PARENT, 1);
    }


    //下拉刷新
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            shop_recy.post(new Runnable() {
                @Override
                public void run() {
//                    shop_recy.loadMoreFinish(false, true);
                    pageIndex = 1;
                    isClear = true;
                    assestPresenter.getQueryaccountassets(pageIndex, 7);
                    sw_refresh.setRefreshing(false);  //停止刷新
                }
            });

        }
    };


    @Override
    public void myClick(View view, int type) {

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
                        shop_recy.loadMoreFinish(false, false);
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
                    double total = 0;
                    for (int i = 0; i < financialDetailsBeen.size(); i++) {
                        double d = financialDetailsBeen.get(i).getDiscountedPrice();
                        total = total + d;
                    }
                    if (onEquivalentAssetsListener != null) {
                        onEquivalentAssetsListener.onResult(total);
                    }
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
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {

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

    public void setOnEquivalentAssetsListener(OnEquivalentAssetsListener onEquivalentAssetsListener) {
        this.onEquivalentAssetsListener = onEquivalentAssetsListener;
    }

    interface OnEquivalentAssetsListener {
        void onResult(double totalAssets);
    }
}
