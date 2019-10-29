package com.jh.wxqb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeViewPagerAdapter extends PagerAdapter {
    private List<Integer> images;
    private Context context;

    public HomeViewPagerAdapter(Context context, List<Integer> images) {
        this.images = images;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     * 初始化一个条目
     *
     * @param container
     * @param position  当前需要加载条目的索引
     * @return
     */
    @NotNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);

        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        int currentPage = position % images.size();
        imageView.setImageResource(images.get(currentPage));
        container.addView(imageView);
        return imageView;
    }

    /**
     * 销毁一个条目
     * position 就是当前需要被销毁的条目的索引
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 把ImageView从ViewPager中移除掉
        container.removeView((View) object);

    }

}
