package com.jh.wxqb.customview;

import android.support.v7.widget.RecyclerView;

public class MyRecycleView extends RecyclerView {
    public MyRecycleView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleView(android.content.Context context) {
        super(context);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
