package com.jh.wxqb.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/8/26 0026.
 * 買入市場--買入底部列表
 */

public class MarketDividendBottomBean extends BaseValue{

    /**
     * code : 8008
     * message : 成功
     * data : {"list":[{"id":83,"memberId":28,"direction":1,"amountPrice":275.17053797,"amountTotal":275.17053797,"accountCommission":1,"acountTransaction":2751.70537969,"orderStatus":1,"createDate":1535288746000,"assetTypeId":1,"updateTime":"2018-08-26 21:06:54"},{"id":81,"memberId":28,"direction":1,"amountPrice":276.10766046,"amountTotal":276.10766046,"accountCommission":1,"acountTransaction":2761.07660459,"orderStatus":1,"createDate":1535286716000,"assetTypeId":1,"updateTime":"2018-08-26 20:33:04"},{"id":79,"memberId":28,"direction":1,"amountPrice":275.90208607,"amountTotal":275.90208607,"accountCommission":1,"acountTransaction":2759.02086074,"orderStatus":1,"createDate":1535286492000,"assetTypeId":1,"updateTime":"2018-08-26 20:29:20"},{"id":78,"memberId":28,"direction":1,"amountPrice":275.90208607,"amountTotal":275.90208607,"accountCommission":1,"acountTransaction":2759.02086074,"orderStatus":1,"createDate":1535286474000,"assetTypeId":1,"updateTime":"2018-08-26 20:29:02"},{"id":74,"memberId":29,"direction":1,"amountPrice":275.93969304,"amountTotal":137.96984652,"accountCommission":0.5,"acountTransaction":1379.69846522,"orderStatus":1,"createDate":1535283775000,"assetTypeId":1,"updateTime":"2018-08-26 19:44:03"}]}
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

    public static class DataBean implements Serializable {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * id : 79
             * memberId : 28
             * direction : 1
             * amountPrice : 275.90208607
             * amountTotal : 275.90208607
             * accountCommission : 1
             * acountTransaction : 2759.02086074
             * orderStatus : 1
             * createDate : 1535286492000
             * assetTypeId : 1
             * updateTime : 2018-08-26 20:29:20
             */

            private int id;
            private int memberId;
            private int direction;
            private BigDecimal amountPrice;
            private BigDecimal amountTotal;
            private BigDecimal accountCommission;
            private BigDecimal acountTransaction;
            private int orderStatus;
            private long createDate;
            private int assetTypeId;
            private String updateTime;

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
