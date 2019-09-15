package com.jh.wxqb.ui.message;

import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.base.BaseView;

/**
 * Created by Administrator on 2018/6/15 0015.
 */

public interface MessageView extends BaseView{

    /**
     * 短信验证码
     *
     * @param result
     */
    void getMessageCodeSuccess(BaseBean result);


}
