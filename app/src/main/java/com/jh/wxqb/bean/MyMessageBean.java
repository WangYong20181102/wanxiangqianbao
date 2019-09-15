package com.jh.wxqb.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/20 0020.
 * 我的消息列表
 */

public class MyMessageBean {

    /**
     * code : 8008
     * message : 成功
     * data : {"mapData":{"pageSum":1,"mapJson":[{"readStatus":1,"id":1,"title":"测试","createDate":"2018-08-23 10:49:30"}]}}
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
         * mapData : {"pageSum":1,"mapJson":[{"readStatus":1,"id":1,"title":"测试","createDate":"2018-08-23 10:49:30"}]}
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
             * mapJson : [{"readStatus":1,"id":1,"title":"测试","createDate":"2018-08-23 10:49:30"}]
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
                 * readStatus : 1
                 * id : 1
                 * title : 测试
                 * createDate : 2018-08-23 10:49:30
                 */

                private int readStatus;
                private int id;
                private String title;
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

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
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
