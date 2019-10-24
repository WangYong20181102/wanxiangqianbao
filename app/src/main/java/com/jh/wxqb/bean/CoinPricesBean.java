package com.jh.wxqb.bean;

public class CoinPricesBean {

    /**
     * code : 8008
     * message : 成功
     * data : {"priceMap":{"bestBuyPrice":0.1913,"ethPrice":175.094344931,"htPrice":3.4127826312}}
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
         * priceMap : {"bestBuyPrice":0.1913,"ethPrice":175.094344931,"htPrice":3.4127826312}
         */

        private PriceMapBean priceMap;

        public PriceMapBean getPriceMap() {
            return priceMap;
        }

        public void setPriceMap(PriceMapBean priceMap) {
            this.priceMap = priceMap;
        }

        public static class PriceMapBean {
            /**
             * bestBuyPrice : 0.1913
             * ethPrice : 175.094344931
             * htPrice : 3.4127826312
             */

            private double bestBuyPrice;
            private double ethPrice;
            private double htPrice;

            public PriceMapBean(double bestBuyPrice, double ethPrice, double htPrice) {
                this.bestBuyPrice = bestBuyPrice;
                this.ethPrice = ethPrice;
                this.htPrice = htPrice;
            }

            public double getBestBuyPrice() {
                return bestBuyPrice;
            }

            public void setBestBuyPrice(double bestBuyPrice) {
                this.bestBuyPrice = bestBuyPrice;
            }

            public double getEthPrice() {
                return ethPrice;
            }

            public void setEthPrice(double ethPrice) {
                this.ethPrice = ethPrice;
            }

            public double getHtPrice() {
                return htPrice;
            }

            public void setHtPrice(double htPrice) {
                this.htPrice = htPrice;
            }
        }
    }
}
