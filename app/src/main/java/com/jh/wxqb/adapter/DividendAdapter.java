package com.jh.wxqb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.MeDividend;
import com.jh.wxqb.utils.MyClicker;
import com.jh.wxqb.utils.TimeUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/11 0011.
 */

public class DividendAdapter extends RecyclerView.Adapter<DividendAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<MeDividend.DataBean.ListBean> meDividends;
    private MyClicker myClicker;

    public DividendAdapter(Context mContext, List<MeDividend.DataBean.ListBean> meDividends, MyClicker myClicker) {
        this.mContext = mContext;
        this.meDividends = meDividends;
        this.myClicker = myClicker;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置自适应布局
        AutoUtils.autoSize(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dividend, null));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dividend, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(meDividends.get(position));
        holder.refurbish(position);
    }

    @Override
    public int getItemCount() {
        return meDividends.size() > 0 ? meDividends.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        MeDividend.DataBean.ListBean item;

        public void setItem(MeDividend.DataBean.ListBean item) {
            this.item = item;
        }

        @BindView(R.id.tv_success)
        TextView tvSuccess;
        @BindView(R.id.tv_dividend)
        TextView tvDividend;
        @BindView(R.id.tv_dividend_state)
        TextView tvDividendState;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_delegate_quantity)
        TextView tvDelegateQuantity;
        @BindView(R.id.tv_dividend_income)
        TextView tvDividendIncome;
        @BindView(R.id.tv_total_turnover)
        TextView tvTotalTurnover;
        @BindView(R.id.tv_volume)
        TextView tvVolume;
        @BindView(R.id.tv_rate)
        TextView tvRate;
        @BindView(R.id.tv_company)
        TextView tvCompany;
        @BindView(R.id.ll_state)
        LinearLayout llState;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        //      1買入中2.停止買入3.停止排队4.挂单中5.已成交6.已撤销7.排队中
        //tvDividendState  1：停止買入   2：停止排队
        public void refurbish(final int position) {
            BigDecimal one = new BigDecimal("1");
            tvTime.setText(TimeUtil.getTime(item.getCreateDate()));
            tvDelegateQuantity.setText(String.valueOf(item.getAmountPrice().divide(one, 4, BigDecimal.ROUND_HALF_UP)));
            tvDividendIncome.setText(String.valueOf(item.getAccountCommission().divide(one, 2, BigDecimal.ROUND_HALF_UP)));
//            tvTotalTurnover.setText(String.valueOf(item.getAmountTotal().divide(one, 2, BigDecimal.ROUND_HALF_UP)));
            tvVolume.setText(String.valueOf(item.getVolume().divide(one, 2, BigDecimal.ROUND_HALF_UP)));
//            tvRate.setText(item.getInterestRatio());
            switch (item.getAssetTypeId()) {
                case 1:
                    tvCompany.setText("委托量(TGM)");
                    break;
                case 2:
                case 3:
                    tvCompany.setText("委托量(USDT)");
                    break;
            }
            switch (item.getStatus()) {
                case 1:
                    llState.setVisibility(View.VISIBLE);
                    tvSuccess.setVisibility(View.VISIBLE);
                    tvSuccess.setText(R.string.in_the_pending_order);
                    tvDividendState.setText(R.string.cancel_order);
                    tvDividendState.setOnClickListener(DividendAdapter.this);
                    tvDividendState.setTag(R.string.value1, position);
                    tvDividendState.setTag(R.string.value2, 1);
                    break;
                case 3:
                    llState.setVisibility(View.GONE);
                    tvSuccess.setVisibility(View.VISIBLE);
                    tvSuccess.setText(R.string.all_success);
                    break;
                case 4:
                    llState.setVisibility(View.GONE);
                    tvSuccess.setVisibility(View.VISIBLE);
                    tvSuccess.setText(R.string.revocation);
                    break;
                case 5:
                    llState.setVisibility(View.VISIBLE);
                    tvSuccess.setVisibility(View.VISIBLE);
                    tvSuccess.setText(R.string.partial_deal);
                    tvDividendState.setText(R.string.cancel_order);
                    tvDividendState.setOnClickListener(DividendAdapter.this);
                    tvDividendState.setTag(R.string.value1, position);
                    tvDividendState.setTag(R.string.value2, 1);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dividend_state:
                myClicker.myClick(v, 0);
                break;
        }
    }

}
