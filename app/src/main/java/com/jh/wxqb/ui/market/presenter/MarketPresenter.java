package com.jh.wxqb.ui.market.presenter;


import com.jh.wxqb.api.ServerInterface;
import com.jh.wxqb.api.biz.Biz;
import com.jh.wxqb.api.biz.bizImpl.BizImpl;
import com.jh.wxqb.base.OnBaseListener;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.CurrentPriceBean;
import com.jh.wxqb.bean.MarketDividendBottomBean;
import com.jh.wxqb.bean.MarketDividendTitleBean;
import com.jh.wxqb.bean.MeDividend;
import com.jh.wxqb.ui.market.view.MarketView;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/11 0011.
 */

public class MarketPresenter {

    private Biz biz;
    private MarketView marketView;
    private static MarketPresenter marketPresenter = null;

    public static MarketPresenter getMarketPresenter(MarketView marView) {
        if (marketPresenter == null) {
            marketPresenter = new MarketPresenter(marView);
        }
        return marketPresenter;
    }

    public MarketPresenter(MarketView marketView) {
        this.marketView = marketView;
        biz = new BizImpl();
    }


    /**
     * 交易记录
     */
    public void myDividend(int pageNum, int type) {
        biz.myDividend(pageNum, type, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("personalInfo==>" + GsonUtil.GsonString(result));
                    MeDividend meDividend = GsonUtil.GsonToBean(result, MeDividend.class);

                    if (meDividend.getCode() == ServerInterface.SUCCESS) {
                        marketView.myDividendSuccess(meDividend);
                    } else {
                        marketView.onViewFailureString(meDividend.getCode(), meDividend.getMessage());
                    }
                } else {
                    marketView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                marketView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 買入市場--買入顶部列表
     */
    public void dividendMarketDividend(int type) {
        biz.dividendMarketDividend(type, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("dividendMarketDividend==>" + GsonUtil.GsonString(result));
                    MarketDividendTitleBean marketDividendTitleBean = GsonUtil.GsonToBean(result, MarketDividendTitleBean.class);
                    if (marketDividendTitleBean.getCode() == ServerInterface.SUCCESS) {
                        marketView.dividendMarketDividendSuccess(marketDividendTitleBean);
                    } else {
                        marketView.onViewFailureString(marketDividendTitleBean.getCode(), marketDividendTitleBean.getMessage());
                    }
                } else {
                    marketView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                marketView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 買入市場--買入底部列表
     */
    public void dividendMarketTopDividend(int pageNum, int type, int direction) {
        biz.dividendMarketTopDividend(pageNum, type, direction, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("dividendMarketTopDividend==>" + GsonUtil.GsonString(result));
                    MarketDividendBottomBean marketDividendBottomBean = GsonUtil.GsonToBean(result, MarketDividendBottomBean.class);
                    if (marketDividendBottomBean.getCode() == ServerInterface.SUCCESS) {
                        marketView.dividendMarketTopDividendSuccess(marketDividendBottomBean);
                    } else {
                        marketView.onViewFailureString(marketDividendBottomBean.getCode(), marketDividendBottomBean.getMessage());
                    }
                } else {
                    marketView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                marketView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 買入市場--购买订单
     */
    public void purchaseOrder(Map<String, String> map) {
        biz.purchaseOrder(map, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("purchaseOrder==>" + GsonUtil.GsonString(result));
                    BaseBean baseBean = GsonUtil.GsonToBean(result, BaseBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        marketView.purchaseOrderSuccess(baseBean);
                    } else {
                        marketView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    marketView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                marketView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 获取以太币和子币的价格
     */
    public void getCurrentPrice(Map<String, String> map) {
        biz.getCurrentPrice(map, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("getCurrentPrice==>" + GsonUtil.GsonString(result));
                    CurrentPriceBean currentPriceBean = GsonUtil.GsonToBean(result, CurrentPriceBean.class);
                    if (currentPriceBean.getCode() == ServerInterface.SUCCESS) {
                        marketView.getCurrentPriceSuccess(currentPriceBean);
                    } else {
                        marketView.onViewFailureString(currentPriceBean.getCode(), currentPriceBean.getMessage());
                    }
                } else {
                    marketView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                marketView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 買入
     */
    public void sellOut(Map<String, String> map) {
        biz.sellOut(map, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("sellOut==>" + GsonUtil.GsonString(result));
                    BaseBean baseBean = GsonUtil.GsonToBean(result, BaseBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        marketView.sellOutSuccess(baseBean);
                    } else {
                        marketView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    marketView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                marketView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 卖出
     */
    public void marketSell(Map<String, String> map) {
        biz.marketSell(map, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("marketSell==>" + GsonUtil.GsonString(result));
                    BaseBean baseBean = GsonUtil.GsonToBean(result, BaseBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        marketView.marketSellSuccess(baseBean);
                    } else {
                        marketView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    marketView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                marketView.onServerFailure(e, code);
            }
        });
    }

}
