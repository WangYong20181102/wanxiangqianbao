package com.jh.wxqb.ui.assets.view;


import com.jh.wxqb.base.BaseView;
import com.jh.wxqb.bean.AssetManagementBean;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.FinancialDetailsBean;
import com.jh.wxqb.bean.SafetyMarkingBean;

/**
 * Created by Administrator on 2018/6/11 0011.
 */

public interface AssetsView extends BaseView {

    /**
     * 获取财务明细列表
     *
     * @param result
     */
    void getFinancialDetailsSuccess(FinancialDetailsBean result);

    /**
     * 资产管理
     * @param result
     */
    void getAssetManagementSuccess(AssetManagementBean result);

    /**
     * 提币
     *
     * @param result
     */
    void withdrawMoneySuccess(BaseBean result);

    /**
     * 获取安全标示
     *
     * @param result
     */
    void safetyMarkingSuccess(SafetyMarkingBean result);


}
