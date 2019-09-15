package com.jh.wxqb.bean;

/**
 * Created by Administrator on 2018/6/15 0015.
 * 协议
 */

public class AgreementBean {

    /**
     * code : 8008
     * message : 登录成功
     * data : {"token":{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjkwNjY3ODUsInVzZXJfbmFtZSI6IjE4OTU1MDQ0NjY0IiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImI1NzgxMDEwLWFlYTItNDM0Mi1iNDE2LTQ0MmMzN2YyMzlhYyIsImNsaWVudF9pZCI6Imthb2xhIiwic2NvcGUiOlsiYWxsIiwicmVhZCIsIndyaXRlIl19.bm_clXJkZvGEqhyWJukTVql1JO-n7bSUcCMLWxOnB10","token_type":"bearer","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiIxODk1NTA0NDY2NCIsInNjb3BlIjpbImFsbCIsInJlYWQiLCJ3cml0ZSJdLCJhdGkiOiJiNTc4MTAxMC1hZWEyLTQzNDItYjQxNi00NDJjMzdmMjM5YWMiLCJleHAiOjE1MzE2NTUxODUsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJiMjgzOTdlYi05YmViLTRlMTMtODg1Yi1hMzgyZTgzMzUxMGEiLCJjbGllbnRfaWQiOiJrYW9sYSJ9.wP2yDtmWbfuDM0hjgti2Km7rECNSzHh65wJo8qOdig0","expires_in":3599,"scope":"all read write","jti":"b5781010-aea2-4342-b416-442c37f239ac"}}
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
         * token : {"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjkwNjY3ODUsInVzZXJfbmFtZSI6IjE4OTU1MDQ0NjY0IiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImI1NzgxMDEwLWFlYTItNDM0Mi1iNDE2LTQ0MmMzN2YyMzlhYyIsImNsaWVudF9pZCI6Imthb2xhIiwic2NvcGUiOlsiYWxsIiwicmVhZCIsIndyaXRlIl19.bm_clXJkZvGEqhyWJukTVql1JO-n7bSUcCMLWxOnB10","token_type":"bearer","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiIxODk1NTA0NDY2NCIsInNjb3BlIjpbImFsbCIsInJlYWQiLCJ3cml0ZSJdLCJhdGkiOiJiNTc4MTAxMC1hZWEyLTQzNDItYjQxNi00NDJjMzdmMjM5YWMiLCJleHAiOjE1MzE2NTUxODUsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJiMjgzOTdlYi05YmViLTRlMTMtODg1Yi1hMzgyZTgzMzUxMGEiLCJjbGllbnRfaWQiOiJrYW9sYSJ9.wP2yDtmWbfuDM0hjgti2Km7rECNSzHh65wJo8qOdig0","expires_in":3599,"scope":"all read write","jti":"b5781010-aea2-4342-b416-442c37f239ac"}
         */

        private TokenBean token;

        public TokenBean getToken() {
            return token;
        }

        public void setToken(TokenBean token) {
            this.token = token;
        }

    }
}
