package com.jh.wxqb.bean;

/**
 * Created by Administrator on 2018/6/4 0004.
 * 首页
 */

public class HomeBean {
    private int img;
    private String name;
    private String price;
    private String company;

    public HomeBean() {
    }

    public HomeBean(int img,String name, String price, String company) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
