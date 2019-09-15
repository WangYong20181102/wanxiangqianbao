package com.jh.wxqb.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/25 0025.
 * 新闻资讯列表
 */

public class NewsMoreListBean {

    /**
     * code : 8008
     * message : 成功
     * data : {"noticeMap":[{"endDate":"2018-09-01 17:03:58","id":10,"title":"测试6","startDate":"2018-08-23 17:04:51","createDate":"2018-08-23 17:05:08"},{"endDate":"2019-01-23 17:04:06","id":9,"title":"测试5","startDate":"2018-08-23 17:04:38","createDate":"2018-08-23 17:05:19"},{"endDate":"2018-09-07 17:04:20","id":8,"title":"测试4","startDate":"2018-08-23 17:02:12","createDate":"2018-08-23 17:05:33"},{"endDate":"2018-08-31 17:04:16","id":7,"title":"测试3","startDate":"2018-08-23 17:02:02","createDate":"2018-08-23 17:05:26"},{"endDate":"2018-08-31 17:04:12","id":6,"title":"测试2","startDate":"2018-08-23 17:01:59","createDate":"2018-08-23 17:05:23"},{"endDate":"2018-10-01 16:37:47","id":5,"title":"测试1","startDate":"2018-08-23 16:37:44","createDate":"2018-08-23 16:38:15"}]}
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
        private List<NoticeMapBean> noticeMap;

        public List<NoticeMapBean> getNoticeMap() {
            return noticeMap;
        }

        public void setNoticeMap(List<NoticeMapBean> noticeMap) {
            this.noticeMap = noticeMap;
        }

        public static class NoticeMapBean {
            /**
             * endDate : 2018-09-01 17:03:58
             * id : 10
             * title : 测试6
             * startDate : 2018-08-23 17:04:51
             * createDate : 2018-08-23 17:05:08
             */

            private String endDate;
            private int id;
            private String title;
            private String startDate;
            private String createDate;

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
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

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
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
