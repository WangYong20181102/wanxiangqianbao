package com.jh.wxqb.ui.message;

import com.jh.wxqb.api.ServerInterface;
import com.jh.wxqb.api.biz.Biz;
import com.jh.wxqb.api.biz.bizImpl.BizImpl;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.base.OnBaseListener;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;

/**
 * Created by Administrator on 2018/6/11 0011.
 */

public class MessagePresenter {

    private Biz biz;
    private MessageView messageView;

    public MessagePresenter(MessageView messageView) {
        this.messageView = messageView;
        biz = new BizImpl();
    }


    /**
     * 获取验证码
     */
    public void getMessageCode(String mobile) {
        biz.getMessageCode(mobile, new OnBaseListener() {
            @Override
            public void onResponse(String result) {
                if (GsonUtil.isJson(result)) {
                    LogUtils.e("getMessageCode==>" + GsonUtil.GsonString(result));
                    BaseBean baseBean = GsonUtil.GsonToBean(result, BaseBean.class);
                    if (baseBean.getCode() == ServerInterface.SUCCESS) {
                        messageView.getMessageCodeSuccess(baseBean);
                    } else {
                        messageView.onViewFailureString(baseBean.getCode(), baseBean.getMessage());
                    }
                } else {
                    messageView.onServerFailure("服务器繁忙",0);
                }
            }

            @Override
            public void onFailure(String e,int code) {
                messageView.onServerFailure(e,code);
            }
        });
    }

}
