package com.jh.wxqb.ui.quotes.view;

import com.jh.wxqb.base.BaseView;
import com.jh.wxqb.bean.QuotesCoinBean;

public interface QuotesView extends BaseView {

    /**
     * 数据请求成功
     */
    void onQuotesDateRequestSuccess(QuotesCoinBean quotesCoinBean);

}
