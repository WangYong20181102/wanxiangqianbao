package com.jh.wxqb.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jh.wxqb.R;
import com.jh.wxqb.bean.MarketDividendTitleBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HandicapView extends View implements HandicapViewInterface {

    private final String TAG = "HandicapView";
    /**
     * 布局宽度
     */
    private int viewWidth;
    /**
     * 布局高度
     */
    private int viewHeight;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 买入背景色
     */
    private int buyBgColor;
    /**
     * 卖出背景色
     */
    private int sellBgColor;
    /**
     * 买入文字颜色
     */
    private int buyTextColor;
    /**
     * 卖出文字颜色
     */
    private int sellTextColor;
    /**
     * 标题文字
     */
    private String[] titles;
    /**
     * 画背景的画笔
     */
    private Paint paint;
    /**
     * 画文字的画笔
     */
    private TextPaint titlePaint;
    /**
     * 背景的矩形
     */
    private Rect rect;

    private BigDecimal nowPrice = new BigDecimal(0);

    private List<MarketDividendTitleBean.DataBean.ListBean.BuyListBean> buyList = new ArrayList<>();

    private List<MarketDividendTitleBean.DataBean.ListBean.SellListBean> sellList = new ArrayList<>();

    private BigDecimal max = new BigDecimal(5000);

    /**
     * 人民币汇率自己初始化
     */
    private BigDecimal rate = new BigDecimal(7);

    /**
     * 中间部分的高度
     */
    private int middleHeight;

    /**
     * 盘口上半部分的高度
     */
    private int topViewHeight;

    /**
     * 标题栏的高度
     */
    private int titleViewHeight;
    private int itemHeight;
    private int titleColor;
    private int itemNumberColor;
    private int titleSize;
    private int itemSize;
    private int aboutPriceSize;
    private int nowPriceSize;
    private int nowPricePadding;
    private int nowPriceLineHeight;
    private int titlePadding;
    private int textPaddingStart;
    private int textPaddingEnd;

    private SelectItemListener selectItemListener;

    public HandicapView(Context context) {
        this(context, null);
    }

    public HandicapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HandicapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        if (initType(context, attrs)) {
            return;
        }
        initPaint();
        initTitle();
        initData();
    }

    public void setSelectItemListener(SelectItemListener selectItemListener) {
        this.selectItemListener = selectItemListener;
    }

    private boolean initType(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HandicapView);
        buyBgColor = typedArray.getResourceId(R.styleable.HandicapView_buy_bg_color, ContextCompat.getColor(context, R.color.colorBuy));
        sellBgColor = typedArray.getResourceId(R.styleable.HandicapView_sell_bg_color, ContextCompat.getColor(context, R.color.colorSell));
        buyTextColor = typedArray.getResourceId(R.styleable.HandicapView_buy_text_color, ContextCompat.getColor(context, R.color.colorTextBuy));
        sellTextColor = typedArray.getResourceId(R.styleable.HandicapView_sell_text_color, ContextCompat.getColor(context, R.color.colorTextSell));
        titleColor = typedArray.getResourceId(R.styleable.HandicapView_title_text_color, ContextCompat.getColor(context, R.color.colorTitleColor));
        itemNumberColor = typedArray.getResourceId(R.styleable.HandicapView_item_number_color, ContextCompat.getColor(context, R.color.color_646482));
        titleSize = (int) typedArray.getDimension(R.styleable.HandicapView_title_text_size, getDimension(R.dimen.s11));
        titlePadding = (int) typedArray.getDimension(R.styleable.HandicapView_title_text_padding, getDimension(R.dimen.d10));
        itemSize = (int) typedArray.getDimension(R.styleable.HandicapView_item_text_size, getDimension(R.dimen.s12));
        aboutPriceSize = (int) typedArray.getDimension(R.styleable.HandicapView_now_price_size, getDimension(R.dimen.s11));
        nowPriceSize = (int) typedArray.getDimension(R.styleable.HandicapView_now_price_size, getDimension(R.dimen.s17));
        nowPricePadding = (int) typedArray.getDimension(R.styleable.HandicapView_now_price_padding, getDimension(R.dimen.d5));
        nowPriceLineHeight = (int) typedArray.getDimension(R.styleable.HandicapView_now_price_line_height, getDimension(R.dimen.d5));
        textPaddingStart = (int) typedArray.getDimension(R.styleable.HandicapView_text_padding_start, getDimension(R.dimen.d0));
        textPaddingEnd = (int) typedArray.getDimension(R.styleable.HandicapView_text_padding_end, getDimension(R.dimen.d15));

        typedArray.recycle();
        return false;
    }

    private float getDimension(@DimenRes int resId) {
        return getResources().getDimension(resId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        drawTitle(canvas);
        drawNowPrice(canvas);
        drawHandicapTopView(canvas);
        drawHandicapDownView(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        Log.e(TAG, "new W :" + w);
        Log.e(TAG, "new H" + h);
    }

    @Override
    public void initPaint() {
        paint = new Paint();
        //开启抗锯齿
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        titlePaint = new TextPaint();
        //初始化标题的画笔
        titlePaint.setAntiAlias(true);
        titlePaint.setDither(true);
        titlePaint.setStrokeWidth(3);
        titlePaint.setTextSize(titleSize);
        titlePaint.setColor(titleColor);
        rect = new Rect();
    }

    @Override
    public void initData() {
        for (int i = 0; i < 7; i++) {
            buyList.add(new MarketDividendTitleBean.DataBean.ListBean.BuyListBean());
            sellList.add(new MarketDividendTitleBean.DataBean.ListBean.SellListBean());
        }
    }

    @Override
    public void initTitle() {
        titles = new String[]{context.getString(R.string.price), context.getString(R.string.market_number)};
    }

    @Override
    public void drawTitle(Canvas canvas) {
        titlePaint.setTextSize(titleSize);
        titlePaint.setColor(titleColor);
        //获取文字所在的矩形
        titlePaint.getTextBounds(titles[0], 0, titles[0].length(), rect);
        // 文本的上边距配置
        int h = titlePadding + rect.height();
        canvas.drawText(titles[0], textPaddingStart, h, titlePaint);

        titlePaint.getTextBounds(titles[1], 0, titles[1].length(), rect);
        //起始位置 = view的宽度 - 文字的宽度 - 右边边距的宽度

        int w = viewWidth - textPaddingEnd - rect.width();
        canvas.drawText(titles[1], w, h, titlePaint);

        titleViewHeight = h + titlePadding;
    }

    @Override
    public void drawNowPrice(Canvas canvas) {

        if (buyList != null && buyList.size() > 0) {
            measureTopViewHeight(nowPricePadding, nowPriceLineHeight);
            BigDecimal onePrice = buyList.get(0).getAmountPrice();
            if (onePrice == null) {
                onePrice = BigDecimal.valueOf(0);
            }
            titlePaint.setTextSize(nowPriceSize);
            titlePaint.setColor(ContextCompat.getColor(context, R.color.color_03AD8F));
            titlePaint.getTextBounds(nowPrice.toPlainString(), 0, nowPrice.toPlainString().length(), rect);
            int h = titleViewHeight + topViewHeight + nowPricePadding + rect.height();
            canvas.drawText(String.valueOf(new BigDecimal(String.valueOf(onePrice)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue()), textPaddingStart, h, titlePaint);
            //cny
            BigDecimal multiply1 = onePrice.multiply(rate);
            String cny = String.valueOf(new BigDecimal(String.valueOf(multiply1)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
            titlePaint.setTextSize(aboutPriceSize);
            titlePaint.setColor(ContextCompat.getColor(context, R.color.color_9393A8));
            titlePaint.getTextBounds(nowPrice.toPlainString(), 0, nowPrice.toPlainString().length(), rect);
            h = h + nowPriceLineHeight + rect.height();
            //todo 自己将≈配置到string.xml
            canvas.drawText("≈¥ " + cny + "CNY", textPaddingStart, h, titlePaint);
        }


    }

    /**
     * 测量
     *
     * @param nowPriceHeight  文字的上下边距
     * @param nowPriceSpacing 文字的中间边距
     */
    private void measureTopViewHeight(int nowPriceHeight, int nowPriceSpacing) {
        // 计算当前价格的文字大小
        //当前价格的大小
        titlePaint.setTextSize(nowPriceSize);
        titlePaint.getTextBounds(nowPrice.toPlainString(), 0, nowPrice.toPlainString().length(), rect);
        int h1 = rect.height();
        //价格
        titlePaint.setTextSize(aboutPriceSize);
        titlePaint.getTextBounds(nowPrice.toPlainString(), 0, nowPrice.toPlainString().length(), rect);
        int h2 = rect.height();
        middleHeight = h1 + h2 + 2 * nowPriceHeight + nowPriceSpacing;

        topViewHeight = (viewHeight - titleViewHeight - middleHeight) / 2;
    }

    @Override
    public void drawHandicapTopView(Canvas canvas) {
        paint.setColor(sellBgColor);
        titlePaint.setTextSize(itemSize);
        itemHeight = topViewHeight / 7;
        int viewTopNoTitle = 0;
        int viewTop = titleViewHeight;
        for (float i = 0; i < buyList.size(); i++) {
            // 卖盘需要倒序绘制
            int index = 6 - (int) i;
            //这里取出价格
            String price = getSellPrice(index, sellList);

            //这里取出价格
            String number = getSellNumber(index, sellList);

            BigDecimal depth = sellList.get(index) == null ? null : sellList.get(index).getDepth();
            //draw BG 左上右下
            drawBg(canvas, i, depth, viewTopNoTitle);

            // draw price
            int textBottom = drawPrice(canvas, viewTop, (int) i, price, sellTextColor);

            // draw Number
            drawNumber(canvas, number, textBottom);
        }

    }


    @Override
    public void drawHandicapDownView(Canvas canvas) {
        paint.setColor(buyBgColor);

        int viewTopNoTitle = topViewHeight + middleHeight;
        //下部分盘口距离顶部的距离
        int viewTop = titleViewHeight + viewTopNoTitle;

        for (float i = 0; i < buyList.size(); i++) {
            int index = (int) i;

            //这里取出价格
            String price = getBuyPrice(index, buyList);

            //这里取出价格
            String number = getBuyNumber(index, buyList);

            BigDecimal depth = buyList.get(index) == null ? null : buyList.get(index).getDepth();

            //draw BG 左上右下
            drawBg(canvas, i, depth, viewTopNoTitle);

            // draw price
            int textBottom = drawPrice(canvas, viewTop, (int) i, price, buyTextColor);

            // draw Number
            drawNumber(canvas, number, textBottom);
        }
    }

    /**
     * 获取数量
     *
     * @param index index
     * @return 数量
     */
    private String getBuyNumber(int index, List<MarketDividendTitleBean.DataBean.ListBean.BuyListBean> list) {
        String number;
        if (max.floatValue() == 0) {
            number = "--";
        } else if (list.get(index) != null && list.get(index).getCountUnfilledVolume() != null) {
            number = list.get(index).getCountUnfilledVolume().toPlainString();
        } else {
            number = "--";
        }
        return number;
    }

    /**
     * 获取数量
     *
     * @param index index
     * @return 数量
     */
    private String getSellNumber(int index, List<MarketDividendTitleBean.DataBean.ListBean.SellListBean> list) {
        String number;
        if (max.floatValue() == 0) {
            number = "--";
        } else if (list.get(index) != null && list.get(index).getCountUnfilledVolume() != null) {
            number = list.get(index).getCountUnfilledVolume().toPlainString();
        } else {
            number = "--";
        }
        return number;
    }

    /**
     * 获取价格
     *
     * @param index index
     * @return price
     */
    private String getBuyPrice(int index, List<MarketDividendTitleBean.DataBean.ListBean.BuyListBean> list) {
        String price;
        if (max.floatValue() == 0) {
            price = "--";
        } else if (list.get(index) != null && list.get(index).getAmountPrice() != null) {
            price = String.valueOf(list.get(index).getAmountPrice());
        } else {
            price = "--";
        }
        return price;
    }

    /**
     * 获取价格
     *
     * @param index index
     * @return price
     */
    private String getSellPrice(int index, List<MarketDividendTitleBean.DataBean.ListBean.SellListBean> list) {
        String price;
        if (max.floatValue() == 0) {
            price = "--";
        } else if (list.get(index) != null && list.get(index).getAmountPrice() != null) {
            price = list.get(index).getAmountPrice().toPlainString();
        } else {
            price = "--";
        }
        return price;
    }


    /**
     * 画出盘口背景
     *
     * @param canvas canvas
     * @param i      第几条
     * @param number number
     */
    private void drawBg(Canvas canvas, float i, BigDecimal number, int top) {
        //max == 0 说明是空数据。 number=null也是空数据
        if (number != null && max.floatValue() != 0) {

            if (number.compareTo(max) > 0) {//大于5000设置背景色块取值等于5000
                number = max;
            }
            int left = (int) ((new BigDecimal(1).subtract(number.divide(max, 4, BigDecimal.ROUND_DOWN))).floatValue() * viewWidth);
            Rect rectBg = new Rect(
                    left,
                    titleViewHeight + itemHeight * (int) i + top,
                    viewWidth,
                    ((int) i + 1) * itemHeight + titleViewHeight + top);
            canvas.drawRect(rectBg, paint);
        }

    }

    /**
     * 画出盘口条目中的价格
     *
     * @param canvas  canvas
     * @param viewTop 盘口距离顶部的高度
     * @param i       第几个条目
     * @param price   价格
     * @param color   文字颜色
     * @return 返回当前条目文字的底部距离 给绘制数量使用
     */
    private int drawPrice(Canvas canvas, int viewTop, int i, String price, int color) {
        String currentPrice;
        if (price.contains("--")) {
            currentPrice = "--";
        } else {
            double d1 = new BigDecimal(price).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            currentPrice = String.valueOf(d1);
        }

        titlePaint.getTextBounds(currentPrice, 0, currentPrice.length(), rect);
        //当前item中文字底部的高度 = 条目的一半 + 文字的一半
        int textBottom = viewTop + itemHeight * i + (itemHeight / 2 + rect.height() / 2);
        //下面的条目的高度需要算出每个条目文字的底部距离
        titlePaint.setColor(color);
        canvas.drawText(currentPrice, textPaddingStart, textBottom, titlePaint);
        return textBottom;
    }


    /**
     * 画出盘口条目中的数量
     *
     * @param canvas     canvas
     * @param number     数量
     * @param textBottom 文字的绘制Y坐标
     */
    private void drawNumber(Canvas canvas, String number, int textBottom) {
        String currentNumber;
        if (number.contains("--")) {
            currentNumber = "--";
        } else {
            double d1 = new BigDecimal(number).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            currentNumber = String.valueOf(d1);
        }
        titlePaint.getTextBounds(currentNumber, 0, currentNumber.length(), rect);
        int w = viewWidth - textPaddingEnd - rect.width();
        titlePaint.setColor(itemNumberColor);
        canvas.drawText(currentNumber, w, textBottom, titlePaint);
    }


    /**
     * 重写触摸事件实现点击回调回来价格
     *
     * @param event event
     * @return 拦截触摸事件
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (selectItemListener != null) {
            Log.e(TAG, "类型：" + event.getAction());
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    int y = (int) event.getY();
                    int x = (int) event.getX();
                    //手势滑动到外面以后不处理
                    if (x > 0 && x < viewWidth && y > 0 && y < viewHeight) {
                        int h = titleViewHeight + topViewHeight + middleHeight;
                        // 判断如果点击标题或者中间显示价格部分 不处理
                        if (y < titleViewHeight || (y > titleViewHeight + topViewHeight && y < h)) {
                            return super.onTouchEvent(event);
                        } else if (y < h) {//盘口上半部分
                            int selectItem = (y - titleViewHeight) / itemHeight;
                            Log.e(TAG, "点击了买入第几条：" + selectItem);
                            if (buyList != null && buyList.get(selectItem) != null) {
                                selectItemListener.selectPrice(String.valueOf(buyList.get(selectItem).getAmountPrice()));
                            }
                        } else if (y > h) {
                            int selectItem = (y - titleViewHeight - topViewHeight - middleHeight) / itemHeight;
                            Log.e(TAG, "点击了卖出第几条：" + selectItem);
                            if (sellList != null && sellList.get(selectItem) != null) {
                                selectItemListener.selectPrice(String.valueOf(sellList.get(selectItem).getAmountPrice()));
                            }

                        }
                    }
                    break;
                default:
                    break;
            }
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }


    /**
     * 聚合数据 刷新页面
     */
    @Override
    public void updateData(MarketDividendTitleBean.DataBean.ListBean listBeans) {
        List<MarketDividendTitleBean.DataBean.ListBean.BuyListBean> buyListBeans = listBeans.getBuyList();
        List<MarketDividendTitleBean.DataBean.ListBean.SellListBean> sellListBeans = listBeans.getSellList();
        if (buyListBeans != null) {
            buyList.clear();
            //初始化买盘列表的数据
            for (int i = 0; i < 7; i++) {
                if (i < buyListBeans.size()) {
                    MarketDividendTitleBean.DataBean.ListBean.BuyListBean bean = buyListBeans.get(i);
                    bean.setDepth(bean.getCountUnfilledVolume());
                    buyList.add(bean);
                } else {
                    buyList.add(null);
                }
            }
        }

        if (sellListBeans != null) {
            sellList.clear();
            //初始化卖盘列表的数据
            for (int i = 0; i < 7; i++) {
                if (i < sellListBeans.size()) {
                    MarketDividendTitleBean.DataBean.ListBean.SellListBean bean = sellListBeans.get(i);
                    bean.setDepth(bean.getCountUnfilledVolume());
                    sellList.add(bean);
                } else {
                    sellList.add(null);
                }
            }
        }
        // 刷新页面
        invalidate();
    }

    @Override
    public void updateData(MarketDividendTitleBean.DataBean.ListBean.BuyListBean buyListBean, MarketDividendTitleBean.DataBean.ListBean.SellListBean sellListBean) {

    }

    public interface SelectItemListener {
        void selectPrice(String selectPrice);
    }
}
