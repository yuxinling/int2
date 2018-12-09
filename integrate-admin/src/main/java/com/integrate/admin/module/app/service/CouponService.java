package com.integrate.admin.module.app.service;

import com.integrate.admin.dao.DaoSupport;
import com.integrate.model.Coupon;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CouponService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public Coupon insertCoupon(Coupon coupon) throws Exception {
		if (coupon != null) {
			int count = (int) dao.save("CouponMapper.insertCoupon", coupon);
			if (count == 1) {
				return coupon;
			}
		}
		return null;
	}
	
	public Coupon updateCoupon(Coupon coupon) throws Exception {
		if (coupon != null) {
			int count = (int) dao.update("CouponMapper.updateCoupon", coupon);
			if (count == 1) {
				return coupon;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Coupon> getCoupons() throws Exception {
		return (List<Coupon>) dao.findForList("CouponMapper.getCoupons", null);
	}
	
	public Coupon getCoupon(Long id) throws Exception {
		Coupon Coupon =  (Coupon) dao.findForObject("CouponMapper.getCoupon", id);
		return Coupon;
	}

	public int deleteCoupon(Long id) throws Exception {
		return (int) dao.delete("CouponMapper.deleteCoupon", id);
	}

}
