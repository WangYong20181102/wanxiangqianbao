package com.jh.wxqb.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/8/21 0021.\
 * 財務明細
 */

public class FinancialDetailsBean {

    /**
     * code : 8008
     * message : 成功
     * data : {"logList":[{"tradeAmount":100,"AcctType":1,"createTime":1534900575000,"phone":"18124010906","Id":95,"tradeType":6},{"tradeAmount":100,"AcctType":3,"createTime":1534900557000,"phone":"18124010906","Id":93,"tradeType":4}],"pageSize":7}
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
         * logList : [{"tradeAmount":100,"AcctType":1,"createTime":1534900575000,"phone":"18124010906","Id":95,"tradeType":6},{"tradeAmount":100,"AcctType":3,"createTime":1534900557000,"phone":"18124010906","Id":93,"tradeType":4}]
         * pageSize : 7
         */

        private int pageSize;
        private List<LogListBean> logList;

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public List<LogListBean> getLogList() {
            return logList;
        }

        public void setLogList(List<LogListBean> logList) {
            this.logList = logList;
        }

        public static class LogListBean {
            /**
             * tradeAmount : 100
             * AcctType : 1
             * createTime : 1534900575000
             * phone : 18124010906
             * Id : 95
             * tradeType : 6
             */

            private BigDecimal tradeAmount;
            private int AcctType;
            private long createTime;
            private String phone;
            private String Id;
            private int tradeType;
            private int flowType;

            public int getFlowType() {
                return flowType;
            }

            public void setFlowType(int flowType) {
                this.flowType = flowType;
            }

            public BigDecimal getTradeAmount() {
                return tradeAmount;
            }

            public void setTradeAmount(BigDecimal tradeAmount) {
                this.tradeAmount = tradeAmount;
            }

            public int getAcctType() {
                return AcctType;
            }

            public void setAcctType(int AcctType) {
                this.AcctType = AcctType;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public int getTradeType() {
                return tradeType;
            }

            public void setTradeType(int tradeType) {
                this.tradeType = tradeType;
            }
        }
    }
}
