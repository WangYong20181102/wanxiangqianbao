package com.jh.wxqb.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.MyMessageAdapter;
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
 * 我的消息
 */
public class MyMessageActivity extends BaseActivity implements HomeView {

    @BindView(R.id.shop_recy)
    SwipeMenuRecyclerView shop_recy;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    protected RecyclerView.ItemDecoration mItemDecoration;  //Item之间的间距
    int pageIndex = 1;
    boolean isClear = true;
    private MyMessageAdapter adapter;
    private List<MyMessageBean.DataBean.MapDataBean.MapJsonBean> myMessageBeen = new ArrayList<>();
    private HomePresenter homePresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_message;
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        showWaitDialog();
        homePresenter = new HomePresenter(this);
        homePresenter.myMessage(pageIndex);
        initRecyclerView();
    }

    @Subscribe
    public void udpMessageList(String udpMessageList){
        if(udpMessageList.equals("udpMessageList")){
            homePresenter.myMessage(pageIndex);
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
        mItemDecoration = createItemDecoration(); //获取ItemDecoration对象
        shop_recy.addItemDecoration(mItemDecoration); //添加每个Item之间的间距
        //初始化适配器
        adapter = new MyMessageAdapter(this, myMessageBeen);
        shop_recy.setAdapter(adapter);  //设置适配器
    }


    //每个Item之间的间距
    protected RecyclerView.ItemDecoration createItemDecoration() {
        //颜色  宽度  高度
        return new DefaultItemDecoration(Color.rgb(204, 204, 204), WindowManager.LayoutParams.MATCH_PARENT, 2);
    }


    //item点击事件
    private SwipeItemClickListener itemClickListener = new SwipeItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
            Intent intent = new Intent(MyMessageActivity.this, MessageInfoActivity.class);
            intent.putExtra("id",String.valueOf(myMessageBeen.get(position).getId()));
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
                    homePresenter.myMessage(pageIndex);
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
                    homePresenter.myMessage(pageIndex);
                    sw_refresh.setRefreshing(false);  //停止刷新
                }
            });

        }
    };


    @Override
    public void myMessageSuccess(MyMessageBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("myMessageSuccess==>" + GsonUtil.GsonString(result));
        if (result.getData().getMapData().getMapJson().size() == 0) {
            shop_recy.loadMoreFinish(false, false);
        }
        if (isClear) {
            myMessageBeen.clear();
        }
        myMessageBeen.addAll(result.getData().getMapData().getMapJson());
        adapter.notifyDataSetChanged();
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

    @Override
    public void newsListSuccess(NewsMoreListBean result) {

    }

}
