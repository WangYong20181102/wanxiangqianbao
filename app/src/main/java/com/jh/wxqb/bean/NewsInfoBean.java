package com.jh.wxqb.bean;

/**
 * Created by Administrator on 2018/8/25 0025.
 * 新闻资讯详情
 */

public class NewsInfoBean {

    /**
     * code : 8008
     * message : 成功
     * data : {"noticeContent":{"id":5,"userId":1,"startDate":1535013464000,"endDate":1538383067000,"title":"测试1","content":"测试1","remark":"测试1","updateTime":1535013490000,"createTime":1535013495000}}
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
         * noticeContent : {"id":5,"userId":1,"startDate":1535013464000,"endDate":1538383067000,"title":"测试1","content":"测试1","remark":"测试1","updateTime":1535013490000,"createTime":1535013495000}
         */

        private NoticeContentBean noticeContent;

        public NoticeContentBean getNoticeContent() {
            return noticeContent;
        }

        public void setNoticeContent(NoticeContentBean noticeContent) {
            this.noticeContent = noticeContent;
        }

        public static class NoticeContentBean {
            /**
             * id : 5
             * userId : 1
             * startDate : 1535013464000
             * endDate : 1538383067000
             * title : 测试1
             * content : 测试1
             * remark : 测试1
             * updateTime : 1535013490000
             * createTime : 1535013495000
             */

            private int id;
            private int userId;
            private long startDate;
            private long endDate;
            private String title;
            private String content;
            private String remark;
            private long updateTime;
            private long createTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public long getStartDate() {
                return startDate;
            }

            public void setStartDate(long startDate) {
                this.startDate = startDate;
            }

            public long getEndDate() {
                return endDate;
            }

            public void setEndDate(long endDate) {
                this.endDate = endDate;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }
        }
    }
}
