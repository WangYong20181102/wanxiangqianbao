package com.jh.wxqb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.FeedbackListBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/11 0011.
 * 反馈列表
 */

public class FeedbackListAdapter extends RecyclerView.Adapter<FeedbackListAdapter.ViewHolder> {

    private Context mContext;
    private List<FeedbackListBean.DataBean.MapDataBean.MapJsonBean> feedbackListBeen;

    public FeedbackListAdapter(Context mContext, List<FeedbackListBean.DataBean.MapDataBean.MapJsonBean> feedbackListBeen) {
        this.mContext = mContext;
        this.feedbackListBeen = feedbackListBeen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置自适应布局
        AutoUtils.autoSize(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback_list, null));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback_list, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(feedbackListBeen.get(position));
        holder.refurbish(position);
    }

    @Override
    public int getItemCount() {
        return feedbackListBeen.size() > 0 ? feedbackListBeen.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        FeedbackListBean.DataBean.MapDataBean.MapJsonBean item;

        public void setItem(FeedbackListBean.DataBean.MapDataBean.MapJsonBean item) {
            this.item = item;
        }

        @BindView(R.id.iv_yes_email)
        ImageView ivYesEmail;
        @BindView(R.id.iv_no_email)
        ImageView ivNoEmail;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void refurbish(final int position) {
            switch (item.getReadStatus()) {
                case 1:
                    ivYesEmail.setVisibility(View.GONE);
                    ivNoEmail.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    ivYesEmail.setVisibility(View.VISIBLE);
                    ivNoEmail.setVisibility(View.GONE);
                    break;
            }
            tvTitle.setText(item.getContent());
            tvTime.setText(item.getCreateDate());
        }
    }


}
