package com.jh.wxqb.base;

public interface BaseView {

    /**
     * 服务器返回的不是 200
     * <p>
     * 服务器返回   400 403 404 405 500(自定状态码)
     */
    void onViewFailureString(int statue, String message);

    /**
     * 服务断开
     */
    void onServerFailure(String e,int code);
}
