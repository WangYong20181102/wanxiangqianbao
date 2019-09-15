package com.jh.wxqb.ui.home.view;


import com.jh.wxqb.base.BaseView;
import com.jh.wxqb.bean.MessageDetailsBean;
import com.jh.wxqb.bean.MyMessageBean;
import com.jh.wxqb.bean.NewsInfoBean;
import com.jh.wxqb.bean.NewsMoreListBean;
import com.jh.wxqb.bean.UserBean;
import com.jh.wxqb.bean.VersionUpdateBean;

/**
 * Created by Administrator on 2018/5/10 0010.
 */

public interface HomeView extends BaseView {
    /**
     * 获取个人信息
     *
     * @param result
     */
    void getUserInfoSuccess(UserBean result);

    /**
     * 版本更新
     * @param result
     */
    void versionUpdateSuccess(VersionUpdateBean result);

    /**
     * 版本更新
     * @param result
     */
    void newsInfoSuccess(NewsInfoBean result);

    /**
     * 新闻资讯列表
     * @param result
     */
    void newsListSuccess(NewsMoreListBean result);

    /**
     * 我的消息列表
     * @param result
     */
    void myMessageSuccess(MyMessageBean result);

    /**
     * 我的消息详情
     * @param result
     */
    void myMessageDetailsSuccess(MessageDetailsBean result);

}
