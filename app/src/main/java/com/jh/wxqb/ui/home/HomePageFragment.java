package com.jh.wxqb.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.HomePageAdapter;
import com.jh.wxqb.adapter.HomeViewPagerAdapter;
import com.jh.wxqb.base.BaseFragment;
import com.jh.wxqb.base.KChatLineBase;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.base.NewsBean;
import com.jh.wxqb.bean.MarketDividendBottomBean;
import com.jh.wxqb.bean.UserBean;
import com.jh.wxqb.bean.VersionUpdateBean;
import com.jh.wxqb.customview.AppUpdateProgressDialog;
import com.jh.wxqb.ui.home.presenter.HomePagePresenter;
import com.jh.wxqb.ui.home.view.HomePagerView;
import com.jh.wxqb.utils.DensityUtils;
import com.jh.wxqb.utils.DownloadService;
import com.jh.wxqb.utils.GetEditionCode;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.SystemUtils;
import com.jh.wxqb.utils.Toasts;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomePageFragment extends BaseFragment implements HomePagerView, ViewPager.OnPageChangeListener {
    @BindView(R.id.ll_message)
    RelativeLayout llMessage;
    @BindView(R.id.swipe_recycle_view)
    SwipeMenuRecyclerView swipeRecycleView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.decorate_com_appbar)
    AppBarLayout decorateComAppbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.ll_points)
    LinearLayout llPoint;
    private int mMaxProgress = 100;//百分比
    private Intent download;
    public static AppUpdateProgressDialog dialog;
    Unbinder unbinder;
    private HomePagePresenter homePagePresenter;
    private HomePageAdapter adapter;
    //轮播图图片
    private List<Integer> mImageList;
    //新闻
    private NewsBean newsBeanList;
    //最近成交
    private MarketDividendBottomBean marketDividendBottomBean;
    private int[] images = {R.mipmap.banner4, R.mipmap.banner1, R.mipmap.banner2, R.mipmap.banner3};
    private Timer timer;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    break;
                case 100://更新
                    dialog.setProgress(DownloadService.progress);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, view);
        homePagePresenter = new HomePagePresenter(this);
        initData();
        initRecyclerView();
        initHttpRequest();
        homePagePresenter.getUserInfo();
        homePagePresenter.versionUpdate();
        return view;
    }

    /**
     * 初始化dot
     */
    private void initDots() {
        llPoint.removeAllViews();
        for (int i = 0; i < mImageList.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            // 设置小圆点imageview的参数
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtils.dp2px(mContext, 9), DensityUtils.dp2px(mContext, 4));
            layoutParams.rightMargin = DensityUtils.dp2px(mContext, 5);
            layoutParams.leftMargin = DensityUtils.dp2px(mContext, 5);
            imageView.setLayoutParams(layoutParams);

            imageView.setBackgroundResource(R.drawable.home_page_dot_state);
            llPoint.addView(imageView);
        }
        updateDots();
    }


    /**
     * 更新点
     */
    private void updateDots() {
        if (mImageList.size() == 0) {
            return;
        }
        int currentPage = viewPager.getCurrentItem() % mImageList.size();
        for (int i = 0; i < llPoint.getChildCount(); i++) {
            llPoint.getChildAt(i).setEnabled(i == currentPage);// 设置setEnabled为true的话
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mImageList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            mImageList.add(images[i]);
        }

        initDots();
        initViewPager();
    }

    private void initViewPager() {
        viewPager.addOnPageChangeListener(this);
        HomeViewPagerAdapter viewPagerAdapter = new HomeViewPagerAdapter(mContext, mImageList);
        viewPager.setAdapter(viewPagerAdapter);
        startTimer();
    }

    @Subscribe
    public void udpHome(String udpHome) {
        switch (udpHome) {
            case "udpHome":
                homePagePresenter.getUserInfo();
                break;
            case "udpHomeWenView":
                break;
            case "homePauseTimer"://暂停轮播
                onPause();
                break;
            case "homeResumeTimer"://开始轮播
                onResume();
                break;
        }
    }

    /**
     * 初始化网络请求
     */
    private void initHttpRequest() {
        //最近成交数据获取
        homePagePresenter.dividendMarketTopDividend(1, 1, 0);
        //新闻列表
        homePagePresenter.getNews(2);
    }

    /**
     * 初始化RecyclerView配置
     */
    public void initRecyclerView() {
        //是否开启加载更多
        swipeRecycleView.loadMoreFinish(false, false);
        //设置布局管理器
        swipeRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        //初始化适配器
        swipeRecycleView.setFocusableInTouchMode(false);
        swipeRecycleView.setItemAnimator(null);
        swipeRefresh.setOnRefreshListener(mRefreshListener);  //下拉刷新
        //下拉冲突
        decorateComAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    //可以刷新
                    swipeRefresh.setEnabled(true);
                } else {
                    swipeRefresh.setEnabled(false);
                }
            }
        });
        //初始化适配器，没网情况下有页面展示
        adapter = new HomePageAdapter(getContext(), newsBeanList, marketDividendBottomBean);
        swipeRecycleView.setAdapter(adapter);

    }

    //下拉刷新
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            initHttpRequest();
            swipeRefresh.setRefreshing(false);

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
        stopTimer();
        unbinder.unbind();
        boolean serviceRunning = SystemUtils.isServiceRunning(getContext(), DownloadService.class.getName());
        //取消下载
        if (serviceRunning) {
            mContext.stopService(download);
        }
        EventBus.getDefault().unregister(this);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void myNewMessageSuccess(NewsBean result) {
        newsBeanList = result;
        if (adapter == null) {
            adapter = new HomePageAdapter(getContext(), newsBeanList, marketDividendBottomBean);
            swipeRecycleView.setAdapter(adapter);
        } else {
            adapter.UpHomeData(newsBeanList, marketDividendBottomBean);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void myKLineSuccess(KChatLineBase result) {

    }

    @Override
    public void myRecentTransactionSuccess(MarketDividendBottomBean result) {
        marketDividendBottomBean = result;
        if (adapter == null) {
            adapter = new HomePageAdapter(getContext(), newsBeanList, marketDividendBottomBean);
            swipeRecycleView.setAdapter(adapter);
        } else {
            adapter.UpHomeData(newsBeanList, marketDividendBottomBean);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getUserInfoSuccess(UserBean result) {
        if (result.getData() != null) {
            MyApplication.setUserBean(null);
            MyApplication.setUserBean(result.getData().getInfo());
            LogUtils.e("本地用户信息==>" + GsonUtil.GsonString(MyApplication.getUserBean()));
            EventBus.getDefault().post("udpMe");
            EventBus.getDefault().post("udpTurnOut");
            EventBus.getDefault().post("udpPurchaseOrder");
        }
    }

    @Override
    public void versionUpdateSuccess(VersionUpdateBean result) {
        //版本升级
        upDataApp(result);
    }

    @Override
    public void onViewFailureString(int statue, String message) {
        Toasts.showShort(message);
    }

    @Override
    public void onServerFailure(String e, int code) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //轮播图点选中状态更新
        updateDots();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 版本更新
     */
    private void upDataApp(VersionUpdateBean result) {
        String versionNameCode = GetEditionCode.getVersionNameCode(mContext);
        LogUtils.e("本地版本号versionNameCode==>" + versionNameCode);
        LogUtils.e("服务端版本号versionNameCode==>" + result.getData().getApp().getVers());
        BigDecimal localOverCode = new BigDecimal(Double.parseDouble(versionNameCode));
        BigDecimal serverVer = new BigDecimal(Double.valueOf(result.getData().getApp().getVers()));
        int comparisonResult = localOverCode.compareTo(serverVer);
        LogUtils.e("比较结果==>" + comparisonResult);
        if (comparisonResult < 0) {
            //进入更新弹窗
            switch (result.getData().getApp().getVersion()) {
                case 1: //非重要版本
                    updAppDialog(mContext, result.getData().getApp().getComment(), false, result.getData().getApp().getUrl(), String.valueOf(result.getData().getApp().getVers()));
                    break;
                case 2:  //重要版本
                    updAppDialog(mContext, result.getData().getApp().getComment(), true, result.getData().getApp().getUrl(), String.valueOf(result.getData().getApp().getVers()));
                    break;
            }
        }
    }

    /**
     * 更新APP弹窗
     */
    public void updAppDialog(Context context, String content, final boolean isShow, final String url, String version) {
        //暂停轮播图
        onPause();

        dialog = new AppUpdateProgressDialog(context, content, isShow, version) {
            @Override
            public void udp() {
                super.udp();
                LogUtils.e("url=======>" + url);
                DownloadService.isShow = isShow;
                if (isShow) {
                    download = new Intent(mContext, DownloadService.class);
                    download.putExtra("URL", url);
                    mContext.startService(download);
                } else {
                    download = new Intent(mContext, DownloadService.class);
                    download.putExtra("URL", url);
                    mContext.startService(download);
                }
                showDialog();
            }

            @Override
            public void close() {
                super.close();
                //开启轮播
                onResume();
                homePagePresenter.getUserInfo();
            }
        };
        dialog.show();
    }

    /**
     * 更新弹窗
     */
    private void showDialog() {

        //正在下载时，不可关闭dialog
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    Toasts.showShort("正在下载,请稍后...");
                    return true;
                } else {
                    return false;
                }
            }
        });
        dialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (DownloadService.progress < mMaxProgress) {
                    Message msg = handler.obtainMessage();
                    msg.what = 100;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }

    @OnClick(R.id.ll_message)
    public void onViewClicked() {
        Intent intent = new Intent(mContext, MyMessageActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        startTimer();
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            }
        }, 1000, 2000);
    }
}
