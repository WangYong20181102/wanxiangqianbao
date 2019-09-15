package com.jh.wxqb.ui.assets;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.ui.me.FinancialDetailsActivity;
import com.jh.wxqb.utils.QRImageUtil;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.Toasts;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 资产--转入
 */
public class TurnInActivity extends BaseActivity {


    @BindView(R.id.ed_transfer_quantity)
    EditText edTransferQuantity;
    @BindView(R.id.tv_create)
    TextView tvCreate;
    @BindView(R.id.iv_qr)
    ImageView ivQr;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_copy)
    TextView tvCopy;

    @Override
    protected int getLayout() {
        return R.layout.activity_turn_in;
    }

    @Override
    protected void init() {
        setTitleListener(R.id.tv_title);
        if (MyApplication.getUserBean() != null) {
            tvAddress.setText(MyApplication.getUserBean().getWalletAddress());
            Bitmap nobgQRImage = QRImageUtil.createNobgQRImage(this,
                    MyApplication.getUserBean().getWalletAddress());
            ivQr.setImageBitmap(nobgQRImage);
        }
    }

    @Override
    protected void toRight() {
        super.toRight();
        Intent intent = new Intent(this, FinancialDetailsActivity.class);
        startActivity(intent);
    }


    @OnClick({R.id.tv_create, R.id.tv_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_create:
                if (!edTransferQuantity.getText().toString().isEmpty()) {
                    Bitmap nobgQRImage = QRImageUtil.createNobgQRImage(this,
                            MyApplication.getUserBean().getWalletAddress()+"zhuanzhang"+edTransferQuantity.getText().toString());
                    ivQr.setImageBitmap(nobgQRImage);
                } else {
                    Toasts.showShort(R.string.please_input_transfer_quantity);
                }
                break;
            case R.id.tv_copy:
                if (MyApplication.getUserBean() != null) {
                    StringUtil.copy(MyApplication.getUserBean().getWalletAddress(), this);
                    Toasts.showShort(R.string.replicating_success);
                }
                break;
        }
    }
}
