package com.jh.wxqb.ui.me.mysendletter;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.PagerAdapter;
import com.jh.wxqb.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 當前委托
 */
public class MySendLetterActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.ll_dividend)
    RelativeLayout llDividend;
    @BindView(R.id.ll_sell)
    RelativeLayout llSell;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_send_letter;
    }

    @Override
    protected void init() {
        initListener();
        initFragment();
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(2);
    }


    private void initFragment() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        DividendFragment dividendFragment = new DividendFragment();
        SellFragment sellFragment = new SellFragment();
        fragmentList.add(dividendFragment);
        fragmentList.add(sellFragment);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectTitle(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    @OnClick({R.id.ll_dividend, R.id.ll_sell})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_dividend:
                selectTitle(0);
                break;
            case R.id.ll_sell:
                selectTitle(1);
                break;
        }
    }

    //选择标题
    public void selectTitle(int index) {
        viewPager.setCurrentItem(index);
        switch (index) {
            case 0:
                llDividend.setBackgroundResource(R.drawable.current_commission_top_bg);
                llSell.setBackgroundResource(R.drawable.market_place_sell_buy_unselect);
                break;
            case 1:
                llDividend.setBackgroundResource(R.drawable.market_place_sell_buy_unselect);
                llSell.setBackgroundResource(R.drawable.current_commission_top_bg);
                break;
        }

    }

}
