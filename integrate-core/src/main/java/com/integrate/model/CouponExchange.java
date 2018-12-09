package com.integrate.model;

public class CouponExchange {
    private long id;
    private long coupon_id;
    private String account;
    private int accountType; //1 - 手机号；2 - 加油卡号； 3 - 银行卡号
    private int amount; //兑换数量
    private int status; //状态：0 - 未处理；1 - 已处理

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(long coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
