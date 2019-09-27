package com.jh.wxqb.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/8/21 0021.
 * 个人中心--買入
 */

public class MeDividend extends BaseValue{

    /**
     * code : 8008
     * message : 成功
     * data : {"pageSize":1,"list":[{"amountTotal":822.92377125,"id":12,"acountTransaction":8229.23771,"accountCommission":3,"direction":1,"createDate":1535003423000,"status":"排队中","amountPrice":274.30792375},{"amountTotal":1799.37109573,"id":11,"acountTransaction":6.35546688,"accountCommission":1000,"direction":2,"createDate":1534995035000,"status":"挂单中","amountPrice":283.12177999}]}
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
        /**
         * pageSize : 1
         * list : [{"amountTotal":822.92377125,"id":12,"acountTransaction":8229.23771,"accountCommission":3,"direction":1,"createDate":1535003423000,"status":"排队中","amountPrice":274.30792375},{"amountTotal":1799.37109573,"id":11,"acountTransaction":6.35546688,"accountCommission":1000,"direction":2,"createDate":1534995035000,"status":"挂单中","amountPrice":283.12177999}]
         */

        private int pageSize;
        private List<ListBean> list;

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * amountTotal : 822.92377125
             * id : 12
             * acountTransaction : 8229.23771
             * accountCommission : 3
             * direction : 1
             * createDate : 1535003423000
             * status : 排队中
             * amountPrice : 274.30792375
             */

            private BigDecimal amountTotal;
            private int id;
            private BigDecimal acountTransaction;
            private BigDecimal accountCommission;
            private BigDecimal volume;
            private String interestRatio;
            private BigDecimal dailyInterest;
            private int direction;
            private long createDate;
            private int status;
            private BigDecimal amountPrice;
            private int assetTypeId;
            public BigDecimal getVolume() {
                return volume;
            }

            public void setVolume(BigDecimal volume) {
                this.volume = volume;
            }
            public int getAssetTypeId() {
                return assetTypeId;
            }

            public void setAssetTypeId(int assetTypeId) {
                this.assetTypeId = assetTypeId;
            }

            private List<MemberInterestsBean> memberInterests;

            public List<MemberInterestsBean> getMemberInterests() {
                return memberInterests;
            }

            public void setMemberInterests(List<MemberInterestsBean> memberInterests) {
                this.memberInterests = memberInterests;
            }

            public String getInterestRatio() {
                return interestRatio;
            }

            public void setInterestRatio(String interestRatio) {
                this.interestRatio = interestRatio;
            }

            public BigDecimal getDailyInterest() {
                return dailyInterest;
            }

            public void setDailyInterest(BigDecimal dailyInterest) {
                this.dailyInterest = dailyInterest;
            }

            public BigDecimal getAmountTotal() {
                return amountTotal;
            }

            public void setAmountTotal(BigDecimal amountTotal) {
                this.amountTotal = amountTotal;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public BigDecimal getAcountTransaction() {
                return acountTransaction;
            }

            public void setAcountTransaction(BigDecimal acountTransaction) {
                this.acountTransaction = acountTransaction;
            }

            public BigDecimal getAccountCommission() {
                return accountCommission;
            }

            public void setAccountCommission(BigDecimal accountCommission) {
                this.accountCommission = accountCommission;
            }

            public int getDirection() {
                return direction;
            }

            public void setDirection(int direction) {
                this.direction = direction;
            }

            public long getCreateDate() {
                return createDate;
            }

            public void setCreateDate(long createDate) {
                this.createDate = createDate;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public BigDecimal getAmountPrice() {
                return amountPrice;
            }

            public void setAmountPrice(BigDecimal amountPrice) {
                this.amountPrice = amountPrice;
            }

            public static class MemberInterestsBean implements Serializable{
                /**
                 * commissionId : 20
                 * createTime : 1534843652000
                 * currentDays : 10
                 * dailyInterest : 2.755
                 * id : 22
                 * interestRatio : 0.1%
                 * interestType : 1
                 * memberId : 27
                 */

                private int commissionId;
                private long createTime;
                private int currentDays;
                private BigDecimal dailyInterest;
                private int id;
                private String interestRatio;
                private int interestType;
                private int memberId;

                public int getCommissionId() {
                    return commissionId;
                }

                public void setCommissionId(int commissionId) {
                    this.commissionId = commissionId;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public int getCurrentDays() {
                    return currentDays;
                }

                public void setCurrentDays(int currentDays) {
                    this.currentDays = currentDays;
                }

                public BigDecimal getDailyInterest() {
                    return dailyInterest;
                }

                public void setDailyInterest(BigDecimal dailyInterest) {
                    this.dailyInterest = dailyInterest;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getInterestRatio() {
                    return interestRatio;
                }

                public void setInterestRatio(String interestRatio) {
                    this.interestRatio = interestRatio;
                }

                public int getInterestType() {
                    return interestType;
                }

                public void setInterestType(int interestType) {
                    this.interestType = interestType;
                }

                public int getMemberId() {
                    return memberId;
                }

                public void setMemberId(int memberId) {
                    this.memberId = memberId;
                }
            }
        }
    }
}
