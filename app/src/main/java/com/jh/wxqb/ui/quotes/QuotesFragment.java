package com.jh.wxqb.ui.quotes;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.base.BaseFragment;
import com.jh.wxqb.utils.TabLayoutUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 行情
 */
public class QuotesFragment extends BaseFragment implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {

    @BindView(R.id.tv_quotes)
    TextView tvQuotes;
    Unbinder unbinder;
    @BindView(R.id.view_pager_quotes)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    private View view;
    private String[] titles = new String[]{"USDT", "TGM"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_quotes, container, false);
            EventBus.getDefault().register(this);
        }
        unbinder = ButterKnife.bind(this, view);
        initTab();
        return view;
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(2);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new CoinTypeQuotesFragment());
        fragmentList.add(new CoinTypeQuotesFragment1());

        QuotesTitleViewPagerAdapter adapter = new QuotesTitleViewPagerAdapter(getActivity().getSupportFragmentManager(), getActivity(), fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                TabLayoutUtils.getInstance().setIndicator(tabLayout, 34,34);
            }
        });
        //将tab和Viewpager绑定
        tabLayout.setupWithViewPager(viewPager);
        //关联之后设置tab的名称在这之前要完成Viewpager的实例化不然会报空指针异常
        for (int i = 0; i < titles.length; i++) {
            tabLayout.getTabAt(i).setText(titles[i]);
        }
        //tabLayout监听
        tabLayout.setOnTabSelectedListener(this);

    }

    @Subscribe
    public void updateQuotes(String quotes) {
        switch (quotes) {

        }
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
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

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
