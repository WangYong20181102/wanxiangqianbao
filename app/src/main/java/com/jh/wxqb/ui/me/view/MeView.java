package com.jh.wxqb.ui.me.view;


import com.jh.wxqb.base.BaseView;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.FinancialDetailsBean;
import com.jh.wxqb.bean.FinancialDetailsTypeBean;
import com.jh.wxqb.bean.MyTeamBean;
import com.jh.wxqb.bean.UserImage;

/**
 * Created by Administrator on 2018/6/11 0011.
 */

public interface MeView extends BaseView {

    /**
     * 修改登录/交易密码
     *
     * @param result
     */
    void udpLoginPwdSuccess(BaseBean result);

    /**
     * 绑定账户邮箱
     *
     * @param result
     */
    void bandingEmailSuccess(BaseBean result);

    /**
     * 我的团队
     *
     * @param result
     */
    void myTeamSuccess(MyTeamBean result);

    /**
     * 查询用户上传的头像
     *
     * @param result
     */
    void selUserImageSuccess(UserImage result);

    /**
     * 上传用户头像
     *
     * @param result
     */
    void uploadUserImageSuccess(BaseBean result);

    /**
     * 获取财务明细类型
     *
     * @param result
     */
    void financialDetailsTypeSuccess(FinancialDetailsTypeBean result);

    /**
     * 获取财务明细列表
     *
     * @param result
     */
    void getFinancialDetailsSuccess(FinancialDetailsBean result);

    /**
     * 获取邮箱验证码
     *
     * @param result
     */
    void getEmailCodeSuccess(BaseBean result);


}
