package com.jh.wxqb.customview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.jh.wxqb.utils.ScreenUtils;

/**
 * 状态栏
 * 
 * @author Myf
 * 
 */
public class StatusBarView extends View {

    int mScreenWidth;
    int mStatusHeight;
    
    public StatusBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public StatusBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        if(isInEditMode()) {
            return;
        }
        
        mScreenWidth = ScreenUtils.getScreenWidth(context);
        mStatusHeight = ScreenUtils.getStatusHeight(context);
        
        if (!isInEditMode()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setVisibility(View.VISIBLE);
            } else {
                setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mScreenWidth, mStatusHeight);
    }

}
