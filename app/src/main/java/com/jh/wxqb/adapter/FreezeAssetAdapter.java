package com.jh.wxqb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.AssetManagementBean;
import com.jh.wxqb.ui.assets.PunchingMoneyActivity;
import com.jh.wxqb.ui.assets.WithdrawMoneyActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 冻结资产适配器
 */

public class FreezeAssetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<AssetManagementBean.DataBean.AccountAssetsBean> financialDetailsBeen;

    public FreezeAssetAdapter(Context mContext, List<AssetManagementBean.DataBean.AccountAssetsBean> financialDetailsBeen) {
        this.mContext = mContext;
        this.financialDetailsBeen = financialDetailsBeen;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_freeze_assets, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            AssetManagementBean.DataBean.AccountAssetsBean item = financialDetailsBeen.get(position);
            switch (item.getBizCurrencyTypeId()) {
                case 1:
                    ((MyViewHolder) holder).tvName.setText("ETH");
                    ((MyViewHolder) holder).imageIcon.setImageResource(R.mipmap.coin_type_icon_eth);
                    break;
                case 2:
                    ((MyViewHolder) holder).tvName.setText("TGM");
                    ((MyViewHolder) holder).imageIcon.setImageResource(R.mipmap.coin_type_icon_tgm);
                    break;
                case 3:
                    ((MyViewHolder) holder).tvName.setText("USDT");
                    ((MyViewHolder) holder).imageIcon.setImageResource(R.mipmap.coin_type_icon_usdt);
                    break;
                case 4:
                    ((MyViewHolder) holder).tvName.setText("HT");
                    ((MyViewHolder) holder).imageIcon.setImageResource(R.mipmap.coin_type_icon_ht);
                    break;
                case 5:
                    ((MyViewHolder) holder).tvName.setText("OKB");
                    ((MyViewHolder) holder).imageIcon.setImageResource(R.mipmap.coin_type_icon_okb);
                    break;
                case 6:
                    ((MyViewHolder) holder).tvName.setText("BNB");
                    ((MyViewHolder) holder).imageIcon.setImageResource(R.mipmap.coin_type_icon_bnb);
                    break;

            }
            ((MyViewHolder) holder).tvAssets.setText(item.getRepurchaseAssets() + "");
        }
    }

    @Override
    public int getItemCount() {
        return financialDetailsBeen.size() > 0 ? financialDetailsBeen.size() : 0;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_icon)
        ImageView imageIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_can_use)
        TextView tvCanUse;
        @BindView(R.id.tv_assets)
        TextView tvAssets;

        public MyViewHolder(View item) {
            super(item);
            ButterKnife.bind(this, item);
        }
    }

}
