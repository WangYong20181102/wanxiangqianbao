package com.jh.wxqb.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/25 0025.
 * 财务明细类型
 */

public class FinancialDetailsTypeBean {

    /**
     * code : 8008
     * message : 成功
     * data : {"assetTypeList":[{"id":1,"name":"全部"},{"id":2,"name":"转出"},{"id":3,"name":"提币"},{"id":4,"name":"转入"},{"id":5,"name":"外部转入"},{"id":6,"name":"買入"},{"id":7,"name":"活动"},{"id":8,"name":"重构"},{"id":9,"name":"買入收益"},{"id":10,"name":"分享奖励"}]}
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
        private List<AssetTypeListBean> assetTypeList;

        public List<AssetTypeListBean> getAssetTypeList() {
            return assetTypeList;
        }

        public void setAssetTypeList(List<AssetTypeListBean> assetTypeList) {
            this.assetTypeList = assetTypeList;
        }

        public static class AssetTypeListBean {
            /**
             * id : 1
             * name : 全部
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
