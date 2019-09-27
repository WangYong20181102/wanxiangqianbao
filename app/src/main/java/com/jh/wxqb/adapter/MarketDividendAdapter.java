package com.jh.wxqb.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.bean.CurrentPriceBean;
import com.jh.wxqb.bean.MarketDividendBottomBean;
import com.jh.wxqb.bean.MarketDividendTitleBean;
import com.jh.wxqb.customview.CancelOrOkDialog;
import com.jh.wxqb.ui.market.CurrentEntrustmentActivity;
import com.jh.wxqb.ui.market.MoreBusinessActivity;
import com.jh.wxqb.ui.market.PurchaseOrderActivity;
import com.jh.wxqb.ui.me.UdpPwdActivity;
import com.jh.wxqb.utils.DialogUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.SharedPreferencesUtil;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.TimeUtil;
import com.jh.wxqb.utils.Toasts;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/15 0015.
 * 買入市场--買入
 */

public class MarketDividendAdapter extends RecyclerView.Adapter implements View.OnClickListener, TextWatcher {

    // 两次点击按钮之间的点击间隔不能少于2000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 500;
    private static long lastClickTime;
    private LayoutInflater inflater;
    private static final int TITLE = 0;
    private static final int MAIN = 1;
    private static final int FOOT = 2;

    private Context mContext;
    private Activity mActivity;
    private String type;   //dividend:買入   sell：卖出
    private TitleHolder titleHolder;
    private MarketDividendTitleBean.DataBean.ListBean buyListBean;
    private List<MarketDividendBottomBean.DataBean.ListBean> listBeen;
    private CurrentPriceBean currentPriceBean;
    private String assetTypeId = "3";  //買入类型  1：活动ETH  2：重购USDT  3.買入USDT
    private PopupWindow optionWindow;
    private String selActiveType;  //选择買入类型文字记录   1活动ETH  2重购USDT  3.買入USDT
    private String expectedAccess = "0.0";  //所有预计获取值
    private int typeText;  //标识当前买卖  选择类型文字改变  0全部 1買入 2卖出
    private String isUdpTypeText;  //标识当前买卖是否修改  0不修改  1修改
    private Intent intent;
    private double v1;

    public MarketDividendAdapter(Context mContext, Activity mActivity, String type,
                                 MarketDividendTitleBean.DataBean.ListBean buyListBean,
                                 List<MarketDividendBottomBean.DataBean.ListBean> listBeen,
                                 CurrentPriceBean currentPriceBean, String selActiveType,
                                 String assetTypeId, int typeText, String isUdpTypeText) {
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.type = type;
        this.buyListBean = buyListBean;
        this.listBeen = listBeen;
        this.currentPriceBean = currentPriceBean;
        this.selActiveType = selActiveType;
        this.assetTypeId = assetTypeId;
        this.typeText = typeText;
        this.isUdpTypeText = isUdpTypeText;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * 跟新数据
     *
     * @param mContext
     * @param mActivity
     * @param type
     * @param buyListBean
     * @param listBeen
     * @param currentPriceBean
     * @param selActiveType
     * @param assetTypeId
     * @param typeText
     * @param isUdpTypeText
     */
    public void updateList(Context mContext, Activity mActivity, String type,
                           MarketDividendTitleBean.DataBean.ListBean buyListBean,
                           List<MarketDividendBottomBean.DataBean.ListBean> listBeen,
                           CurrentPriceBean currentPriceBean, String selActiveType,
                           String assetTypeId, int typeText, String isUdpTypeText) {

        this.mActivity = mActivity;
        this.mContext = mContext;
        this.type = type;
        this.buyListBean = buyListBean;
        this.listBeen = listBeen;
        this.currentPriceBean = currentPriceBean;
        this.selActiveType = selActiveType;
        this.assetTypeId = assetTypeId;
        this.typeText = typeText;
        this.isUdpTypeText = isUdpTypeText;
    }

    @Override
    public int getItemCount() {
        return listBeen.size() < 5 ? listBeen.size() + 1 : 7;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TITLE;
        } else if (position < 6) {
            return MAIN;
        } else {
            return FOOT;
        }
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TITLE:
                View itemTitle = inflater.inflate(R.layout.item_market_dividend_title, parent, false);
                return new TitleHolder(itemTitle);
            case MAIN:
                View itemMain = inflater.inflate(R.layout.item_market_dividend_main, parent, false);
                return new MainHolder(itemMain);
            case FOOT:
                View itemFoot = inflater.inflate(R.layout.item_market_dividend_foot, parent, false);
                return new FootHolder(itemFoot);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleHolder) {
            setTitle((TitleHolder) holder);
        } else if (holder instanceof MainHolder) {
            setMain((MainHolder) holder, position);
        } else if (holder instanceof FootHolder) {
            setFoot((FootHolder) holder);
        }
    }

    /**
     * 查看更多
     *
     * @param holder
     */
    private void setFoot(FootHolder holder) {
        holder.rlShowMore.setOnClickListener(this);
    }

    /**
     * 當前價格
     *
     * @param currentPriceBean
     */
    public void setCurrentPrice(CurrentPriceBean currentPriceBean) {
        this.currentPriceBean = currentPriceBean;
    }


    public class TitleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ed_entrust_num)
        EditText edEntrustNum;
        @BindView(R.id.ll_type)
        LinearLayout llType;
        @BindView(R.id.linear_entrust)
        LinearLayout linearEntrust;
        @BindView(R.id.tv_expected)
        TextView tvExpected;
        @BindView(R.id.tv_current_price)
        TextView tvCurrentPrice;
        @BindView(R.id.tv_current_balance)
        TextView tvCurrentBalance;
        @BindView(R.id.tv_entrust_type)
        TextView tvEntrustType;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.ed_pwd)
        EditText edPwd;
        @BindView(R.id.tv_dividends)
        TextView tvDividends;
        @BindView(R.id.tv_dividend_pkb)
        TextView tvDividendPkb;
        @BindView(R.id.tv_current)
        EditText tvCurrent;
        @BindView(R.id.tv_balance)
        TextView tvBalance;
        @BindView(R.id.tv_action)
        TextView tvAction;
        @BindView(R.id.tv_company)
        TextView tvCompany;

        @BindView(R.id.shop_up)
        RecyclerView shopUp;
        @BindView(R.id.shop_down)
        RecyclerView shopDown;
        @BindView(R.id.ll_sel_more)
        RelativeLayout llSelMore;
        @BindView(R.id.ll_sel_type)
        LinearLayout llSelType;
        @BindView(R.id.iv_down)
        ImageView ivDown;
        @BindView(R.id.iv_business_down)
        ImageView ivBusinessDown;

        TitleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MainHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_turnover)
        TextView tvTurnover;
        @BindView(R.id.tv_purchase)
        TextView tvPurchase;
        @BindView(R.id.tv_no)
        TextView tvNo;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FootHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_show_more)
        RelativeLayout rlShowMore;

        public FootHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setTitle(final TitleHolder holder) {
        titleHolder = holder;
        switch (selActiveType) {
            case "1":
//                holder.tvCurrent.setText(R.string.the_current_price);
                holder.tvBalance.setText(R.string.current_eth_balance);
                holder.tvEntrustType.setText(R.string.activity_eth);
//                if (currentPriceBean != null) {
//                    if (currentPriceBean.getData() != null) {
//                        if (currentPriceBean.getData().getResultMap() != null) {
//                            if (currentPriceBean.getData().getResultMap().getCurrentPrice() != null) {
//                                holder.tvCurrentPrice.setText(StringUtil.checkDoubleOrInt(currentPriceBean.getData().getResultMap().getCurrentPrice()));
//                            }
//                        }
//                    }
//                }
                break;
            case "2":
//                holder.tvCurrent.setText(R.string.the_current_pkb_price);
                holder.tvBalance.setText(R.string.current_usdt_balance);
                holder.tvEntrustType.setText(R.string.repurchase_usdt);
//                if (currentPriceBean != null) {
//                    if (currentPriceBean.getData() != null) {
//                        if (currentPriceBean.getData().getResultMap() != null) {
//                            if (currentPriceBean.getData().getResultMap().getCurrentPrice() != null) {
//                                holder.tvCurrentPrice.setText(StringUtil.checkDoubleOrInt(currentPriceBean.getData().getResultMap().getTodayPrice()));
//                            }
//                        }
//                    }
//                }
                break;
            case "3":
//                holder.tvCurrent.setText(R.string.the_current_pkb_price);
                holder.tvBalance.setText(R.string.current_usdt_balance);
                holder.tvEntrustType.setText(R.string.dividend_tgm);
//                if (currentPriceBean != null) {
//                    if (currentPriceBean.getData() != null) {
//                        if (currentPriceBean.getData().getResultMap() != null) {
//                            if (currentPriceBean.getData().getResultMap().getCurrentPrice() != null) {
//                                holder.tvCurrentPrice.setText(StringUtil.checkDoubleOrInt(currentPriceBean.getData().getResultMap().getTodayPrice()));
//                            }
//                        }
//                    }
//                }
                break;
        }
        switch (type) {
            case "dividend":
//                holder.tvDividendPkb.setVisibility(View.GONE);
//                holder.llType.setVisibility(View.VISIBLE);
                holder.tvExpected.setTextColor(Color.rgb(0, 204, 102));
//                holder.tvCurrent.setTextColor(Color.WHITE);
//                holder.tvCurrentPrice.setTextColor(Color.WHITE);
//                holder.tvCurrentPrice.setVisibility(View.GONE);
                holder.tvBalance.setTextColor(Color.rgb(0, 204, 102));
                holder.tvCompany.setTextColor(Color.rgb(0, 204, 102));
                holder.tvCurrentBalance.setTextColor(Color.rgb(0, 204, 102));
                holder.tvDividends.setBackgroundResource(R.drawable.market_buy_bg);
                holder.tvDividends.setText(R.string.access_to_dividends);
                holder.tvBalance.setText(R.string.current_eth_balance);
//                holder.tvType.setText(R.string.dividend);
//                titleHolder.tvCompany.setText("USDT");
                switch (assetTypeId) {
                    case "1":
                        holder.tvBalance.setText(R.string.current_eth_balance);
                        break;
                    case "2":
                        holder.tvBalance.setText(R.string.current_usdt_balance);
                        break;
                    case "3":
                        holder.tvBalance.setText(R.string.current_usdt_balance);
                        break;
                }
                break;
            case "sell":
//                holder.tvDividendPkb.setVisibility(View.VISIBLE);
                holder.llType.setVisibility(View.GONE);
                holder.tvExpected.setTextColor(Color.parseColor("#d6734b"));
//                holder.tvCurrent.setTextColor(Color.WHITE);
//                holder.tvCurrentPrice.setTextColor(Color.WHITE);
//                holder.tvCurrentPrice.setVisibility(View.VISIBLE);
                holder.tvBalance.setTextColor(Color.parseColor("#d6734b"));
                holder.tvCurrentBalance.setTextColor(Color.parseColor("#d6734b"));
                holder.tvDividends.setBackgroundResource(R.drawable.market_sell_bg);
                holder.tvCompany.setTextColor(Color.parseColor("#d6734b"));
//                holder.tvType.setText(R.string.sell);
                holder.tvDividends.setText(R.string.sell);
                holder.tvBalance.setText(R.string.current_tgm_balance);
//                titleHolder.tvCompany.setText("USDT");
//                holder.tvCurrentPrice.setText("USDT");
                break;
        }
        LogUtils.e("isUdpTypeText==>" + isUdpTypeText);
        //单价 tvPrice.setText(String.valueOf(item.getSellList().get(position).getAmountPrice().divide(one,4,BigDecimal.ROUND_HALF_UP).doubleValue()));
        if (isUdpTypeText.equals("1")) {
            switch (typeText) {
                case 0:
                    holder.tvType.setText(R.string.all);
                    break;
                case 1:
                    holder.tvType.setText(R.string.dividend);
                    break;
                case 2:
                    holder.tvType.setText(R.string.sell);
                    break;
            }
        }

        if (currentPriceBean != null) {
            if (currentPriceBean.getData() != null) {
                if (currentPriceBean.getData().getResultMap() != null) {
                    switch (type) {
                        case "dividend":
                            holder.tvCurrentBalance.setText(StringUtil.checkDoubleOrInt(currentPriceBean.getData().getUsdtAccount()));
                            break;
                        case "sell":
                            holder.tvCurrentBalance.setText(StringUtil.checkDoubleOrInt(currentPriceBean.getData().getTgmAccount()));
                            break;
                    }
                }
            }
        }

        holder.edEntrustNum.addTextChangedListener(this);
        holder.tvCurrent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                countExpected(charSequence.toString(), 1);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setStackFromEnd(true);
        holder.shopUp.setLayoutManager(manager);
        MarketDividendTitleAdapter titleUpAdapter = new MarketDividendTitleAdapter(mContext, true, buyListBean);
        holder.shopUp.setAdapter(titleUpAdapter);

        holder.shopDown.setLayoutManager(new LinearLayoutManager(mContext));
        MarketDividendTitleAdapter titleDownAdapter = new MarketDividendTitleAdapter(mContext, false, buyListBean);
        holder.shopDown.setAdapter(titleDownAdapter);

        holder.llType.setOnClickListener(this);
//        holder.llSelType.setOnClickListener(this);
        holder.llSelMore.setOnClickListener(this);
        holder.tvDividends.setOnClickListener(this);
        holder.linearEntrust.setOnClickListener(this);

    }

    private void setMain(MainHolder holder, final int position) {
        MainHolder mainHolder = holder;
        BigDecimal one = new BigDecimal("1");
        switch (listBeen.get(position - 1).getDirection()) {
            case 1:
//                holder.tvPurchase.setVisibility(View.GONE);
//                holder.tvNo.setVisibility(View.VISIBLE);
                holder.tvType.setTextColor(Color.rgb(0, 204, 102));
                holder.tvType.setText(R.string.dividend);
                holder.tvTurnover.setText(listBeen.get(position - 1).getAmountPrice().divide(one, 4, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
                break;
            case 2:
//                holder.tvPurchase.setVisibility(View.VISIBLE);
//                holder.tvNo.setVisibility(View.GONE);
                holder.tvType.setTextColor(Color.parseColor("#d6734b"));
                holder.tvType.setText(R.string.sell);
                holder.tvTurnover.setText(listBeen.get(position - 1).getAmountPrice().divide(one, 4, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
                break;
        }
        switch (listBeen.get(position - 1).getOrderStatus()) {
            case 1://挂单中
                holder.tvNo.setText(R.string.in_the_pending_order);
                break;
            case 3://全部成交
                holder.tvNo.setText(R.string.all_success);
                break;
            case 4://已撤销
                holder.tvNo.setText(R.string.revocation);
                break;
            case 5://部分成交
                holder.tvNo.setText(R.string.partial_deal);
                break;
        }
        holder.tvTime.setText(TimeUtil.getTime(listBeen.get(position - 1).getCreateDate()));
        String unit = "";
        switch (listBeen.get(position - 1).getAssetTypeId()) {
            case 1:
                unit = "TGM";
                break;
            case 2:
            case 3:
                unit = "USDT";
                break;
        }
        holder.tvNum.setText(listBeen.get(position - 1).getAccountCommission().divide(one, 2, BigDecimal.ROUND_DOWN).doubleValue() + "");
        holder.tvPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PurchaseOrderActivity.class);
                intent.putExtra("data", listBeen.get(position - 1));
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_sel_more:
                intent = new Intent(mContext, MoreBusinessActivity.class);
                mActivity.startActivity(intent);
                break;
            case R.id.rl_show_more://查看更多
                intent = new Intent(mContext, MoreBusinessActivity.class);
                mActivity.startActivity(intent);
                break;
            case R.id.ll_type:
                selEntrustType(titleHolder.llType);
                break;
            case R.id.ll_sel_type:
                selBusinessType(titleHolder.llSelType);
                break;
            case R.id.linear_entrust://交易記錄
                intent = new Intent(mContext, CurrentEntrustmentActivity.class);
                mActivity.startActivity(intent);
                break;
            case R.id.tv_activity_eth:
                optionWindow.dismiss();
                titleHolder.ivDown.setImageResource(R.drawable.iv_down);
                titleHolder.tvEntrustType.setText(R.string.activity_eth);
//                titleHolder.tvCurrent.setText(R.string.the_current_price);
                intent = new Intent();
                intent.putExtra("type", "udpCurrentPrice");
                intent.putExtra("coinType", "1");
                intent.putExtra("assetTypeId", "1");
                EventBus.getDefault().post(intent);
                break;
            case R.id.tv_repurchase_pkb:
                optionWindow.dismiss();
                titleHolder.ivDown.setImageResource(R.drawable.iv_down);
                titleHolder.tvEntrustType.setText(R.string.repurchase_usdt);
//                titleHolder.tvCurrent.setText(R.string.the_current_pkb_price);
                intent = new Intent();
                intent.putExtra("type", "udpCurrentPrice");
                intent.putExtra("coinType", "2");
                intent.putExtra("assetTypeId", "2");
                EventBus.getDefault().post(intent);
                break;
            case R.id.tv_dividend_pkb:
                optionWindow.dismiss();
                titleHolder.ivDown.setImageResource(R.drawable.iv_down);
                titleHolder.tvEntrustType.setText(R.string.dividend_tgm);
//                titleHolder.tvCurrent.setText(R.string.the_current_pkb_price);
                intent = new Intent();
                intent.putExtra("type", "udpCurrentPrice");
                intent.putExtra("coinType", "3");
                intent.putExtra("assetTypeId", "3");
                EventBus.getDefault().post(intent);
                break;
            case R.id.tv_all:
                optionWindow.dismiss();
                titleHolder.ivBusinessDown.setImageResource(R.drawable.iv_down);
                titleHolder.tvType.setText(R.string.all);
                intent = new Intent();
                intent.putExtra("type", "udpBusiness");
                intent.putExtra("typeNum", 0);
                intent.putExtra("isUdpTypeText", "1");
                EventBus.getDefault().post(intent);
                break;
            case R.id.tv_dividend:
                optionWindow.dismiss();
                titleHolder.ivBusinessDown.setImageResource(R.drawable.iv_down);
                titleHolder.tvType.setText(R.string.dividend);
                intent = new Intent();
                intent.putExtra("type", "udpBusiness");
                intent.putExtra("typeNum", 1);
                intent.putExtra("isUdpTypeText", "1");
                EventBus.getDefault().post(intent);
                break;
            case R.id.tv_sell:
                optionWindow.dismiss();
                titleHolder.ivBusinessDown.setImageResource(R.drawable.iv_down);
                titleHolder.tvType.setText(R.string.sell);
                intent = new Intent();
                intent.putExtra("type", "udpBusiness");
                intent.putExtra("typeNum", 2);
                intent.putExtra("isUdpTypeText", "1");
                EventBus.getDefault().post(intent);
                break;
            case R.id.tv_dividends:
//                Toasts.showShort("暫未開放此功能！");
                if (!isFastClick()) {//防止误触发
                    return;
                }
                if (!checkEdit(titleHolder.edEntrustNum) || !checkEdit(titleHolder.tvCurrent)) {
                    return;
                }
                if (MyApplication.getUserBean() != null) {
                    if (currentPriceBean != null) {
                        if (currentPriceBean.getData() != null) {
                            if (currentPriceBean.getData().getResultMap() != null) {
                                switch (type) {
                                    case "dividend":
                                        if (v1 > currentPriceBean.getData().getUsdtAccount().doubleValue()) {
                                            Toasts.showShort("餘額不足");
                                            return;
                                        }
                                        break;
                                    case "sell":
                                        if (v1 > currentPriceBean.getData().getTgmAccount().doubleValue()) {
                                            Toasts.showShort("餘額不足");
                                            return;
                                        }
                                        break;
                                }
                            }
                        }
                    }
                    if (type.equals("sell")) {
                        if (buyListBean != null) {
                            if (buyListBean.getBuyList() != null) {
                                if (buyListBean.getBuyList().size() > 0) {
                                    if (Double.valueOf(titleHolder.tvCurrent.getText().toString()) < buyListBean.getBuyList().get(0).getAmountPrice().doubleValue()) {
                                        Toasts.showShort("卖出单价须大于或等于买1单价");
                                        return;
                                    }
                                }
                            }
                        } else {
                            return;
                        }
                    }
                    if (MyApplication.getUserBean().getIsHasTradePwd()) {
                        if (SharedPreferencesUtil.getPrefInt(mContext, "FirstVerification", 0) == 0) {//第一次输入支付密码
                            showPayDialog();
                        } else {
                            String oldTime = TimeUtil.getTimeByLong(SharedPreferencesUtil.getPrefLong(mContext, "dateTime", System.currentTimeMillis()));
                            if (TimeUtil.friendly_time(oldTime) > 24) {
                                showPayDialog();
                            } else {
                                buyOrSell();
                            }
                        }

                    } else {
                        CancelOrOkDialog cancelOrOkDialog = new CancelOrOkDialog(mContext, R.string.please_set_the_transaction_password_first) {
                            @Override
                            public void ok() {
                                super.ok();
                                Intent intent = new Intent(mContext, UdpPwdActivity.class);
                                intent.putExtra("type", "pay");
                                mContext.startActivity(intent);
                                dismiss();
                            }
                        };
                        cancelOrOkDialog.show();
                    }
                } else {
                    EventBus.getDefault().post("udpHome");
                }
                break;
        }

    }

    /**
     * 显示支付密码弹框
     */
    public void showPayDialog() {
        DialogUtil.customDialog(mContext, new DialogUtil.OnClickYesListener() {
            @Override
            public void onClickYes(String strPassword) {
                if (!TextUtils.isEmpty(strPassword)) {
                    SharedPreferencesUtil.setPrefString(mContext, "payPassword", strPassword);//存储支付密码
                    buyOrSell();
                } else {
                    Toasts.showShort(mContext.getResources().getString(R.string.please_input_pay_pwd));
                }
            }
        });
    }

    /**
     * 买入卖出
     */
    private void buyOrSell() {
        switch (type) {
            case "dividend":
                intent = new Intent();
                intent.putExtra("type", "openDividends");
                intent.putExtra("number", titleHolder.edEntrustNum.getText().toString());
                intent.putExtra("tradePassword", SharedPreferencesUtil.getPrefString(mContext, "payPassword", ""));
                intent.putExtra("currentPrice", titleHolder.tvCurrent.getText().toString());
                intent.putExtra("pendingCurrencyId", "2");
                intent.putExtra("exchangeCurrencyId", "3");
                intent.putExtra("acountTransaction", expectedAccess);
                EventBus.getDefault().post(intent);
                titleHolder.edEntrustNum.setText(null);
                titleHolder.edPwd.setText(null);
                titleHolder.tvExpected.setText(null);
                expectedAccess = "0.0";
                break;
            case "sell":
                intent = new Intent();
                intent.putExtra("type", "marketSell");
                intent.putExtra("number", titleHolder.edEntrustNum.getText().toString());
                intent.putExtra("tradePassword", SharedPreferencesUtil.getPrefString(mContext, "payPassword", ""));
                intent.putExtra("currentPrice", titleHolder.tvCurrent.getText().toString());
                intent.putExtra("pendingCurrencyId", "2");
                intent.putExtra("exchangeCurrencyId", "3");
                intent.putExtra("acountTransaction", expectedAccess);
                EventBus.getDefault().post(intent);
                titleHolder.edEntrustNum.setText(null);
                titleHolder.edPwd.setText(null);
                titleHolder.tvExpected.setText(null);
                expectedAccess = "0.0";
                break;
        }
    }

    /**
     * 判读输入框格式
     */
    private boolean checkEdit(View v) {
        String str = ((TextView) v).getText().toString();
        switch (v.getId()) {
            case R.id.ed_entrust_num:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.input_number);
                    return false;
                }
                break;
            case R.id.ed_pwd:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_enter_the_tran_password);
                    return false;
                }
            case R.id.tv_current:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_enter_the_tran_price);
                    return false;
                }
                break;
        }
        return true;
    }


    /**
     * 选择委托类型
     */
    @SuppressLint("WrongConstant")
    private void selEntrustType(LinearLayout view) {
        titleHolder.ivDown.setImageResource(R.drawable.iv_up);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_sel_entrusrtype, null);
        TextView tvActivityEth = (TextView) contentView.findViewById(R.id.tv_activity_eth);
        TextView tvRepurchasePkb = (TextView) contentView.findViewById(R.id.tv_repurchase_pkb);
        TextView tvDividendPkb = (TextView) contentView.findViewById(R.id.tv_dividend_pkb);
        tvActivityEth.setOnClickListener(this);
        tvRepurchasePkb.setOnClickListener(this);
        tvDividendPkb.setOnClickListener(this);
        optionWindow = new PopupWindow(contentView, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);
        optionWindow.setTouchable(true);
        optionWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));   //设置背景
        optionWindow.setOutsideTouchable(true);
        optionWindow.showAsDropDown(view, -15, 0);
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();    //将背景颜色参数重新设置
        lp.alpha = 0.5f;
        mActivity.getWindow().setAttributes(lp);
        optionWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        optionWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        optionWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);   //从底部弹出
        optionWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
    }

    /**
     * 选择买卖订单类型类型
     */
    @SuppressLint("WrongConstant")
    private void selBusinessType(LinearLayout view) {
        titleHolder.ivBusinessDown.setImageResource(R.drawable.iv_up);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_business_type, null);
        TextView tvAll = (TextView) contentView.findViewById(R.id.tv_all);
        TextView tvDividend = (TextView) contentView.findViewById(R.id.tv_dividend);
        TextView tvSell = (TextView) contentView.findViewById(R.id.tv_sell);
        tvAll.setOnClickListener(this);
        tvDividend.setOnClickListener(this);
        tvSell.setOnClickListener(this);
        optionWindow = new PopupWindow(contentView, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);
        optionWindow.setTouchable(true);
        optionWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));   //设置背景
        optionWindow.setOutsideTouchable(true);
        optionWindow.showAsDropDown(view, -15, 0);
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();    //将背景颜色参数重新设置
        lp.alpha = 0.5f;
        mActivity.getWindow().setAttributes(lp);
        optionWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        optionWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        optionWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);   //从底部弹出
        optionWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        countExpected(s.toString(), 2);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    //计算预计获得
    public void countExpected(String strPrice, int iType) {
        if (strPrice.equals("")) {
            titleHolder.tvExpected.setText("");
            return;
        }
        BigDecimal one = new BigDecimal("1");
        BigDecimal ten = new BigDecimal(Double.valueOf("10"));
        BigDecimal oneHundred = new BigDecimal(Double.valueOf("100"));
//        BigDecimal accelerationValue = MyApplication.getUserBean().getAccelerationValue();
//        BigDecimal oneHundredCount = accelerationValue.divide(oneHundred, 2, BigDecimal.ROUND_DOWN);  //换算加速值
        BigDecimal decimal = new BigDecimal(Double.valueOf(strPrice));//输入值

        switch (type) {
            case "dividend":
                switch (assetTypeId) {
                    case "1": //活动ETH    挂单数量*主流币的价格/子币的价格=A    （A*加速值*10）+A=最终计算结果
//                        if (StringUtil.isCheckNumber(strPrice)) {
//                            if (!StringUtil.isCheckNumZero(strPrice)) {
//                                if (currentPriceBean != null) {
//                                    if (currentPriceBean.getData() != null) {
//                                        if (currentPriceBean.getData().getResultMap() != null)
//                                            if (!TextUtils.isEmpty(currentPriceBean.getData().getResultMap().getConfigValue())) {
//                                                BigDecimal currentPrice = currentPriceBean.getData().getResultMap().getCurrentPrice();
//                                                BigDecimal multiply = decimal.multiply(currentPrice);
//                                                BigDecimal v = multiply.divide(currentPriceBean.getData().getResultMap().getTodayPrice(), 2, BigDecimal.ROUND_DOWN);
//
//                                                //服务器传参保留8位
//                                                BigDecimal pass = multiply.divide(currentPriceBean.getData().getResultMap().getTodayPrice(), 8, BigDecimal.ROUND_DOWN);
//                                                BigDecimal pass1 = pass.multiply(oneHundredCount).multiply(ten);
//                                                BigDecimal add1 = pass1.add(v);
//                                                expectedAccess = String.valueOf(add1.divide(one, 8, BigDecimal.ROUND_DOWN).doubleValue());
//
//                                                //APP展示保留2位
//                                                BigDecimal multiply1 = v.multiply(oneHundredCount).multiply(ten);
//                                                BigDecimal add = multiply1.add(v);
//                                                double v1 = add.divide(one, 2, BigDecimal.ROUND_DOWN).doubleValue();
//                                                titleHolder.tvExpected.setText("" + v1);
////                                                titleHolder.tvCompany.setText("USDT");
//                                            }
//                                    }
//                                }
//                            }
//                        }
                        break;
                    case "2"://重购USDT    挂单数量*子币的价格/子币的价格
//                        if (StringUtil.isCheckNumber(strPrice)) {
//                            if (!StringUtil.isCheckNumZero(strPrice)) {
//                                if (currentPriceBean != null) {
//                                    if (currentPriceBean.getData() != null) {
//                                        if (currentPriceBean.getData().getResultMap() != null) {
//                                            if (currentPriceBean.getData().getResultMap().getCurrentPrice() != null) {
//                                                BigDecimal multiply1 = decimal.multiply(oneHundredCount).multiply(ten);
//                                                BigDecimal add = multiply1.add(decimal);
//
//                                                expectedAccess = String.valueOf(add.divide(one, 8, BigDecimal.ROUND_DOWN).doubleValue());
//
//                                                double v1 = add.divide(one, 2, BigDecimal.ROUND_DOWN).doubleValue();
//                                                titleHolder.tvExpected.setText("" + v1);
////                                                titleHolder.tvCompany.setText("USDT");
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
                        break;
                    case "3"://買入USDT    挂单数量*子币的价格/子币的价格
                        if (StringUtil.isCheckNumber(strPrice)) {
                            if (!StringUtil.isCheckNumZero(strPrice)) {
                                if (currentPriceBean != null) {
                                    if (currentPriceBean.getData() != null) {
                                        if (currentPriceBean.getData().getResultMap() != null) {
                                            String configValue = currentPriceBean.getData().getResultMap().getConfigValue();
                                            if (!TextUtils.isEmpty(configValue)) {
                                                BigDecimal multiply1 = null;
                                                if (TextUtils.isEmpty(titleHolder.edEntrustNum.getText().toString()) || TextUtils.isEmpty(titleHolder.tvCurrent.getText().toString())) {
                                                    return;
                                                }
                                                switch (iType) {
                                                    case 1:
                                                        multiply1 = decimal.multiply(BigDecimal.valueOf(Double.valueOf(titleHolder.edEntrustNum.getText().toString())));
                                                        break;
                                                    case 2:
                                                        multiply1 = decimal.multiply(BigDecimal.valueOf(Double.valueOf(titleHolder.tvCurrent.getText().toString())));
                                                        break;
                                                }

                                                BigDecimal num = new BigDecimal(configValue);
                                                expectedAccess = String.valueOf(multiply1.divide(num, 8, BigDecimal.ROUND_HALF_UP).doubleValue());
                                                v1 = multiply1.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                                if (v1 > currentPriceBean.getData().getUsdtAccount().doubleValue()) {
                                                    Toasts.showShort("餘額不足");
                                                }
                                                titleHolder.tvExpected.setText("" + v1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                }
                break;
            case "sell":   //挂单数量*子币的价格/主流币价格
                if (StringUtil.isCheckNumber(strPrice)) {
                    if (!StringUtil.isCheckNumZero(strPrice)) {
                        if (currentPriceBean != null) {
                            if (currentPriceBean.getData() != null) {
                                if (currentPriceBean.getData().getResultMap() != null) {
                                    String configValue = currentPriceBean.getData().getResultMap().getConfigValue();
                                    if (!TextUtils.isEmpty(configValue)) {
                                        BigDecimal multiply = null;
                                        if (TextUtils.isEmpty(titleHolder.edEntrustNum.getText().toString()) || TextUtils.isEmpty(titleHolder.tvCurrent.getText().toString())) {
                                            return;
                                        }
                                        switch (iType) {
                                            case 1:
                                                multiply = decimal.multiply(BigDecimal.valueOf(Double.valueOf(titleHolder.edEntrustNum.getText().toString())));
                                                break;
                                            case 2:
                                                multiply = decimal.multiply(BigDecimal.valueOf(Double.valueOf(titleHolder.tvCurrent.getText().toString())));
                                                break;
                                        }
                                        BigDecimal num = new BigDecimal(configValue);
                                        expectedAccess = String.valueOf(multiply.divide(num, 8, BigDecimal.ROUND_HALF_UP).doubleValue());
                                        v1 = BigDecimal.valueOf(Double.valueOf(titleHolder.edEntrustNum.getText().toString())).divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                        double v2 = multiply.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                        if (v1 > currentPriceBean.getData().getTgmAccount().doubleValue()) {
                                            Toasts.showShort("餘額不足");
                                        }
                                        titleHolder.tvExpected.setText("" + v2);
                                    }
                                }
                            }
                        }
                    }
                }
                break;
        }
    }

    private static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
