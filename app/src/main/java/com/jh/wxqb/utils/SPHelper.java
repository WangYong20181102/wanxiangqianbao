package com.jh.wxqb.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/9 0009.
 */

/**
 * 一般用于记住密码功能
 */
public class SPHelper {

    public static final String FILE_NAME = "file_SPHelper";
    public SPHelper() {
    }

    public static SharedPreferences getSharedPreferences(Context context){
        return  context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
    }

    /**
     * 提供存储数据方法
     * @param context   当前activity
     * @param key       键值
     * @param value     存储的值
     * @return          boolean
     */
    public static boolean save(Context context,String key,Object value){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        if(value instanceof String){
            edit.putString(key,(String) value);
        }else if(value instanceof Boolean){
            edit.putBoolean(key,(Boolean) value);
        }else if(value instanceof Long){
            edit.putLong(key,(Long) value);
        }else if(value instanceof Float){
            edit.putFloat(key,(Float) value);
        }else if(value instanceof Integer){
            edit.putInt(key,(Integer) value);
        }else if(value instanceof List){}
        return edit.commit();
    }

    /**
     *  获取数据的方法
     * @param context  当前activity
     * @param key       键值
     * @param DefVal    默认值
     * @return          获取到的结果
     */
    public static Object get(Context context,String key,Object DefVal){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        Object data=null;
        if(DefVal instanceof String){
            data=sharedPreferences.getString(key,(String) DefVal);
        }else if(DefVal instanceof Boolean){
            data=sharedPreferences.getBoolean(key,(Boolean)DefVal);
        }else if(DefVal instanceof Long){
            data=sharedPreferences.getLong(key,(Long)DefVal);
        }else if(DefVal instanceof Float){
            data=sharedPreferences.getFloat(key,(Float)DefVal);
        }else if(DefVal instanceof Integer){
            data=sharedPreferences.getInt(key,(Integer)DefVal);
        }
        return data;
    }

    /**
     * 根据键值 删除指定数据
     * @param context
     * @param key
     * @return
     */
    public static boolean remove(Context context,String key){
        SharedPreferences.Editor editor= getSharedPreferences(context).edit();
        return  editor.remove(key).commit();
    }

    /**
     * 清除所有数据
     * @param context
     * @return
     */
    public static boolean claer(Context context){
        return  getSharedPreferences(context).edit().clear().commit();
    }


    /**
     * 保存List集合到本地
     * @param mContext
     * @param list
     * @return
     */
    public static boolean saveArray(Context mContext, List<String> list) {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME,mContext.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1= sp.edit();
        mEdit1.putInt("Status_size",list.size());

        for(int i=0;i<list.size();i++) {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, list.get(i));
        }
        return mEdit1.commit();
    }

    /**
     * 获取本地List集合数据
     * @param mContext
     * @return
     */
    public static List<String> loadArray(Context mContext) {
        List<String> list=new ArrayList<>();
        list.clear();
        SharedPreferences mSharedPreference1 = mContext.getSharedPreferences(FILE_NAME, mContext.MODE_PRIVATE);
        int size = mSharedPreference1.getInt("Status_size", 0);
        for(int i=0;i<size;i++) {
            list.add(mSharedPreference1.getString("Status_" + i, null));
        }
        return list;
    }

}
