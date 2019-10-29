package com.jh.wxqb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.MarketDividendTitleBean;
import com.jh.wxqb.customview.MyCanvasView;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HandicapDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private MarketDividendTitleBean.DataBean.ListBean listBean;
    private LayoutInflater inflater;

    public HandicapDetailAdapter(Context context, MarketDividendTitleBean.DataBean.ListBean listBean) {
        this.context = context;
        this.listBean = listBean;
        inflater = LayoutInflater.from(context);
    }

    public void upDataList(MarketDividendTitleBean.DataBean.ListBean listBean) {
        this.listBean = listBean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_handicap_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            if (listBean != null) {
                if (position > listBean.getBuyList().size() - 1) {
                    ((MyViewHolder) holder).tvLeftId.setText("--");
                    ((MyViewHolder) holder).tvLeftPrices.setText("--");
                    ((MyViewHolder) holder).tvLeftNum.setText("--");
                    ((MyViewHolder) holder).myCanvasLeft.updateBgSize(new BigDecimal(0), 0);
                } else {
                    MarketDividendTitleBean.DataBean.ListBean.BuyListBean buyListBean = listBean.getBuyList().get(position);
                    ((MyViewHolder) holder).tvLeftId.setText(buyListBean.getId() + "");
                    ((MyViewHolder) holder).tvLeftPrices.setText(String.valueOf(buyListBean.getAmountPrice().doubleValue()));
                    ((MyViewHolder) holder).tvLeftNum.setText(String.valueOf(buyListBean.getCountUnfilledVolume().doubleValue()));
                    ((MyViewHolder) holder).myCanvasLeft.updateBgSize(buyListBean.getCountUnfilledVolume(), 0);
                }
                if (position > listBean.getSellList().size() - 1) {
                    ((MyViewHolder) holder).tvRightId.setText("--");
                    ((MyViewHolder) holder).tvRightPrices.setText("--");
                    ((MyViewHolder) holder).tvRightNum.setText("--");
                    ((MyViewHolder) holder).myCanvasRight.updateBgSize(new BigDecimal(0), 0);
                } else {
                    MarketDividendTitleBean.DataBean.ListBean.SellListBean sellListBean = listBean.getSellList().get(position);
                    ((MyViewHolder) holder).tvRightId.setText(sellListBean.getId() + "");
                    ((MyViewHolder) holder).tvRightPrices.setText(String.valueOf(sellListBean.getAmountPrice().doubleValue()));
                    ((MyViewHolder) holder).tvRightNum.setText(String.valueOf(sellListBean.getCountUnfilledVolume().doubleValue()));
                    ((MyViewHolder) holder).myCanvasRight.updateBgSize(sellListBean.getCountUnfilledVolume(), 1);
                }

            }
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_canvas_left)
        MyCanvasView myCanvasLeft;
        @BindView(R.id.tv_left_id)
        TextView tvLeftId;
        @BindView(R.id.tv_left_prices)
        TextView tvLeftPrices;
        @BindView(R.id.tv_left_num)
        TextView tvLeftNum;
        @BindView(R.id.my_canvas_right)
        MyCanvasView myCanvasRight;
        @BindView(R.id.tv_right_num)
        TextView tvRightNum;
        @BindView(R.id.tv_right_prices)
        TextView tvRightPrices;
        @BindView(R.id.tv_right_id)
        TextView tvRightId;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
