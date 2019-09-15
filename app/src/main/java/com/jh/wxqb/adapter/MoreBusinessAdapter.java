package com.jh.wxqb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.MarketDividendBottomBean;
import com.jh.wxqb.utils.MyClicker;
import com.jh.wxqb.utils.TimeUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/11 0011.
 * 当前买卖
 */

public class MoreBusinessAdapter extends RecyclerView.Adapter<MoreBusinessAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<MarketDividendBottomBean.DataBean.ListBean> listBeen;
    private MyClicker myClicker;

    public MoreBusinessAdapter(Context mContext, List<MarketDividendBottomBean.DataBean.ListBean> listBeen, MyClicker myClicker) {
        this.mContext = mContext;
        this.listBeen = listBeen;
        this.myClicker = myClicker;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置自适应布局
        AutoUtils.autoSize(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_dividend_main, null));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_dividend_main, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(listBeen.get(position));
        holder.refurbish(position);
    }

    @Override
    public int getItemCount() {
        return listBeen.size() > 0 ? listBeen.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        MarketDividendBottomBean.DataBean.ListBean item;

        public void setItem(MarketDividendBottomBean.DataBean.ListBean item) {
            this.item = item;
        }

        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_turnover)
        TextView tvTurnover;
        @BindView(R.id.tv_purchase)
        TextView tvPurchase;
        @BindView(R.id.tv_no)
        TextView tvNo;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void refurbish(final int position) {
            BigDecimal one=new BigDecimal("1");
            switch (listBeen.get(position).getDirection()) {
                case 1:
                    tvPurchase.setVisibility(View.GONE);
                    tvNo.setVisibility(View.VISIBLE);
                    tvType.setTextColor(Color.rgb(0, 204, 102));
                    tvType.setText(R.string.dividend);
                    tvTurnover.setText(String.valueOf(listBeen.get(position).getAmountTotal().divide(one,1,BigDecimal.ROUND_DOWN).doubleValue()+"PKB"));
                    break;
                case 2:
                    tvPurchase.setVisibility(View.VISIBLE);
                    tvNo.setVisibility(View.GONE);
                    tvType.setTextColor(Color.rgb(204, 0, 0));
                    tvType.setText(R.string.sell);
                    tvTurnover.setText(String.valueOf(listBeen.get(position).getAcountTransaction().divide(one,1,BigDecimal.ROUND_DOWN).doubleValue()+"ETH"));
                    break;
            }
            String unit="";
            switch (listBeen.get(position).getAssetTypeId()) {
                case 1:
                    unit="ETH";
                    break;
                case 2:
                case 3:
                    unit="PKB";
                    break;
            }
            tvTime.setText(TimeUtil.getTime(listBeen.get(position).getCreateDate()));
            tvNum.setText(String.valueOf(listBeen.get(position).getAccountCommission().divide(one,2,BigDecimal.ROUND_DOWN).doubleValue()+unit));
            tvPurchase.setOnClickListener(MoreBusinessAdapter.this);
            tvPurchase.setTag(position);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_purchase:
                myClicker.myClick(v,0);
                break;
        }
    }

}
