package com.jh.wxqb.ui.assets;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.PagerAdapter;
import com.jh.wxqb.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 資產管理
 */
public class AssetsFragment extends BaseFragment implements ViewPager.OnPageChangeListener {


    @BindView(R.id.tv_active_assets)
    TextView tvActiveAssets;
    @BindView(R.id.view_active_assets)
    View viewActiveAssets;
    @BindView(R.id.tv_repurchase_assets)
    TextView tvRepurchaseAssets;
    @BindView(R.id.view_repurchase_assets)
    View viewRepurchaseAssets;
    @BindView(R.id.tv_dividend_assets)
    TextView tvDividendAssets;
    @BindView(R.id.view_dividend_assets)
    View viewDividendAssets;
    @BindView(R.id.layout_view_pager)
    ViewPager layoutAssets;
    @BindViews({R.id.tv_active_assets, R.id.tv_repurchase_assets, R.id.tv_dividend_assets})
    List<TextView> allTitle;
    @BindViews({R.id.view_active_assets, R.id.view_repurchase_assets, R.id.view_dividend_assets})
    List<View> allView;
    private ArrayList<Fragment> fragmentList;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_assets, container, false);
            ButterKnife.bind(this, view);
            initListener();
            initFragment();
            selectTitle(0);
        }
        return view;
    }

    private void initListener() {
        layoutAssets.addOnPageChangeListener(this);
        layoutAssets.setOffscreenPageLimit(3);
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        ActiveAssetsFragment activeAssetsFragment = new ActiveAssetsFragment();
        RepurchaseAssetsFragment repurchaseAssetsFragment = new RepurchaseAssetsFragment();
        DividendAssetsFragment dividendAssetsFragment = new DividendAssetsFragment();
        fragmentList.add(activeAssetsFragment);
        fragmentList.add(repurchaseAssetsFragment);
        fragmentList.add(dividendAssetsFragment);
        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        layoutAssets.setAdapter(adapter);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    @OnClick({R.id.ll_active_assets, R.id.ll_repurchase_assets, R.id.ll_dividend_assets})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_active_assets:
                selectTitle(0);
                break;
            case R.id.ll_repurchase_assets:
                selectTitle(1);
                break;
            case R.id.ll_dividend_assets:
                selectTitle(2);
                break;
        }
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

    //选择标题
    public void selectTitle(int index){
        clearViewAndTitle();
        layoutAssets.setCurrentItem(index);
        allTitle.get(index).setTextColor(Color.parseColor("#1881d2"));
        allView.get(index).setVisibility(View.VISIBLE);
    }

    //初始化标题
    public void clearViewAndTitle() {
        for (TextView text : allTitle) {
            text.setTextColor(Color.WHITE);
        }
        for (View view : allView) {
            view.setVisibility(View.GONE);
        }
    }

}
