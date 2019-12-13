package com.jh.wxqb.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.QuotesCoinBean;
import com.jh.wxqb.utils.Toasts;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoinTypeQuotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<QuotesCoinBean.DataBean.QuotesListBean> beanList;
    private Context context;
    private LayoutInflater inflater;

    public CoinTypeQuotesAdapter(List<QuotesCoinBean.DataBean.QuotesListBean> beanList, Context context) {
        this.beanList = beanList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 更新数据
     *
     * @param beanList
     */
    public void updateBeanList(List<QuotesCoinBean.DataBean.QuotesListBean> beanList) {
        this.beanList = beanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.quotes_coin_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.llNewsClick.setOnClickListener(this);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            QuotesCoinBean.DataBean.QuotesListBean quotesListBean = beanList.get(position);
            ((MyViewHolder) holder).llNewsClick.setTag(position);
            //币种
            ((MyViewHolder) holder).tvCoin.setText(quotesListBean.getTradingPair());
            //24小时成交量
            ((MyViewHolder) holder).tvVolume.setText("24H量 " + new BigDecimal(String.valueOf(quotesListBean.getTodayVolume())).setScale(2,BigDecimal.ROUND_HALF_UP));
            //折合人民币
            ((MyViewHolder) holder).tvEquivalent.setText("¥" + new BigDecimal(String.valueOf(quotesListBean.getCoinValue())).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            //涨跌幅
            if (!TextUtils.isEmpty(quotesListBean.getQuoteChange())) {
                String percentagePoint = quotesListBean.getQuoteChange();
                if (percentagePoint.contains("+")) {
                    ((MyViewHolder) holder).tvUpsAndDowns.setBackgroundResource(R.drawable.quotes_rate);
                    ((MyViewHolder) holder).tvUpsAndDowns.setTextColor(ContextCompat.getColor(context, R.color.color_03AD8F));
                } else if (percentagePoint.contains("-")) {
                    ((MyViewHolder) holder).tvUpsAndDowns.setBackgroundResource(R.drawable.quotes_rate_down);
                    ((MyViewHolder) holder).tvUpsAndDowns.setTextColor(ContextCompat.getColor(context, R.color.color_D14B64));
                } else {
                    ((MyViewHolder) holder).tvUpsAndDowns.setBackgroundResource(R.drawable.quotes_rate_stop);
                    ((MyViewHolder) holder).tvUpsAndDowns.setTextColor(ContextCompat.getColor(context, R.color.color_7E93A2));
                }
                ((MyViewHolder) holder).tvUpsAndDowns.setText(percentagePoint);
            }
            //价格
            ((MyViewHolder) holder).tvPrices.setText(String.valueOf(new BigDecimal(String.valueOf(quotesListBean.getUsaPrice())).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue()));


        }

    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_news_click:
                if (beanList.get((int) view.getTag()).getTradingPair().contains("TGM")) {
                    EventBus.getDefault().post("clickToMarketPlace");
                    EventBus.getDefault().post("backBuy");
                } else {
                    Toasts.showShort("暂未开放此功能！");
                }
                break;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_coin)
        TextView tvCoin;
        @BindView(R.id.tv_volume)
        TextView tvVolume;
        @BindView(R.id.tv_prices)
        TextView tvPrices;
        @BindView(R.id.tv_equivalent)
        TextView tvEquivalent;
        @BindView(R.id.tv_ups_and_downs)
        TextView tvUpsAndDowns;
        @BindView(R.id.ll_news_click)
        RelativeLayout llNewsClick;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
