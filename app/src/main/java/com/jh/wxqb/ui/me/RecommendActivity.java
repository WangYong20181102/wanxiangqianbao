package com.jh.wxqb.ui.me;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.api.ServerInterface;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.CoreKeys;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.customview.CircleImageView;
import com.jh.wxqb.utils.BitmapUtil;
import com.jh.wxqb.utils.PreferencesLoader;
import com.jh.wxqb.utils.QRImageUtil;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.Toasts;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我要推薦
 */
public class RecommendActivity extends BaseActivity {


    @BindView(R.id.iv_user_img)
    CircleImageView ivUserImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    @Override
    protected int getLayout() {
        return R.layout.activity_recommend;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        if (PreferencesLoader.getObject(CoreKeys.USER_IMG, String.class) != null) {
            ivUserImg.setImageBitmap(BitmapUtil.compressImage(BitmapUtil.ratio(BitmapUtil.stringtoBitmap(MyApplication.getUserImg()), 200, 200)));
        }
        if (MyApplication.getUserBean() != null) {
            tvName.setText(MyApplication.getUserBean().getUserName());
            tvCode.setText(MyApplication.getUserBean().getUserId());
            tvAddress.setText(StringUtil.ellipsisString(ServerInterface.BASE_WEB_REGISTER_URL + MyApplication.getUserBean().getUserId(),20));
            Bitmap nobgQRImage = QRImageUtil.createNobgQRImage(this, ServerInterface.BASE_WEB_REGISTER_URL + MyApplication.getUserBean().getUserId());
            ivCode.setImageBitmap(nobgQRImage);
        }

    }

    @OnClick(R.id.tv_copy)
    public void onViewClicked() {
        StringUtil.copy(ServerInterface.BASE_WEB_REGISTER_URL + MyApplication.getUserBean().getUserId(),this);
        Toasts.showShort(R.string.replicating_success);
    }
}
