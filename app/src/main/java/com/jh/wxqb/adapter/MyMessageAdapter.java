package com.jh.wxqb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.MyMessageBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/11 0011.
 * 我的消息
 */

public class MyMessageAdapter extends RecyclerView.Adapter<MyMessageAdapter.ViewHolder> {


    private Context mContext;
    private List<MyMessageBean.DataBean.MapDataBean.MapJsonBean> myMessageBeen;

    public MyMessageAdapter(Context mContext, List<MyMessageBean.DataBean.MapDataBean.MapJsonBean> myMessageBeen) {
        this.mContext = mContext;
        this.myMessageBeen = myMessageBeen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置自适应布局
        AutoUtils.autoSize(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mymessage, null));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mymessage, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(myMessageBeen.get(position));
        holder.refurbish(position);
    }

    @Override
    public int getItemCount() {
        return myMessageBeen.size() > 0 ? myMessageBeen.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        MyMessageBean.DataBean.MapDataBean.MapJsonBean item;

        public void setItem(MyMessageBean.DataBean.MapDataBean.MapJsonBean item) {
            this.item = item;
        }

        @BindView(R.id.iv_no_email)
        ImageView ivNoEmail;
        @BindView(R.id.iv_yes_email)
        ImageView ivYesEmail;
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
                    ivNoEmail.setVisibility(View.VISIBLE);
                    ivYesEmail.setVisibility(View.GONE);
                    break;
                case 2:
                    ivNoEmail.setVisibility(View.GONE);
                    ivYesEmail.setVisibility(View.VISIBLE);
                    break;
            }
            tvTitle.setText(item.getTitle());
            tvTime.setText(item.getCreateDate());
        }
    }


}
