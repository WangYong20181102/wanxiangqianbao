package com.jh.wxqb.base;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.jh.wxqb.bean.TokenBean;
import com.jh.wxqb.bean.UserData;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.PreferencesLoader;
import com.jh.wxqb.utils.SPHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MyApplication extends Application {
    public List<Activity> activityList;
    private static MyApplication instance;
    public static boolean isHasTradePwd = false;  //是否有交易密码
    public static boolean isCertified = false;  //是否已认证个人信息
    public static String lanuage;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        changeAppLanguage();
        instance = this;
        activityList = new ArrayList<>();
        //初始化PreferencesLoader
        PreferencesLoader.init(this);
    }

    /**
     * 获取语言
     */
    public static String getLanuage() {
        return lanuage;
    }

    /**
     * 获取用图像
     */
    public static String getUserImg() {
        return PreferencesLoader.getObject(CoreKeys.USER_IMG, String.class);
    }

    /**
     * 设置用图像
     */
    public static void setUserImg(String userImg) {
        PreferencesLoader.setObject(CoreKeys.USER_IMG, userImg);
    }

    /**
     * 获取本地用户信息
     */
    public static UserData getUserBean() {
        return PreferencesLoader.getObject(CoreKeys.USER_BEAN, UserData.class);
    }

    /**
     * 将用户信息存到本地
     */
    public static void setUserBean(UserData userBean) {
        PreferencesLoader.setObject(CoreKeys.USER_BEAN, userBean);
        LogUtils.e("userBean=>" + GsonUtil.GsonString(MyApplication.getUserBean()));
    }

    /**
     * 获取本地用户Token信息
     */
    public static TokenBean getToken() {
        return PreferencesLoader.getObject(CoreKeys.USER_TOKEN, TokenBean.class);
    }

    /**
     * 将用户Token信息存到本地
     */
    public static void setToken(TokenBean token) {
        PreferencesLoader.setObject(CoreKeys.USER_TOKEN, token);
        LogUtils.e("userToken=>" + GsonUtil.GsonString(MyApplication.getToken()));
    }

    //设置语言
    public void changeAppLanguage() {
        String sta = (String) SPHelper.get(this, CoreKeys.SAVE_LANGUAGE, "zh");
        // 本地语言设置
        Locale myLocale = new Locale(sta);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        lanuage = this.getResources().getConfiguration().locale.getLanguage();
        LogUtils.e("当前语言配置=" + lanuage);
    }

    /**
     * 加载activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 关闭程序，退出所有activity
     */
    public void systemExit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}
