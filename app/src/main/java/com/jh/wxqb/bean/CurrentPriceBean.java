package com.jh.wxqb.bean;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/8/26 0026.
 * 获取以太币和子币的价格
 */

public class CurrentPriceBean {

    /**
     * code : 8008
     * message : 查询成功！
     * data : {"resultMap":{"todayPrice":0.1,"balance":30.86,"currentPrice":275.510871758}}
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
        /**
         * resultMap : {"todayPrice":0.1,"balance":30.86,"currentPrice":275.510871758}
         */

        private ResultMapBean resultMap;

        public ResultMapBean getResultMap() {
            return resultMap;
        }

        public void setResultMap(ResultMapBean resultMap) {
            this.resultMap = resultMap;
        }

        public static class ResultMapBean {
            /**
             * todayPrice : 0.1
             * balance : 30.86
             * currentPrice : 275.510871758
             */

            private BigDecimal todayPrice;
            private BigDecimal balance;
            private BigDecimal currentPrice;

            public BigDecimal getTodayPrice() {
                return todayPrice;
            }

            public void setTodayPrice(BigDecimal todayPrice) {
                this.todayPrice = todayPrice;
            }

            public BigDecimal getBalance() {
                return balance;
            }

            public void setBalance(BigDecimal balance) {
                this.balance = balance;
            }

            public BigDecimal getCurrentPrice() {
                return currentPrice;
            }

            public void setCurrentPrice(BigDecimal currentPrice) {
                this.currentPrice = currentPrice;
            }
        }
    }
}
