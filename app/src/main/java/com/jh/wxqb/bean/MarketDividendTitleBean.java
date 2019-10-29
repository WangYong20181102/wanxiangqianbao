package com.jh.wxqb.bean;


import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/8/23 0023.
 * 買入市场--買入 Title
 */

public class MarketDividendTitleBean {

    /**
     * code : 8008
     * message : 成功
     * data : {"pageSize":1,"list":{"buyList":[{"id":175,"memberId":90,"direction":1,"amountPrice":291.44589408,"amountTotal":6411.81178816,"accountCommission":3,"acountTransaction":5828.9178816,"orderStatus":1,"createDate":1535896502000,"assetTypeId":1,"updateTime":"2018-09-02 21:55:02"},{"id":174,"memberId":89,"direction":1,"amountPrice":0.15,"amountTotal":110,"accountCommission":100,"acountTransaction":100,"orderStatus":1,"createDate":1535895641000,"assetTypeId":2,"updateTime":"2018-09-02 21:40:41"},{"id":173,"memberId":88,"direction":1,"amountPrice":0.15,"amountTotal":110,"accountCommission":100,"acountTransaction":100,"orderStatus":3,"createDate":1535895409000,"assetTypeId":2,"updateTime":"2018-09-02 21:37:50"},{"id":172,"memberId":87,"direction":1,"amountPrice":291.53058976,"amountTotal":2137.89099157,"accountCommission":1,"acountTransaction":1943.53726506,"orderStatus":1,"createDate":1535895306000,"assetTypeId":1,"updateTime":"2018-09-02 21:35:05"},{"id":171,"memberId":88,"direction":1,"amountPrice":0.15,"amountTotal":110,"accountCommission":100,"acountTransaction":100,"orderStatus":3,"createDate":1535895062000,"assetTypeId":2,"updateTime":"2018-09-02 21:34:25"}],"SellList":[{"id":168,"memberId":87,"direction":2,"amountPrice":292.74790001,"amountTotal":1000,"accountCommission":1000,"acountTransaction":0.51238625,"orderStatus":1,"createDate":1535891253000,"assetTypeId":3,"updateTime":"2018-09-02 20:27:32"},{"id":170,"memberId":88,"direction":2,"amountPrice":292.37478348,"amountTotal":100,"accountCommission":100,"acountTransaction":0.05,"orderStatus":3,"createDate":1535894648000,"assetTypeId":3,"updateTime":"2018-09-02 21:24:27"}]}}
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
         * pageSize : 1
         * list : {"buyList":[{"id":175,"memberId":90,"direction":1,"amountPrice":291.44589408,"amountTotal":6411.81178816,"accountCommission":3,"acountTransaction":5828.9178816,"orderStatus":1,"createDate":1535896502000,"assetTypeId":1,"updateTime":"2018-09-02 21:55:02"},{"id":174,"memberId":89,"direction":1,"amountPrice":0.15,"amountTotal":110,"accountCommission":100,"acountTransaction":100,"orderStatus":1,"createDate":1535895641000,"assetTypeId":2,"updateTime":"2018-09-02 21:40:41"},{"id":173,"memberId":88,"direction":1,"amountPrice":0.15,"amountTotal":110,"accountCommission":100,"acountTransaction":100,"orderStatus":3,"createDate":1535895409000,"assetTypeId":2,"updateTime":"2018-09-02 21:37:50"},{"id":172,"memberId":87,"direction":1,"amountPrice":291.53058976,"amountTotal":2137.89099157,"accountCommission":1,"acountTransaction":1943.53726506,"orderStatus":1,"createDate":1535895306000,"assetTypeId":1,"updateTime":"2018-09-02 21:35:05"},{"id":171,"memberId":88,"direction":1,"amountPrice":0.15,"amountTotal":110,"accountCommission":100,"acountTransaction":100,"orderStatus":3,"createDate":1535895062000,"assetTypeId":2,"updateTime":"2018-09-02 21:34:25"}],"SellList":[{"id":168,"memberId":87,"direction":2,"amountPrice":292.74790001,"amountTotal":1000,"accountCommission":1000,"acountTransaction":0.51238625,"orderStatus":1,"createDate":1535891253000,"assetTypeId":3,"updateTime":"2018-09-02 20:27:32"},{"id":170,"memberId":88,"direction":2,"amountPrice":292.37478348,"amountTotal":100,"accountCommission":100,"acountTransaction":0.05,"orderStatus":3,"createDate":1535894648000,"assetTypeId":3,"updateTime":"2018-09-02 21:24:27"}]}
         */

        private int pageSize;
        private ListBean list = null;
        private String quoteChange;

        public String getQuoteChange() {
            return quoteChange;
        }

        public void setQuoteChange(String quoteChange) {
            this.quoteChange = quoteChange;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public static class ListBean {
            private List<BuyListBean> buyList;
            private List<SellListBean> SellList;

            public List<BuyListBean> getBuyList() {
                return buyList;
            }

            public void setBuyList(List<BuyListBean> buyList) {
                this.buyList = buyList;
            }

            public List<SellListBean> getSellList() {
                return SellList;
            }

            public void setSellList(List<SellListBean> SellList) {
                this.SellList = SellList;
            }

            public static class BuyListBean {
                /**
                 * id : 175
                 * memberId : 90
                 * direction : 1
                 * amountPrice : 291.44589408
                 * amountTotal : 6411.81178816
                 * accountCommission : 3
                 * acountTransaction : 5828.9178816
                 * orderStatus : 1
                 * createDate : 1535896502000
                 * assetTypeId : 1
                 * updateTime : 2018-09-02 21:55:02
                 */

                private int id;
                private int memberId;
                private int direction;
                private BigDecimal amountPrice;
                private BigDecimal amountTotal;
                private BigDecimal accountCommission;
                private BigDecimal acountTransaction;
                private BigDecimal countUnfilledVolume;
                private int orderStatus;
                private long createDate;
                private int assetTypeId;
                private String updateTime;
                private BigDecimal depth;

                public BigDecimal getDepth() {
                    if (depth == null) {
                        return new BigDecimal("0");
                    }
                    return depth;
                }

                public void setDepth(BigDecimal depth) {
                    this.depth = depth;
                }

                public BigDecimal getCountUnfilledVolume() {
                    if (countUnfilledVolume == null){
                        return new BigDecimal("0");
                    }
                    return countUnfilledVolume;
                }

                public void setCountUnfilledVolume(BigDecimal countUnfilledVolume) {
                    this.countUnfilledVolume = countUnfilledVolume;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getMemberId() {
                    return memberId;
                }

                public void setMemberId(int memberId) {
                    this.memberId = memberId;
                }

                public int getDirection() {
                    return direction;
                }

                public void setDirection(int direction) {
                    this.direction = direction;
                }

                public BigDecimal getAmountPrice() {
                    if (amountPrice == null){
                        return new BigDecimal("0");
                    }
                    return amountPrice;
                }

                public void setAmountPrice(BigDecimal amountPrice) {
                    this.amountPrice = amountPrice;
                }

                public BigDecimal getAmountTotal() {
                    return amountTotal;
                }

                public void setAmountTotal(BigDecimal amountTotal) {
                    this.amountTotal = amountTotal;
                }

                public BigDecimal getAccountCommission() {
                    return accountCommission;
                }

                public void setAccountCommission(BigDecimal accountCommission) {
                    this.accountCommission = accountCommission;
                }

                public BigDecimal getAcountTransaction() {
                    return acountTransaction;
                }

                public void setAcountTransaction(BigDecimal acountTransaction) {
                    this.acountTransaction = acountTransaction;
                }

                public int getOrderStatus() {
                    return orderStatus;
                }

                public void setOrderStatus(int orderStatus) {
                    this.orderStatus = orderStatus;
                }

                public long getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(long createDate) {
                    this.createDate = createDate;
                }

                public int getAssetTypeId() {
                    return assetTypeId;
                }

                public void setAssetTypeId(int assetTypeId) {
                    this.assetTypeId = assetTypeId;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }
            }

            public static class SellListBean {
                /**
                 * id : 168
                 * memberId : 87
                 * direction : 2
                 * amountPrice : 292.74790001
                 * amountTotal : 1000
                 * accountCommission : 1000
                 * acountTransaction : 0.51238625
                 * orderStatus : 1
                 * createDate : 1535891253000
                 * assetTypeId : 3
                 * updateTime : 2018-09-02 20:27:32
                 */

                private int id;
                private int memberId;
                private int direction;
                private BigDecimal amountPrice;
                private BigDecimal amountTotal;
                private BigDecimal accountCommission;
                private BigDecimal acountTransaction;
                private BigDecimal countUnfilledVolume;
                private int orderStatus;
                private long createDate;
                private int assetTypeId;
                private String updateTime;
                private BigDecimal depth;

                public BigDecimal getDepth() {
                    if (depth == null) {
                        return new BigDecimal("0");
                    }
                    return depth;
                }

                public void setDepth(BigDecimal depth) {
                    this.depth = depth;
                }

                public BigDecimal getCountUnfilledVolume() {
                    if (countUnfilledVolume == null){
                        return new BigDecimal("0");
                    }
                    return countUnfilledVolume;
                }

                public void setCountUnfilledVolume(BigDecimal countUnfilledVolume) {
                    this.countUnfilledVolume = countUnfilledVolume;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getMemberId() {
                    return memberId;
                }

                public void setMemberId(int memberId) {
                    this.memberId = memberId;
                }

                public int getDirection() {
                    return direction;
                }

                public void setDirection(int direction) {
                    this.direction = direction;
                }

                public BigDecimal getAmountPrice() {
                    if (amountPrice == null){
                        return new BigDecimal("0");
                    }
                    return amountPrice;
                }

                public void setAmountPrice(BigDecimal amountPrice) {
                    this.amountPrice = amountPrice;
                }

                public BigDecimal getAmountTotal() {
                    return amountTotal;
                }

                public void setAmountTotal(BigDecimal amountTotal) {
                    this.amountTotal = amountTotal;
                }

                public BigDecimal getAccountCommission() {
                    return accountCommission;
                }

                public void setAccountCommission(BigDecimal accountCommission) {
                    this.accountCommission = accountCommission;
                }

                public BigDecimal getAcountTransaction() {
                    return acountTransaction;
                }

                public void setAcountTransaction(BigDecimal acountTransaction) {
                    this.acountTransaction = acountTransaction;
                }

                public int getOrderStatus() {
                    return orderStatus;
                }

                public void setOrderStatus(int orderStatus) {
                    this.orderStatus = orderStatus;
                }

                public long getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(long createDate) {
                    this.createDate = createDate;
                }

                public int getAssetTypeId() {
                    return assetTypeId;
                }

                public void setAssetTypeId(int assetTypeId) {
                    this.assetTypeId = assetTypeId;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }
            }
        }
    }
}
