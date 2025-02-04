package com.jh.wxqb.ui.me;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.base.BaseFragment;
import com.jh.wxqb.base.MainActivity;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.FinancialDetailsBean;
import com.jh.wxqb.bean.FinancialDetailsTypeBean;
import com.jh.wxqb.bean.MyTeamBean;
import com.jh.wxqb.bean.UserImage;
import com.jh.wxqb.customview.CircleImageView;
import com.jh.wxqb.ui.home.MyMessageActivity;
import com.jh.wxqb.ui.me.feedback.FeedbackActivity;
import com.jh.wxqb.ui.me.mysendletter.MySendLetterActivity;
import com.jh.wxqb.ui.me.presenter.MePresenter;
import com.jh.wxqb.ui.me.view.MeView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.BitmapUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.PreferencesLoader;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.Toasts;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心
 */
public class MeFragment extends BaseFragment implements View.OnClickListener, MeView {

    @BindView(R.id.iv_user_img)
    ImageView ivUserImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    private View view;
    private PopupWindow contentWindow;
//    private MePresenter mePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_me, container, false);
            ButterKnife.bind(this, view);
            EventBus.getDefault().register(this);
//            mePresenter = new MePresenter(this);
        }
        return view;
    }

    @OnClick({R.id.ll_message, R.id.iv_user_img, R.id.ll_van_nex_mall,R.id.ll_userinfo, R.id.ll_my_dividend, R.id.ll_my_recommendation, R.id.ll_all_send_car, R.id.ll_login_pwd, R.id.ll_pay_pwd, R.id.ll_binding_account, R.id.ll_my_team, R.id.ll_user_feedback, R.id.ll_financial_details, R.id.tv_out_login})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_message://我的消息
//                intent = new Intent(mContext, MyMessageActivity.class);
//                startActivity(intent);
                break;
            case R.id.iv_user_img:
//                intent = new Intent(mContext, ChangeImageActivity.class);
//                startActivity(intent);
                break;
            case R.id.ll_userinfo://个人信息
                intent = new Intent(mContext, UserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_my_dividend://當前委托
                intent = new Intent(mContext, MySendLetterActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_my_recommendation://我要推荐
                intent = new Intent(mContext, RecommendActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_all_send_car://全民发车
                intent = new Intent(mContext, NationalDepartureActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_van_nex_mall://van nex mall
//                Toasts.showShort("暂未开放此功能！");
                intent = new Intent(mContext, VanNexMallActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_login_pwd://登录密码
                intent = new Intent(mContext, UdpPwdActivity.class);
                intent.putExtra("type", "login");
                startActivity(intent);
                break;
            case R.id.ll_pay_pwd://支付密码
                intent = new Intent(mContext, UdpPwdActivity.class);
                intent.putExtra("type", "pay");
                startActivity(intent);
                break;
            case R.id.ll_binding_account://绑定账户
                intent = new Intent(mContext, BindingAccountActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_my_team://我的团队
                intent = new Intent(mContext, MyTeamActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_user_feedback://用户反馈
                intent = new Intent(mContext, FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_financial_details:
                intent = new Intent(mContext, FinancialDetailsActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_out_login:
                outApp();
                break;
        }
    }

    /**
     * 退出APP
     */
    @SuppressLint("WrongConstant")
    private void outApp() {
        //转换设置布局文件
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_out_app, null);
        TextView tvCancel = contentView.findViewById(R.id.tv_cancel);
        TextView tvDetermine = contentView.findViewById(R.id.tv_determine);
        ImageView imageClose = contentView.findViewById(R.id.image_close);
        RelativeLayout rlDialogBgClick = contentView.findViewById(R.id.rl_dialog_bg_click);
        LinearLayout llDialogBg = contentView.findViewById(R.id.ll_dialog_bg);
        llDialogBg.setOnClickListener(null);
        imageClose.setOnClickListener(this);
        rlDialogBgClick.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvDetermine.setOnClickListener(this);
        //创建PopupWindow对象
        contentWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        contentWindow.setTouchable(true);   //允许点击
        contentWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));   //设置背景
        contentWindow.setOutsideTouchable(false);     //点击其他区域消失
        contentWindow.setFocusable(true);
        contentWindow.setAnimationStyle(android.R.style.Animation_InputMethod);   //设置动画

        //获取当前Activity所在的窗体
        Window dialogWindow = getActivity().getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.alpha = 0.5f;
        //设置宽
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置高
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);

        contentWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);

        contentWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        contentWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        contentWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);   //从底部弹出
        contentWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
            case R.id.image_close:
            case R.id.rl_dialog_bg_click:
                contentWindow.dismiss();
                break;
            case R.id.tv_determine:
                contentWindow.dismiss();
                AgainLoginUtil.againLogin(mContext, 9009);
                break;
        }
    }


    @Subscribe
    public void udpMe(String udpMe) {
        switch (udpMe) {
            case "udpMe":
                if (MyApplication.getUserBean() != null) {
                    tvName.setText(MyApplication.getUserBean().getUserName());
                    tvPhone.setText(StringUtil.makePhoneNum(MyApplication.getUserBean().getPhone()));
                }
//                mePresenter.selUserImage();
                break;
//            case "udpUserImage":
//                mePresenter.selUserImage();
//                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void selUserImageSuccess(UserImage result) {
        if (((MainActivity) mContext).isFinishing()) {
            return;
        }
        LogUtils.e("selUserImageSuccess==>" + GsonUtil.GsonString(result));

        if (result.getData().getMeImg() != null && !result.getData().getMeImg().equals("")) {
            MyApplication.setUserImg(null);
            MyApplication.setUserImg(result.getData().getMeImg());
        }
//        if (PreferencesLoader.getObject(CoreKeys.USER_IMG, String.class) != null) {
//            ivUserImg.setImageBitmap(BitmapUtil.compressImage(BitmapUtil.ratio(BitmapUtil.stringtoBitmap(MyApplication.getUserImg()), 130, 130)));
//        }
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
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    @Override
    public void onViewFailureString(int statue, String message) {
        if (((MainActivity) mContext).isFinishing()) {
            return;
        }
        ((MainActivity) mContext).dismissWaitDialog();
        AgainLoginUtil.againLogin(mContext, statue);
        Toasts.showShort(message);
    }

    @Override
    public void onServerFailure(String e, int code) {
        if (((MainActivity) mContext).isFinishing()) {
            return;
        }
        ((MainActivity) mContext).dismissWaitDialog();
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

}
