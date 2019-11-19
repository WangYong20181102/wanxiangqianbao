package com.jh.wxqb.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.math.BigDecimal;

public class MyCanvasView extends View {

    /**
     * 布局宽度
     */
    private int viewWidth;
    private int viewHeight;
    /**
     * 画笔
     */
    private Paint paint;
    private BigDecimal numTotal;
    private int top = 20;
    private int bottom;
    private BigDecimal max = new BigDecimal("5000");
    private int type = 0;
    /**
     * 买入背景色
     */
    private int buyBgColor;
    /**
     * 卖出背景色
     */
    private int sellBgColor;

    public MyCanvasView(Context context) {
        super(context);
        initView();
    }

    public MyCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    public MyCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    private void initView() {
        paint = new Paint();
        //开启抗锯齿
        paint.setAntiAlias(true);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.reset();
        drawBg(canvas);
    }

    /**
     * 画出盘口背景
     *
     * @param canvas canvas
     */
    private void drawBg(Canvas canvas) {
        //max == 0 说明是空数据。 number=null也是空数据
        if (numTotal != null && max.floatValue() != 0) {

            if (numTotal.compareTo(max) > 0) {//大于5000设置背景色块取值等于5000
                numTotal = max;
            }
            switch (type) {
                case 0://买
                    paint.setColor(Color.parseColor("#E9F8F5"));
                    int left = (int) ((new BigDecimal(1).subtract(numTotal.divide(max, 4, BigDecimal.ROUND_DOWN))).floatValue() * (viewWidth));
                    Path path = new Path();
                    path.moveTo(left, viewHeight);
                    path.lineTo(viewWidth, viewHeight);
                    path.lineTo(viewWidth, 0);
                    path.lineTo(left, 0);
                    canvas.drawPath(path, paint);
                    break;
                case 1://卖
                    paint.setColor(Color.parseColor("#FDEBEE"));
                    int right = (int) ((new BigDecimal(1).subtract(numTotal.divide(max, 4, BigDecimal.ROUND_DOWN))).floatValue() * (viewWidth));

                    Path path1 = new Path();
                    path1.moveTo(0, viewHeight);
                    path1.lineTo(viewWidth - right, viewHeight);
                    path1.lineTo(viewWidth - right, 0);
                    path1.lineTo(0, 0);
                    canvas.drawPath(path1, paint);
                    break;
            }

        }

    }

    /**
     * 更新数据
     */
    public void updateBgSize(BigDecimal numTotal, int type) {
        this.numTotal = numTotal;
        this.type = type;
        invalidate();

    }
}
