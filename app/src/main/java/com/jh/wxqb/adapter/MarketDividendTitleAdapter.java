package com.jh.wxqb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.MarketDividendTitleBean;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/11 0011.
 * 買入市场--買入 Title
 */

public class MarketDividendTitleAdapter extends RecyclerView.Adapter<MarketDividendTitleAdapter.ViewHolder> {

    private Context mContext;
    private MarketDividendTitleBean.DataBean.ListBean marketDividendTitleBeen;
    private boolean color;  //true:红  false：绿
    private final LayoutInflater mInflater;

    public MarketDividendTitleAdapter(Context mContext, boolean color, MarketDividendTitleBean.DataBean.ListBean marketDividendTitleBeen) {
        this.mContext = mContext;
        this.color = color;
        this.marketDividendTitleBeen = marketDividendTitleBeen;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置自适应布局
        View childView = mInflater.inflate(R.layout.item_dividend_title, parent, false);
        return new ViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(marketDividendTitleBeen);
        holder.refurbish(position);
    }

    @Override
    public int getItemCount() {
        if (marketDividendTitleBeen != null) {
            if (color) {
                return marketDividendTitleBeen.getSellList().size() > 0 ? marketDividendTitleBeen.getSellList().size() : 0;
            } else {
                return marketDividendTitleBeen.getBuyList().size() > 0 ? marketDividendTitleBeen.getBuyList().size() : 0;
            }
        }else {
            return 0;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        MarketDividendTitleBean.DataBean.ListBean item;

        public void setItem(MarketDividendTitleBean.DataBean.ListBean item) {
            this.item = item;
        }

        @BindView(R.id.tv_index)
        TextView tvIndex;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_number)
        TextView tvNumber;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void refurbish(final int position) {
            BigDecimal one=new BigDecimal("1");


            if (color) {
                tvPrice.setText(String.valueOf(item.getSellList().get(position).getAmountPrice().divide(one,4,BigDecimal.ROUND_HALF_UP).doubleValue()));
                tvNumber.setText(String.valueOf(item.getSellList().get(position).getCountUnfilledVolume().divide(one,2,BigDecimal.ROUND_HALF_UP).doubleValue()));
                tvIndex.setText(String.valueOf(item.getSellList().get(position).getId()));
                tvPrice.setTextColor(Color.parseColor("#D14B64"));
            } else {
                tvPrice.setText(String.valueOf(item.getBuyList().get(position).getAmountPrice().divide(one,4,BigDecimal.ROUND_HALF_UP).doubleValue()));
                tvNumber.setText(String.valueOf(item.getBuyList().get(position).getCountUnfilledVolume().divide(one,2,BigDecimal.ROUND_HALF_UP).doubleValue()));
                tvIndex.setText(String.valueOf(item.getBuyList().get(position).getId()));
                tvPrice.setTextColor(Color.parseColor("#03AD8F"));
            }
        }
    }


}
