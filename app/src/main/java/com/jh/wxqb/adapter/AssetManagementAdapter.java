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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.AssetManagementBean;
import com.jh.wxqb.ui.assets.PunchingMoneyActivity;
import com.jh.wxqb.ui.assets.WithdrawMoneyActivity;
import com.jh.wxqb.ui.me.FinancialDetailsActivity;
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
            final AssetManagementBean.DataBean.AccountAssetsBean item = financialDetailsBeen.get(position);
            ((MyViewHolder) holder).tvWithdrawMoney.setTag(position);
            ((MyViewHolder) holder).tvTran.setTag(position);
            switch (item.getBizCurrencyTypeId()) {
                case 1:
                    ((MyViewHolder) holder).tvCurrency.setText("ETH");
                    ((MyViewHolder) holder).ivMoneyImg.setImageResource(R.drawable.icon_eth_small);
                    ((MyViewHolder) holder).imageRight.setImageResource(R.drawable.icon_eth_right);
                    ((MyViewHolder) holder).tvTeamExcitation.setVisibility(View.GONE);
                    break;
                case 2:
                    ((MyViewHolder) holder).tvCurrency.setText("TGM");
                    ((MyViewHolder) holder).ivMoneyImg.setImageResource(R.drawable.iv_tgm_small);
                    ((MyViewHolder) holder).imageRight.setImageResource(R.drawable.iv_tgm_right);
                    if (item.getIsGrouper() == 1) {
                        ((MyViewHolder) holder).tvTeamExcitation.setVisibility(View.VISIBLE);
                        ((MyViewHolder) holder).tvTeamExcitation.setText("(社区长激励：" + item.getSharingRevenue().doubleValue() + ")");
                    } else {
                        ((MyViewHolder) holder).tvTeamExcitation.setVisibility(View.GONE);
                    }
                    break;
                case 3:
                    ((MyViewHolder) holder).tvCurrency.setText("USDT");
                    ((MyViewHolder) holder).ivMoneyImg.setImageResource(R.drawable.icon_usdt_small);
                    ((MyViewHolder) holder).imageRight.setImageResource(R.drawable.icon_usdt_right);
                    ((MyViewHolder) holder).tvTeamExcitation.setVisibility(View.GONE);
                    break;
                case 4:
                    ((MyViewHolder) holder).tvCurrency.setText("HT");
                    ((MyViewHolder) holder).ivMoneyImg.setImageResource(R.drawable.iv_ht_small);
                    ((MyViewHolder) holder).imageRight.setImageResource(R.drawable.iv_ht_right);
                    ((MyViewHolder) holder).tvTeamExcitation.setVisibility(View.GONE);
                    break;
                case 5:
                    ((MyViewHolder) holder).tvCurrency.setText("OKB");
                    ((MyViewHolder) holder).ivMoneyImg.setImageResource(R.drawable.iv_okb_small);
                    ((MyViewHolder) holder).imageRight.setImageResource(R.drawable.iv_okb_right);
                    ((MyViewHolder) holder).tvTeamExcitation.setVisibility(View.GONE);
                    break;
                case 6:
                    ((MyViewHolder) holder).tvCurrency.setText("BNB");
                    ((MyViewHolder) holder).ivMoneyImg.setImageResource(R.drawable.iv_bnb_small);
                    ((MyViewHolder) holder).imageRight.setImageResource(R.drawable.iv_bnb_right);
                    ((MyViewHolder) holder).tvTeamExcitation.setVisibility(View.GONE);
                    break;
            }
            ((MyViewHolder) holder).tvBalance.setText(item.getActiveAssets() + "");
            ((MyViewHolder) holder).tvtvEquivalentAssets.setText(item.getDiscountedPrice() + "");
            ((MyViewHolder) holder).rlBgClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, FinancialDetailsActivity.class);
                    intent.putExtra("coinTypeId", item.getBizCurrencyTypeId());
                    mContext.startActivity(intent);
                }
            });
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
        @BindView(R.id.tv_equivalent_assets)
        TextView tvtvEquivalentAssets;
        @BindView(R.id.rl_bg_click)
        RelativeLayout rlBgClick;
        @BindView(R.id.tv_team_excitation)
        TextView tvTeamExcitation;

        public MyViewHolder(View item) {
            super(item);
            ButterKnife.bind(this, item);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        AssetManagementBean.DataBean.AccountAssetsBean item = financialDetailsBeen.get((Integer) v.getTag());
        switch (v.getId()) {
            case R.id.tv_tran://充币
                switch (item.getBizCurrencyTypeId()) {
                    case 1:
                    case 2:
                    case 3:
                        intent = new Intent(mContext, PunchingMoneyActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 4:
                    case 5:
                    case 6:
                        Toasts.showShort("暫未開放此功能!");
                        break;
                }
                break;
            case R.id.tv_withdraw_money://提币
//                Toasts.showShort("暫未開放此功能!");
                switch (item.getBizCurrencyTypeId()) {
                    case 1:
                    case 2:
                    case 3:
                        intent = new Intent(mContext, WithdrawMoneyActivity.class);
                        intent.putExtra(AssetManagementBean.class.getName(), financialDetailsBeen.get((Integer) v.getTag()));
                        mContext.startActivity(intent);
                        break;
                    case 4:
                    case 5:
                    case 6:
                        Toasts.showShort("暫未開放此功能!");
                        break;
                }
                break;
        }
    }

}
