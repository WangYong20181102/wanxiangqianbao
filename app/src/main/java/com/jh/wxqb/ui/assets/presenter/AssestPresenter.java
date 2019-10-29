package com.jh.wxqb.ui.assets.presenter;


import com.jh.wxqb.adapter.AssetManagementAdapter;
import com.jh.wxqb.api.ServerInterface;
import com.jh.wxqb.api.biz.Biz;
import com.jh.wxqb.api.biz.bizImpl.BizImpl;
import com.jh.wxqb.base.OnBaseListener;
import com.jh.wxqb.bean.AssetManagementBean;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.CoinPricesBean;
import com.jh.wxqb.bean.FinancialDetailsBean;
import com.jh.wxqb.bean.SafetyMarkingBean;
import com.jh.wxqb.ui.assets.view.AssetsView;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/11 0011.
 */

public class AssestPresenter {

    private Biz biz;
    private AssetsView assetsView;

    public AssestPresenter(AssetsView assetsView) {
        this.assetsView = assetsView;
        biz = new BizImpl();
    }


    /**
     * 获取财务明细列表
     */
    public void getFinancialDetails(int pageNum, int type, int coinTypeId) {
        biz.getFinancialDetails(pageNum, type, coinTypeId, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("getFinancialDetails==>" + GsonUtil.GsonString(result));
                    FinancialDetailsBean financialDetailsBean = GsonUtil.GsonToBean(result, FinancialDetailsBean.class);
                    if (financialDetailsBean.getCode() == ServerInterface.SUCCESS) {
                        assetsView.getFinancialDetailsSuccess(financialDetailsBean);
                    } else {
                        assetsView.onViewFailureString(financialDetailsBean.getCode(), financialDetailsBean.getMessage());
                    }
                } else {
                    assetsView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                assetsView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 資產管理
     *
     * @param pageNum
     * @param type
     */
    public void getQueryaccountassets(int pageNum, int type) {
        biz.getQueryaccountassets(pageNum, type, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("getQueryaccountassets==>" + GsonUtil.GsonString(result));
                    AssetManagementBean financialDetailsBean = GsonUtil.GsonToBean(result, AssetManagementBean.class);
                    if (financialDetailsBean.getCode() == ServerInterface.SUCCESS) {
                        assetsView.getAssetManagementSuccess(financialDetailsBean);
                    } else {
                        assetsView.onViewFailureString(financialDetailsBean.getCode(), financialDetailsBean.getMessage());
                    }
                } else {
                    assetsView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                assetsView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 提币
     */
    public void withdrawMoney(Map<String, String> map) {
        biz.withdrawMoney(map, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("withdrawMoney==>" + GsonUtil.GsonString(result));
                    BaseBean baseBean = GsonUtil.GsonToBean(result, BaseBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        assetsView.withdrawMoneySuccess(baseBean);
                    } else {
                        assetsView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    assetsView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                assetsView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 安全便是
     */
    public void safetyMarking() {
        biz.safetyMarking(new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("safetyMarking==>" + GsonUtil.GsonString(result));
                    SafetyMarkingBean safetyMarkingBean = GsonUtil.GsonToBean(result, SafetyMarkingBean.class);
                    if (safetyMarkingBean.getCode() == ServerInterface.SUCCESS) {
                        assetsView.safetyMarkingSuccess(safetyMarkingBean);
                    } else {
                        assetsView.onViewFailureString(safetyMarkingBean.getCode(), safetyMarkingBean.getMessage());
                    }
                } else {
                    assetsView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                assetsView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 币种价格
     */
    public void getCoinPrices() {
        biz.getQuerycoinprice(new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("coinPrices==>" + GsonUtil.GsonString(result));
                    CoinPricesBean coinPricesBean = GsonUtil.GsonToBean(result, CoinPricesBean.class);
                    if (coinPricesBean.getCode() == ServerInterface.SUCCESS) {
                        assetsView.coinPricesSuccess(coinPricesBean);
                    } else {
                        assetsView.onViewFailureString(coinPricesBean.getCode(), coinPricesBean.getMessage());
                    }
                } else {
                    assetsView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                assetsView.onServerFailure(e, code);
            }
        });
    }
    /**
     * 币种兑换
     */
    public void getSavechangeinfo(Map<String, String> map){
        biz.getSavechangeinfo(map, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("币种兑换==>" + GsonUtil.GsonString(result));
                    BaseBean baseBean = GsonUtil.GsonToBean(result, BaseBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        assetsView.coinRechangeSuccess(baseBean);
                    } else {
                        assetsView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    assetsView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                assetsView.onServerFailure(e, code);
            }
        });
    }

}
