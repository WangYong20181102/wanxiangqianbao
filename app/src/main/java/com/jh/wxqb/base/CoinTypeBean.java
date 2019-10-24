package com.jh.wxqb.base;

public class CoinTypeBean {
    private int type;
    private String coin;

    public CoinTypeBean(int type, String coin) {
        this.type = type;
        this.coin = coin;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }
}
