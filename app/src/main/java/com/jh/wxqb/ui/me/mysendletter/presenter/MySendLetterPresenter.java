package com.jh.wxqb.ui.me.mysendletter.presenter;


import com.jh.wxqb.api.ServerInterface;
import com.jh.wxqb.api.biz.Biz;
import com.jh.wxqb.api.biz.bizImpl.BizImpl;
import com.jh.wxqb.base.OnBaseListener;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.MeDividend;
import com.jh.wxqb.ui.me.mysendletter.view.MySendLetterView;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/11 0011.
 */

public class MySendLetterPresenter {

    private Biz biz;
    private MySendLetterView mySendLetterView;

    public MySendLetterPresenter(MySendLetterView mySendLetterView) {
        this.mySendLetterView = mySendLetterView;
        biz = new BizImpl();
    }

    /**
     * 我的買入
     */
    public void myDividend(int pageNum, int type) {
        biz.myDividend(pageNum, type, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("personalInfo==>" + GsonUtil.GsonString(result));
                    MeDividend meDividend = GsonUtil.GsonToBean(result, MeDividend.class);

                    if (meDividend.getCode() == ServerInterface.SUCCESS) {
                        mySendLetterView.myDividendSuccess(meDividend);
                    } else {
                        mySendLetterView.onViewFailureString(meDividend.getCode(), meDividend.getMessage());
                    }
                } else {
                    mySendLetterView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                mySendLetterView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 停止買入
     */
    public void stopDividend(Map<String, String> map) {
        biz.stopDividend(map, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("stopDividend==>" + GsonUtil.GsonString(result));
                    BaseBean baseBean = GsonUtil.GsonToBean(result, BaseBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        mySendLetterView.stopDividendSuccess(baseBean);
                    } else {
                        mySendLetterView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    mySendLetterView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                mySendLetterView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 撤消挂卖/停止排队
     */
    public void stopQueuing(Map<String, String> map) {
        biz.stopQueuing(map, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("stopQueuing==>" + GsonUtil.GsonString(result));
                    BaseBean baseBean = GsonUtil.GsonToBean(result, BaseBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        mySendLetterView.stopQueuingSuccess(baseBean);
                    } else {
                        mySendLetterView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    mySendLetterView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                mySendLetterView.onServerFailure(e, code);
            }
        });
    }
}
