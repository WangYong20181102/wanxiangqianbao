package com.jh.wxqb.ui.me;

import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.MyApplication;

import butterknife.BindView;

/**
 * 用户信息
 */
public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;

    @Override
    protected int getLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void init() {
        if(MyApplication.getUserBean()!=null){
            tvName.setText(MyApplication.getUserBean().getUserName());
            tvPhone.setText(MyApplication.getUserBean().getPhone());
            tvCode.setText(MyApplication.getUserBean().getUserId());
        }
    }


}
