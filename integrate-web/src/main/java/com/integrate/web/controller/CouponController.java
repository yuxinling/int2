package com.integrate.web.controller;

import com.integrate.enums.SysMsgEnumType;
import com.integrate.model.Category;
import com.integrate.model.Coupon;
import com.integrate.url.UrlCommand;
import com.integrate.web.common.Message;
import com.integrate.web.service.CategoryService;
import com.integrate.web.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CouponController {

    @Autowired
    private CouponService couponService;

    @ResponseBody
    @RequestMapping(value = UrlCommand.categorys, method = RequestMethod.GET)
    public void getCategorys(HttpServletRequest request, HttpServletResponse response) {
        List<Coupon> coupons = couponService.getCoupons();
        Message.writeMsg(response, SysMsgEnumType.SUCCESS, coupons);
    }
}
