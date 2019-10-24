package com.jh.wxqb.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.bean.CurrentPriceBean;
import com.jh.wxqb.bean.MarketDividendBottomBean;
import com.jh.wxqb.bean.MarketDividendTitleBean;
import com.jh.wxqb.customview.CancelOrOkDialog;
import com.jh.wxqb.customview.HandicapView;
import com.jh.wxqb.ui.market.CurrentEntrustmentActivity;
import com.jh.wxqb.ui.market.MoreBusinessActivity;
import com.jh.wxqb.ui.market.PurchaseOrderActivity;
import com.jh.wxqb.ui.me.UdpPwdActivity;
import com.jh.wxqb.utils.DialogUtil;
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
 * 交易市场适配器
 */

public class MarketPlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
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
    private String expectedAccess = "0.0";  //所有预计获取值
    private Intent intent;
    //交易额
    private double v1;
    //判断是刷新界面还是点击买入卖出按钮事件 1 (买入卖出) 0 (adapter刷新)
    private int showType;
    //保存单价用于判断单价新老数值用于取消百分比选中状态
    private String strSavePrices = "";
    //保存数量用于判断数量新老数值用于取消百分比选中状态
    private String strSaveNum = "";
    //初始化显示买入价格
    private boolean bPrices;
    //选中状态
    private boolean bSelect = false;

    public MarketPlaceAdapter(Context mContext, String type,
                              MarketDividendTitleBean.DataBean.ListBean buyListBean,
                              List<MarketDividendBottomBean.DataBean.ListBean> listBeen,
                              CurrentPriceBean currentPriceBean, int showType) {
        this.mActivity = (Activity) mContext;
        this.mContext = mContext;
        this.type = type;
        this.buyListBean = buyListBean;
        this.listBeen = listBeen;
        this.showType = showType;
        this.currentPriceBean = currentPriceBean;
        bPrices = true;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * 跟新数据
     *
     * @param mContext
     * @param type
     * @param buyListBean
     * @param listBeen
     * @param currentPriceBean
     */
    public void updateList(Context mContext, String type,
                           MarketDividendTitleBean.DataBean.ListBean buyListBean,
                           List<MarketDividendBottomBean.DataBean.ListBean> listBeen,
                           CurrentPriceBean currentPriceBean, int showType) {

        this.mActivity = (Activity) mContext;
        this.mContext = mContext;
        this.type = type;
        this.buyListBean = buyListBean;
        this.listBeen = listBeen;
        this.showType = showType;
        this.currentPriceBean = currentPriceBean;
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
                View itemTitle = inflater.inflate(R.layout.item_market_title, parent, false);
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
    public void setCurrentPrice(CurrentPriceBean currentPriceBean, String type, int showType) {
        this.currentPriceBean = currentPriceBean;
        this.type = type;
        this.showType = showType;
    }


    public class TitleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ed_entrust_num)
        EditText edEntrustNum;
        @BindView(R.id.linear_entrust)
        LinearLayout linearEntrust;
        @BindView(R.id.tv_expected)
        TextView tvExpected;
        @BindView(R.id.tv_current_balance)
        TextView tvCurrentBalance;
        @BindView(R.id.tv_dividends)
        TextView tvDividends;
        @BindView(R.id.tv_current)
        EditText tvCurrent;
        @BindView(R.id.tv_balance)
        TextView tvBalance;
        @BindView(R.id.tv_company)
        TextView tvCompany;
        @BindView(R.id.ll_sel_more)
        RelativeLayout llSelMore;
        @BindView(R.id.handicap_view)
        HandicapView handicapView;
        @BindView(R.id.tv_cny)
        TextView tvCny;
        @BindView(R.id.tv_percentage_point1)
        TextView tvPercentagePoint1;
        @BindView(R.id.tv_percentage_point2)
        TextView tvPercentagePoint2;
        @BindView(R.id.tv_percentage_point3)
        TextView tvPercentagePoint3;
        @BindView(R.id.tv_percentage_point4)
        TextView tvPercentagePoint4;

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

        private MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FootHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_show_more)
        RelativeLayout rlShowMore;

        private FootHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setTitle(final TitleHolder holder) {
        titleHolder = holder;
        if (bPrices) {//第一次进入用于显示买1或者卖1价格
            switch (type) {
                case "dividend":
                    if (buyListBean != null) {
                        if (buyListBean.getBuyList().size() > 0) {
                            strSavePrices = String.valueOf(buyListBean.getSellList().get(0).getAmountPrice().doubleValue());
                            holder.tvCurrent.setText(strSavePrices);
                            bPrices = false;
                        }
                    }
                    break;
                case "sell":
                    if (buyListBean != null) {
                        if (buyListBean.getSellList().size() > 0) {
                            strSavePrices = String.valueOf(buyListBean.getBuyList().get(0).getAmountPrice().doubleValue());
                            holder.tvCurrent.setText(strSavePrices);
                            bPrices = false;
                        }
                    }
                    break;
            }
        }
        if (showType == 1) {//切换买入卖出按钮清除百分比选中状态
            bSelect = false;
            clearSelection();
            titleHolder.edEntrustNum.setText("");
            switch (type) {
                case "dividend":
                    if (buyListBean != null) {
                        if (buyListBean.getBuyList().size() > 0) {
                            strSavePrices = String.valueOf(buyListBean.getSellList().get(0).getAmountPrice().doubleValue());
                            holder.tvCurrent.setText(strSavePrices);
                        }
                    }
                    break;
                case "sell":
                    if (buyListBean != null) {
                        if (buyListBean.getSellList().size() > 0) {
                            strSavePrices = String.valueOf(buyListBean.getBuyList().get(0).getAmountPrice().doubleValue());
                            holder.tvCurrent.setText(strSavePrices);
                        }
                    }
                    break;
            }
        }
        switch (type) {
            case "dividend":
                holder.tvExpected.setTextColor(Color.parseColor("#03AD8F"));
                holder.tvBalance.setTextColor(Color.parseColor("#03AD8F"));
                holder.tvCompany.setTextColor(Color.parseColor("#03AD8F"));
                holder.tvCurrentBalance.setTextColor(Color.parseColor("#03AD8F"));
                holder.tvDividends.setBackgroundResource(R.drawable.market_buy_bg);
                holder.tvDividends.setText(R.string.access_to_dividends);
                holder.tvBalance.setText(R.string.current_balance);

                break;
            case "sell":
                holder.tvExpected.setTextColor(Color.parseColor("#D14B64"));
                holder.tvBalance.setTextColor(Color.parseColor("#D14B64"));
                holder.tvCurrentBalance.setTextColor(Color.parseColor("#D14B64"));
                holder.tvDividends.setBackgroundResource(R.drawable.market_sell_bg);
                holder.tvCompany.setTextColor(Color.parseColor("#D14B64"));
                holder.tvDividends.setText(R.string.sell);
                holder.tvBalance.setText(R.string.current_balance);
                break;
        }
        if (currentPriceBean != null) {
            if (currentPriceBean.getData() != null) {
                if (currentPriceBean.getData().getResultMap() != null) {
                    switch (type) {
                        case "dividend":
                            holder.tvCurrentBalance.setText(StringUtil.checkDoubleOrInt(currentPriceBean.getData().getUsdtAccount()) + " USDT");
                            break;
                        case "sell":
                            holder.tvCurrentBalance.setText(StringUtil.checkDoubleOrInt(currentPriceBean.getData().getTgmAccount()) + " TGM");
                            break;
                    }
                }
            }
        }
        holder.edEntrustNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!strSaveNum.equals(charSequence.toString())) {
                    if (!bSelect) {
                        clearSelection();
                    }
                }
                countExpected(charSequence.toString(), 2);

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        holder.tvCurrent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ("dividend".equals(type)) {//买入
                    if (!strSavePrices.equals(charSequence.toString())) {
                        if (!bSelect) {
                            clearSelection();
                        }
                    }
                }
                countExpected(charSequence.toString(), 1);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //盘口
        if (buyListBean != null) {
            titleHolder.handicapView.updateData(buyListBean);
        }

        holder.llSelMore.setOnClickListener(this);
        holder.tvDividends.setOnClickListener(this);
        holder.linearEntrust.setOnClickListener(this);
        holder.tvPercentagePoint1.setOnClickListener(this);
        holder.tvPercentagePoint2.setOnClickListener(this);
        holder.tvPercentagePoint3.setOnClickListener(this);
        holder.tvPercentagePoint4.setOnClickListener(this);

    }

    @SuppressLint("SetTextI18n")
    private void setMain(MainHolder holder, final int position) {
        BigDecimal one = new BigDecimal("1");
        switch (listBeen.get(position - 1).getDirection()) {
            case 1:
                holder.tvType.setTextColor(Color.parseColor("#03AD8F"));
                holder.tvType.setText(R.string.dividend);
                holder.tvTurnover.setText(listBeen.get(position - 1).getAmountPrice().divide(one, 4, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
                break;
            case 2:
                holder.tvType.setTextColor(Color.parseColor("#D14B64"));
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
        holder.tvTime.setText(listBeen.get(position - 1).getUpdateTime());
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
            case R.id.linear_entrust://交易記錄
                intent = new Intent(mContext, CurrentEntrustmentActivity.class);
                mActivity.startActivity(intent);
                break;
            case R.id.tv_dividends:
                if (!isFastClick()) {//防止误触发
                    return;
                }
                if (checkEditIsNull(titleHolder.tvCurrent) || checkEditIsNull(titleHolder.edEntrustNum)) {
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
                    switch (type) {
                        case "dividend":
                            if (buyListBean != null) {
                                if (buyListBean.getSellList() != null) {
                                    if (buyListBean.getSellList().size() > 0) {
                                        if (Double.valueOf(titleHolder.tvCurrent.getText().toString()) > buyListBean.getSellList().get(buyListBean.getSellList().size() - 1).getAmountPrice().doubleValue()) {
                                            Toasts.showShort("买入单价须等于或小于卖1单价");
                                            return;
                                        }
                                    } else {
                                        return;
                                    }
                                } else {
                                    return;
                                }
                            } else {
                                return;
                            }
                            break;
                        case "sell":
                            if (buyListBean != null) {
                                if (buyListBean.getBuyList() != null) {
                                    if (buyListBean.getBuyList().size() > 0) {
                                        if (Double.valueOf(titleHolder.tvCurrent.getText().toString()) < buyListBean.getBuyList().get(0).getAmountPrice().doubleValue()) {
                                            Toasts.showShort("卖出单价须等于或大于买1单价");
                                            return;
                                        }
                                    } else {
                                        return;
                                    }
                                } else {
                                    return;
                                }
                            } else {
                                return;
                            }
                            break;
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
            case R.id.tv_percentage_point1:
                bSelect = true;
                clearSelection();
                titleHolder.tvPercentagePoint1.setTextColor(ContextCompat.getColor(mContext, R.color.color_16263E));
                calculationTgmNumber(new BigDecimal(0.25));
                break;
            case R.id.tv_percentage_point2:
                bSelect = true;
                clearSelection();
                titleHolder.tvPercentagePoint2.setTextColor(ContextCompat.getColor(mContext, R.color.color_16263E));
                calculationTgmNumber(new BigDecimal(0.5));
                break;
            case R.id.tv_percentage_point3:
                bSelect = true;
                clearSelection();
                titleHolder.tvPercentagePoint3.setTextColor(ContextCompat.getColor(mContext, R.color.color_16263E));
                calculationTgmNumber(new BigDecimal(0.75));
                break;
            case R.id.tv_percentage_point4:
                bSelect = true;
                clearSelection();
                titleHolder.tvPercentagePoint4.setTextColor(ContextCompat.getColor(mContext, R.color.color_16263E));
                calculationTgmNumber(new BigDecimal(1));
                break;
        }
    }

    /**
     * 计算tgm数量
     *
     * @param d
     */
    @SuppressLint("SetTextI18n")
    private void calculationTgmNumber(BigDecimal d) {
        if (currentPriceBean != null) {
            if (currentPriceBean.getData() != null) {
                switch (type) {
                    case "dividend":
                        //usdt余额
                        BigDecimal usdtNum = currentPriceBean.getData().getUsdtAccount();
                        BigDecimal d1 = usdtNum.multiply(d);
                        String inputPrice = titleHolder.tvCurrent.getText().toString().trim();
                        if (!TextUtils.isEmpty(inputPrice)) {
                            titleHolder.edEntrustNum.setText(d1.divide(new BigDecimal(inputPrice), 2, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
                        } else {
                            titleHolder.edEntrustNum.setText("0.0");
                        }
                        break;
                    case "sell":
                        //tgm余额
                        BigDecimal tgmNum = currentPriceBean.getData().getTgmAccount();
                        String d2 = String.valueOf(tgmNum.multiply(d).doubleValue());
                        titleHolder.edEntrustNum.setText(new BigDecimal(d2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
                        break;
                }
            }
        }
    }

    /**
     * 清除所有选中状态
     */
    private void clearSelection() {
        titleHolder.tvPercentagePoint1.setTextColor(ContextCompat.getColor(mContext, R.color.color_8b9cae));
        titleHolder.tvPercentagePoint2.setTextColor(ContextCompat.getColor(mContext, R.color.color_8b9cae));
        titleHolder.tvPercentagePoint3.setTextColor(ContextCompat.getColor(mContext, R.color.color_8b9cae));
        titleHolder.tvPercentagePoint4.setTextColor(ContextCompat.getColor(mContext, R.color.color_8b9cae));
    }

    /**
     * 显示支付密码弹框
     */
    private void showPayDialog() {
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
                clearSelection();
                titleHolder.edEntrustNum.setText(null);
                titleHolder.tvExpected.setText("0.0");
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
                clearSelection();
                titleHolder.edEntrustNum.setText(null);
                titleHolder.tvExpected.setText("0.0");
                expectedAccess = "0.0";
                break;
        }
    }

    /**
     * 判读输入框是否为空
     */
    private boolean checkEditIsNull(View v) {
        String str = ((TextView) v).getText().toString();
        switch (v.getId()) {
            case R.id.ed_entrust_num:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.input_number);
                    return true;
                }
                break;
            case R.id.ed_pwd:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_enter_the_tran_password);
                    return true;
                }
            case R.id.tv_current:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_enter_the_tran_price);
                    return true;
                }
                break;
        }
        return false;
    }

    //计算预计获得 iType(1单价  2数量)
    @SuppressLint("SetTextI18n")
    private void countExpected(String strPrice, int iType) {
        bSelect = false;
        if (strPrice.equals("")) {
            titleHolder.tvExpected.setText("0.0");
            if (iType == 1) {//单价输入框
                titleHolder.tvCny.setText("≈¥ 0.0CNY");
            }
            return;
        }
        //判断输入第一位不能为小数点（BigDecimal第一位小数点无法编译）
        if (strPrice.length() == 1 && strPrice.equals(".")) {
            switch (iType) {
                case 1:
                    titleHolder.tvCurrent.setText("");
                    break;
                case 2:
                    titleHolder.edEntrustNum.setText("");
                    break;
            }
            return;
        }
        switch (iType) {
            case 1:
                strSavePrices = strPrice;
                titleHolder.tvCny.setText("≈¥ " + new BigDecimal(titleHolder.tvCurrent.getText().toString()).multiply(new BigDecimal("7")).doubleValue() + "CNY");
                break;
            case 2:
                strSaveNum = strPrice;
                break;
        }
        BigDecimal one = new BigDecimal("1");
        BigDecimal decimal = new BigDecimal(Double.valueOf(strPrice));//输入值

        switch (type) {
            case "dividend":
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

    /**
     * 按钮触发时间控制
     *
     * @return
     */
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
