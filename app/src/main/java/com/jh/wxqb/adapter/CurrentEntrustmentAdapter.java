package com.jh.wxqb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.MeDividend;
import com.jh.wxqb.utils.TimeUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/11 0011.
 * 当前委托
 */

public class CurrentEntrustmentAdapter extends RecyclerView.Adapter<CurrentEntrustmentAdapter.ViewHolder> {

    private Context mContext;
    private List<MeDividend.DataBean.ListBean> currentEntrustmentBeen;

    public CurrentEntrustmentAdapter(Context mContext, List<MeDividend.DataBean.ListBean> currentEntrustmentBeen) {
        this.mContext = mContext;
        this.currentEntrustmentBeen = currentEntrustmentBeen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置自适应布局w
        AutoUtils.autoSize(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_dividend_main, null));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_dividend_main, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(currentEntrustmentBeen.get(position));
        holder.refurbish(position);
    }

    @Override
    public int getItemCount() {
        return currentEntrustmentBeen.size() > 0 ? currentEntrustmentBeen.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        MeDividend.DataBean.ListBean item;

        public void setItem(MeDividend.DataBean.ListBean item) {
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
            tvTime.setText(TimeUtil.getTime(item.getCreateDate()));

            //1、挂买 2、挂卖
            switch (item.getDirection()) {
                case 1:
                    tvType.setTextColor(Color.WHITE);
                    tvType.setTextColor(Color.rgb(0, 204, 102));
                    tvType.setText(R.string.dividend);
                    tvTurnover.setText(String.valueOf(item.getAmountPrice().divide(one, 4, BigDecimal.ROUND_DOWN).doubleValue() + ""));
                    break;
                case 2:
                    tvType.setTextColor(Color.parseColor("#d6734b"));
                    tvType.setText(R.string.sell);
                    tvTurnover.setText(String.valueOf(item.getAmountPrice().divide(one, 4, BigDecimal.ROUND_DOWN).doubleValue() + ""));
                    break;
            }
            String unit = "";
            switch (item.getAssetTypeId()) {
                case 1:
                    unit = "TGM";
                    break;
                case 2:
                case 3:
                    unit = "USDT";
                    break;
            }
            tvNum.setText(item.getAccountCommission().divide(one, 2, BigDecimal.ROUND_DOWN).doubleValue() + "");
            tvStatus.setVisibility(View.VISIBLE);
            switch (item.getStatus()) {
                case 1:
                    tvStatus.setTextColor(Color.rgb(55, 105, 245));
                    tvStatus.setText(R.string.in_the_pending_order);
                    break;
//                case 2:
//                    tvStatus.setTextColor(Color.parseColor("#d6734b"));
//                    tvStatus.setText(R.string.stop_the_dividend);
//                    break;
                case 3:
                    tvStatus.setTextColor(Color.rgb(0, 204, 102));
                    tvStatus.setText(R.string.all_success);
                    break;
                case 4:
                    tvStatus.setTextColor(Color.parseColor("#d6734b"));
                    tvStatus.setText(R.string.revocation);
                    break;
                case 5:
                    tvStatus.setTextColor(Color.rgb(0, 204, 102));
                    tvStatus.setText(R.string.partial_deal);
                    break;
//                case 6:
//                    tvStatus.setTextColor(Color.parseColor("#d6734b"));
//                    tvStatus.setText(R.string.revocation);
//                    break;
//                case 7:
//                    tvStatus.setTextColor(Color.rgb(0, 204, 102));
//                    tvStatus.setText(R.string.queuing);
//                    break;
            }
        }
    }


}
