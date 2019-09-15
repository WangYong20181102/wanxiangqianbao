package com.jh.wxqb.ui.me.feedback.presenter;


import com.jh.wxqb.api.ServerInterface;
import com.jh.wxqb.api.biz.Biz;
import com.jh.wxqb.api.biz.bizImpl.BizImpl;
import com.jh.wxqb.base.OnBaseListener;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.FeedbackInfoBean;
import com.jh.wxqb.bean.FeedbackListBean;
import com.jh.wxqb.ui.me.feedback.view.FeedBackView;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2018/6/11 0011.
 */

public class FeedBackPresenter {

    private Biz biz;
    private FeedBackView feedBackView;

    public FeedBackPresenter(FeedBackView feedBackView) {
        this.feedBackView = feedBackView;
        biz = new BizImpl();
    }

    /**
     * 保存用户反馈
     */
    public void saveUserFeedback(List<MultipartBody.Part> file, String content) {
        biz.saveUserFeedback(file, content, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("saveUserFeedback==>" + GsonUtil.GsonString(result));
                    BaseBean baseBean = GsonUtil.GsonToBean(result, BaseBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        feedBackView.saveUserFeedbackSuccess(baseBean);
                    } else {
                        feedBackView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    feedBackView.onServerFailure("服务器繁忙", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                feedBackView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 用户反馈列表
     */
    public void userFeedbackList(int pageNum) {
        biz.userFeedbackList(pageNum, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("userFeedbackList==>" + GsonUtil.GsonString(result));
                    FeedbackListBean feedbackListBean = GsonUtil.GsonToBean(result, FeedbackListBean.class);
                    if (feedbackListBean.getCode() == ServerInterface.SUCCESS) {
                        feedBackView.userFeedbackListSuccess(feedbackListBean);
                    } else {
                        feedBackView.onViewFailureString(feedbackListBean.getCode(), feedbackListBean.getMessage());
                    }
                } else {
                    feedBackView.onServerFailure("服务器繁忙", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                feedBackView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 查询用户反馈详情
     */
    public void queryUserFeedback(String id) {
        biz.queryUserFeedback(id, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("queryUserFeedback==>" + GsonUtil.GsonString(result));
                    FeedbackInfoBean feedbackInfoBean = GsonUtil.GsonToBean(result, FeedbackInfoBean.class);
                    if (feedbackInfoBean.getCode() == ServerInterface.SUCCESS) {
                        feedBackView.queryUserFeedbackSuccess(feedbackInfoBean);
                    } else {
                        feedBackView.onViewFailureString(feedbackInfoBean.getCode(), feedbackInfoBean.getMessage());
                    }
                } else {
                    feedBackView.onServerFailure("服务器繁忙", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                feedBackView.onServerFailure(e, code);
            }
        });
    }

}
