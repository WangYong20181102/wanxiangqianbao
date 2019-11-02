package com.jh.wxqb.adapter;

import android.annotation.SuppressLint;
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

/**
 * 交易深度
 * （盘口）
 */
public class HandicapDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private MarketDividendTitleBean.DataBean.ListBean listBean;
    private LayoutInflater inflater;

    public HandicapDetailAdapter(Context context, MarketDividendTitleBean.DataBean.ListBean listBean) {
        this.context = context;
        this.listBean = listBean;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 数据更新
     *
     * @param listBean
     */
    public void upDataList(MarketDividendTitleBean.DataBean.ListBean listBean) {
        this.listBean = listBean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_handicap_detail, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            if (listBean != null) {
                if (position > listBean.getBuyList().size() - 1) { //判断是否大于买盘集合大小，大于显示默认“--”
                    ((MyViewHolder) holder).tvBuyId.setText("--");//买盘盘口id
                    ((MyViewHolder) holder).tvBuyPrices.setText("--");//买盘价格
                    ((MyViewHolder) holder).tvBuyNum.setText("--");//数量
                    ((MyViewHolder) holder).myCanvasBuy.updateBgSize(new BigDecimal(0), 0);//更新背景
                } else {
                    MarketDividendTitleBean.DataBean.ListBean.BuyListBean buyListBean = listBean.getBuyList().get(position);
                    ((MyViewHolder) holder).tvBuyId.setText(buyListBean.getId() + "");
                    ((MyViewHolder) holder).tvBuyPrices.setText(String.valueOf(buyListBean.getAmountPrice().doubleValue()));
                    ((MyViewHolder) holder).tvBuyNum.setText(String.valueOf(buyListBean.getCountUnfilledVolume().doubleValue()));
                    ((MyViewHolder) holder).myCanvasBuy.updateBgSize(buyListBean.getCountUnfilledVolume(), 0);
                }
                if (position > listBean.getSellList().size() - 1) {//判断是否大于卖盘集合大小，大于显示默认“--”
                    ((MyViewHolder) holder).tvSellId.setText("--");//卖盘盘口id
                    ((MyViewHolder) holder).tvSellPrices.setText("--");//卖盘价格
                    ((MyViewHolder) holder).tvSellNum.setText("--");//数量
                    ((MyViewHolder) holder).myCanvasSell.updateBgSize(new BigDecimal(0), 0);
                } else {
                    MarketDividendTitleBean.DataBean.ListBean.SellListBean sellListBean = listBean.getSellList().get(position);
                    ((MyViewHolder) holder).tvSellId.setText(sellListBean.getId() + "");
                    ((MyViewHolder) holder).tvSellPrices.setText(String.valueOf(sellListBean.getAmountPrice().doubleValue()));
                    ((MyViewHolder) holder).tvSellNum.setText(String.valueOf(sellListBean.getCountUnfilledVolume().doubleValue()));
                    ((MyViewHolder) holder).myCanvasSell.updateBgSize(sellListBean.getCountUnfilledVolume(), 1);
                }

            }
        }
    }

    @Override
    public int getItemCount() {
        return 20;//固定20条数据
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_canvas_buy)
        MyCanvasView myCanvasBuy;
        @BindView(R.id.tv_buy_id)
        TextView tvBuyId;
        @BindView(R.id.tv_buy_prices)
        TextView tvBuyPrices;
        @BindView(R.id.tv_buy_num)
        TextView tvBuyNum;
        @BindView(R.id.my_canvas_sell)
        MyCanvasView myCanvasSell;
        @BindView(R.id.tv_sell_num)
        TextView tvSellNum;
        @BindView(R.id.tv_sell_prices)
        TextView tvSellPrices;
        @BindView(R.id.tv_sell_id)
        TextView tvSellId;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
