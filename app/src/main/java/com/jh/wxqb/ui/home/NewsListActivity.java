package com.jh.wxqb.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.NewsListAdapter;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.bean.MessageDetailsBean;
import com.jh.wxqb.bean.MyMessageBean;
import com.jh.wxqb.bean.NewsInfoBean;
import com.jh.wxqb.bean.NewsMoreListBean;
import com.jh.wxqb.bean.UserBean;
import com.jh.wxqb.bean.VersionUpdateBean;
import com.jh.wxqb.customview.DefineLoadMoreView;
import com.jh.wxqb.ui.home.presenter.HomePresenter;
import com.jh.wxqb.ui.home.view.HomeView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.MoveDistanceUtils;
import com.jh.wxqb.utils.Toasts;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 资讯列表
 */
public class NewsListActivity extends BaseActivity implements HomeView {

    @BindView(R.id.shop_recy)
    SwipeMenuRecyclerView shop_recy;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    int pageIndex = 1;
    boolean isClear = true;
    private NewsListAdapter adapter;
    private List<NewsMoreListBean.DataBean.NoticeMapBean> newsListBeen = new ArrayList<>();
    private HomePresenter homePresenter;
    private boolean iMove;//屏幕滑动距离
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected int getLayout() {
        return R.layout.activity_news_list;
    }

    @Override
    protected void init() {
        showWaitDialog();
        homePresenter = new HomePresenter(this);
        homePresenter.newsList("1", pageIndex);
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
        shop_recy.setSwipeItemClickListener(itemClickListener);   //每项Item点击事件

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
        adapter = new NewsListAdapter(this, newsListBeen);
        shop_recy.setAdapter(adapter);  //设置适配器
    }


    //item点击事件
    private SwipeItemClickListener itemClickListener = new SwipeItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
            Intent intent = new Intent(NewsListActivity.this, NewsInfoActivity.class);
            intent.putExtra("id", String.valueOf(newsListBeen.get(position).getId()));
            startActivity(intent);
        }
    };
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
                                homePresenter.newsList("1", pageIndex);
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
                    homePresenter.newsList("1", pageIndex);
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
                    homePresenter.newsList("1", pageIndex);
                    sw_refresh.setRefreshing(false);  //停止刷新
                }
            });

        }
    };


    @Override
    public void newsListSuccess(NewsMoreListBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("newsListSuccess==>" + GsonUtil.GsonString(result));
        if (result.getData().getNoticeMap().size() == 0) {
            shop_recy.loadMoreFinish(false, false);
        }
        if (isClear) {
            newsListBeen.clear();
        }
        newsListBeen.addAll(result.getData().getNoticeMap());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void myMessageSuccess(MyMessageBean result) {

    }

    @Override
    public void myMessageDetailsSuccess(MessageDetailsBean result) {

    }

    @Override
    public void onViewFailureString(int statue, String message) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        Toasts.showShort(message);
        AgainLoginUtil.againLogin(this, statue);
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
    public void getUserInfoSuccess(UserBean result) {

    }

    @Override
    public void versionUpdateSuccess(VersionUpdateBean result) {

    }

    @Override
    public void newsInfoSuccess(NewsInfoBean result) {

    }

}
