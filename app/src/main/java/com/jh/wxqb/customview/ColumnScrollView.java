package com.jh.wxqb.customview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class ColumnScrollView extends HorizontalScrollView {
    /**
     * 传入整体布局
     */
    private View ll_content;
    /**
     * 屏幕宽度
     */
    private int mScreenWitdh = 0;
    /**
     * 父类的活动activity
     */
    private Activity activity;

    public ColumnScrollView(Context context) {
        super(context);
    }

    public ColumnScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColumnScrollView(Context context, AttributeSet attrs,
                            int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 在拖动的时候执行
     */
    @Override
    protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        // TODO Auto-generated method stub
        super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
        shade_ShowOrHide();
    }

    /**
     * 传入父类布局中的资源文件
     */
    public void setParam(Activity activity, int mScreenWitdh, View paramView1) {
        this.activity = activity;
        this.mScreenWitdh = mScreenWitdh;
        ll_content = paramView1;
    }

    /**
     * 判断左右阴影的显示隐藏效果
     */
    public void shade_ShowOrHide() {
        if (!activity.isFinishing() && ll_content != null) {
            measure(0, 0);
        }
    }
}
