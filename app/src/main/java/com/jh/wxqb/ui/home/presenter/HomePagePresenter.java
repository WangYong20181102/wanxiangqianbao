package com.jh.wxqb.ui.home.presenter;

import com.jh.wxqb.api.ServerInterface;
import com.jh.wxqb.api.biz.Biz;
import com.jh.wxqb.api.biz.bizImpl.BizImpl;
import com.jh.wxqb.base.KChatLineBase;
import com.jh.wxqb.base.NewsBean;
import com.jh.wxqb.base.OnBaseListener;
import com.jh.wxqb.bean.MarketDividendBottomBean;
import com.jh.wxqb.bean.UserBean;
import com.jh.wxqb.bean.VersionUpdateBean;
import com.jh.wxqb.ui.home.view.HomePagerView;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;

import java.util.Map;

public class HomePagePresenter {
    private Biz biz;
    private HomePagerView homePagerView;

    public HomePagePresenter(HomePagerView homePagerView) {
        this.homePagerView = homePagerView;
        biz = new BizImpl();
    }

    /**
     * 获取个人信息
     */
    public void getUserInfo() {
        biz.getUserInfo(new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("personalInfo==>" + GsonUtil.GsonString(result));
                    UserBean userBean = GsonUtil.GsonToBean(result, UserBean.class);
                    if (userBean.getCode() == ServerInterface.SUCCESS) {
                        homePagerView.getUserInfoSuccess(userBean);
                    } else {
                        homePagerView.onViewFailureString(userBean.getCode(), userBean.getMessage());
                    }
                } else {
                    homePagerView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                homePagerView.onServerFailure(e, code);
            }
        });
    }


    /**
     * 版本更新
     */
    public void versionUpdate() {
        biz.versionUpdate(new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("createWalletAddress==>" + GsonUtil.GsonString(result));
                    VersionUpdateBean versionUpdateBean = GsonUtil.GsonToBean(result, VersionUpdateBean.class);
                    if (versionUpdateBean.getCode() == ServerInterface.SUCCESS) {
                        homePagerView.versionUpdateSuccess(versionUpdateBean);
                    } else {
                        homePagerView.onViewFailureString(versionUpdateBean.getCode(), versionUpdateBean.getMessage());
                    }
                } else {
                    homePagerView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                homePagerView.onServerFailure(e, code);
            }
        });
    }
    /**
     * 新闻资讯
     */
    public void getNews(int type) {
        biz.getNewVoices(type, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    NewsBean newsBean = GsonUtil.GsonToBean(result, NewsBean.class);
                    if (newsBean.getCode() == ServerInterface.SUCCESS) {
                        homePagerView.myNewMessageSuccess(newsBean);
                    } else {
                        homePagerView.onViewFailureString(newsBean.getCode(), newsBean.getMessage());
                    }
                } else {
                    homePagerView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                homePagerView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 折线图
     */
    public void getLineChatData() {
        biz.getKChatLineData(new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    KChatLineBase kChatLineBase = GsonUtil.GsonToBean(result, KChatLineBase.class);
                    if (kChatLineBase.getCode() == ServerInterface.SUCCESS) {
                        homePagerView.myKLineSuccess(kChatLineBase);
                    } else {
                        homePagerView.onViewFailureString(kChatLineBase.getCode(), kChatLineBase.getMessage());
                    }
                } else {
                    homePagerView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                homePagerView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 最近成交
     */
    public void dividendMarketTopDividend(int pageNum, int type, int direction) {
        biz.dividendMarketTopDividend(pageNum, type, direction, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    MarketDividendBottomBean marketDividendBottomBean = GsonUtil.GsonToBean(result, MarketDividendBottomBean.class);
                    if (marketDividendBottomBean.getCode() == ServerInterface.SUCCESS) {
                        homePagerView.myRecentTransactionSuccess(marketDividendBottomBean);
                    } else {
                        homePagerView.onViewFailureString(marketDividendBottomBean.getCode(), marketDividendBottomBean.getMessage());
                    }
                } else {
                    homePagerView.onServerFailure("网络异常", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                homePagerView.onServerFailure(e, code);
            }
        });
    }

}
