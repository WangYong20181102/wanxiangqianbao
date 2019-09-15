package com.jh.wxqb.customview;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jh.wxqb.R;


public class AppUpdateProgressDialog extends Dialog {

    private NumberProgressBar numberProgressBar;
    private ImageView ivClose;
    private TextView updateTv;
    private TextView tvContent;
    private TextView tvUdp;
    private TextView tvVersion;
    private String content;
    private String version;
    private boolean isImportant=false;  //标识是否是重要本版

    public AppUpdateProgressDialog(Context context) {
        super(context, R.style.Custom_Progress);
        initLayout();
    }

    public AppUpdateProgressDialog(Context context, String content, boolean isImportant,String version) {
        super(context, R.style.Custom_Progress);
        this.content=content;
        this.isImportant=isImportant;
        this.version=version;
        initLayout();
    }

    private void initLayout() {
        this.setContentView(R.layout.update_progress_layout);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        numberProgressBar = (NumberProgressBar) findViewById(R.id.number_progress);
        ivClose = (ImageView) findViewById(R.id.iv_close);
        updateTv = (TextView) findViewById(R.id.update_tv);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvUdp = (TextView) findViewById(R.id.tv_udp);
        tvVersion = (TextView) findViewById(R.id.tv_version);
        this.setCanceledOnTouchOutside(false);//点击dialog背景部分不消失
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                close();
            }
        });
        tvUdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivClose.setVisibility(View.GONE);
                AppUpdateProgressDialog.this.setCancelable(false);//dialog出现时，点击back键不消失
                tvUdp.setVisibility(View.GONE);
                numberProgressBar.setVisibility(View.VISIBLE);
                updateTv.setVisibility(View.VISIBLE);
                udp();
            }
        });
        if(isImportant){
            ivClose.setVisibility(View.GONE);
            this.setCancelable(false);//dialog出现时，点击back键不消失
        }else {
            this.setCancelable(true);//dialog出现时，点击back键消失
        }
        tvContent.setText(content);
        tvVersion.setText(version);
    }

    public void setProgress(int mProgress) {
        numberProgressBar.setProgress(mProgress);
    }

    /**
     * 更新
     */
    public void udp(){}
    /**
     * 关闭
     */
    public void close(){}
}