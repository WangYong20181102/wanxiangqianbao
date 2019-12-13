package com.jh.wxqb.utils;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jh.wxqb.R;

import java.lang.reflect.Field;

/**
 * Created by Mr.Wang on 2019/7/3 17:18.
 */
public class TabLayoutUtils {
    private static TabLayoutUtils tabLayoutUtils;

    /**
     * 单例
     *
     * @return
     */
    public static TabLayoutUtils getInstance() {
        if (tabLayoutUtils == null) {
            tabLayoutUtils = new TabLayoutUtils();
        }
        return tabLayoutUtils;
    }

    /**
     * 改变字体间间距
     * @param tabLayout
     */
    public void reflex(final TabLayout tabLayout, int margin) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        try {
            //拿到tabLayout的mTabStrip属性
            LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

            int dp = DensityUtils.dp2px(tabLayout.getContext(), margin);

            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);

                //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                mTextViewField.setAccessible(true);

                TextView mTextView = (TextView) mTextViewField.get(tabView);

                tabView.setPadding(0, 0, 0, 0);

                //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                int width = 0;
                width = mTextView.getWidth();
                if (width == 0) {
                    mTextView.measure(0, 0);
                    width = mTextView.getMeasuredWidth();
                }

                //设置tab左右间距为  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                params.width = width;
                params.leftMargin = dp;
                params.rightMargin = dp;
                tabView.setLayoutParams(params);

                tabView.invalidate();
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射 改变tab的指示器的长度
     *
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public  void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        tabs.setTabGravity(Gravity.CENTER);
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        assert tabStrip != null;
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
        assert llTab != null;
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.leftMargin = left / 3;
            params.rightMargin = right / 3;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    /**
     * 更改字体样式
     *
     * @param tab
     * @param isSelect
     */
    public void updateTabView(TabLayout.Tab tab, boolean isSelect) {
        assert tab.getCustomView() != null;
        TextView tabSelect = tab.getCustomView().findViewById(R.id.tv_tab_item);
        if (isSelect) {
            //选中加粗
            tabSelect.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tabSelect.setTextColor(Color.parseColor("#333333"));
            tabSelect.setTextSize(25);
        } else {
            tabSelect.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tabSelect.setTextColor(Color.parseColor("#999999"));
            tabSelect.setTextSize(23);
        }
        tabSelect.setText(tab.getText());
    }
}
