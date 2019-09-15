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
import com.jh.wxqb.utils.Toasts;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * 我的消息详情
 */
public class MessageInfoActivity extends BaseActivity implements HomeView {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private HomePresenter homePresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_message_info;
    }

    @Override
    protected void init() {
        showWaitDialog();
        homePresenter = new HomePresenter(this);
        String id = getIntent().getStringExtra("id");
        homePresenter.myMessageDetails(id);
    }

    @Override
    public void myMessageDetailsSuccess(MessageDetailsBean result) {
        if (isFinishing()) {
            return;
        }
        LogUtils.e("myMessageDetailsSuccess==>" + GsonUtil.GsonString(result));
        dismissWaitDialog();
        if (result.getData() != null) {
            if (result.getData().getSysMessage() != null) {
                tvTitle.setText(result.getData().getSysMessage().getTitle());
                tvTime.setText(result.getData().getSysMessage().getCreateTime());
                tvContent.setText(result.getData().getSysMessage().getContent());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post("udpMessageList");
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

    @Override
    public void newsListSuccess(NewsMoreListBean result) {

    }

    @Override
    public void myMessageSuccess(MyMessageBean result) {

    }
}
