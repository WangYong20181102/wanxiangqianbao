package com.jh.wxqb.base;

import java.util.List;

public class KChatLineBase {

    /**
     * code : 8008
     * message : 成功
     * data : {"eList":[{"id":1,"price":0.14,"createTime":"2019-10-11"},{"id":22,"price":0.16,"createTime":"2019-10-12"},{"id":23,"price":0.1604,"createTime":"2019-10-13"},{"id":24,"price":0.1721,"createTime":"2019-10-14"},{"id":25,"price":0.1721,"createTime":"2019-10-15"},{"id":26,"price":0.1725,"createTime":"2019-10-16"},{"id":27,"price":0.19,"createTime":"2019-10-17"},{"id":28,"price":0.19,"createTime":"2019-10-18"},{"id":29,"price":0.19,"createTime":"2019-10-19"},{"id":30,"price":0.19,"createTime":"2019-10-20"},{"id":31,"price":0.1517,"createTime":"2019-10-21"},{"id":32,"price":0.18,"createTime":"2019-10-22"},{"id":33,"price":0.2,"createTime":"2019-10-23"},{"id":34,"price":0.2402,"createTime":"2019-10-24"},{"id":35,"price":0.2403,"createTime":"2019-10-25"}]}
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
        private List<EListBean> eList;

        public List<EListBean> getEList() {
            return eList;
        }

        public void setEList(List<EListBean> eList) {
            this.eList = eList;
        }

        public static class EListBean {
            /**
             * id : 1
             * price : 0.14
             * createTime : 2019-10-11
             */

            private int id;
            private double price;
            private String createTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
