package com.jh.wxqb.bean;

/**
 * Created by Administrator on 2018/8/25 0025.
 * 用户图像
 */

public class UserImage {

    /**
     * code : 8008
     * message : 当前用户没有上传支付宝微信图片
     * data : {"meImg":""}
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
         * meImg :
         */

        private String meImg;

        public String getMeImg() {
            return meImg;
        }

        public void setMeImg(String meImg) {
            this.meImg = meImg;
        }
    }
}
