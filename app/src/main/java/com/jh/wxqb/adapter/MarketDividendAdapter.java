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
import com.jh.wxqb.ui.market.MoreBusinessActivity;
import com.jh.wxqb.ui.market.PurchaseOrderActivity;
import com.jh.wxqb.ui.me.UdpPwdActivity;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.TimeUtil;
import com.jh.wxqb.utils.Toasts;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/15 0015.
 * 買入市场--買入
 */

public class MarketDividendAdapter extends RecyclerView.Adapter implements View.OnClickListener, TextWatcher {


    private LayoutInflater inflater;
    private static final int TITLE = 0;
    private static final int MAIN = 1;

    private Context mContext;
    private Activity mActivity;
    private String type;   //dividend:買入   sell：卖出
    private TitleHolder titleHolder;
    private MainHolder mainHolder;
    private MarketDividendTitleBean.DataBean.ListBean buyListBean;
    private List<MarketDividendBottomBean.DataBean.ListBean> listBeen;
    private CurrentPriceBean currentPriceBean;
    private String assetTypeId = "1";  //買入类型  1：活动ETH  2：重购PKB  3.買入PKB
    private PopupWindow optionWindow;
    private String selActiveType;  //选择買入类型文字记录   1活动ETH  2重购PKB  3.買入PKB
    private String expectedAccess = "0.0";  //所有预计获取值
    private int typeText;  //标识当前买卖  选择类型文字改变  0全部 1買入 2卖出
    private String isUdpTypeText;  //标识当前买卖是否修改  0不修改  1修改

    public MarketDividendAdapter(Context mContext, Activity mActivity, String type,
                                 MarketDividendTitleBean.DataBean.ListBean buyListBean,
                                 List<MarketDividendBottomBean.DataBean.ListBean> listBeen,
                                 CurrentPriceBean currentPriceBean, String selActiveType,
                                 String assetTypeId, int typeText,String isUdpTypeText) {
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

    @Override
    public int getItemCount() {
        return listBeen.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TITLE;
        } else if (position > 0) {
            return MAIN;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TITLE:
                View itemTitle = inflater.inflate(R.layout.item_market_dividend_title, parent, false);
                return new TitleHolder(itemTitle);
            case MAIN:
                View itemMain = inflater.inflate(R.layout.item_market_dividend_main, parent, false);
                return new MainHolder(itemMain);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleHolder) {
            setTitle((TitleHolder) holder);
        } else if (holder instanceof MainHolder) {
            setMain((MainHolder) holder, position);
        }
    }


    public class TitleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ed_entrust_num)
        EditText edEntrustNum;
        @BindView(R.id.ll_type)
        LinearLayout llType;
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
        TextView tvCurrent;
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

    private void setTitle(final TitleHolder holder) {
        titleHolder = holder;
        switch (selActiveType) {
            case "1":
                holder.tvCurrent.setText(R.string.the_current_price);
                holder.tvBalance.setText(R.string.current_eth_balance);
                holder.tvEntrustType.setText(R.string.activity_eth);
                if (currentPriceBean != null) {
                    if (currentPriceBean.getData() != null) {
                        if (currentPriceBean.getData().getResultMap() != null) {
                            if (currentPriceBean.getData().getResultMap().getCurrentPrice() != null) {
                                holder.tvCurrentPrice.setText(StringUtil.checkDoubleOrInt(currentPriceBean.getData().getResultMap().getCurrentPrice()));
                            }
                        }
                    }
                }
                break;
            case "2":
                holder.tvCurrent.setText(R.string.the_current_pkb_price);
                holder.tvBalance.setText(R.string.current_pkb_balance);
                holder.tvEntrustType.setText(R.string.repurchase_pkb);
                if (currentPriceBean != null) {
                    if (currentPriceBean.getData() != null) {
                        if (currentPriceBean.getData().getResultMap() != null) {
                            if (currentPriceBean.getData().getResultMap().getCurrentPrice() != null) {
                                holder.tvCurrentPrice.setText(StringUtil.checkDoubleOrInt(currentPriceBean.getData().getResultMap().getTodayPrice()));
                            }
                        }
                    }
                }
                break;
            case "3":
                holder.tvCurrent.setText(R.string.the_current_pkb_price);
                holder.tvBalance.setText(R.string.current_pkb_balance);
                holder.tvEntrustType.setText(R.string.dividend_pkb);
                if (currentPriceBean != null) {
                    if (currentPriceBean.getData() != null) {
                        if (currentPriceBean.getData().getResultMap() != null) {
                            if (currentPriceBean.getData().getResultMap().getCurrentPrice() != null) {
                                holder.tvCurrentPrice.setText(StringUtil.checkDoubleOrInt(currentPriceBean.getData().getResultMap().getTodayPrice()));
                            }
                        }
                    }
                }
                break;
        }
        switch (type) {
            case "dividend":
                holder.tvDividendPkb.setVisibility(View.GONE);
                holder.llType.setVisibility(View.VISIBLE);
                holder.tvExpected.setTextColor(Color.rgb(0, 204, 102));
                holder.tvCurrent.setTextColor(Color.rgb(0, 204, 102));
                holder.tvCurrentPrice.setTextColor(Color.rgb(0, 204, 102));
                holder.tvBalance.setTextColor(Color.rgb(0, 204, 102));
                holder.tvCompany.setTextColor(Color.rgb(0, 204, 102));
                holder.tvCurrentBalance.setTextColor(Color.rgb(0, 204, 102));
                holder.tvDividends.setBackgroundColor(Color.rgb(0, 204, 102));
                holder.tvDividends.setText(R.string.access_to_dividends);
                holder.tvBalance.setText(R.string.current_eth_balance);
                holder.tvType.setText(R.string.dividend);
                titleHolder.tvCompany.setText("PKB");
                switch (assetTypeId) {
                    case "1":
                        holder.tvBalance.setText(R.string.current_eth_balance);
                        break;
                    case "2":
                        holder.tvBalance.setText(R.string.current_pkb_balance);
                        break;
                    case "3":
                        holder.tvBalance.setText(R.string.current_pkb_balance);
                        break;
                }
                break;
            case "sell":
                holder.tvDividendPkb.setVisibility(View.VISIBLE);
                holder.llType.setVisibility(View.GONE);
                holder.tvExpected.setTextColor(Color.parseColor("#d6734b"));
                holder.tvCurrent.setTextColor(Color.parseColor("#d6734b"));
                holder.tvCurrentPrice.setTextColor(Color.parseColor("#d6734b"));
                holder.tvBalance.setTextColor(Color.parseColor("#d6734b"));
                holder.tvCurrentBalance.setTextColor(Color.parseColor("#d6734b"));
                holder.tvDividends.setBackgroundColor(Color.parseColor("#d6734b"));
                holder.tvCompany.setTextColor(Color.parseColor("#d6734b"));
                holder.tvType.setText(R.string.sell);
                holder.tvDividends.setText(R.string.sell);
                holder.tvBalance.setText(R.string.current_pkb_balance);
                holder.tvCurrent.setText(R.string.the_current_price);
                titleHolder.tvCompany.setText("ETH");
                holder.tvCurrentPrice.setText(StringUtil.checkDoubleOrInt(currentPriceBean.getData().getResultMap().getCurrentPrice()));
                break;
        }
        LogUtils.e("isUdpTypeText==>"+isUdpTypeText);
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
                    if (currentPriceBean.getData().getResultMap().getCurrentPrice() != null) {
                        holder.tvCurrentBalance.setText(StringUtil.checkDoubleOrInt(currentPriceBean.getData().getResultMap().getBalance()));
                    }
                }
            }
        }

        holder.edEntrustNum.addTextChangedListener(this);

        holder.shopUp.setLayoutManager(new LinearLayoutManager(mContext));
        MarketDividendTitleAdapter titleUpAdapter = new MarketDividendTitleAdapter(mContext, true, buyListBean);
        holder.shopUp.setAdapter(titleUpAdapter);

        holder.shopDown.setLayoutManager(new LinearLayoutManager(mContext));
        MarketDividendTitleAdapter titleDownAdapter = new MarketDividendTitleAdapter(mContext, false, buyListBean);
        holder.shopDown.setAdapter(titleDownAdapter);

        holder.llType.setOnClickListener(this);
        holder.llSelType.setOnClickListener(this);
        holder.llSelMore.setOnClickListener(this);
        holder.tvDividends.setOnClickListener(this);
    }

    private void setMain(MainHolder holder, final int position) {
        mainHolder = holder;
        BigDecimal one = new BigDecimal("1");
        switch (listBeen.get(position - 1).getDirection()) {
            case 1:
                holder.tvPurchase.setVisibility(View.GONE);
                holder.tvNo.setVisibility(View.VISIBLE);
                holder.tvType.setTextColor(Color.rgb(0, 204, 102));
                holder.tvType.setText(R.string.dividend);
                holder.tvTurnover.setText(listBeen.get(position - 1).getAmountTotal().divide(one, 1, BigDecimal.ROUND_DOWN).doubleValue() + "PKB");
                break;
            case 2:
                holder.tvPurchase.setVisibility(View.VISIBLE);
                holder.tvNo.setVisibility(View.GONE);
                holder.tvType.setTextColor(Color.parseColor("#d6734b"));
                holder.tvType.setText(R.string.sell);
                holder.tvTurnover.setText(listBeen.get(position - 1).getAcountTransaction().divide(one, 1, BigDecimal.ROUND_DOWN).doubleValue() + "ETH");
                break;
        }
        holder.tvTime.setText(TimeUtil.getTime(listBeen.get(position - 1).getCreateDate()));
        String unit = "";
        switch (listBeen.get(position - 1).getAssetTypeId()) {
            case 1:
                unit = "ETH";
                break;
            case 2:
            case 3:
                unit = "PKB";
                break;
        }
        holder.tvNum.setText(listBeen.get(position - 1).getAccountCommission().divide(one, 2, BigDecimal.ROUND_DOWN).doubleValue() + unit);
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
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_sel_more:
                intent = new Intent(mContext, MoreBusinessActivity.class);
                mActivity.startActivity(intent);
                break;
            case R.id.ll_type:
                selEntrustType(titleHolder.llType);
                break;
            case R.id.ll_sel_type:
                selBusinessType(titleHolder.llSelType);
                break;
            case R.id.tv_activity_eth:
                optionWindow.dismiss();
                titleHolder.ivDown.setImageResource(R.drawable.iv_down);
                titleHolder.tvEntrustType.setText(R.string.activity_eth);
                titleHolder.tvCurrent.setText(R.string.the_current_price);
                intent = new Intent();
                intent.putExtra("type", "udpCurrentPrice");
                intent.putExtra("coinType", "1");
                intent.putExtra("assetTypeId", "1");
                EventBus.getDefault().post(intent);
                break;
            case R.id.tv_repurchase_pkb:
                optionWindow.dismiss();
                titleHolder.ivDown.setImageResource(R.drawable.iv_down);
                titleHolder.tvEntrustType.setText(R.string.repurchase_pkb);
                titleHolder.tvCurrent.setText(R.string.the_current_pkb_price);
                intent = new Intent();
                intent.putExtra("type", "udpCurrentPrice");
                intent.putExtra("coinType", "2");
                intent.putExtra("assetTypeId", "2");
                EventBus.getDefault().post(intent);
                break;
            case R.id.tv_dividend_pkb:
                optionWindow.dismiss();
                titleHolder.ivDown.setImageResource(R.drawable.iv_down);
                titleHolder.tvEntrustType.setText(R.string.dividend_pkb);
                titleHolder.tvCurrent.setText(R.string.the_current_pkb_price);
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
                if (MyApplication.getUserBean() != null) {
                    if (MyApplication.getUserBean().getIsHasTradePwd()) {
                        if (!checkEdit(titleHolder.edEntrustNum) || !checkEdit(titleHolder.edPwd)) {
                            return;
                        }
                        switch (type) {
                            case "dividend":
                                intent = new Intent();
                                intent.putExtra("type", "openDividends");
                                intent.putExtra("number", titleHolder.edEntrustNum.getText().toString());
                                intent.putExtra("tradePassword", titleHolder.edPwd.getText().toString());
                                intent.putExtra("currentPrice", currentPriceBean.getData().getResultMap().getCurrentPrice().toString());
                                intent.putExtra("assetTypeId", assetTypeId);
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
                                intent.putExtra("tradePassword", titleHolder.edPwd.getText().toString());
                                intent.putExtra("currentPrice", currentPriceBean.getData().getResultMap().getCurrentPrice().toString());
                                intent.putExtra("acountTransaction", titleHolder.tvExpected.getText().toString());
                                EventBus.getDefault().post(intent);
                                titleHolder.edEntrustNum.setText(null);
                                titleHolder.edPwd.setText(null);
                                titleHolder.tvExpected.setText(null);
                                expectedAccess = "0.0";
                                break;
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
                }
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
                    Toasts.showShort(R.string.entrustment_number);
                    return false;
                }
                break;
            case R.id.ed_pwd:
                if (StringUtil.isEmpty(str)) {
                    Toasts.showShort(R.string.please_enter_the_tran_password);
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

    }

    @Override
    public void afterTextChanged(Editable s) {
        countExpected();
    }

    //计算预计获得
    public void countExpected() {
        LogUtils.e("assetTypeI===>" + assetTypeId);
        String inputNum = titleHolder.edEntrustNum.getText().toString();
        if (inputNum.equals("")) {
            titleHolder.tvExpected.setText("0.0");
            return;
        }
        BigDecimal one = new BigDecimal("1");
        BigDecimal ten = new BigDecimal(Double.valueOf("10"));
        BigDecimal oneHundred = new BigDecimal(Double.valueOf("100"));
        BigDecimal accelerationValue = MyApplication.getUserBean().getAccelerationValue();
        BigDecimal oneHundredCount = accelerationValue.divide(oneHundred, 2, BigDecimal.ROUND_DOWN);  //换算加速值
        BigDecimal decimal = new BigDecimal(Double.valueOf(inputNum));//输入值

        switch (type) {
            case "dividend":
                switch (assetTypeId) {
                    case "1": //活动ETH    挂单数量*主流币的价格/子币的价格=A    （A*加速值*10）+A=最终计算结果
                        if (StringUtil.isCheckNumber(inputNum)) {
                            if (!StringUtil.isCheckNumZero(inputNum)) {
                                if (currentPriceBean != null) {
                                    if (currentPriceBean.getData() != null) {
                                        if (currentPriceBean.getData()
                                                .getResultMap() != null)
                                            if (currentPriceBean.getData().getResultMap().getCurrentPrice() != null) {
                                                BigDecimal currentPrice = currentPriceBean.getData().getResultMap().getCurrentPrice();
                                                BigDecimal multiply = decimal.multiply(currentPrice);
                                                BigDecimal v = multiply.divide(currentPriceBean.getData().getResultMap().getTodayPrice(), 2, BigDecimal.ROUND_DOWN);

                                                //服务器传参保留8位
                                                BigDecimal pass = multiply.divide(currentPriceBean.getData().getResultMap().getTodayPrice(), 8, BigDecimal.ROUND_DOWN);
                                                BigDecimal pass1 = pass.multiply(oneHundredCount).multiply(ten);
                                                BigDecimal add1 = pass1.add(v);
                                                expectedAccess = String.valueOf(add1.divide(one, 8, BigDecimal.ROUND_DOWN).doubleValue());

                                                //APP展示保留2位
                                                BigDecimal multiply1 = v.multiply(oneHundredCount).multiply(ten);
                                                BigDecimal add = multiply1.add(v);
                                                double v1 = add.divide(one, 2, BigDecimal.ROUND_DOWN).doubleValue();
                                                titleHolder.tvExpected.setText("" + v1);
                                                titleHolder.tvCompany.setText("PKB");
                                            }
                                    }
                                }
                            }
                        }
                        break;
                    case "2"://重购PKB    挂单数量*子币的价格/子币的价格
                        if (StringUtil.isCheckNumber(inputNum)) {
                            if (!StringUtil.isCheckNumZero(inputNum)) {
                                if (currentPriceBean != null) {
                                    if (currentPriceBean.getData() != null) {
                                        if (currentPriceBean.getData().getResultMap() != null) {
                                            if (currentPriceBean.getData().getResultMap().getCurrentPrice() != null) {
                                                BigDecimal multiply1 = decimal.multiply(oneHundredCount).multiply(ten);
                                                BigDecimal add = multiply1.add(decimal);

                                                expectedAccess = String.valueOf(add.divide(one, 8, BigDecimal.ROUND_DOWN).doubleValue());

                                                double v1 = add.divide(one, 2, BigDecimal.ROUND_DOWN).doubleValue();
                                                titleHolder.tvExpected.setText("" + v1);
                                                titleHolder.tvCompany.setText("PKB");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case "3"://買入PKB    挂单数量*子币的价格/子币的价格
                        if (StringUtil.isCheckNumber(inputNum)) {
                            if (!StringUtil.isCheckNumZero(inputNum)) {
                                if (currentPriceBean != null) {
                                    if (currentPriceBean.getData() != null) {
                                        if (currentPriceBean.getData().getResultMap() != null) {
                                            if (currentPriceBean.getData().getResultMap().getCurrentPrice() != null) {
                                                BigDecimal multiply1 = decimal.multiply(oneHundredCount).multiply(ten);
                                                BigDecimal add = multiply1.add(decimal);

                                                expectedAccess = String.valueOf(add.divide(one, 8, BigDecimal.ROUND_DOWN).doubleValue());

                                                double v1 = add.divide(one, 2, BigDecimal.ROUND_DOWN).doubleValue();
                                                titleHolder.tvExpected.setText("" + v1);
                                                titleHolder.tvCompany.setText("PKB");
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
                if (StringUtil.isCheckNumber(inputNum)) {
                    if (!StringUtil.isCheckNumZero(inputNum)) {
                        if (currentPriceBean != null) {
                            if (currentPriceBean.getData() != null) {
                                if (currentPriceBean.getData().getResultMap() != null) {
                                    if (currentPriceBean.getData().getResultMap().getCurrentPrice() != null) {
                                        BigDecimal multiply = decimal.multiply(currentPriceBean.getData().getResultMap().getTodayPrice());
                                        expectedAccess = String.valueOf(multiply.divide(currentPriceBean.getData().getResultMap().getCurrentPrice(), 8, BigDecimal.ROUND_DOWN).doubleValue());
                                        double v = multiply.divide(currentPriceBean.getData().getResultMap().getCurrentPrice(), 2, BigDecimal.ROUND_DOWN).doubleValue();
                                        titleHolder.tvExpected.setText("" + v);
                                        titleHolder.tvCompany.setText("ETH");
                                    }
                                }
                            }
                        }
                    }
                }
                break;
        }
    }

}
