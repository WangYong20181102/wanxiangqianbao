package com.jh.wxqb.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/8/22 0022.
 * 我的团队
 */

public class MyTeamBean {

    /**
     * code : 8008
     * message : 成功
     * data : {"teamInfo":[{"createTime":"2018-08-20 16:42:13","userName":"admin02","userId":"A0000028"},{"createTime":"2018-08-21 16:42:29","userName":"admin03","userId":"A0000029"},{"createTime":"2018-08-24 10:16:33","userName":"A1234","userId":"A0000030"}]}
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
        private String teamPeples;

        public String getTeamPeples() {
            return teamPeples;
        }

        public void setTeamPeples(String teamPeples) {
            this.teamPeples = teamPeples;
        }

        private List<TeamInfoBean> teamInfo;

        public List<TeamInfoBean> getTeamInfo() {
            return teamInfo;
        }

        public void setTeamInfo(List<TeamInfoBean> teamInfo) {
            this.teamInfo = teamInfo;
        }

        public static class TeamInfoBean {
            /**
             * createTime : 2018-08-20 16:42:13
             * userName : admin02
             * userId : A0000028
             */

            private String createTime;
            private String userName;
            private String userId;
            private BigDecimal activeAsset;

            public BigDecimal getSum() {
                return activeAsset;
            }

            public void setSum(BigDecimal activeAsset) {
                this.activeAsset = activeAsset;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }
    }
}
