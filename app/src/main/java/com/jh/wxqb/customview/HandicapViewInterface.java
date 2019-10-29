package com.jh.wxqb.customview;

import android.graphics.Canvas;

import com.jh.wxqb.bean.MarketDividendTitleBean;

import java.util.List;

public interface HandicapViewInterface {
    /**
     * 初始化画笔
     */
    void initPaint();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化标题
     */
    void initTitle();

    /**
     * 绘制标题
     */
    void drawTitle(Canvas canvas);

    /**
     * 绘制盘口上半部分
     */
    void drawHandicapTopView(Canvas canvas);

    /**
     * 绘制当前价格
     */
    void drawNowPrice(Canvas canvas);

    /**
     * 绘制盘口下半部分
     */
    void drawHandicapDownView(Canvas canvas);

    /**
     * 设置数据(socket 抓到数据后更新盘口显示)
     */
    void updateData(MarketDividendTitleBean.DataBean.ListBean listBeans);

    /**
     * 设置数据(socket 抓到数据后更新盘口显示)
     */
    void updateData(MarketDividendTitleBean.DataBean.ListBean.BuyListBean buyListBean,MarketDividendTitleBean.DataBean.ListBean.SellListBean sellListBean);


}
