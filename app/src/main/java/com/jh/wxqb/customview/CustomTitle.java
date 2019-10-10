package com.jh.wxqb.customview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.utils.StringUtil;
import com.zhy.autolayout.AutoRelativeLayout;


/**
 * 标题控件
 */
public class CustomTitle extends AutoRelativeLayout {

    private int tittleBg;
    private String titleText;
    private boolean canBack;
    private String backText = "";
    private String moreText = "";
    private int moreImg;
    private TextView tvMore;

    public CustomTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_title, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomTitle, 0, 0);
        try {
            titleText = ta.getString(R.styleable.CustomTitle_titleText);
            canBack = ta.getBoolean(R.styleable.CustomTitle_canBack, false);
            backText = ta.getString(R.styleable.CustomTitle_backText);
            moreImg = ta.getResourceId(R.styleable.CustomTitle_moreImg, 0);
            moreText = ta.getString(R.styleable.CustomTitle_moreText);
            tittleBg = ta.getColor(R.styleable.CustomTitle_tittle_bg, Color.WHITE);
            setUpView();
        } finally {
            ta.recycle();
        }
    }

    private void setUpView() {
        TextView tvTitle = (TextView) findViewById(R.id.title);
        RelativeLayout rlTittle = findViewById(R.id.rl_title);
        tvTitle.setText(titleText);
        LinearLayout backBtn = (LinearLayout) findViewById(R.id.title_back);
        backBtn.setVisibility(canBack ? VISIBLE : INVISIBLE);
        TextView tvBack = (TextView) findViewById(R.id.txt_back);
        if (canBack) {
            if (backText != null && !backText.equals("")) {
                tvBack.setVisibility(VISIBLE);
                tvBack.setText(backText);
            }
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringUtil.Closekeyboard((Activity) getContext());
                    ((Activity) getContext()).finish();
                }
            });
        }
        ImageView moreImgView = (ImageView) findViewById(R.id.img_more);
        if (moreImg != 0) {
            moreImgView.setImageDrawable(getContext().getResources().getDrawable(moreImg));
        }
        tvMore = (TextView) findViewById(R.id.txt_more);
        if (moreText != null && !moreText.equals("")) {
            tvMore.setVisibility(VISIBLE);
            tvMore.setText(moreText);
        }
        rlTittle.setBackgroundColor(tittleBg);
    }


    /**
     * 标题控件
     *
     * @param titleText 设置标题文案
     */
    public void setTitleText(String titleText) {
        this.titleText = titleText;
        TextView tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(titleText);
    }

    /**
     * 标题控件
     *
     * @param titleText 设置标题文案
     */
    public void setTitleText(int titleText) {
        TextView tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(titleText);
    }

    /**
     * 标题更多按钮
     *
     * @param img 设置更多按钮
     */
    public void setMoreImg(int img) {
        moreImg = img;
        ImageView moreImgView = (ImageView) findViewById(R.id.img_more);
        if (moreImg != 0) {
            moreImgView.setVisibility(View.VISIBLE);
            moreImgView.setImageDrawable(getContext().getResources().getDrawable(moreImg));
        } else {
            moreImgView.setVisibility(View.GONE);
        }
    }


    /**
     * 设置更多按钮事件
     *
     * @param listener 事件监听
     */
    public void setMoreImgAction(View.OnClickListener listener) {
        ImageView moreImgView = (ImageView) findViewById(R.id.img_more);
        moreImgView.setOnClickListener(listener);
    }


    /**
     * 设置更多按钮事件
     *
     * @param listener 事件监听
     */
    public void setMoreTextAction(View.OnClickListener listener) {
        tvMore.setOnClickListener(listener);
    }


    /**
     * 设置更多文字内容
     *
     * @param text 更多文本
     */
    public void setMoreTextContext(String text) {
        tvMore.setVisibility(VISIBLE);
        tvMore.setText(text);
    }

    public void setMoreTextColor(int color) {
        tvMore.setTextColor(color);
    }

    /**
     * 设置返回按钮事件
     *
     * @param listener 事件监听
     */
    public void setBackListener(View.OnClickListener listener) {
        if (canBack) {
            LinearLayout backBtn = (LinearLayout) findViewById(R.id.title_back);
            backBtn.setOnClickListener(listener);
        }
    }

}
