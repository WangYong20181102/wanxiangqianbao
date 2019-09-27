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
import com.jh.wxqb.utils.Toasts;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 资产管理
 */

public class AssetManagementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<AssetManagementBean.DataBean.AccountAssetsBean> financialDetailsBeen;

    public AssetManagementAdapter(Context mContext, List<AssetManagementBean.DataBean.AccountAssetsBean> financialDetailsBeen) {
        this.mContext = mContext;
        this.financialDetailsBeen = financialDetailsBeen;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_assets_management, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.tvTran.setOnClickListener(this);
        holder.tvWithdrawMoney.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            AssetManagementBean.DataBean.AccountAssetsBean item = financialDetailsBeen.get(position);
            ((MyViewHolder) holder).tvWithdrawMoney.setTag(position);
            ((MyViewHolder) holder).tvTran.setTag(position);
            switch (item.getBizCurrencyTypeId()) {
                case 1:
                    ((MyViewHolder) holder).tvCurrency.setText("ETH");
                    ((MyViewHolder) holder).ivMoneyImg.setImageResource(R.drawable.icon_eth_small);
                    ((MyViewHolder) holder).imageRight.setImageResource(R.drawable.icon_eth_right);
                    break;
                case 2:
                    ((MyViewHolder) holder).tvCurrency.setText("TGM");
                    ((MyViewHolder) holder).ivMoneyImg.setImageResource(R.drawable.iv_tgm_small);
                    ((MyViewHolder) holder).imageRight.setImageResource(R.drawable.iv_tgm_right);
                    break;
                case 3:
                    ((MyViewHolder) holder).tvCurrency.setText("USDT");
                    ((MyViewHolder) holder).ivMoneyImg.setImageResource(R.drawable.icon_usdt_small);
                    ((MyViewHolder) holder).imageRight.setImageResource(R.drawable.icon_usdt_right);
                    break;
            }
            ((MyViewHolder) holder).tvBalance.setText(item.getActiveAssets() + "");
        }
    }

    @Override
    public int getItemCount() {
        return financialDetailsBeen.size() > 0 ? financialDetailsBeen.size() : 0;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_money_img)
        ImageView ivMoneyImg;
        @BindView(R.id.tv_currency)
        TextView tvCurrency;
        @BindView(R.id.tv_balance)
        TextView tvBalance;
        @BindView(R.id.ll_layout)
        LinearLayout llLayout;
        @BindView(R.id.tv_tran)
        TextView tvTran;
        @BindView(R.id.tv_withdraw_money)
        TextView tvWithdrawMoney;
        @BindView(R.id.image_right)
        ImageView imageRight;

        public MyViewHolder(View item) {
            super(item);
            ButterKnife.bind(this, item);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_tran://充币
//                AssetManagementBean.DataBean.AccountAssetsBean item = financialDetailsBeen.get((Integer) v.getTag());
//                if (item.getBizCurrencyTypeId() == 3){
//                    Toasts.showShort("暫未開放此功能!");
//                }else {
                    intent = new Intent(mContext, PunchingMoneyActivity.class);
                    mContext.startActivity(intent);
//                }
                break;
            case R.id.tv_withdraw_money://提币
                Toasts.showShort("暫未開放此功能!");
//                intent = new Intent(mContext, WithdrawMoneyActivity.class);
//                intent.putExtra(AssetManagementBean.class.getName(), financialDetailsBeen.get((Integer) v.getTag()));
//                mContext.startActivity(intent);
                break;
        }
    }

}
