package com.jh.wxqb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jh.wxqb.R;
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

public class FinancialTypeAdapter extends RecyclerView.Adapter<FinancialTypeAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<FinancialDetailsTypeBean.DataBean.AssetTypeListBean> assetTypeListBeen;
    private MyClicker myClick;
    public FinancialTypeAdapter(Context mContext, List<FinancialDetailsTypeBean.DataBean.AssetTypeListBean> assetTypeListBeen,MyClicker myClick) {
        this.mContext = mContext;
        this.assetTypeListBeen = assetTypeListBeen;
        this.myClick=myClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置自适应布局
        AutoUtils.autoSize(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_financial_type_sel, null));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_financial_type_sel, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(assetTypeListBeen.get(position));
        holder.refurbish(position);
    }

    @Override
    public int getItemCount() {
        return assetTypeListBeen.size() > 0 ? assetTypeListBeen.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        FinancialDetailsTypeBean.DataBean.AssetTypeListBean item;

        public void setItem(FinancialDetailsTypeBean.DataBean.AssetTypeListBean item) {
            this.item = item;
        }
        @BindView(R.id.tv_type)
        TextView tvType;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void refurbish(final int position) {
            tvType.setText(item.getName());
            tvType.setOnClickListener(FinancialTypeAdapter.this);
            tvType.setTag(position);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_type:
                myClick.myClick(v,0);
                break;
        }
    }

}
