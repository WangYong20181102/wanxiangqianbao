package com.jh.wxqb.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.CoinTypeAdapter;
import com.jh.wxqb.base.CoinTypeBean;
import com.jh.wxqb.bean.AssetManagementBean;
import com.jh.wxqb.bean.CoinPricesBean;
import com.jh.wxqb.utils.Toasts;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RealTimeRedemptionDialog extends Dialog implements CoinTypeAdapter.OnItemResultListener, TextWatcher {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_exchange_rate)
    TextView tvExchangeRate;
    @BindView(R.id.image_left_icon)
    ImageView imageLeftIcon;
    @BindView(R.id.tv_left_coin)
    TextView tvLeftCoin;
    @BindView(R.id.image_left_pull_down)
    ImageView imageLeftPullDown;
    @BindView(R.id.rl_left_coin_click)
    RelativeLayout rlLeftCoinClick;
    @BindView(R.id.et_out_num)
    EditText etOutNum;
    @BindView(R.id.image_rechange)
    ImageView imageRechange;
    @BindView(R.id.image_right_icon)
    ImageView imageRightIcon;
    @BindView(R.id.tv_right_coin)
    TextView tvRightCoin;
    @BindView(R.id.image_right_pull_down)
    ImageView imageRightPullDown;
    @BindView(R.id.rl_right_coin_click)
    RelativeLayout rlRightCoinClick;
    @BindView(R.id.tv_input_num)
    TextView tvInputNum;
    @BindView(R.id.ll_fast_redemption)
    LinearLayout llFastRedemption;
    private Context context;
    private PopupWindow optionWindow;
    private int type = 0;
    //币种集合
    private List<CoinTypeBean> coinTypeBeans;
    private boolean bChange = true;
    //币种实时价格
    private CoinPricesBean coinPricesBean;
    //币种type
    private int coinType = 2;
    //兑换价格
    private double rogerThatPrice;
    //转出价格(实时价格)
    private double turnOutPrice;
    private double v1;
    //兑换币种id
    private int rogerThatId = 4;
    //获取余额资产
    private List<AssetManagementBean.DataBean.AccountAssetsBean> financialDetailsBeen;
    private AssetManagementBean.DataBean.AccountAssetsBean assetsBean;


    public RealTimeRedemptionDialog(Context context, CoinPricesBean coinPricesBean, List<AssetManagementBean.DataBean.AccountAssetsBean> financialDetailsBeen) {
        super(context, R.style.custom_dialog);
        this.context = context;
        this.coinPricesBean = coinPricesBean;
        this.financialDetailsBeen = financialDetailsBeen;
        if (financialDetailsBeen != null && financialDetailsBeen.size() > 0) {
            assetsBean = financialDetailsBeen.get(1);
        }
        //指定布局
        setContentView(R.layout.dialog_reel_time_redemption);
        ButterKnife.bind(this);
        coinTypeBeans = new ArrayList<>();
        initView();
        initData();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        coinTypeBeans.add(new CoinTypeBean(2, "TGM"));
        coinTypeBeans.add(new CoinTypeBean(1, "ETH"));
//        coinTypeBeans.add(new CoinTypeBean(3, "USDT"));
        coinTypeBeans.add(new CoinTypeBean(4, "HT"));
//        coinTypeBeans.add(new CoinTypeBean(5, "OKB"));
//        coinTypeBeans.add(new CoinTypeBean(6, "BNB"));

        tvTitle.setText("TGM当天开盘价");
        turnOutPrice = coinPricesBean.getData().getPriceMap().getBestBuyPrice();
        rogerThatPrice = coinPricesBean.getData().getPriceMap().getHtPrice();
        tvExchangeRate.setText(new BigDecimal(turnOutPrice).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue() + "");

    }


    /**
     * 初始化视图
     */
    private void initView() {
        rlLeftCoinClick.setEnabled(true);
        rlRightCoinClick.setEnabled(false);
        etOutNum.addTextChangedListener(this);
    }


    /**
     * 选择类型
     */
    private void selType(RelativeLayout view) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.item_coin_type, null);
        SwipeMenuRecyclerView shopRecy = contentView.findViewById(R.id.shop_recy);
        shopRecy.setLayoutManager(new LinearLayoutManager(context));
        CoinTypeAdapter coinTypeAdapter = new CoinTypeAdapter(context, coinTypeBeans, this);
        shopRecy.setAdapter(coinTypeAdapter);
        optionWindow = new PopupWindow(contentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        optionWindow.setTouchable(true);
        optionWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        optionWindow.setOutsideTouchable(true);
        optionWindow.showAsDropDown(view, 0, 0);
    }


    /**
     * 确认
     */
    public void onSure(Map<String, String> params) {

    }

    @Override
    public void onResult(CoinTypeBean coinTypeBean) {
        optionWindow.dismiss();
        coinType = coinTypeBean.getType();
//        if (type == 0) {
        switch (coinType) {
            case 1:
                tvTitle.setText("实时价格");
                if (financialDetailsBeen != null && financialDetailsBeen.size() > 0) {
                    assetsBean = financialDetailsBeen.get(0);
                }
                turnOutPrice = coinPricesBean.getData().getPriceMap().getEthPrice();
                rogerThatPrice = 1.0;
                rogerThatId = 3;
                imageLeftIcon.setImageResource(R.mipmap.eth);
                tvExchangeRate.setText(new BigDecimal(turnOutPrice).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
                imageRightIcon.setImageResource(R.mipmap.usdt);
                tvRightCoin.setText("USDT");
                break;
            case 2:
                tvTitle.setText("TGM当天开盘价");
                if (financialDetailsBeen != null && financialDetailsBeen.size() > 0) {
                    assetsBean = financialDetailsBeen.get(1);
                }
                turnOutPrice = coinPricesBean.getData().getPriceMap().getBestBuyPrice();
                rogerThatPrice = coinPricesBean.getData().getPriceMap().getHtPrice();
                rogerThatId = 4;
                imageLeftIcon.setImageResource(R.mipmap.tgm);
                tvExchangeRate.setText(new BigDecimal(turnOutPrice).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
                imageRightIcon.setImageResource(R.mipmap.ht);
                tvRightCoin.setText("HT");
                break;
            case 3:
                imageLeftIcon.setImageResource(R.mipmap.usdt);
                tvExchangeRate.setText("7.0");
                break;
            case 4:
                tvTitle.setText("实时价格");
                if (financialDetailsBeen != null && financialDetailsBeen.size() > 0) {
                    assetsBean = financialDetailsBeen.get(3);
                }
                turnOutPrice = coinPricesBean.getData().getPriceMap().getHtPrice();
                rogerThatPrice = 1.0;
                rogerThatId = 3;
                imageLeftIcon.setImageResource(R.mipmap.ht);
                tvExchangeRate.setText(new BigDecimal(turnOutPrice).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
                imageRightIcon.setImageResource(R.mipmap.usdt);
                tvRightCoin.setText("USDT");
                break;
            case 5:
                imageLeftIcon.setImageResource(R.mipmap.okb);
                break;
            case 6:
                imageLeftIcon.setImageResource(R.mipmap.bnb);
                break;
        }
        tvLeftCoin.setText(coinTypeBean.getCoin());
    }
    //        else {
//            switch (coinTypeBean.getType()) {
//                case 1:
//                    imageRightIcon.setImageResource(R.mipmap.eth);
//                    break;
//                case 2:
//                    imageRightIcon.setImageResource(R.mipmap.tgm);
//                    break;
//                case 3:
//                    imageRightIcon.setImageResource(R.mipmap.usdt);
//                    break;
//                case 4:
//                    imageRightIcon.setImageResource(R.mipmap.ht);
//                    break;
//                case 5:
//                    imageRightIcon.setImageResource(R.mipmap.okb);
//                    break;
//            }
//            inputCoinType = coinTypeBean.getType();
//            tvRightCoin.setText(coinTypeBean.getCoin());
//        }
//    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String turnOutNum = charSequence.toString().trim();
        if (!TextUtils.isEmpty(turnOutNum)) {
            BigDecimal bigDecimal = new BigDecimal(turnOutNum);
            switch (coinType) {
                case 1:
                case 4:
                    if (new BigDecimal(turnOutPrice).compareTo(new BigDecimal("0.0")) > 0) {//判断是否获取到实时价格
                        double v = bigDecimal.multiply(new BigDecimal(turnOutPrice)).doubleValue();
                        v1 = new BigDecimal(v).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                        tvInputNum.setText(v1 + "");
                    } else {
                        Toasts.showShort("实时价格获取失败");
                    }
                    break;
                case 2:
                    if (new BigDecimal(turnOutPrice).compareTo(new BigDecimal("0.0")) > 0 && new BigDecimal(rogerThatPrice).compareTo(new BigDecimal("0.0")) > 0) {//判断是否获取到实时价格
                        BigDecimal bigDecimal1 = bigDecimal.multiply(new BigDecimal(turnOutPrice));
                        v1 = bigDecimal1.divide(new BigDecimal(rogerThatPrice), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
                        tvInputNum.setText(v1 + "");
                    } else {
                        Toasts.showShort("HT实时价格获取失败");
                    }
                    break;
            }
        } else {
            tvInputNum.setText("收到数量");
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @OnClick({R.id.rl_left_coin_click, R.id.rl_right_coin_click, R.id.ll_fast_redemption, R.id.image_rechange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_left_coin_click:
                type = 0;
                selType(rlLeftCoinClick);
                break;
            case R.id.rl_right_coin_click:
//                type = 1;
//                selType(rlRightCoinClick);
                break;
            case R.id.ll_fast_redemption:
                if (TextUtils.isEmpty(etOutNum.getText().toString().trim())) {
                    Toasts.showShort("请输入兑换数量");
                    return;
                }
                if (new BigDecimal(tvExchangeRate.getText().toString().trim()).compareTo(new BigDecimal("0.0")) <= 0) {//判断是否获取到实时价格
                    Toasts.showShort("实时价格获取失败");
                    return;
                }
                if (new BigDecimal(etOutNum.getText().toString().trim()).compareTo(new BigDecimal(assetsBean.getActiveAssets())) > 0) {//判断余额
                    Toasts.showShort("余额不足");
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("useChangePrice", String.valueOf(turnOutPrice));
                params.put("changePrice", String.valueOf(rogerThatPrice));
                params.put("useChangeAmount", etOutNum.getText().toString().trim());
                params.put("changeAmount", String.valueOf(v1));
                params.put("useChangeTypeId", String.valueOf(coinType));
                params.put("changeTypeId", String.valueOf(rogerThatId));
                onSure(params);
                dismiss();
                break;
            case R.id.image_rechange:
//                if (bChange) {
//                    bChange = false;
//                    rlLeftCoinClick.setEnabled(false);
//                    rlRightCoinClick.setEnabled(false);
//                    imageLeftIcon.setImageResource(R.mipmap.tgm);
//                    imageRightIcon.setImageResource(R.mipmap.ht);
//                    tvRightCoin.setText("HT");
//                    tvLeftCoin.setText("TGM");
//                    imageLeftPullDown.setVisibility(View.GONE);
//                    imageRightPullDown.setVisibility(View.GONE);
//                    tvExchangeRate.setText(new BigDecimal(coinPricesBean.getData().getPriceMap().getBestBuyPrice()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue()+"");
//                } else {
//                    bChange = true;
//                    rlLeftCoinClick.setEnabled(true);
//                    rlRightCoinClick.setEnabled(false);
//                    imageRightIcon.setImageResource(R.mipmap.tgm);
//                    imageLeftIcon.setImageResource(R.mipmap.eth);
//                    tvLeftCoin.setText("ETH");
//                    tvRightCoin.setText("TGM");
//                    imageLeftPullDown.setVisibility(View.VISIBLE);
//                    imageRightPullDown.setVisibility(View.GONE);
//                    tvExchangeRate.setText(new BigDecimal(coinPricesBean.getData().getPriceMap().getEthPrice()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue()+"");
//                }
                break;
        }
    }
}
