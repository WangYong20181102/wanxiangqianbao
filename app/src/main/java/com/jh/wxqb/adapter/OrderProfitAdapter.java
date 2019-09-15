package com.jh.wxqb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.bean.MeDividend;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.TimeUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/11 0011.
 * 订单详情 --收益
 */

public class OrderProfitAdapter extends RecyclerView.Adapter<OrderProfitAdapter.ViewHolder> {


    private Context mContext;
    private List<MeDividend.DataBean.ListBean.MemberInterestsBean> orderProfitBeen;

    public OrderProfitAdapter(Context mContext, List<MeDividend.DataBean.ListBean.MemberInterestsBean> orderProfitBeen) {
        this.mContext = mContext;
        this.orderProfitBeen = orderProfitBeen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置自适应布局
        AutoUtils.autoSize(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_profit, null));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_profit, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(orderProfitBeen.get(position));
        holder.refurbish(position);
    }

    @Override
    public int getItemCount() {
        return orderProfitBeen.size() > 0 ? orderProfitBeen.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        MeDividend.DataBean.ListBean.MemberInterestsBean item;

        public void setItem(MeDividend.DataBean.ListBean.MemberInterestsBean item) {
            this.item = item;
        }

        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_profit)
        TextView tvProfit;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void refurbish(final int position) {
            tvTime.setText(TimeUtil.getTime(item.getCreateTime()));
            switch (item.getInterestType()) {
                case 1:
                    tvProfit.setText("+" + StringUtil.subZeroAndDot(item.getDailyInterest().toPlainString()));
                    break;
                case 2:
                    tvProfit.setTextColor(Color.rgb(204, 0, 0));
                    String tips="額外";
                    switch (MyApplication.getLanuage()) {
                        case "zh":
                            tips="額外";
                            break;
                        case "en":
                            tips="Extra";
                            break;
                    }
                    tvProfit.setText(tips+ "  +" + StringUtil.subZeroAndDot(item.getDailyInterest().toPlainString()));
                    break;
            }
        }
    }


}
