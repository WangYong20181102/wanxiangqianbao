package com.jh.wxqb.customview;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jh.wxqb.R;


/**
 * @author zhaoyasong
 * @date 28/09/2017 15:58
 * @description 创建取消或确认的Dialog
 */
public class MyDialog extends Dialog {

    public MyDialog(Context context, String title, String content) {
        super(context, R.style.custom_dialog);
        //指定布局
        setContentView(R.layout.item_my_dialog);
        //点击外部不消失
        setCancelable(false);
        //设置标题
        TextView titleTv = (TextView) findViewById(R.id.dialog_title_tv);
        TextView tvContent = (TextView) findViewById(R.id.tv_content);
        titleTv.setText(title);
        if (content != null) {
            tvContent.setText(content);
        } else {
            tvContent.setVisibility(View.GONE);
        }
        //设置取消按钮的点击事件
        findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                cancel();
            }
        });

        //设置确认按钮的点击事件
        findViewById(R.id.ok_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认
                ok();
            }
        });
    }

    /**
     * 确认的方法
     */
    public void ok() {

    }
}
