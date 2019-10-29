package com.jh.wxqb.ui.me.mysendletter;


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
import com.jh.wxqb.adapter.MeSellAdapter;
import com.jh.wxqb.base.BaseFragment;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.MeDividend;
import com.jh.wxqb.customview.CancelOrOkDialog;
import com.jh.wxqb.customview.DefineLoadMoreView;
import com.jh.wxqb.ui.me.mysendletter.presenter.MySendLetterPresenter;
import com.jh.wxqb.ui.me.mysendletter.view.MySendLetterView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.MyClicker;
import com.jh.wxqb.utils.Toasts;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 賣出
 */
public class SellFragment extends BaseFragment implements MyClicker, MySendLetterView {

    private View view;
    @BindView(R.id.shop_recy)
    SwipeMenuRecyclerView shop_recy;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    int pageIndex = 1;
    boolean isClear = true;
    private MeSellAdapter adapter;
    private List<MeDividend.DataBean.ListBean> meSellBeen = new ArrayList<>();
    private MySendLetterPresenter presenter;
    private boolean isMaxPageIndex = false; //标识是否是已请求到最大页码。已无数据

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_sell, container, false);
            ButterKnife.bind(this, view);
            presenter = new MySendLetterPresenter(this);
            presenter.myDividend(pageIndex, 2);
            initRecyclerView();
        }
        return view;
    }

    /**
     * 初始化RecyclerView配置
     */
    public void initRecyclerView() {
        //是否开启加载更多
        shop_recy.loadMoreFinish(false, true);
        //设置布局管理器
        shop_recy.setLayoutManager(new LinearLayoutManager(mContext));
        shop_recy.setSwipeItemClickListener(itemClickListener);   //每项Item点击事件

        // 自定义的核心就是DefineLoadMoreView类。
        DefineLoadMoreView loadMoreView = new DefineLoadMoreView(mContext);
        shop_recy.addFooterView(loadMoreView); // 添加为Footer。
        shop_recy.setLoadMoreView(loadMoreView); // 设置LoadMoreView更新监听。
        shop_recy.setLoadMoreListener(mLoadMoreListener);   //上拉加载更多
        sw_refresh.setOnRefreshListener(mRefreshListener);  //下拉刷新
        //初始化适配器
        adapter = new MeSellAdapter(mContext, meSellBeen, this);
        shop_recy.setAdapter(adapter);  //设置适配器
    }


    //item点击事件
    private SwipeItemClickListener itemClickListener = new SwipeItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
//            Intent intent = new Intent(NewsListActivity.this, NewsInfoActivity.class);
//            startActivity(intent);
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
                    presenter.myDividend(pageIndex, 2);
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
                    isMaxPageIndex=false;
                    pageIndex = 1;
                    isClear = true;
                    presenter.myDividend(pageIndex, 2);
                    sw_refresh.setRefreshing(false);  //停止刷新
                }
            });

        }
    };

    @Override
    public void myClick(final View view, int type) {
        switch (type) {
            case 0:
                CancelOrOkDialog cancelOrOkDialog = new CancelOrOkDialog(mContext, R.string.cance_order) {
                    @Override
                    public void ok() {
                        super.ok();
                        ((MySendLetterActivity) mContext).showWaitDialog();
                        int position = (int) view.getTag();
                        int id = meSellBeen.get(position).getId();
                        map = new HashMap<>();
                        map.put("commissonId", String.valueOf(id));
                        presenter.stopQueuing(map);
                        dismiss();
                    }
                };
                cancelOrOkDialog.show();
                break;
        }
    }

    @Override
    public void myDividendSuccess(MeDividend result) {
        if (((MySendLetterActivity) mContext).isFinishing()) {
            return;
        }
        ((MySendLetterActivity) mContext).dismissWaitDialog();
        LogUtils.e("myDividendSuccess==>" + GsonUtil.GsonString(result));
        if (result.getData().getList().size() == 0) {
            shop_recy.loadMoreFinish(false, false);
            isMaxPageIndex = true;
        }
        if (isClear) {
            meSellBeen.clear();
        }
        meSellBeen.addAll(result.getData().getList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stopDividendSuccess(BaseBean result) {

    }

    @Override
    public void stopQueuingSuccess(BaseBean result) {
        if (((MySendLetterActivity) mContext).isFinishing()) {
            return;
        }
        ((MySendLetterActivity) mContext).dismissWaitDialog();
        Toasts.showShort(result.getMessage());
        LogUtils.e("stopQueuingSuccess==>" + GsonUtil.GsonString(result));
        if (isMaxPageIndex) {
            shop_recy.loadMoreFinish(false, true);
            pageIndex = 1;
            isClear = true;
            presenter.myDividend(pageIndex, 2);
        }else {
            presenter.myDividend(pageIndex, 2);
        }
        EventBus.getDefault().post("udpHome");
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    @Override
    public void onViewFailureString(int statue, String message) {
        if (((MySendLetterActivity) mContext).isFinishing()) {
            return;
        }
        ((MySendLetterActivity) mContext).dismissWaitDialog();
        AgainLoginUtil.againLogin(mContext, statue);
        Toasts.showShort(message);
    }

    @Override
    public void onServerFailure(String e, int code) {
        if (((MySendLetterActivity) mContext).isFinishing()) {
            return;
        }
        ((MySendLetterActivity) mContext).dismissWaitDialog();
        Toasts.showShort(e);
    }


}
