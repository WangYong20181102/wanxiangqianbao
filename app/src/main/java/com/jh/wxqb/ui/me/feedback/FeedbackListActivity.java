package com.jh.wxqb.ui.me.feedback;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.FeedbackListAdapter;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.FeedbackInfoBean;
import com.jh.wxqb.bean.FeedbackListBean;
import com.jh.wxqb.customview.DefineLoadMoreView;
import com.jh.wxqb.ui.me.feedback.presenter.FeedBackPresenter;
import com.jh.wxqb.ui.me.feedback.view.FeedBackView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.Toasts;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 反饋列表
 */
public class FeedbackListActivity extends BaseActivity implements FeedBackView {


    @BindView(R.id.shop_recy)
    SwipeMenuRecyclerView shop_recy;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    int pageIndex = 1;
    boolean isClear = true;
    private FeedbackListAdapter adapter;
    private List<FeedbackListBean.DataBean.MapDataBean.MapJsonBean> feedbackListBeen = new ArrayList<>();
    private FeedBackPresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_feedback_list;
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        showWaitDialog();
        presenter = new FeedBackPresenter(this);
        presenter.userFeedbackList(pageIndex);
        initRecyclerView();
    }

    @Subscribe
    public void udpFeedbackList(String udpFeedbackList) {
        if (udpFeedbackList.equals("udpFeedbackList")) {
            presenter.userFeedbackList(pageIndex);
        }
    }

    /**
     * 初始化RecyclerView配置
     */
    public void initRecyclerView() {
        //是否开启加载更多
        shop_recy.loadMoreFinish(false, true);
        //设置布局管理器
        shop_recy.setLayoutManager(new LinearLayoutManager(this));
        shop_recy.setSwipeItemClickListener(itemClickListener);   //每项Item点击事件

        // 自定义的核心就是DefineLoadMoreView类。
        DefineLoadMoreView loadMoreView = new DefineLoadMoreView(this);
        shop_recy.addFooterView(loadMoreView); // 添加为Footer。
        shop_recy.setLoadMoreView(loadMoreView); // 设置LoadMoreView更新监听。
        shop_recy.setLoadMoreListener(mLoadMoreListener);   //上拉加载更多
        sw_refresh.setOnRefreshListener(mRefreshListener);  //下拉刷新
        //初始化适配器
        adapter = new FeedbackListAdapter(this, feedbackListBeen);
        shop_recy.setAdapter(adapter);  //设置适配器
    }


    //item点击事件
    private SwipeItemClickListener itemClickListener = new SwipeItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
            Intent intent = new Intent(FeedbackListActivity.this, FeedbackInfoActivity.class);
            intent.putExtra("id", String.valueOf(feedbackListBeen.get(position).getId()));
            startActivity(intent);
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
                    presenter.userFeedbackList(pageIndex);
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
                    presenter.userFeedbackList(pageIndex);
                    sw_refresh.setRefreshing(false);  //停止刷新
                }
            });

        }
    };


    @Override
    public void userFeedbackListSuccess(FeedbackListBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("userFeedbackListSuccess==>" + GsonUtil.GsonString(result));
        if (result.getData().getMapData().getMapJson().size() == 0) {
            shop_recy.loadMoreFinish(false, false);
        }
        if (isClear) {
            feedbackListBeen.clear();
        }
        feedbackListBeen.addAll(result.getData().getMapData().getMapJson());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void queryUserFeedbackSuccess(FeedbackInfoBean result) {

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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void saveUserFeedbackSuccess(BaseBean result) {

    }

}
