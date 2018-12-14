package com.integrate.web.service;

import com.integrate.model.Coupon;
import com.integrate.web.dao.CouponDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponDao couponDao;

    public List<Coupon> getCoupons() {
        return couponDao.getCoupons();
    }

    public Coupon getCoupon(long id) {
        return couponDao.getCoupon(id);
    }
}
