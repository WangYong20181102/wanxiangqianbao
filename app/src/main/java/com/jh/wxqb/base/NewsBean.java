package com.jh.wxqb.base;

import java.util.List;

public class NewsBean {

    /**
     * code : 8008
     * message : 成功
     * data : {"noticeMap":[{"endDate":"2019-12-31 00:00:00","id":7,"title":"公告  万象钱包已完成3.0版本升级","startDate":"2019-10-07 00:00:00","createDate":"2019-10-11 18:56:01"},{"endDate":"2019-12-31 00:00:00","id":6,"title":"以太坊是一种商品 将允许以太坊期货上市 ","startDate":"2019-10-08 00:00:00","createDate":"2019-10-11 17:06:30"},{"endDate":"2019-12-28 00:00:00","id":5,"title":"美国国税局新规：因分叉取得虚拟货币要交所得税","startDate":"2019-10-08 00:00:00","createDate":"2019-10-11 17:04:54"},{"endDate":"2019-12-28 00:00:00","id":4,"title":"巴西国税局：8月起国民有义务报告加密交易情况 违者将面临罚款","startDate":"2019-09-20 00:00:00","createDate":"2019-09-24 20:37:29"},{"endDate":"2019-12-13 00:00:00","id":3,"title":"泰国政府将修订反洗钱法律以规范加密货币","startDate":"2019-09-18 00:00:00","createDate":"2019-09-24 20:36:38"}],"pageSize":2}
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
         * noticeMap : [{"endDate":"2019-12-31 00:00:00","id":7,"title":"公告  万象钱包已完成3.0版本升级","startDate":"2019-10-07 00:00:00","createDate":"2019-10-11 18:56:01"},{"endDate":"2019-12-31 00:00:00","id":6,"title":"以太坊是一种商品 将允许以太坊期货上市 ","startDate":"2019-10-08 00:00:00","createDate":"2019-10-11 17:06:30"},{"endDate":"2019-12-28 00:00:00","id":5,"title":"美国国税局新规：因分叉取得虚拟货币要交所得税","startDate":"2019-10-08 00:00:00","createDate":"2019-10-11 17:04:54"},{"endDate":"2019-12-28 00:00:00","id":4,"title":"巴西国税局：8月起国民有义务报告加密交易情况 违者将面临罚款","startDate":"2019-09-20 00:00:00","createDate":"2019-09-24 20:37:29"},{"endDate":"2019-12-13 00:00:00","id":3,"title":"泰国政府将修订反洗钱法律以规范加密货币","startDate":"2019-09-18 00:00:00","createDate":"2019-09-24 20:36:38"}]
         * pageSize : 2
         */

        private int pageSize;
        private List<NoticeMapBean> noticeMap;

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public List<NoticeMapBean> getNoticeMap() {
            return noticeMap;
        }

        public void setNoticeMap(List<NoticeMapBean> noticeMap) {
            this.noticeMap = noticeMap;
        }

        public static class NoticeMapBean {
            /**
             * endDate : 2019-12-31 00:00:00
             * id : 7
             * title : 公告  万象钱包已完成3.0版本升级
             * startDate : 2019-10-07 00:00:00
             * createDate : 2019-10-11 18:56:01
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
