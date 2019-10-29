package com.jh.wxqb.ui.home.view;

import com.jh.wxqb.base.BaseView;
import com.jh.wxqb.base.KChatLineBase;
import com.jh.wxqb.base.NewsBean;
import com.jh.wxqb.bean.MarketDividendBottomBean;
import com.jh.wxqb.bean.UserBean;
import com.jh.wxqb.bean.VersionUpdateBean;

public interface HomePagerView extends BaseView {
    /**
     * 新闻资讯
     * @param result
     */
    void myNewMessageSuccess(NewsBean result);
    /**
     * 走势图
     * @param result
     */
    void myKLineSuccess(KChatLineBase result);
    /**
     * 最近成交
     */
    void myRecentTransactionSuccess(MarketDividendBottomBean marketDividendBottomBean);
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
}
