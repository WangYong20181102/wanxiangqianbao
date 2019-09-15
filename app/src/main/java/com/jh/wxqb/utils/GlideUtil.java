package com.jh.wxqb.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2018/1/22 0022.
 * 图片加载框架
 */

public class GlideUtil {
    public static void show_Img(Context context, Object img_Url, ImageView view) {
        if (img_Url == null || view == null || context == null) {
            return;
        }
        Glide.with(context)  // 表示某个组件需要加载图片
                .load(img_Url)   //加载的图片地址
                .into(view);      // 指定显示在某个ImageView 上面

    }
}
