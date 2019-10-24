package com.jh.wxqb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.base.CoinTypeBean;
import com.jh.wxqb.bean.FinancialDetailsTypeBean;
import com.jh.wxqb.utils.MyClicker;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/11 0011.
 * 选择类型
 */

public class CoinTypeAdapter extends RecyclerView.Adapter<CoinTypeAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<CoinTypeBean> coinTypeBeans;
    private OnItemResultListener onItemResultListener;

    public CoinTypeAdapter(Context mContext, List<CoinTypeBean> coinTypeBeans, OnItemResultListener onItemResultListener) {
        this.mContext = mContext;
        this.coinTypeBeans = coinTypeBeans;
        this.onItemResultListener = onItemResultListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置自适应布局
        AutoUtils.autoSize(LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_type_item, null));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_type_item, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(coinTypeBeans.get(position));
        holder.refurbish(position);
    }

    @Override
    public int getItemCount() {
        return coinTypeBeans.size() > 0 ? coinTypeBeans.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        CoinTypeBean item;

        public void setItem(CoinTypeBean item) {
            this.item = item;
        }

        @BindView(R.id.tv_type)
        TextView tvType;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void refurbish(final int position) {
            tvType.setText(item.getCoin());
            tvType.setOnClickListener(CoinTypeAdapter.this);
            tvType.setTag(position);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_type:
                onItemResultListener.onResult(coinTypeBeans.get((Integer) v.getTag()));
                break;
        }
    }

    public interface OnItemResultListener {
        void onResult(CoinTypeBean coin);
    }

}
