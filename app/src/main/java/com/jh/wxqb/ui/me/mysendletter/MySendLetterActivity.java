package com.jh.wxqb.ui.me.mysendletter;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.PagerAdapter;
import com.jh.wxqb.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * 當前委托
 */
public class MySendLetterActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindViews({R.id.tv_dividend, R.id.tv_sell})
    List<TextView> allTitle;
    @BindViews({R.id.view_dividend, R.id.view_sell})
    List<View> allView;
    private ArrayList<Fragment> fragmentList;

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
        fragmentList = new ArrayList<>();
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
        clearViewAndTitle();
        viewPager.setCurrentItem(index);
        allTitle.get(index).setTextColor(getResources().getColor(R.color.color_16263E));
        allView.get(index).setVisibility(View.VISIBLE);
    }

    //初始化标题
    public void clearViewAndTitle() {
        for (TextView text : allTitle) {
            text.setTextColor(getResources().getColor(R.color.color_8C9FAD));
        }
        for (View view : allView) {
            view.setVisibility(View.GONE);
        }
    }

}
