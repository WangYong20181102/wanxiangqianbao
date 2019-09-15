package com.jh.wxqb.bean;

/**
 * Created by Administrator on 2018/8/30 0030.
 * 安全标示
 */

public class SafetyMarkingBean {

    /**
     * code : 8008
     * message : 成功
     * data : {"ident":"08a7d7bf-cddb-4bd0-9991-588c77df3243"}
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
         * ident : 08a7d7bf-cddb-4bd0-9991-588c77df3243
         */

        private String ident;

        public String getIdent() {
            return ident;
        }

        public void setIdent(String ident) {
            this.ident = ident;
        }
    }
}
