package com.jh.wxqb.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gyf.immersionbar.ImmersionBar;
import com.jh.wxqb.R;
import com.jh.wxqb.customview.CustomTitle;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.Toasts;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 项目里面所有Activity的基类
 */
@SuppressWarnings("all")
public abstract class BaseActivity extends MyAutoLayoutActivity {

    //全局等待提示
    protected ProgressDialog waitDialog;
    //自定义标题栏
    private CustomTitle title;
    public Map<String, String> map;
    private Unbinder mUnBinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将当前Activity添加到栈中
        AppManager.getInstance().addActivity(this);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        //初始化Toasts
        Toasts.register(this);
        //设置沉浸式状态栏
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        //初始化
        init();
    }


    protected abstract int getLayout();

    protected abstract void init();

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mUnBinder.unbind();
        //销毁当前Activity
        AppManager.getInstance().finishActivity(this);
    }


    /**
     * 给标题栏添加监听，若不设置，默认只有左上角返回键有响应
     */
    public void setTitleListener(int titleId) {

        //初始化标题栏控件
        title = (CustomTitle) findViewById(titleId);

        //标题栏左边返回键点击事件
        //如果在子界面不重写本方法 则默认关闭当前界面
        title.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toBack();
            }
        });

        //标题栏右边图片点击事情
        title.setMoreImgAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRight();
            }
        });

        //标题栏右边文字点击事件
        title.setMoreTextAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRight();
            }
        });
    }

    /**
     * 点击标题栏右上角
     */
    protected void toRight() {

    }

    /**
     * 点击标题栏左上角
     */
    protected void toBack() {
        StringUtil.Closekeyboard(this);
        finish();
    }

    /**
     * 获取该activity的根布局
     */
    public View getContentView() {
        return findViewById(android.R.id.content).getRootView();
    }

    /**
     * 显示全局等待对话框
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void showWaitDialog() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //判断waitDialog当前是否已经显示
                if (waitDialog == null || !waitDialog.isShowing()) {
                    //设置dialog悬浮风格
                    waitDialog = new ProgressDialog(BaseActivity.this, R.style.dialog);
                    waitDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    //设置为点击外围不消散
                    waitDialog.setCanceledOnTouchOutside(false);
                    //创建等待转圈的view
                    ImageView view = new ImageView(BaseActivity.this);
                    view.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    //设置转圈动画
                    Animation loadAnimation = AnimationUtils.loadAnimation(
                            BaseActivity.this, R.anim.rotate);
                    //动画开始执行
                    view.startAnimation(loadAnimation);
                    loadAnimation.start();
                    view.setImageResource(R.drawable.loading);
                    waitDialog.show();
                    waitDialog.setContentView(view);
                }
            }
        });
    }

    /**
     * 全局等待对话框消散
     */
    public void dismissWaitDialog() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //调用方法时，等待框处于显示状态，则关闭等待框，不处于显示状态，则不做任何操作。
                if (waitDialog != null && waitDialog.isShowing()) {
                    waitDialog.dismiss();
                    waitDialog = null;
                }
            }
        });
    }

    /**
     * 没网的情况下，网络请求失败的提示
     */
    public void netError() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toasts.showShort(R.string.network_connection_timeout);
            }
        });
    }
}
