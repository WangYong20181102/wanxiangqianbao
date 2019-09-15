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
 * 个人中心--卖出
 */

public class MeSellAdapter extends RecyclerView.Adapter<MeSellAdapter.ViewHolder> implements View.OnClickListener {


    private Context mContext;
    private List<MeDividend.DataBean.ListBean> meSellBeen;
    private MyClicker myClicker;

    public MeSellAdapter(Context mContext, List<MeDividend.DataBean.ListBean> meSellBeen, MyClicker myClicker) {
        this.mContext = mContext;
        this.meSellBeen = meSellBeen;
        this.myClicker = myClicker;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置自适应布局
        AutoUtils.autoSize(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_me_sell, null));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_me_sell, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(meSellBeen.get(position));
        holder.refurbish(position);
    }

    @Override
    public int getItemCount() {
        return meSellBeen.size() > 0 ? meSellBeen.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        MeDividend.DataBean.ListBean item;

        public void setItem(MeDividend.DataBean.ListBean item) {
            this.item = item;
        }

        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_delegate_quantity)
        TextView tvDelegateQuantity;
        @BindView(R.id.tv_volume)
        TextView tvVolume;
        @BindView(R.id.tv_total_turnover)
        TextView tvTotalTurnover;
        @BindView(R.id.tv_order_state)
        TextView tvOrderState;
        @BindView(R.id.tv_cancel_order)
        TextView tvCancelOrder;
        @BindView(R.id.tv_success)
        TextView tvSuccess;
        @BindView(R.id.ll_register)
        LinearLayout llRegister;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        //1買入中2.停止買入3.停止排队4.挂单中5.已成交6.已撤销7.排队中
        public void refurbish(final int position) {
            BigDecimal one=new BigDecimal("1");
            tvTime.setText(TimeUtil.getTime(item.getCreateDate()));
            tvDelegateQuantity.setText(String.valueOf(item.getAccountCommission().divide(one,1,BigDecimal.ROUND_HALF_UP)));
            tvVolume.setText(String.valueOf(item.getAcountTransaction().divide(one,2,BigDecimal.ROUND_HALF_UP)));
            tvTotalTurnover.setText(String.valueOf(item.getAccountCommission().divide(one,2,BigDecimal.ROUND_HALF_UP)));

            tvCancelOrder.setOnClickListener(MeSellAdapter.this);
            tvCancelOrder.setTag(position);

            switch (item.getStatus()) {
                case 4:
                    llRegister.setVisibility(View.VISIBLE);
                    tvSuccess.setVisibility(View.GONE);
                    break;
                case 5:
                    llRegister.setVisibility(View.GONE);
                    tvSuccess.setVisibility(View.VISIBLE);
                    tvSuccess.setText(R.string.have_done_deal);
                    break;
                case 6:
                    llRegister.setVisibility(View.GONE);
                    tvSuccess.setVisibility(View.VISIBLE);
                    tvSuccess.setText(R.string.revocation);
                    break;
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel_order:
                myClicker.myClick(v, 0);
                break;
        }
    }

}
