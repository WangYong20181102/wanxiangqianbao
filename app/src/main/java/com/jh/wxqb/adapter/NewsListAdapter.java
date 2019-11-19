package com.jh.wxqb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.NewsMoreListBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/11 0011.
 * 资讯列表
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {


    private Context mContext;
    private List<NewsMoreListBean.DataBean.NoticeMapBean> newsListBeen;


    public NewsListAdapter(Context mContext, List<NewsMoreListBean.DataBean.NoticeMapBean> newsListBeen) {
        this.mContext = mContext;
        this.newsListBeen = newsListBeen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置自适应布局
        AutoUtils.autoSize(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newslist, null));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newslist, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(newsListBeen.get(position));
        holder.refurbish(position);
    }

    @Override
    public int getItemCount() {
        return newsListBeen.size() > 0 ? newsListBeen.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        NewsMoreListBean.DataBean.NoticeMapBean item;

        public void setItem(NewsMoreListBean.DataBean.NoticeMapBean item) {
            this.item = item;
        }

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void refurbish(final int position) {
            tvTitle.setText(item.getTitle());
            tvTime.setText(item.getCreateDate());
        }
    }


}
