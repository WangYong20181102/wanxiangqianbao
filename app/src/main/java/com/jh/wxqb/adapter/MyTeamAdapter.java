package com.jh.wxqb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.MyTeamBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/11 0011.
 * 我的团队
 */

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.ViewHolder> {


    private Context mContext;
    private List<MyTeamBean.DataBean.TeamInfoBean> myTeamBeen;

    public MyTeamAdapter(Context mContext, List<MyTeamBean.DataBean.TeamInfoBean> myTeamBeen) {
        this.mContext = mContext;
        this.myTeamBeen = myTeamBeen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置自适应布局
        AutoUtils.autoSize(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_team, null));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_team, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(myTeamBeen.get(position));
        holder.refurbish(position);
    }

    @Override
    public int getItemCount() {
        return myTeamBeen.size() > 0 ? myTeamBeen.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        MyTeamBean.DataBean.TeamInfoBean item;

        public void setItem(MyTeamBean.DataBean.TeamInfoBean item) {
            this.item = item;
        }

        @BindView(R.id.tv_id)
        TextView tvId;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_performance)
        TextView tvPerformance;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void refurbish(final int position) {
            tvId.setText(item.getUserId());
            tvName.setText(item.getUserName());
            tvTime.setText(item.getCreateTime());
            BigDecimal one=new BigDecimal("1");
            tvPerformance.setText(String.valueOf(item.getSum().divide(one,1,BigDecimal.ROUND_DOWN).doubleValue()));
        }
    }


}
