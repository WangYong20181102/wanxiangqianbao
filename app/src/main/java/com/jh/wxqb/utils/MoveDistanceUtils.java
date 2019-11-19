package com.jh.wxqb.utils;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Mr.Wang on 2019/3/21 10:04.
 */
public class MoveDistanceUtils {
    private float downX;    //按下时 的X坐标
    private float downY;    //按下时 的Y坐标
    private boolean b = true;

    public void getMoveDistance(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //继承了Activity的onTouchEvent方法，直接监听点击事件
                float x = event.getX();
                float y = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //将按下时的坐标存储
//                        downX = x;
//                        downY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (b){
                            b = false;
                            downX = x;
                            downY = y;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        b = true;
                        //获取到距离差
                        float dx = x - downX;
                        float dy = y - downY;
                        //防止是按下也判断
                        if (Math.abs(dx) > 8 && Math.abs(dy) > 8) {
                            //通过距离差判断方向
                            int orientation = getOrientation(dx, dy);
                            switch (orientation) {
                                case 'r':
                                case 'l':
                                case 'b':
                                    if (onMoveDistanceListener != null) {
                                        onMoveDistanceListener.onMoveDistance(false);
                                    }
                                    break;
                                case 't':
                                    if (onMoveDistanceListener != null) {
                                        onMoveDistanceListener.onMoveDistance(true);
                                    }
                                    break;
                            }
                        }
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 根据距离差判断 滑动方向
     *
     * @param dx X轴的距离差
     * @param dy Y轴的距离差
     * @return 滑动的方向
     */
    private int getOrientation(float dx, float dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            //X轴移动
            return dx > 0 ? 'r' : 'l';
        } else {
            //Y轴移动
            return dy > 0 ? 'b' : 't';
        }
    }

    private OnMoveDistanceListener onMoveDistanceListener = null;

    public void setOnMoveDistanceListener(View view, OnMoveDistanceListener onMoveDistanceListener) {
        this.onMoveDistanceListener = onMoveDistanceListener;
        getMoveDistance(view);
    }

    public interface OnMoveDistanceListener {
        void onMoveDistance(boolean b);
    }
}
