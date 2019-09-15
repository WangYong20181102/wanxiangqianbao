package com.jh.wxqb.bean;

/**
 * 用户个人信息
 */
public class UserBean extends BaseValue {


    /**
     * code : 8008
     * data : {"info":{"activeAssets":800,"configRevenue":0,"dividendAssets":400,"email":"","isCertified":false,"isHasTradePwd":true,"personalId":"","phone":"18124010906","repurchaseAssets":0,"sharingRevenue":1000,"userId":"A0000028","userName":"admin02","walletAddress":"0xfda76ed8ed8ba021e535a2891c59550ed46bf913"}}
     * message : 成功
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * info : {"activeAssets":800,"configRevenue":0,"dividendAssets":400,"email":"","isCertified":false,"isHasTradePwd":true,"personalId":"","phone":"18124010906","repurchaseAssets":0,"sharingRevenue":1000,"userId":"A0000028","userName":"admin02","walletAddress":"0xfda76ed8ed8ba021e535a2891c59550ed46bf913"}
         */

        private UserData info;

        public UserData getInfo() {
            return info;
        }

        public void setInfo(UserData info) {
            this.info = info;
        }

    }
}
