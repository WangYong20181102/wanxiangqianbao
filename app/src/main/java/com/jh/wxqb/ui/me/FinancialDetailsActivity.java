package com.jh.wxqb.ui.me;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.FinancialDetailsAdapter;
import com.jh.wxqb.adapter.FinancialTypeAdapter;
import com.jh.wxqb.base.BaseActivity;
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
import com.jh.wxqb.utils.MyClicker;
import com.jh.wxqb.utils.Toasts;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 財務明細
 */
public class FinancialDetailsActivity extends BaseActivity implements MeView, MyClicker {

    @BindView(R.id.shop_recy)
    SwipeMenuRecyclerView shop_recy;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    @BindView(R.id.tv_type)
    TextView tvType;

//    protected RecyclerView.ItemDecoration mItemDecoration;  //Item之间的间距
    int pageIndex = 1;
    int type = 1;  //财务类型   	1.全部2.转出3.提币4.转入5.外部转入6.買入7.活动 8.重构9.買入收益10.分享奖励
    int coinTypeId = 0;//币种
    boolean isClear = true;
    private FinancialDetailsAdapter adapter;
    private List<FinancialDetailsBean.DataBean.LogListBean> financialDetailsBeen = new ArrayList<>();
    private List<FinancialDetailsTypeBean.DataBean.AssetTypeListBean> assetTypeListBeen = new ArrayList<>();
    private PopupWindow optionWindow;
    private MePresenter mePresenter;


    @Override
    protected int getLayout() {
        return R.layout.activity_financial_details;
    }

    @Override
    protected void init() {
        coinTypeId = getIntent().getIntExtra("coinTypeId", 0);
        mePresenter = new MePresenter(this);
        mePresenter.financialDetailsType();
        mePresenter.getFinancialDetails(pageIndex, type, coinTypeId);
        initRecyclerView();

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
//        mItemDecoration = createItemDecoration(); //获取ItemDecoration对象
//        shop_recy.addItemDecoration(mItemDecoration); //添加每个Item之间的间距
        //初始化适配器
        adapter = new FinancialDetailsAdapter(this, financialDetailsBeen);
        shop_recy.setAdapter(adapter);  //设置适配器
    }


    //每个Item之间的间距
    protected RecyclerView.ItemDecoration createItemDecoration() {
        //颜色  宽度  高度
        return new DefaultItemDecoration(Color.parseColor("#999999"), WindowManager.LayoutParams.MATCH_PARENT, 1);
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
                    mePresenter.getFinancialDetails(pageIndex, type, coinTypeId);
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
                    mePresenter.getFinancialDetails(pageIndex, type, coinTypeId);
                    sw_refresh.setRefreshing(false);  //停止刷新
                }
            });

        }
    };


    @OnClick({R.id.img_back, R.id.tv_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_type:
                selType(tvType);
                break;
        }
    }

    /**
     * 选择类型
     */
    private void selType(TextView view) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.item_financial_type, null);
        SwipeMenuRecyclerView shopRecy = (SwipeMenuRecyclerView) contentView.findViewById(R.id.shop_recy);
        shopRecy.setLayoutManager(new LinearLayoutManager(this));
        FinancialTypeAdapter financialTypeAdapter = new FinancialTypeAdapter(this, assetTypeListBeen, this);
        shopRecy.setAdapter(financialTypeAdapter);
        optionWindow = new PopupWindow(contentView, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);
        optionWindow.setTouchable(true);
        optionWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        optionWindow.setOutsideTouchable(true);
        optionWindow.showAsDropDown(view, -10, 0);
    }


    @Override
    public void myClick(View view, int type) {
        switch (type) {
            case 0:
                optionWindow.dismiss();
                int position = (int) view.getTag();
                tvType.setText(assetTypeListBeen.get(position).getName());
                this.type = assetTypeListBeen.get(position).getId();
                shop_recy.loadMoreFinish(false, true);
                pageIndex = 1;
                isClear = true;
                mePresenter.getFinancialDetails(pageIndex, this.type, coinTypeId);
                break;
        }
    }

    @Override
    public void financialDetailsTypeSuccess(FinancialDetailsTypeBean result) {
        if (isFinishing()) {
            return;
        }
        LogUtils.e("financialDetailsTypeSuccess==>" + GsonUtil.GsonString(result));
        assetTypeListBeen.addAll(result.getData().getAssetTypeList());
    }

    @Override
    public void getFinancialDetailsSuccess(FinancialDetailsBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("getFinancialDetailsSuccess==>" + GsonUtil.GsonString(result));
        if (result.getData().getLogList().size() == 0) {
            shop_recy.loadMoreFinish(false, false);
        }
        if (isClear) {
            financialDetailsBeen.clear();
        }
        financialDetailsBeen.addAll(result.getData().getLogList());
        adapter.notifyDataSetChanged();
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

    @Override
    public void myTeamSuccess(MyTeamBean result) {

    }

    @Override
    public void selUserImageSuccess(UserImage result) {

    }

    @Override
    public void uploadUserImageSuccess(BaseBean result) {

    }

}
