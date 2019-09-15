package com.jh.wxqb.ui.me.feedback;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.CurrentFeedbackAdapter;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.FeedbackInfoBean;
import com.jh.wxqb.bean.FeedbackListBean;
import com.jh.wxqb.customview.CircleImageView;
import com.jh.wxqb.customview.NoScrollGridView;
import com.jh.wxqb.ui.me.feedback.presenter.FeedBackPresenter;
import com.jh.wxqb.ui.me.feedback.view.FeedBackView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.BitmapUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.TimeUtil;
import com.jh.wxqb.utils.Toasts;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 反馈内容
 */
public class FeedbackInfoActivity extends BaseActivity implements FeedBackView {

    @BindView(R.id.iv_user_img)
    CircleImageView ivUserImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.gridView)
    NoScrollGridView mGridView;
    @BindView(R.id.tv_square)
    TextView tvSquare;
    @BindView(R.id.tv_reply)
    TextView tvReply;
    @BindView(R.id.tv_time)
    TextView tvTime;
    protected RecyclerView.ItemDecoration mItemDecoration;  //Item之间的间距
    int pageIndex = 1;
    boolean isClear = true;
    private CurrentFeedbackAdapter feedbackInfoAdapter;
    private List<FeedbackInfoBean.DataBean.MessageBean.FileListBean> list = new ArrayList<>();
    private FeedBackPresenter feedBackPresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_feedback_info;
    }

    @Override
    protected void init() {
        showWaitDialog();
        feedBackPresenter = new FeedBackPresenter(this);
        String id = getIntent().getStringExtra("id");
        feedBackPresenter.queryUserFeedback(id);
        initGridView();
        initView();
    }

    private void initView() {
        if (MyApplication.getUserImg() != null) {
            ivUserImg.setImageBitmap(BitmapUtil.compressImage(BitmapUtil.ratio(BitmapUtil.stringtoBitmap(MyApplication.getUserImg()), 90, 90)));
        }
    }

    /**
     * 初始化GridView
     */
    private void initGridView() {
        feedbackInfoAdapter = new CurrentFeedbackAdapter(FeedbackInfoActivity.this, list);
        mGridView.setAdapter(feedbackInfoAdapter);
    }

    @Override
    public void queryUserFeedbackSuccess(FeedbackInfoBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("queryUserFeedbackSuccess==>" + GsonUtil.GsonString(result));
        if (result.getData().getMessage() != null) {
            tvName.setText(result.getData().getMessage().getName());
            tvContent.setText(result.getData().getMessage().getMemo());
            tvReply.setText(result.getData().getMessage().getFeedback());
            tvTime.setText(TimeUtil.getTime(result.getData().getMessage().getCreateDate()));
            list.addAll(result.getData().getMessage().getFileList());
            feedbackInfoAdapter.notifyDataSetChanged();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post("udpFeedbackList");
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
    public void saveUserFeedbackSuccess(BaseBean result) {

    }

    @Override
    public void userFeedbackListSuccess(FeedbackListBean result) {

    }
}
