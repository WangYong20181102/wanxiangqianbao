package com.jh.wxqb.bean;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/6/11 0011.
 * 用户信息
 */

public class UserData extends BaseValue{

    /**
     * activeAssets : 800
     * configRevenue : 0
     * dividendAssets : 400
     * email :
     * isCertified : false
     * isHasTradePwd : true
     * personalId :
     * phone : 18124010906
     * repurchaseAssets : 0
     * sharingRevenue : 1000
     * userId : A0000028
     * userName : admin02
     * walletAddress : 0xfda76ed8ed8ba021e535a2891c59550ed46bf913
     */

    private BigDecimal activeAssets;
    private BigDecimal configRevenue;
    private BigDecimal dividendAssets;
    private String email;
    private boolean isCertified;
    private boolean isHasTradePwd;
    private String personalId;
    private String phone;
    private BigDecimal repurchaseAssets;
    private BigDecimal sharingRevenue;
    private BigDecimal accelerationValue;
    private String userId;
    private String userName;
    private String walletAddress;

    public BigDecimal getAccelerationValue() {
        return accelerationValue;
    }

    public void setAccelerationValue(BigDecimal accelerationValue) {
        this.accelerationValue = accelerationValue;
    }

    public BigDecimal getActiveAssets() {
        return activeAssets;
    }

    public void setActiveAssets(BigDecimal activeAssets) {
        this.activeAssets = activeAssets;
    }

    public BigDecimal getConfigRevenue() {
        return configRevenue;
    }

    public void setConfigRevenue(BigDecimal configRevenue) {
        this.configRevenue = configRevenue;
    }

    public BigDecimal getDividendAssets() {
        return dividendAssets;
    }

    public void setDividendAssets(BigDecimal dividendAssets) {
        this.dividendAssets = dividendAssets;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isCertified() {
        return isCertified;
    }

    public void setCertified(boolean certified) {
        isCertified = certified;
    }

    public boolean getIsHasTradePwd() {
        return isHasTradePwd;
    }

    public void setIsHasTradePwd(boolean hasTradePwd) {
        isHasTradePwd = hasTradePwd;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getRepurchaseAssets() {
        return repurchaseAssets;
    }

    public void setRepurchaseAssets(BigDecimal repurchaseAssets) {
        this.repurchaseAssets = repurchaseAssets;
    }

    public BigDecimal getSharingRevenue() {
        return sharingRevenue;
    }

    public void setSharingRevenue(BigDecimal sharingRevenue) {
        this.sharingRevenue = sharingRevenue;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }
}
