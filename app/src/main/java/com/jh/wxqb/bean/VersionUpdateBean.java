package com.jh.wxqb.bean;

/**
 * Created by Administrator on 2018/6/29 0029.
 * 版本更新
 */

public class VersionUpdateBean {

    /**
     * code : 8008
     * message : 获取成功!
     * data : {"app":{"id":2,"comment":"描述详情","platform":"android","type":"new","url":"https://www.hao123.com","vers":1.2,"version":2,"urlCode":"https://www.hao123.com"}}
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
         * app : {"id":2,"comment":"描述详情","platform":"android","type":"new","url":"https://www.hao123.com","vers":1.2,"version":2,"urlCode":"https://www.hao123.com"}
         */

        private AppBean app;

        public AppBean getApp() {
            return app;
        }

        public void setApp(AppBean app) {
            this.app = app;
        }

        public static class AppBean {
            /**
             * id : 2
             * comment : 描述详情
             * platform : android
             * type : new
             * url : https://www.hao123.com
             * vers : 1.2
             * version : 2
             * urlCode : https://www.hao123.com
             */

            private int id;
            private String comment;
            private String platform;
            private String type;
            private String url;
            private double vers;
            private int version;
            private String urlCode;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public double getVers() {
                return vers;
            }

            public void setVers(double vers) {
                this.vers = vers;
            }

            public int getVersion() {
                return version;
            }

            public void setVersion(int version) {
                this.version = version;
            }

            public String getUrlCode() {
                return urlCode;
            }

            public void setUrlCode(String urlCode) {
                this.urlCode = urlCode;
            }
        }
    }
}
