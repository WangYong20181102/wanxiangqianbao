package com.jh.wxqb.ui.me.feedback.view;


import com.jh.wxqb.base.BaseView;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.FeedbackInfoBean;
import com.jh.wxqb.bean.FeedbackListBean;

/**
 * Created by Administrator on 2018/6/11 0011.
 */

public interface FeedBackView extends BaseView {

    /**
     * 保存用户反馈
     *
     * @param result
     */
    void saveUserFeedbackSuccess(BaseBean result);

    /**
     * 用户反馈列表
     *
     * @param result
     */
    void userFeedbackListSuccess(FeedbackListBean result);

    /**
     * 查询用户反馈详情
     *
     * @param result
     */
    void queryUserFeedbackSuccess(FeedbackInfoBean result);


}
