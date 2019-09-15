package com.jh.wxqb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jh.wxqb.R;
import com.jh.wxqb.base.CoreKeys;
import com.jh.wxqb.utils.GlideUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.MyClicker;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 晒单评价适配器
 */
public class GridViewAdapter extends BaseAdapter implements  View.OnClickListener {
    private static final String TAG = "GridViewAdapter";
    private List<String> imgList;
    private Context context;
    private final LayoutInflater mInflater;
    private MyClicker myClicker;

    public GridViewAdapter(Context context, List<String> imgList, MyClicker myClicker) {
        this.context = context;
        this.imgList = imgList;
        mInflater = LayoutInflater.from(context);
        this.myClicker=myClicker;
    }

    @Override
    public int getCount() {
        if (imgList.size() == 0) {
            return 0;
        } else if (imgList.size() < CoreKeys.MAX_SELECT_PIV_NUM) {
            return imgList.size() + 1;
        } else {
            return 3;
        }
    }

    @Override
    public Object getItem(int position) {
        return imgList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogUtils.e("position===> " + position + "当前集合大小===>" + imgList.size());
        convertView = mInflater.inflate(R.layout.grid_item, parent, false);
        AutoUtils.autoSize(convertView);
        ImageView iv_item = (ImageView) convertView.findViewById(R.id.pic_iv);
        ImageView del_add_img = (ImageView) convertView.findViewById(R.id.del_add_img);
        del_add_img.setOnClickListener(this);
        del_add_img.setTag(position);
        if (imgList.size() > 0 && imgList.size() < CoreKeys.MAX_SELECT_PIV_NUM) {
            if (position == imgList.size()) {
                del_add_img.setVisibility(View.GONE);
            } else {
                String picUrl = imgList.get(position);
                GlideUtil.show_Img(context, picUrl, iv_item);
                del_add_img.setVisibility(View.VISIBLE);
            }
        } else if (imgList.size() == CoreKeys.MAX_SELECT_PIV_NUM) {
            String picUrl = imgList.get(position);
            GlideUtil.show_Img(context, picUrl, iv_item);
            del_add_img.setVisibility(View.VISIBLE);
        }
        return convertView;
    }


    @Override
    public void onClick(View v) {
        myClicker.myClick(v,0);
    }
}
