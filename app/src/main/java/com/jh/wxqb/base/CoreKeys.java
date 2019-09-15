package com.jh.wxqb.base;


import android.os.Environment;

import java.io.File;

public class CoreKeys {

    public static final String USER_BEAN = "userBean";
    public static final String USER_TOKEN= "userToken";
    public static final String FIRST_OPEN = "firstOpen";
    public static final String TOKEN = "token";
    public static final String CHARACTER_SET = "utf-8";
    public static final int RESULT_CODE = 5;
    public static final String LOAD_SUCCESS ="加载完成";
    public static final String REFRESH_SUCCESS ="刷新成功";

    public static String local_file = Environment.getExternalStorageDirectory()
            + File.separator + Environment.DIRECTORY_DCIM
            + File.separator + "Camera" + File.separator;
    public static String down_file = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bxtx/";

    public static final int RESULT_LOAD_IMAGE = 0;
    //最多上传3张图片
    public static final int MAX_SELECT_PIV_NUM = 3;
    //共几张图片
    public static final String IMG_LIST = "img_list";
    //第几张图片
    public static final String POSITION = "position";
    //查看大图页面的结果码
    public static final int RESULT_CODE_VIEW_IMG = 11;
    //请求码
    public static final int REQUEST_CODE_MAIN = 10;
    //拍照返回码
    public static final int TAKEPICTURE = 6;
    public static final int NUMBER_ONE = 1;
    //存储用户图像
    public static final String USER_IMG= "userimg";
    //保存语言
    public static final String SAVE_LANGUAGE = "save_language";



}
