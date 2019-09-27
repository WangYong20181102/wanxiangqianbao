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
     * data : {"resultMap":{"id":38,"configKey":"usdt_price","configName":"USDT价格(人民币)","configValue":"7"},"usdtAccount":30}
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
         * resultMap : {"id":38,"configKey":"usdt_price","configName":"USDT价格(人民币)","configValue":"7"}
         * usdtAccount : 30.0
         */

        private ResultMapBean resultMap;
        private BigDecimal usdtAccount;
        private BigDecimal tgmAccount;

        public BigDecimal getTgmAccount() {
            return tgmAccount;
        }

        public void setTgmAccount(BigDecimal tgmAccount) {
            this.tgmAccount = tgmAccount;
        }

        public ResultMapBean getResultMap() {
            return resultMap;
        }

        public void setResultMap(ResultMapBean resultMap) {
            this.resultMap = resultMap;
        }

        public BigDecimal getUsdtAccount() {
            return usdtAccount;
        }

        public void setUsdtAccount(BigDecimal usdtAccount) {
            this.usdtAccount = usdtAccount;
        }

        public static class ResultMapBean {
            /**
             * id : 38
             * configKey : usdt_price
             * configName : USDT价格(人民币)
             * configValue : 7
             */

            private int id;
            private String configKey;
            private String configName;
            private String configValue;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getConfigKey() {
                return configKey;
            }

            public void setConfigKey(String configKey) {
                this.configKey = configKey;
            }

            public String getConfigName() {
                return configName;
            }

            public void setConfigName(String configName) {
                this.configName = configName;
            }

            public String getConfigValue() {
                return configValue;
            }

            public void setConfigValue(String configValue) {
                this.configValue = configValue;
            }
        }
    }
}
