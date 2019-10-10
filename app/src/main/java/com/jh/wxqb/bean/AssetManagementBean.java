package com.jh.wxqb.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 资产管理
 */
public class AssetManagementBean implements Serializable {

    /**
     * code : 8008
     * message : 成功
     * data : {"accountAssets":[{"id":92,"memberId":131,"bizCurrencyTypeId":1,"repurchaseAssets":1,"activeAssets":10,"dividendAssets":0,"sharingRevenue":0,"configRevenue":0,"updateTime":1568619785000,"createTime":1568619785000},{"id":1000,"memberId":131,"bizCurrencyTypeId":2,"repurchaseAssets":100000,"activeAssets":10,"dividendAssets":100000,"sharingRevenue":0,"configRevenue":0,"updateTime":1568619798000,"createTime":1568619798000},{"id":1001,"memberId":131,"bizCurrencyTypeId":3,"repurchaseAssets":100000,"activeAssets":10,"dividendAssets":100000,"sharingRevenue":0,"configRevenue":0,"updateTime":1568619819000,"createTime":1568619819000}]}
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
        private List<AccountAssetsBean> accountAssets;

        public List<AccountAssetsBean> getAccountAssets() {
            return accountAssets;
        }

        public void setAccountAssets(List<AccountAssetsBean> accountAssets) {
            this.accountAssets = accountAssets;
        }

        public static class AccountAssetsBean implements Serializable{
            /**
             * id : 92
             * memberId : 131
             * bizCurrencyTypeId : 1
             * repurchaseAssets : 1
             * activeAssets : 10
             * dividendAssets : 0
             * sharingRevenue : 0
             * configRevenue : 0
             * updateTime : 1568619785000
             * createTime : 1568619785000
             */

            private int id;
            private int memberId;
            private int bizCurrencyTypeId;
            private double repurchaseAssets;
            private double activeAssets;
            private double discountedPrice;
            private double dividendAssets;
            private int sharingRevenue;
            private int configRevenue;
            private long updateTime;
            private long createTime;

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

            public int getBizCurrencyTypeId() {
                return bizCurrencyTypeId;
            }

            public void setBizCurrencyTypeId(int bizCurrencyTypeId) {
                this.bizCurrencyTypeId = bizCurrencyTypeId;
            }

            public double getRepurchaseAssets() {
                return repurchaseAssets;
            }

            public void setRepurchaseAssets(int repurchaseAssets) {
                this.repurchaseAssets = repurchaseAssets;
            }

            public double getDiscountedPrice() {
                return discountedPrice;
            }

            public void setDiscountedPrice(double discountedPrice) {
                this.discountedPrice = discountedPrice;
            }

            public double getActiveAssets() {
                return activeAssets;
            }

            public void setActiveAssets(int activeAssets) {
                this.activeAssets = activeAssets;
            }

            public double getDividendAssets() {
                return dividendAssets;
            }

            public void setDividendAssets(int dividendAssets) {
                this.dividendAssets = dividendAssets;
            }

            public int getSharingRevenue() {
                return sharingRevenue;
            }

            public void setSharingRevenue(int sharingRevenue) {
                this.sharingRevenue = sharingRevenue;
            }

            public int getConfigRevenue() {
                return configRevenue;
            }

            public void setConfigRevenue(int configRevenue) {
                this.configRevenue = configRevenue;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }
        }
    }
}
