package com.jh.wxqb.customview;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jh.wxqb.R;

/**
 * success  提现成功/失败
 * title    标题
 * tips     提示信息
 * 提现结果弹窗
 */
public class WithdrawMoneyDialog extends Dialog {
    private Context context;
    private boolean success;
    private int title;
    private int tips;
    private String strTitle;
    private String strTips;
    private ImageView ivImage;
    private TextView tvTitle;
    private TextView tvTips;

//    public WithdrawMoneyDialog(Context context,boolean success,String title,String tips) {
//        super(context, R.style.custom_dialog);
//        this.context=context;
//        this.success=success;
//        this.strTitle=title;
//        this.strTips=tips;
//        initLayout();
//    }

    public WithdrawMoneyDialog(Context context, boolean success) {
        super(context, R.style.custom_dialog);
        this.context = context;
        this.success = success;
        initLayout();
    }

    public WithdrawMoneyDialog(Context context, boolean success, int title) {
        super(context, R.style.custom_dialog);
        this.context = context;
        this.success = success;
        this.title = title;
        initLayout();
    }

    public WithdrawMoneyDialog(Context context, boolean success, int title, int tips) {
        super(context, R.style.custom_dialog);
        this.context = context;
        this.success = success;
        this.title = title;
        this.tips = tips;
        initLayout();
    }

    private void initLayout() {
        //指定布局
        setContentView(R.layout.item_withdraw_money);
        //点击外部不消失
        setCancelable(false);
        //设置标题
        ivImage = (ImageView) findViewById(R.id.iv_img);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTips = (TextView) findViewById(R.id.tv_tips);

        if (success) {
            ivImage.setImageResource(R.drawable.iv_wthdrawmoney_success);
        } else {
            ivImage.setImageResource(R.drawable.iv_wthdrawmoney_error);
        }
//        tvTitle.setText(title);
//        tvTips.setText(tips);

        //设置取消按钮的点击事件
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                cancel();
                clickCancel();
            }
        });

    }

    public void clickCancel() {

    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setTips(String tips) {
        tvTips.setText(tips);
    }

    public void setTitle(int title) {
        tvTitle.setText(title);
    }

    public void setTips(int tips) {
        tvTips.setText(tips);
    }

    public void setImage(int img) {
        ivImage.setImageResource(img);
    }

}
