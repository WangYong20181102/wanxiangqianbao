package com.jh.wxqb.bean;

/**
 * Created by Administrator on 2018/9/6 0006.
 * 获取token失效时间
 */

public class GetTimeBean {

    /**
     * code : 8008
     * message : 成功！
     * data : {"tokenEffectiveTime":3600000,"tokenInvalidTime":46800000}
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
         * tokenEffectiveTime : 3600000
         * tokenInvalidTime : 46800000
         */

        private long tokenEffectiveTime;
        private long tokenInvalidTime;

        public long getTokenEffectiveTime() {
            return tokenEffectiveTime;
        }

        public void setTokenEffectiveTime(long tokenEffectiveTime) {
            this.tokenEffectiveTime = tokenEffectiveTime;
        }

        public long getTokenInvalidTime() {
            return tokenInvalidTime;
        }

        public void setTokenInvalidTime(long tokenInvalidTime) {
            this.tokenInvalidTime = tokenInvalidTime;
        }
    }
}
