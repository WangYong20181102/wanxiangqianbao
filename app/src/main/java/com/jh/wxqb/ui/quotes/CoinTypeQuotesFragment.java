package com.jh.wxqb.ui.quotes;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.CoinTypeQuotesAdapter;
import com.jh.wxqb.base.BaseFragment;
import com.jh.wxqb.bean.QuotesCoinBean;
import com.jh.wxqb.ui.quotes.presenter.QuotesPresenter;
import com.jh.wxqb.ui.quotes.view.QuotesView;
import com.jh.wxqb.utils.Toasts;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 币种行情
 */
public class CoinTypeQuotesFragment extends BaseFragment implements QuotesView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    private View view;
    private List<QuotesCoinBean.DataBean.QuotesListBean> beanList;
    private QuotesPresenter presenter;
    private CoinTypeQuotesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coin_type_quotes, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        presenter = new QuotesPresenter(this);
        initData();
        initRequest();
        return view;
    }

    /**
     * 初始化网络请求
     */
    private void initRequest() {
        presenter.getQuotesRequest();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        beanList = new ArrayList<>();

        swRefresh.setOnRefreshListener(mRefreshListener);//下拉刷新
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }

    @Subscribe
    public void updateQuotes(String message) {
        switch (message) {
            case "updateCoinTypeQuotes":
                presenter.getQuotesRequest();
                break;
        }
    }

    //下拉刷新
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            swRefresh.post(new Runnable() {
                @Override
                public void run() {
                    presenter.getQuotesRequest();
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewFailureString(int statue, String message) {
        if (swRefresh != null) {
            swRefresh.setRefreshing(false);  //停止刷新
        }
        Toasts.showShort(message);
    }

    @Override
    public void onServerFailure(String e, int code) {
        if (swRefresh != null) {
            swRefresh.setRefreshing(false);  //停止刷新
        }
    }

    @Override
    public void onQuotesDateRequestSuccess(QuotesCoinBean quotesCoinBean) {
        if (swRefresh != null) {
            swRefresh.setRefreshing(false);  //停止刷新
        }
        if (quotesCoinBean.getData().getQuotesList() != null) {
            beanList = null;
            beanList = quotesCoinBean.getData().getQuotesList();
            if (adapter == null) {
                adapter = new CoinTypeQuotesAdapter(beanList, getActivity());
                recyclerView.setAdapter(adapter);
            } else {
                adapter.updateBeanList(beanList);
                adapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
