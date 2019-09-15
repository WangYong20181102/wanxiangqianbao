package com.jh.wxqb.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2018/5/24 0024.
 * 获取APP版本号
 */

public class GetEditionCode {
    /**
     * 获取版本信息
     *
     * @throws PackageManager.NameNotFoundException
     */
    public static String getVersionNameCode(Context context) {
        //获取包管理对象
        PackageManager packageManager = context.getPackageManager();
        //获取包信息
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo.versionName;  //获取版本码
    }
}
