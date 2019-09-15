package com.jh.wxqb.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;

import com.jh.wxqb.base.CoreKeys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zitao on 2016/4/12 0012
 * EMAIL: zzt20080808@126.com
 */
public class MyUtils {

    /**
     * 格式化金额显示，后八位为小数点
     *
     * @param amount 金额输入 ：900000000
     * @return 格式化后输出：9.00000000
     */
    public static String formatCoin(String amount) {
        if (amount.length() <= 8) {
            int length = amount.length();
            for (int i = 0; i < 8 - length + 1; i++) {
                amount = "0" + amount;
            }
        }
        String behindStr = amount.substring(amount.length() - 8, amount.length());
        String frontStr = amount.substring(0, amount.length() - 8);
        return frontStr + "." + behindStr;
    }

    public static String getReserveHash256(String str) {
        String ss = "";
        if (str.length() % 2 != 0) {
//            toastString("算Hash长度不对 : " + str.length());
            Log.e("算Hash长度不对 ", str.length() + "");
        } else {
            String sArr[] = new String[str.length() / 2];
            for (int i = 0; i < str.length() / 2; i++) {
                sArr[i] = str.substring(2 * i, 2 * i + 2);
            }
            List<String> list = new ArrayList<>();
            list = Arrays.asList(sArr);
            Collections.reverse(list);
            for (String word : list) {
                ss += word;
            }
        }
        Log.e("ReserveHash256", ss);
        return ss;
    }

    // 判断是否是电话号码
    public static boolean isPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        phone = phone.trim();
        // return phone.matches("^((1[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        return phone.matches("^((1[3-9][0-9]))\\d{8}$");

    }


    /**
     * 保存到本地
     *
     * @param mBitmap
     */
    public static void savePhotoToLocal(Bitmap mBitmap, String picName) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(CoreKeys.local_file);
        file.mkdirs();// 创建文件夹
        File filePic = new File(CoreKeys.local_file + picName + ".jpg");
        if (filePic.exists()) {
            filePic.delete();
        }
        try {
            b = new FileOutputStream(CoreKeys.local_file + picName + ".jpg");
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            Log.e("saveBitmap", "保存失败 FileNotFoundException");
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                Log.e("saveBitmap", "保存失败 IOException");
                e.printStackTrace();
            }
        }
    }

    /**
     * 校验身份证格式是否正确
     *
     * @return
     */
    public static boolean checkSFZ(String sfzStr) {
        //定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
        //通过Pattern获得Matcher
        Matcher idNumMatcher = idNumPattern.matcher(sfzStr);
        //判断用户输入是否为身份证号
        if (idNumMatcher.matches()) {
//            System.out.println("您的出生年月日是：");
            //如果是，定义正则表达式提取出身份证中的出生日期
//            Pattern birthDatePattern= Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");//身份证上的前6位以及出生年月日
            //通过Pattern获得Matcher
//            Matcher birthDateMather= birthDatePattern.matcher(sfzStr);
            //通过Matcher获得用户的出生年月日
//            if(birthDateMather.find()){
//                String year = birthDateMather.group(1);
//                String month = birthDateMather.group(2);
//                String date = birthDateMather.group(3);
            //输出用户的出生年月日
//                System.out.println(year+"年"+month+"月"+date+"日");
//            }
            return true;
        } else {
            //如果不是，输出信息提示用户
//            System.out.println("您输入的并不是身份证号");
            return false;
        }
    }

    public static int dp2px(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * dip 2 px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        return (int) (dipValue * context.getResources().getDisplayMetrics().density + 0.5f);
    }

}
