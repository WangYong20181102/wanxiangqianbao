package com.jh.wxqb.base;


public interface OnBaseListener {

    /**
     * 服务器响应
     */
    void onResponse(String result);

    /**
     * 服务器未响应
     */
    void onFailure(String e,int code);
}
