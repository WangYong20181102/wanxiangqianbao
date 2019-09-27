package com.jh.wxqb.ui.assets;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.bean.AssetManagementBean;
import com.jh.wxqb.utils.QRImageUtil;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.Toasts;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 冲币
 */
public class PunchingMoneyActivity extends BaseActivity {

    @BindView(R.id.iv_qr)
    ImageView ivQr;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    @Override
    protected int getLayout() {
        return R.layout.activity_punching_money;
    }

    @Override
    protected void init() {
        if (MyApplication.getUserBean() != null) {
            tvAddress.setText(MyApplication.getUserBean().getWalletAddress());
            Bitmap nobgQRImage = QRImageUtil.createNobgQRImage(this,
                    MyApplication.getUserBean().getWalletAddress());
            ivQr.setImageBitmap(nobgQRImage);
        }
    }


    @OnClick({R.id.tv_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_copy:
                if (MyApplication.getUserBean() != null) {
                    StringUtil.copy(MyApplication.getUserBean().getWalletAddress(), this);
                    Toasts.showShort(R.string.replicating_success);
                }
                break;
        }
    }
}
