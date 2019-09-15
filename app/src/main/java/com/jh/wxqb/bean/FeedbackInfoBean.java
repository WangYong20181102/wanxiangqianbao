package com.jh.wxqb.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22 0022.
 * 反馈内容
 */

public class FeedbackInfoBean {

    /**
     * code : 8008
     * data : {"message":{"backDate":"","createDate":1534572825000,"feedback":"","fileList":[{"file":"/9j/4AAQSkZJRgABAgAAAQABAAD/2wBD"}],"id":1,"memo":"123456","name":"admin02","readStatus":1}}
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
         * message : {"backDate":"","createDate":1534572825000,"feedback":"","fileList":[{"file":"/9j/4AAQSkZJRgABAgAAAQABAAD/2wBD"}],"id":1,"memo":"123456","name":"admin02","readStatus":1}
         */

        private MessageBean message;

        public MessageBean getMessage() {
            return message;
        }

        public void setMessage(MessageBean message) {
            this.message = message;
        }

        public static class MessageBean {
            /**
             * backDate :
             * createDate : 1534572825000
             * feedback :
             * fileList : [{"file":"/9j/4AAQSkZJRgABAgAAAQABAAD/2wBD"}]
             * id : 1
             * memo : 123456
             * name : admin02
             * readStatus : 1
             */

            private String backDate;
            private long createDate;
            private String feedback;
            private int id;
            private String memo;
            private String name;
            private int readStatus;
            private List<FileListBean> fileList;

            public String getBackDate() {
                return backDate;
            }

            public void setBackDate(String backDate) {
                this.backDate = backDate;
            }

            public long getCreateDate() {
                return createDate;
            }

            public void setCreateDate(long createDate) {
                this.createDate = createDate;
            }

            public String getFeedback() {
                return feedback;
            }

            public void setFeedback(String feedback) {
                this.feedback = feedback;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getReadStatus() {
                return readStatus;
            }

            public void setReadStatus(int readStatus) {
                this.readStatus = readStatus;
            }

            public List<FileListBean> getFileList() {
                return fileList;
            }

            public void setFileList(List<FileListBean> fileList) {
                this.fileList = fileList;
            }

            public static class FileListBean {
                /**
                 * file : /9j/4AAQSkZJRgABAgAAAQABAAD/2wBD
                 */

                private String file;

                public String getFile() {
                    return file;
                }

                public void setFile(String file) {
                    this.file = file;
                }
            }
        }
    }
}
