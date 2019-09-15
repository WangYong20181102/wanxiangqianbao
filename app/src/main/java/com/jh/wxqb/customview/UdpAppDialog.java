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
public class UdpAppDialog extends Dialog {

    public UdpAppDialog(Context context, String title, String prompt, boolean isShow) {
        super(context, R.style.custom_dialog);
        //指定布局
        setContentView(R.layout.item_upd_app);
        //点击外部不消失
        setCancelable(false);
        //设置标题
        TextView titleTv = (TextView) findViewById(R.id.dialog_title_tv);
        TextView tvPrompt = (TextView) findViewById(R.id.tv_prompt);
        TextView tvImportant = (TextView) findViewById(R.id.tv_important);
        TextView cancelTv = (TextView) findViewById(R.id.cancel_tv);
        TextView okTv = (TextView) findViewById(R.id.ok_tv);
        View viewSpipt = (View) findViewById(R.id.view_spipt);

        titleTv.setText(title);

        if(prompt!=null){
            tvPrompt.setVisibility(View.VISIBLE);
            tvPrompt.setText(prompt);
        }else {
            tvPrompt.setVisibility(View.GONE);
        }

        if (isShow) {
            tvImportant.setVisibility(View.VISIBLE);
            cancelTv.setVisibility(View.GONE);
            okTv.setVisibility(View.GONE);
            viewSpipt.setVisibility(View.GONE);
        } else {
            tvImportant.setVisibility(View.GONE);
            cancelTv.setVisibility(View.VISIBLE);
            okTv.setVisibility(View.VISIBLE);
            viewSpipt.setVisibility(View.VISIBLE);
        }
        //设置取消按钮的点击事件
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                cancel();
            }
        });

        //设置确认按钮的点击事件
        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认
                ok();
            }
        });

        //设置确认按钮的点击事件
        tvImportant.setOnClickListener(new View.OnClickListener() {
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
