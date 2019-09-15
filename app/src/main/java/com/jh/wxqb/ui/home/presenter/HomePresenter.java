package com.jh.wxqb.ui.home.presenter;

import com.jh.wxqb.api.ServerInterface;
import com.jh.wxqb.api.biz.Biz;
import com.jh.wxqb.api.biz.bizImpl.BizImpl;
import com.jh.wxqb.base.OnBaseListener;
import com.jh.wxqb.bean.MessageDetailsBean;
import com.jh.wxqb.bean.MyMessageBean;
import com.jh.wxqb.bean.NewsInfoBean;
import com.jh.wxqb.bean.NewsMoreListBean;
import com.jh.wxqb.bean.UserBean;
import com.jh.wxqb.bean.VersionUpdateBean;
import com.jh.wxqb.ui.home.view.HomeView;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;

/**
 * Created by Administrator on 2018/5/10 0010.
 */

public class HomePresenter {

    private Biz biz;
    private HomeView homeView;

    public HomePresenter(HomeView homeView) {
        this.homeView = homeView;
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
                        homeView.getUserInfoSuccess(userBean);
                    } else {
                        homeView.onViewFailureString(userBean.getCode(), userBean.getMessage());
                    }
                } else {
                    homeView.onServerFailure("服务器繁忙", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                homeView.onServerFailure(e, code);
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
                        homeView.versionUpdateSuccess(versionUpdateBean);
                    } else {
                        homeView.onViewFailureString(versionUpdateBean.getCode(), versionUpdateBean.getMessage());
                    }
                } else {
                    homeView.onServerFailure("服务器繁忙", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                homeView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 查看新闻资讯详情
     */
    public void newsInfo(String id) {
        biz.newsInfo(id, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("newsInfo==>" + GsonUtil.GsonString(result));
                    NewsInfoBean newsInfoBean = GsonUtil.GsonToBean(result, NewsInfoBean.class);
                    if (newsInfoBean.getCode() == ServerInterface.SUCCESS) {
                        homeView.newsInfoSuccess(newsInfoBean);
                    } else {
                        homeView.onViewFailureString(newsInfoBean.getCode(), newsInfoBean.getMessage());
                    }
                } else {
                    homeView.onServerFailure("服务器繁忙", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                homeView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 新闻资讯列表
     */
    public void newsList(String type, int pageNum) {
        biz.newsList(type, pageNum, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("newsList==>" + GsonUtil.GsonString(result));
                    NewsMoreListBean newsMoreListBean = GsonUtil.GsonToBean(result, NewsMoreListBean.class);
                    if (newsMoreListBean.getCode() == ServerInterface.SUCCESS) {
                        homeView.newsListSuccess(newsMoreListBean);
                    } else {
                        homeView.onViewFailureString(newsMoreListBean.getCode(), newsMoreListBean.getMessage());
                    }
                } else {
                    homeView.onServerFailure("服务器繁忙", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                homeView.onServerFailure(e, code);
            }
        });
    }


    /**
     * 我的消息列表
     */
    public void myMessage(int pageNum) {
        biz.myMessage(pageNum, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("myMessage==>" + GsonUtil.GsonString(result));
                    MyMessageBean myMessageBean = GsonUtil.GsonToBean(result, MyMessageBean.class);
                    if (myMessageBean.getCode() == ServerInterface.SUCCESS) {
                        homeView.myMessageSuccess(myMessageBean);
                    } else {
                        homeView.onViewFailureString(myMessageBean.getCode(), myMessageBean.getMessage());
                    }
                } else {
                    homeView.onServerFailure("服务器繁忙", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                homeView.onServerFailure(e, code);
            }
        });
    }

    /**
     * 我的消息详情
     */
    public void myMessageDetails(String id) {
        biz.myMessageDetails(id, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("myMessageDetails==>" + GsonUtil.GsonString(result));
                    MessageDetailsBean messageDetailsBean = GsonUtil.GsonToBean(result, MessageDetailsBean.class);
                    if (messageDetailsBean.getCode() == ServerInterface.SUCCESS) {
                        homeView.myMessageDetailsSuccess(messageDetailsBean);
                    } else {
                        homeView.onViewFailureString(messageDetailsBean.getCode(), messageDetailsBean.getMessage());
                    }
                } else {
                    homeView.onServerFailure("服务器繁忙", 0);
                }
            }

            @Override
            public void onFailure(String e, int code) {
                homeView.onServerFailure(e, code);
            }
        });
    }
}
