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

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/11 0011.
 * 查看更多
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

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        //设置自适应布局
        AutoUtils.autoSize(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_dividend_main, null));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_dividend_main, null));
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
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
        @BindView(R.id.tv_status)
        TextView tvStatus;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void refurbish(final int position) {
            BigDecimal one = new BigDecimal("1");
            switch (listBeen.get(position).getDirection()) {
                case 1:
                    tvType.setTextColor(Color.parseColor("#03AD8F"));
                    tvType.setText(R.string.dividend);
                    tvTurnover.setText(String.valueOf(listBeen.get(position).getAmountPrice().divide(one, 4, BigDecimal.ROUND_HALF_UP).doubleValue()));
                    break;
                case 2:
                    tvType.setTextColor(Color.parseColor("#D14B64"));
                    tvType.setText(R.string.sell);
                    tvTurnover.setText(String.valueOf(listBeen.get(position).getAmountPrice().divide(one, 4, BigDecimal.ROUND_HALF_UP).doubleValue()));
                    break;
            }
            switch (listBeen.get(position).getOrderStatus()) {
                case 1://挂单中
                    tvStatus.setText(R.string.in_the_pending_order);
                    break;
                case 3://全部成交
                    tvStatus.setText(R.string.all_success);
                    break;
                case 4://已撤销
                    tvStatus.setText(R.string.revocation);
                    break;
                case 5://部分成交
                    tvStatus.setText(R.string.partial_deal);
                    break;
            }
            //更新时间
            tvTime.setText(listBeen.get(position).getUpdateTime());
            //数量
            tvNum.setText(String.valueOf(listBeen.get(position).getAccountCommission().divide(one, 2, BigDecimal.ROUND_DOWN).doubleValue()));
            tvPurchase.setOnClickListener(MoreBusinessAdapter.this);
            tvPurchase.setTag(position);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_purchase:
                myClicker.myClick(v, 0);
                break;
        }
    }

}
