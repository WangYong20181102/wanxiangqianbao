package com.jh.wxqb.base;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.PagerAdapter;
import com.jh.wxqb.customview.NoScrollMainPager;
import com.jh.wxqb.ui.assets.AssetsFragment;
import com.jh.wxqb.ui.home.HomePageFragment;
import com.jh.wxqb.ui.market.MarketPlaceFragment;
import com.jh.wxqb.ui.me.MeFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.layout_home)
    NoScrollMainPager layout_home;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.iv_market)
    ImageView ivMarket;
    @BindView(R.id.tv_market)
    TextView tvMarket;
    @BindView(R.id.iv_assets)
    ImageView ivAssets;
    @BindView(R.id.tv_assets)
    TextView tvAssets;
    @BindView(R.id.iv_me)
    ImageView ivMe;
    @BindView(R.id.tv_me)
    TextView tvMe;

    private long exitTime = 0;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        initListener();
        initFragment();
    }

    private void initListener() {
        layout_home.addOnPageChangeListener(this);
        layout_home.setOffscreenPageLimit(4);
    }

    private void initFragment() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        HomePageFragment homeFragment = new HomePageFragment();
//        MarketFragment marketFragment = new MarketFragment();
        MarketPlaceFragment marketFragment = new MarketPlaceFragment();
        AssetsFragment assetsFragment = new AssetsFragment();
        MeFragment meFragment = new MeFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(marketFragment);
        fragmentList.add(assetsFragment);
        fragmentList.add(meFragment);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), fragmentList);
        layout_home.setAdapter(adapter);
    }

    @OnClick({R.id.ll_home, R.id.ll_market, R.id.ll_assets, R.id.ll_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                setTabSelection(0);
                EventBus.getDefault().post("pauseTimer");//暂停交易市场数据请求
                EventBus.getDefault().post("homeResumeTimer");//开启首页轮播
                break;
            case R.id.ll_market:
                setTabSelection(1);
                EventBus.getDefault().post("homePauseTimer");//暂停首页轮播
                EventBus.getDefault().post("resumeTimer");//开启交易市场数据请求
                break;
            case R.id.ll_assets:
                setTabSelection(2);
                EventBus.getDefault().post("homePauseTimer");//暂停首页轮播
                EventBus.getDefault().post("pauseTimer");//暂停交易市场数据请求
                EventBus.getDefault().post("udpAssestData");
                break;
            case R.id.ll_me:
                setTabSelection(3);
                EventBus.getDefault().post("homePauseTimer");//暂停首页轮播
                EventBus.getDefault().post("pauseTimer");//暂停交易市场数据请求
                EventBus.getDefault().post("udpHome");
                break;
        }
    }

    public void setTabSelection(int index) {
        // 每次选中之前先清除掉上次的选中状态
        clearSelection();
        switch (index) {
            case 0:
                ivHome.setImageResource(R.mipmap.home_true);
                tvHome.setTextColor(ContextCompat.getColor(this, R.color.new_home_text_color));
                break;
            case 1:
                ivMarket.setImageResource(R.mipmap.market_true);
                tvMarket.setTextColor(ContextCompat.getColor(this, R.color.new_home_text_color));
                break;
            case 2:
                ivAssets.setImageResource(R.mipmap.assets_true);
                tvAssets.setTextColor(ContextCompat.getColor(this, R.color.new_home_text_color));
                break;
            case 3:
                ivMe.setImageResource(R.mipmap.me_true);
                tvMe.setTextColor(ContextCompat.getColor(this, R.color.new_home_text_color));
                break;
        }
        layout_home.setCurrentItem(index);
    }

    private void clearSelection() {
        ivHome.setImageResource(R.mipmap.home_false);
        ivMarket.setImageResource(R.mipmap.market_false);
        ivAssets.setImageResource(R.mipmap.assets_false);
        ivMe.setImageResource(R.mipmap.me_false);

        tvHome.setTextColor(ContextCompat.getColor(this, R.color.home_tv_no_click));
        tvMarket.setTextColor(ContextCompat.getColor(this, R.color.home_tv_no_click));
        tvAssets.setTextColor(ContextCompat.getColor(this, R.color.home_tv_no_click));
        tvMe.setTextColor(ContextCompat.getColor(this, R.color.home_tv_no_click));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * 2次退出效果
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), R.string.one_more_exit_program, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
