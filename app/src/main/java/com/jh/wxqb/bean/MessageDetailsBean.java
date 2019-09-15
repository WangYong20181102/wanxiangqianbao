package com.jh.wxqb.bean;

/**
 * Created by Administrator on 2018/8/25 0025.
 * 我的消息详情
 */

public class MessageDetailsBean {

    /**
     * code : 8008
     * message : 成功
     * data : {"sysMessage":{"createTime":"2018-08-23 10:10:53","remark":"aaa","id":1,"title":"测试","content":"测试内容"}}
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
         * sysMessage : {"createTime":"2018-08-23 10:10:53","remark":"aaa","id":1,"title":"测试","content":"测试内容"}
         */

        private SysMessageBean sysMessage;

        public SysMessageBean getSysMessage() {
            return sysMessage;
        }

        public void setSysMessage(SysMessageBean sysMessage) {
            this.sysMessage = sysMessage;
        }

        public static class SysMessageBean {
            /**
             * createTime : 2018-08-23 10:10:53
             * remark : aaa
             * id : 1
             * title : 测试
             * content : 测试内容
             */

            private String createTime;
            private String remark;
            private int id;
            private String title;
            private String content;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
