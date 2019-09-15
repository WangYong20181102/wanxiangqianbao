package com.jh.wxqb.ui.me.feedback;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.ViewPagerAdapter;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.CoreKeys;
import com.jh.wxqb.customview.CancelOrOkDialog;
import com.jh.wxqb.utils.LogUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class PlusImageActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ArrayList<String> mImaList;
    private int mPosition;
    private ViewPagerAdapter mAdapter;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindView(R.id.position_tv)
    TextView mPositionTv;
    @Override
    protected int getLayout() {
        return R.layout.activity_plus_image;
    }

    @Override
    protected void init() {
        //获取传递过来的数据
        mImaList = getIntent().getStringArrayListExtra(CoreKeys.IMG_LIST);
        mPosition = getIntent().getIntExtra(CoreKeys.POSITION, 0);
        //初始化控件
        initWidget();
    }


    private void initWidget() {
        mPositionTv = (TextView) findViewById(R.id.position_tv);
        //返回的点击事件
       /* findViewById(R.id.back_iv).setOnClickListener(this);
        findViewById(R.id.delete_iv).setOnClickListener(this);*/
        //设置ViewPager切换的事件监听
        mViewPager.addOnPageChangeListener(this);

        mAdapter = new ViewPagerAdapter(PlusImageActivity.this, mImaList);
        mViewPager.setAdapter(mAdapter);
        mPositionTv.setText(mPosition + 1 + "/" + mImaList.size());
        mViewPager.setCurrentItem(mPosition);
    }

    /**
     * 定义删除图片的方法
     */
    private void deletePic() {
        CancelOrOkDialog dialog = new CancelOrOkDialog(PlusImageActivity.this, R.string.confirmed_that_delete_pictures) {
            @Override
            public void ok() {
                super.ok();
                if (mImaList.size() > 0) {
                    //从数据源移除要删除的图片
                    mImaList.remove(mPosition);
                    setPosition();
                    if(mImaList.size()==0){
                        back();
                    }
                }
                LogUtils.e("当前剩余图片===>"+mImaList.size());
                dismiss();
            }
        };
        dialog.show();

    }

    /**
     * 设置当前显示的位置
     */
    private void setPosition() {
        mPositionTv.setText(mPosition + 1 + "/" + mImaList.size());
        mViewPager.setCurrentItem(mPosition);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 定义方法返回上一个界面
     */
    private void back() {
        Intent intent = getIntent();
        intent.putStringArrayListExtra(CoreKeys.IMG_LIST, mImaList);
        setResult(CoreKeys.RESULT_CODE_VIEW_IMG, intent);
        finish();
    }


    @OnClick({R.id.back_iv,R.id.delete_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                back();
                break;
            case R.id.delete_iv:
                deletePic();
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        mPositionTv.setText(position + 1 + "/" + mImaList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //如果返回键被按下
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
