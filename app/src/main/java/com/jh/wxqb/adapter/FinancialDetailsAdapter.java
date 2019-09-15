package com.jh.wxqb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.FinancialDetailsBean;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.TimeUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/11 0011.
 * 財務明細
 */

public class FinancialDetailsAdapter extends RecyclerView.Adapter<FinancialDetailsAdapter.ViewHolder> implements View.OnClickListener {


    private Context mContext;
    private List<FinancialDetailsBean.DataBean.LogListBean> financialDetailsBeen;

    public FinancialDetailsAdapter(Context mContext, List<FinancialDetailsBean.DataBean.LogListBean> financialDetailsBeen) {
        this.mContext = mContext;
        this.financialDetailsBeen = financialDetailsBeen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置自适应布局
        AutoUtils.autoSize(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_financial_details, null));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_financial_details, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(financialDetailsBeen.get(position));
        holder.refurbish(position);
    }

    @Override
    public int getItemCount() {
        return financialDetailsBeen.size() > 0 ? financialDetailsBeen.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        FinancialDetailsBean.DataBean.LogListBean item;

        public void setItem(FinancialDetailsBean.DataBean.LogListBean item) {
            this.item = item;
        }

        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.ll_number)
        LinearLayout llNumber;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_unit)
        TextView tvUnit;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void refurbish(final int position) {
            tvNumber.setText(item.getId());
            tvPhone.setText(item.getPhone());
            tvTime.setText(TimeUtil.getTime(item.getCreateTime()));
            switch (item.getTradeType()) {
                case 2:
                    tvType.setText(R.string.buy);
                    break;
                case 3:
                    tvType.setText(R.string.sell);
                    break;
                case 4:
                    tvType.setText(R.string.dividends_pkb_transferred_out);
                    break;
                case 5:
                    tvType.setText(R.string.dividends_pkb_transferred);
                    break;
                case 6:
                    tvType.setText(R.string.activity_eth_turned_out);
                    break;
                case 7:
                    tvType.setText(R.string.activity_eth_turned);
                    break;
                case 8:
                    tvType.setText(R.string.recharge);
                    break;
                case 9:  //充值撤回
                    tvType.setText(R.string.recharge_withdraw);
                    break;
                case 10: //停止買入（買入PKB）
                    tvType.setText(R.string.daily_dividend);
                    break;
                case 11: //分享奖励（買入PKB）
                    tvType.setText(R.string.sharing_rewards);
                    break;
                case 12:  //買入
                    tvType.setText(R.string.dividend);
                    break;
                case 13: //.取消買入（取消排队）
                    tvType.setText(R.string.cancel_the_purchase);
                    break;
                case 14:  //取消卖出
                    tvType.setText(R.string.cancel_the_sell);
                    break;
                case 15:  //卖出成功
                    tvType.setText(R.string.sell_success);
                    break;
                case 16:  //分享奖励（重购PKB）
                    tvType.setText(R.string.share_rewards);
                    break;
                case 17: //停止買入（重购PKB)
                    tvType.setText(R.string.share_stop_dividend);
                    break;
                case 18: //買入成功
                    tvType.setText(R.string.buying_success);
                    break;
                case 19: //内部活动资产转入
                    tvType.setText(R.string.transfer_internal_activity_assets);
                    break;
                case 20: //内部活动资产转出
                    tvType.setText(R.string.internal_activity_assets_transferred_out);
                    break;
            }
            switch (item.getAcctType()) {
                case 1:
                    tvUnit.setText("ETH");
                    break;
                case 2:
                case 3:
                    tvUnit.setText("PKB");
                    break;
            }
            String type = "";
            switch (item.getFlowType()) {
                case 1:
                    type = "+ ";
                    tvType.setTextColor(Color.rgb(23, 176, 62));
                    break;
                case 2:
                    type = "- ";
                    tvType.setTextColor(Color.rgb(204, 0, 0));
                    break;
            }
            tvNum.setText(type + StringUtil.checkDoubleOrInt(item.getTradeAmount()));

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.purchase_immediately:
//                myClicker.myClick(v,0);
//                break;
        }
    }

}
