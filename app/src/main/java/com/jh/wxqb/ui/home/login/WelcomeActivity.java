package com.jh.wxqb.ui.home.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.jh.wxqb.R;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.MainActivity;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.utils.PermissionUtils;

public class WelcomeActivity extends BaseActivity {
    private static final int WHAT_DELAY = 0x11;
    private static final int DELAY_TIME = 5000;// 延时时间
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_DELAY:
                    goActivity();
                    break;
            }
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void init() {
        handler.sendEmptyMessageDelayed(WHAT_DELAY, DELAY_TIME);
        PermissionUtils.writerReadSDcard(this);
    }

    /**
     * 跳转
     */
    private void goActivity() {
        if (MyApplication.getToken() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
