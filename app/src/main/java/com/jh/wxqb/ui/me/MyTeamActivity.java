package com.jh.wxqb.ui.me;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.MyTeamAdapter;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.FinancialDetailsBean;
import com.jh.wxqb.bean.FinancialDetailsTypeBean;
import com.jh.wxqb.bean.MyTeamBean;
import com.jh.wxqb.bean.UserImage;
import com.jh.wxqb.customview.DefineLoadMoreView;
import com.jh.wxqb.ui.me.presenter.MePresenter;
import com.jh.wxqb.ui.me.view.MeView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.Toasts;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的团队
 */
public class MyTeamActivity extends BaseActivity implements MeView {


    @BindView(R.id.shop_recy)
    SwipeMenuRecyclerView shop_recy;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    @BindView(R.id.tv_number)
    TextView tvNumber;

    int pageIndex = 1;
    boolean isClear = true;
    private MyTeamAdapter adapter;
    private List<MyTeamBean.DataBean.TeamInfoBean> myTeamBeen = new ArrayList<>();
    private MePresenter mePresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_team;
    }

    @Override
    protected void init() {
        showWaitDialog();
        mePresenter = new MePresenter(this);
        mePresenter.myTeam(pageIndex);
        initRecyclerView();
    }

    /**
     * 初始化RecyclerView配置
     */
    public void initRecyclerView() {
        //是否开启加载更多
        shop_recy.loadMoreFinish(false, true);
        //设置布局管理器
        shop_recy.setLayoutManager(new GridLayoutManager(this, 2));
        // 自定义的核心就是DefineLoadMoreView类。
        DefineLoadMoreView loadMoreView = new DefineLoadMoreView(this);
        shop_recy.addFooterView(loadMoreView); // 添加为Footer。
        shop_recy.setLoadMoreView(loadMoreView); // 设置LoadMoreView更新监听。

        //禁止下拉刷新
        sw_refresh.setRefreshing(false);
        sw_refresh.setEnabled(false);

        shop_recy.setLoadMoreListener(mLoadMoreListener);   //上拉加载更多
//        sw_refresh.setOnRefreshListener(mRefreshListener);  //下拉刷新
        //初始化适配器
        adapter = new MyTeamAdapter(this, myTeamBeen);
        shop_recy.setAdapter(adapter);  //设置适配器
    }

    //上拉加载更多
    private SwipeMenuRecyclerView.LoadMoreListener mLoadMoreListener = new SwipeMenuRecyclerView.LoadMoreListener() {
        @Override
        public void onLoadMore() {
            shop_recy.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isClear = false;
                    pageIndex = pageIndex + 1;
                    mePresenter.myTeam(pageIndex);
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
                    mePresenter.myTeam(pageIndex);
                    sw_refresh.setRefreshing(false);  //停止刷新
                }
            });

        }
    };


    @Override
    public void myTeamSuccess(MyTeamBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("myTeamSuccess==>" + GsonUtil.GsonString(result));
        if (result.getData().getTeamInfo().size() == 0) {
            shop_recy.loadMoreFinish(false, false);
        }
        if (result.getData() != null) {
            if (result.getData().getTeamPeples() != null && !result.getData().getTeamPeples().equals("")) {
                switch (MyApplication.getLanuage()) {
                    case "zh":
                        tvNumber.setText(result.getData().getTeamPeples());
                        break;
                    case "en":
                        tvNumber.setText(result.getData().getTeamPeples());
                        break;
                }
            } else {
                switch (MyApplication.getLanuage()) {
                    case "zh":
                        tvNumber.setText("0");
                        break;
                    case "en":
                        tvNumber.setText("0");
                        break;
                }
            }
        } else {
            switch (MyApplication.getLanuage()) {
                case "zh":
                    tvNumber.setText("0");
                    break;
                case "en":
                    tvNumber.setText("0");
                    break;
            }
        }

        if (isClear) {
            myTeamBeen.clear();
        }
        myTeamBeen.addAll(result.getData().getTeamInfo());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void selUserImageSuccess(UserImage result) {

    }

    @Override
    public void uploadUserImageSuccess(BaseBean result) {

    }

    @Override
    public void financialDetailsTypeSuccess(FinancialDetailsTypeBean result) {

    }

    @Override
    public void getFinancialDetailsSuccess(FinancialDetailsBean result) {

    }

    @Override
    public void getEmailCodeSuccess(BaseBean result) {

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


    @Override
    public void udpLoginPwdSuccess(BaseBean result) {

    }

    @Override
    public void bandingEmailSuccess(BaseBean result) {

    }
}
