package com.jh.wxqb.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.base.NewsBean;
import com.jh.wxqb.bean.MarketDividendBottomBean;
import com.jh.wxqb.ui.home.NewsInfoActivity;
import com.jh.wxqb.ui.home.NewsListActivity;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int HEAD = 0;//头部
    private static final int NEW = 1;//新闻
    private static final int TRANSACTION_TITLE = 2;//最近成交头部
    private static final int TRANSACTION = 3;//最近成交
    private NewsBean newsBeans;//新闻列表
    private MarketDividendBottomBean marketDividendBottomBean;//最近成交记录

    private Context context;
    private LayoutInflater inflater;
    private int newsTotalNum = 0;//消息总条数
    private int bottomItemTotalNum = 0;//成交记录总条数

    public HomePageAdapter(Context context, NewsBean newsBeans, MarketDividendBottomBean marketDividendBottomBean) {
        this.context = context;
        this.newsBeans = newsBeans;
        this.marketDividendBottomBean = marketDividendBottomBean;
        inflater = LayoutInflater.from(context);
        if (newsBeans != null) {
            if (newsBeans.getData() != null) {
                if (newsBeans.getData().getNoticeMap() != null) {
                    if (newsBeans.getData().getNoticeMap().size() > 0) {
                        newsTotalNum = newsBeans.getData().getNoticeMap().size() > 2 ? 2 : newsBeans.getData().getNoticeMap().size();
                    }
                }
            }
        }
        if (marketDividendBottomBean != null) {
            if (marketDividendBottomBean.getData() != null) {
                if (marketDividendBottomBean.getData().getList() != null) {
                    if (marketDividendBottomBean.getData().getList().size() > 0) {
                        bottomItemTotalNum = marketDividendBottomBean.getData().getList().size();
                    }
                }
            }
        }

    }


    /**
     * 更新数据
     *
     * @param newsBeans
     * @param marketDividendBottomBean
     */
    public void UpHomeData(NewsBean newsBeans, MarketDividendBottomBean marketDividendBottomBean) {
        this.newsBeans = newsBeans;
        this.marketDividendBottomBean = marketDividendBottomBean;
        if (newsBeans != null) {
            if (newsBeans.getData() != null) {
                if (newsBeans.getData().getNoticeMap() != null) {
                    if (newsBeans.getData().getNoticeMap().size() > 0) {
                        newsTotalNum = newsBeans.getData().getNoticeMap().size() > 2 ? 2 : newsBeans.getData().getNoticeMap().size();
                    }
                }
            }
        }
        if (marketDividendBottomBean != null) {
            if (marketDividendBottomBean.getData() != null) {
                if (marketDividendBottomBean.getData().getList() != null) {
                    if (marketDividendBottomBean.getData().getList().size() > 0) {
                        bottomItemTotalNum = marketDividendBottomBean.getData().getList().size();
                    }
                }
            }
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEAD) {
            View view = inflater.inflate(R.layout.item_head, parent, false);
            MyViewHolderHead myViewHolderHead = new MyViewHolderHead(view);
            myViewHolderHead.imageShowMore.setOnClickListener(this);
            return myViewHolderHead;
        } else if (viewType == NEW) {
            View view = inflater.inflate(R.layout.item_news, parent, false);
            MyViewHolderNews myViewHolderNews = new MyViewHolderNews(view);
            myViewHolderNews.llNewsClick.setOnClickListener(this);
            return myViewHolderNews;
        } else if (viewType == TRANSACTION_TITLE) {
            View view = inflater.inflate(R.layout.item_transaction_title, parent, false);
            return new MyViewHolderTitle(view);
        } else if (viewType == TRANSACTION) {
            View view = inflater.inflate(R.layout.item_transaction, parent, false);
            return new MyViewHolderBottom(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolderNews) {
            ((MyViewHolderNews) holder).llNewsClick.setTag(position - 1);//点击查看详情
            ((MyViewHolderNews) holder).tvNewsTitle.setText(newsBeans.getData().getNoticeMap().get(position - 1).getTitle());//新闻标题
            ((MyViewHolderNews) holder).tvNewsDate.setText(newsBeans.getData().getNoticeMap().get(position - 1).getCreateDate());//新闻时间
        } else if (holder instanceof MyViewHolderBottom) {
            BigDecimal one = new BigDecimal("1");
            MarketDividendBottomBean.DataBean.ListBean listBeans = marketDividendBottomBean.getData().getList().get(position - newsTotalNum - 2);
            switch (listBeans.getDirection()) {
                case 1:
                    ((MyViewHolderBottom) holder).tvType.setTextColor(ContextCompat.getColor(context, R.color.colorTextBuy));
                    ((MyViewHolderBottom) holder).tvType.setText(R.string.dividend);
                    ((MyViewHolderBottom) holder).tvTurnover.setText(listBeans.getAmountPrice().divide(one, 4, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
                    break;
                case 2:
                    ((MyViewHolderBottom) holder).tvType.setTextColor(ContextCompat.getColor(context, R.color.colorTextSell));
                    ((MyViewHolderBottom) holder).tvType.setText(R.string.sell);
                    ((MyViewHolderBottom) holder).tvTurnover.setText(listBeans.getAmountPrice().divide(one, 4, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
                    break;
            }
            switch (listBeans.getOrderStatus()) {
                case 1://挂单中
                    ((MyViewHolderBottom) holder).tvStatus.setText(R.string.in_the_pending_order);
                    break;
                case 3://全部成交
                    ((MyViewHolderBottom) holder).tvStatus.setText(R.string.all_success);
                    break;
                case 4://已撤销
                    ((MyViewHolderBottom) holder).tvStatus.setText(R.string.revocation);
                    break;
                case 5://部分成交
                    ((MyViewHolderBottom) holder).tvStatus.setText(R.string.partial_deal);
                    break;
            }
            ((MyViewHolderBottom) holder).tvTime.setText(listBeans.getUpdateTime());
            ((MyViewHolderBottom) holder).tvNum.setText(listBeans.getAccountCommission().divide(one, 2, BigDecimal.ROUND_DOWN).doubleValue() + "");
        }
    }

    @Override
    public int getItemCount() {
        return newsTotalNum + bottomItemTotalNum + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD;
        } else if (position <= newsTotalNum) {
            return NEW;
        } else if (position <= newsTotalNum + 1) {
            return TRANSACTION_TITLE;
        } else {
            return TRANSACTION;
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_news_click://新闻点击查看详情
                intent = new Intent(context, NewsInfoActivity.class);
                intent.putExtra("id", newsBeans.getData().getNoticeMap().get((Integer) view.getTag()).getId() + "");
                context.startActivity(intent);
                break;
            case R.id.show_more://查看更多
                intent = new Intent(context, NewsListActivity.class);
                context.startActivity(intent);
                break;
        }
    }

    class MyViewHolderHead extends RecyclerView.ViewHolder {

        @BindView(R.id.show_more)
        ImageView imageShowMore;

        public MyViewHolderHead(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MyViewHolderTitle extends RecyclerView.ViewHolder {

        public MyViewHolderTitle(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MyViewHolderNews extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_news_click)
        RelativeLayout llNewsClick;
        @BindView(R.id.tv_news_title)
        TextView tvNewsTitle;
        @BindView(R.id.tv_news_date)
        TextView tvNewsDate;

        public MyViewHolderNews(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MyViewHolderBottom extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_turnover)
        TextView tvTurnover;
        @BindView(R.id.tv_status)
        TextView tvStatus;

        public MyViewHolderBottom(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
