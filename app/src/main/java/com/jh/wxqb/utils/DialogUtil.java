package com.jh.wxqb.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jh.wxqb.R;


/**
 * 简单的对话框提示工具类
 *
 * @author dell
 */
public class DialogUtil {

    // 因为本类不是activity所以通过继承接口的方法获取到点击的事件
    public interface OnClickYesListener {
        void onClickYes(String strPassword);
    }

    public interface OnClickNoListener {
        void onClickNo();
    }

    /**
     * 提示对话框
     */
    public static Dialog customDialog;

    /**
     * @param context
     * @return
     */
    public static Dialog customDialog(Context context, final OnClickYesListener listenerYes) {

        Activity activity = (Activity) context;

        customDialog = new Dialog(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_password, null);
        final EditText etPassword = view.findViewById(R.id.ed_pwd);
        TextView tvCancle = view.findViewById(R.id.tv_cancel);
        TextView tvSure = view.findViewById(R.id.tv_sure);
        customDialog.setContentView(view);
        customDialog.setCancelable(false);
        tvCancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });
        tvSure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listenerYes != null) {
                    listenerYes.onClickYes(etPassword.getText().toString());
                }
                customDialog.dismiss();
            }
        });

        // 判断Activity是否被销毁
        if (!activity.isFinishing()) {
            customDialog.dismiss();
            customDialog.show();
        }

        return customDialog;
    }


}
