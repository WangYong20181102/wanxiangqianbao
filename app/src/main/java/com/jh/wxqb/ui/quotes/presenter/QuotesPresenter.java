package com.jh.wxqb.ui.quotes.presenter;

import com.jh.wxqb.api.ServerInterface;
import com.jh.wxqb.api.biz.Biz;
import com.jh.wxqb.api.biz.bizImpl.BizImpl;
import com.jh.wxqb.base.OnBaseListener;
import com.jh.wxqb.bean.QuotesCoinBean;
import com.jh.wxqb.ui.quotes.view.QuotesView;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;

public class QuotesPresenter {
    private Biz biz;
    private QuotesView quotesView;

    public QuotesPresenter(QuotesView quotesView) {
        this.quotesView = quotesView;
        biz = new BizImpl();
    }

    /**
     * 行情数据请求
     */
    public void getQuotesRequest() {
        biz.getQuotesDateRequest(new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("行情====" + GsonUtil.GsonString(result));
                    QuotesCoinBean coinBean = GsonUtil.GsonToBean(result, QuotesCoinBean.class);
                    if (coinBean.getCode() == ServerInterface.SUCCESS) {
                        quotesView.onQuotesDateRequestSuccess(coinBean);
                    } else {
                        quotesView.onViewFailureString(coinBean.getCode(), coinBean.getMessage());
                    }
                } else {
                    quotesView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {

            }
        });
    }
}
