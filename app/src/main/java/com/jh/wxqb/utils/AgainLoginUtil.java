package com.jh.wxqb.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.jh.wxqb.api.ServerInterface;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.ui.home.login.LoginActivity;

/**
 * Created by Administrator on 2018/5/21 0021.
 * 重新登录
 */

public class AgainLoginUtil {

    public static void againLogin(Context mContext, int statue){
        SharedPreferencesUtil.setPrefInt(mContext,"FirstVerification",0);
        if (statue == ServerInterface.LOGIN_INVALID) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
            MyApplication.setUserBean(null);
            MyApplication.setToken(null);
            for (Activity activity : MyApplication.getInstance().activityList) {
                if (activity == null || activity instanceof LoginActivity) {
                    continue;
                }
                activity.finish();
            }
        }
    }

}
