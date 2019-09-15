package com.jh.wxqb.ui.home;

import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.bean.MessageDetailsBean;
import com.jh.wxqb.bean.MyMessageBean;
import com.jh.wxqb.bean.NewsInfoBean;
import com.jh.wxqb.bean.NewsMoreListBean;
import com.jh.wxqb.bean.UserBean;
import com.jh.wxqb.bean.VersionUpdateBean;
import com.jh.wxqb.ui.home.presenter.HomePresenter;
import com.jh.wxqb.ui.home.view.HomeView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.TimeUtil;
import com.jh.wxqb.utils.Toasts;

import butterknife.BindView;

/**
 * 新闻资讯详情
 */
public class NewsInfoActivity extends BaseActivity implements HomeView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected int getLayout() {
        return R.layout.activity_news_info;
    }

    @Override
    protected void init() {
        HomePresenter homePresenter = new HomePresenter(this);
        String id = getIntent().getStringExtra("id");
        LogUtils.e("新闻详情ID==>" + id);
        showWaitDialog();
        homePresenter.newsInfo(id);
    }

    @Override
    public void newsInfoSuccess(NewsInfoBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("newsInfoSuccess==>" + GsonUtil.GsonString(result));
        if (result.getData() != null) {
            if (result.getData().getNoticeContent() != null) {
                tvTitle.setText(result.getData().getNoticeContent().getTitle());
                tvTime.setText(TimeUtil.getTime(result.getData().getNoticeContent().getCreateTime()));
                tvContent.setText(result.getData().getNoticeContent().getContent());
            }
        }
    }

    @Override
    public void newsListSuccess(NewsMoreListBean result) {

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
}
