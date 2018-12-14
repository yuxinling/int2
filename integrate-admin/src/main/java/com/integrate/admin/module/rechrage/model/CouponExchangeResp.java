package com.integrate.admin.module.rechrage.model;

import com.integrate.model.CouponExchange;

public class CouponExchangeResp extends CouponExchange {
    private String nickName;
    private int money;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
