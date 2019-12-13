package com.jh.wxqb.bean;

import java.math.BigDecimal;
import java.util.List;

public class QuotesCoinBean {

    /**
     * code : 8008
     * message : 成功
     * data : {"quotesList":[{"tradingPair":"TGM/USDT","todayVolume":9761.74,"coinValue":2.884,"quoteChange":"+2.74%","usaPrice":0.412}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<QuotesListBean> quotesList;

        public List<QuotesListBean> getQuotesList() {
            return quotesList;
        }

        public void setQuotesList(List<QuotesListBean> quotesList) {
            this.quotesList = quotesList;
        }

        public static class QuotesListBean {
            /**
             * tradingPair : TGM/USDT
             * todayVolume : 9761.74
             * coinValue : 2.884
             * quoteChange : +2.74%
             * usaPrice : 0.412
             */

            private String tradingPair;
            private BigDecimal todayVolume;
            private BigDecimal coinValue;
            private String quoteChange;
            private BigDecimal usaPrice;

            public String getTradingPair() {
                return tradingPair;
            }

            public void setTradingPair(String tradingPair) {
                this.tradingPair = tradingPair;
            }

            public BigDecimal getTodayVolume() {
                return todayVolume;
            }

            public void setTodayVolume(BigDecimal todayVolume) {
                this.todayVolume = todayVolume;
            }

            public BigDecimal getCoinValue() {
                return coinValue;
            }

            public void setCoinValue(BigDecimal coinValue) {
                this.coinValue = coinValue;
            }

            public String getQuoteChange() {
                return quoteChange;
            }

            public void setQuoteChange(String quoteChange) {
                this.quoteChange = quoteChange;
            }

            public BigDecimal getUsaPrice() {
                return usaPrice;
            }

            public void setUsaPrice(BigDecimal usaPrice) {
                this.usaPrice = usaPrice;
            }
        }
    }
}
