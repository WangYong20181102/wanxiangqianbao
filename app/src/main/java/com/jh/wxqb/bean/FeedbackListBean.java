package com.jh.wxqb.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22 0022.
 * 反馈列表
 */

public class FeedbackListBean {

    /**
     * code : 8008
     * message : 成功
     * data : {"mapData":{"pageSum":1,"mapJson":[{"readStatus":null,"id":7,"content":"123456adfdsafas","createDate":"2018-08-23 11:34:53"},{"readStatus":1,"id":6,"content":"123456adfdsafas","createDate":"2018-08-22 15:26:57"},{"readStatus":1,"id":5,"content":"123456adfdsafas","createDate":"2018-08-20 11:52:12"},{"readStatus":1,"id":4,"content":"123456adfdsafas","createDate":"2018-08-20 11:42:57"},{"readStatus":1,"id":3,"content":"123456adfdsafas","createDate":"2018-08-20 11:41:35"},{"readStatus":1,"id":2,"content":"123456","createDate":"2018-08-18 14:14:30"},{"readStatus":1,"id":1,"content":"123456","createDate":"2018-08-18 14:13:45"}]}}
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
         * mapData : {"pageSum":1,"mapJson":[{"readStatus":null,"id":7,"content":"123456adfdsafas","createDate":"2018-08-23 11:34:53"},{"readStatus":1,"id":6,"content":"123456adfdsafas","createDate":"2018-08-22 15:26:57"},{"readStatus":1,"id":5,"content":"123456adfdsafas","createDate":"2018-08-20 11:52:12"},{"readStatus":1,"id":4,"content":"123456adfdsafas","createDate":"2018-08-20 11:42:57"},{"readStatus":1,"id":3,"content":"123456adfdsafas","createDate":"2018-08-20 11:41:35"},{"readStatus":1,"id":2,"content":"123456","createDate":"2018-08-18 14:14:30"},{"readStatus":1,"id":1,"content":"123456","createDate":"2018-08-18 14:13:45"}]}
         */

        private MapDataBean mapData;

        public MapDataBean getMapData() {
            return mapData;
        }

        public void setMapData(MapDataBean mapData) {
            this.mapData = mapData;
        }

        public static class MapDataBean {
            /**
             * pageSum : 1
             * mapJson : [{"readStatus":null,"id":7,"content":"123456adfdsafas","createDate":"2018-08-23 11:34:53"},{"readStatus":1,"id":6,"content":"123456adfdsafas","createDate":"2018-08-22 15:26:57"},{"readStatus":1,"id":5,"content":"123456adfdsafas","createDate":"2018-08-20 11:52:12"},{"readStatus":1,"id":4,"content":"123456adfdsafas","createDate":"2018-08-20 11:42:57"},{"readStatus":1,"id":3,"content":"123456adfdsafas","createDate":"2018-08-20 11:41:35"},{"readStatus":1,"id":2,"content":"123456","createDate":"2018-08-18 14:14:30"},{"readStatus":1,"id":1,"content":"123456","createDate":"2018-08-18 14:13:45"}]
             */

            private int pageSum;
            private List<MapJsonBean> mapJson;

            public int getPageSum() {
                return pageSum;
            }

            public void setPageSum(int pageSum) {
                this.pageSum = pageSum;
            }

            public List<MapJsonBean> getMapJson() {
                return mapJson;
            }

            public void setMapJson(List<MapJsonBean> mapJson) {
                this.mapJson = mapJson;
            }

            public static class MapJsonBean {
                /**
                 * readStatus : null
                 * id : 7
                 * content : 123456adfdsafas
                 * createDate : 2018-08-23 11:34:53
                 */

                private int readStatus;
                private int id;
                private String content;
                private String createDate;

                public int getReadStatus() {
                    return readStatus;
                }

                public void setReadStatus(int readStatus) {
                    this.readStatus = readStatus;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }
            }
        }
    }
}
