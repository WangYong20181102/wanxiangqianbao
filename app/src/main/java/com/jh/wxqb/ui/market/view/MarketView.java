package com.jh.wxqb.ui.market.view;


import com.jh.wxqb.base.BaseView;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.CurrentPriceBean;
import com.jh.wxqb.bean.MarketDividendBottomBean;
import com.jh.wxqb.bean.MarketDividendTitleBean;
import com.jh.wxqb.bean.MeDividend;

/**
 * Created by Administrator on 2018/6/11 0011.
 */

public interface MarketView extends BaseView {
    /**
     * 当前委托
     *
     * @param result
     */
    void myDividendSuccess(MeDividend result);

    /**
     * 買入市場--買入顶部列表
     *
     * @param result
     */
    void dividendMarketDividendSuccess(MarketDividendTitleBean result);

    /**
     * 買入市場--買入底部列表
     *
     * @param result
     */
    void dividendMarketTopDividendSuccess(MarketDividendBottomBean result);

    /**
     * 购买订单
     *
     * @param result
     */
    void purchaseOrderSuccess(BaseBean result);

    /**
     * 获取以太币和子币的价格
     *
     * @param result
     */
    void getCurrentPriceSuccess(CurrentPriceBean result);

    /**
     * 買入
     *
     * @param result
     */
    void sellOutSuccess(BaseBean result);

    /**
     * 卖出
     *
     * @param result
     */
    void marketSellSuccess(BaseBean result);


}
