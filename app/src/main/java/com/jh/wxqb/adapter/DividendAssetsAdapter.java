package com.jh.wxqb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.FinancialDetailsBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/11 0011.
 * 買入資產
 */

public class DividendAssetsAdapter extends RecyclerView.Adapter<DividendAssetsAdapter.ViewHolder> {

    private Context mContext;
    private List<FinancialDetailsBean> dividendAssetsBeen;

    public DividendAssetsAdapter(Context mContext, List<FinancialDetailsBean> dividendAssetsBeen) {
        this.mContext = mContext;
        this.dividendAssetsBeen = dividendAssetsBeen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置自适应布局
        AutoUtils.autoSize(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dividend_assets, null));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dividend_assets, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.setItem(homeBeen.get(position));
//        holder.refurbish(position);
    }

    @Override
    public int getItemCount() {
        return 4;
//        return repurchaseAssetsBeen.size() > 0 ? repurchaseAssetsBeen.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void refurbish(final int position) {
        }
    }


}
