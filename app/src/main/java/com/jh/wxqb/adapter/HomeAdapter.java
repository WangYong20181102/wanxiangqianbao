package com.jh.wxqb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.HomeBean;
import com.jh.wxqb.utils.MyClicker;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/11 0011.
 * 首页适配器
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<HomeBean> homeBeen;
    private MyClicker myClicker;

    public HomeAdapter(Context mContext, List<HomeBean> homeBeen, MyClicker myClicker) {
        this.mContext = mContext;
        this.homeBeen = homeBeen;
        this.myClicker = myClicker;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置自适应布局
        AutoUtils.autoSize(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, null));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.setItem(homeBeen.get(position));
//        holder.refurbish(position);
    }

    @Override
    public int getItemCount() {
        return homeBeen.size() > 0 ? homeBeen.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void refurbish(final int position) {
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
