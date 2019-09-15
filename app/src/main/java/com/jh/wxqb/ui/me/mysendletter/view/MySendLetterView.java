package com.jh.wxqb.ui.me.mysendletter.view;


import com.jh.wxqb.base.BaseView;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.MeDividend;

/**
 * Created by Administrator on 2018/6/11 0011.
 */

public interface MySendLetterView extends BaseView {
    /**
     * 我的買入
     *
     * @param result
     */
    void myDividendSuccess(MeDividend result);

    /**
     * 停止買入
     *
     * @param result
     */
    void stopDividendSuccess(BaseBean result);

    /**
     * 撤消挂卖/停止排队
     *
     * @param result
     */
    void stopQueuingSuccess(BaseBean result);

}
