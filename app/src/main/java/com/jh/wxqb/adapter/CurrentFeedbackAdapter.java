package com.jh.wxqb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.FeedbackInfoBean;
import com.jh.wxqb.utils.BitmapUtil;
import com.jh.wxqb.utils.MyClicker;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 当前反馈图片内容
 */
public class CurrentFeedbackAdapter extends BaseAdapter {
    private static final String TAG = "GridViewAdapter";
    private List<FeedbackInfoBean.DataBean.MessageBean.FileListBean> imgList;
    private Context context;
    private final LayoutInflater mInflater;
    private MyClicker myClicker;

    public CurrentFeedbackAdapter(Context context, List<FeedbackInfoBean.DataBean.MessageBean.FileListBean> imgList) {
        this.context = context;
        this.imgList = imgList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imgList.size();
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
        convertView = mInflater.inflate(R.layout.item_current_feedback, parent, false);
        AutoUtils.autoSize(convertView);
        ImageView iv_item = (ImageView) convertView.findViewById(R.id.pic_iv);
        iv_item.setImageBitmap(BitmapUtil.compressImage(BitmapUtil.ratio(BitmapUtil.stringtoBitmap(imgList.get(position).getFile()),110,110)));
//        GlideUtil.show_Img(context, BitmapUtil.compressImage(BitmapUtil.ratio(BitmapUtil.stringtoBitmap(imgList.get(position).getFile()),110,110)), iv_item);
        return convertView;
    }


}
