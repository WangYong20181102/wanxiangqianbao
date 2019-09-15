package com.jh.wxqb.bean;

/**
 * Created by Administrator on 2018/6/15 0015.
 */

public class TokenBean extends BaseValue {

    /**
     * access_token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjkwNjY3ODUsInVzZXJfbmFtZSI6IjE4OTU1MDQ0NjY0IiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImI1NzgxMDEwLWFlYTItNDM0Mi1iNDE2LTQ0MmMzN2YyMzlhYyIsImNsaWVudF9pZCI6Imthb2xhIiwic2NvcGUiOlsiYWxsIiwicmVhZCIsIndyaXRlIl19.bm_clXJkZvGEqhyWJukTVql1JO-n7bSUcCMLWxOnB10
     * token_type : bearer
     * refresh_token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiIxODk1NTA0NDY2NCIsInNjb3BlIjpbImFsbCIsInJlYWQiLCJ3cml0ZSJdLCJhdGkiOiJiNTc4MTAxMC1hZWEyLTQzNDItYjQxNi00NDJjMzdmMjM5YWMiLCJleHAiOjE1MzE2NTUxODUsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJiMjgzOTdlYi05YmViLTRlMTMtODg1Yi1hMzgyZTgzMzUxMGEiLCJjbGllbnRfaWQiOiJrYW9sYSJ9.wP2yDtmWbfuDM0hjgti2Km7rECNSzHh65wJo8qOdig0
     * expires_in : 3599
     * scope : all read write
     * jti : b5781010-aea2-4342-b416-442c37f239ac
     */

    private String access_token;
    private String token_type;
    private String refresh_token;
    private String expires_in;
    private String scope;
    private String jti;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }
}
