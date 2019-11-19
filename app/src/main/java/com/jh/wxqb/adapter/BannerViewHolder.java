package com.jh.wxqb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.android.library.YLCircleImageView;
import com.jh.wxqb.R;
import com.zhouwei.mzbanner.holder.MZViewHolder;

public class BannerViewHolder implements MZViewHolder<Integer> {
    private YLCircleImageView mImageView;
    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
        mImageView = view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(Context context, int position, Integer data) {
        // 数据绑定
        mImageView.setImageResource(data);
    }

}
