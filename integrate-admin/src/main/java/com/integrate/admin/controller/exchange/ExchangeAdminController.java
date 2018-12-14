package com.integrate.admin.controller.exchange;

import com.integrate.admin.controller.base.BaseController;
import com.integrate.admin.entity.Page;
import com.integrate.admin.entity.system.AdminUser;
import com.integrate.admin.module.city.service.CityService;
import com.integrate.admin.module.rechrage.model.CouponExchangeResp;
import com.integrate.admin.module.rechrage.model.RechrageResp;
import com.integrate.admin.module.rechrage.service.ExchangeService;
import com.integrate.admin.module.rechrage.service.RechrageService;
import com.integrate.admin.module.user.model.UserBase;
import com.integrate.admin.module.user.service.UserService;
import com.integrate.admin.util.Const;
import com.integrate.admin.util.PageData;
import com.integrate.enums.SysMsgEnumType;
import com.integrate.exception.BusinessException;
import com.integrate.url.UrlCommand;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class ExchangeAdminController extends BaseController {

    @Autowired
    private RechrageService rechrageService;
    @Autowired
    private ExchangeService exchangeService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 0:充值，1：兑换
     *
     * @throws ParseException
     */
    @RequestMapping(value = UrlCommand.rechrage_exchange)
    public ModelAndView exchangelist(Page page, HttpServletRequest request) throws ParseException {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        long begin = 0;
        long end = System.currentTimeMillis();

        String beginTime = pd.getString("begin");
        String endTime = pd.getString("end");
        String type = request.getParameter("type");
        logger.warn("beginTime:[{}],endTime:[{}],type:[{}]", beginTime, endTime, type);
        if (StringUtils.isNotBlank(type)) {
            pd.put("type", type.trim());
        }
        if (StringUtils.isNotBlank(beginTime)) {

            Date b = sdf.parse(beginTime);
            begin = b.getTime();

        }
        if (StringUtils.isNotBlank(endTime)) {
            Date b = sdf.parse(endTime);
            end = b.getTime() + 1000 * 60 * 60 * 24;
        }

        pd.put("begin", begin);
        pd.put("end", end);
        page.setPd(pd);
        List<RechrageResp> rechrageList = rechrageService.getRechrageList(page);
        logger.warn(" size:[{}]", rechrageList.size());
        mv.setViewName("recharge/exchange");
        mv.addObject("type", type);
        mv.addObject("varList", rechrageList);
        mv.addObject("pd", pd);
        if (beginTime == null || StringUtils.isBlank(beginTime)) {
            beginTime = "";
        }
        if (endTime == null || StringUtils.isBlank(endTime)) {
            endTime = "";
        }
        mv.addObject("begin", beginTime);
        mv.addObject("end", endTime);
        mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限

        return mv;
    }

    @RequestMapping(value = UrlCommand.exchange_to_edit)
    public ModelAndView exchangeToEdit(HttpServletRequest request) {

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String mobile = request.getParameter("mobile");
        String status = request.getParameter("status");
        String money = request.getParameter("money");

        RechrageResp recharge = rechrageService.getRecharge(Long.valueOf(id));

        ModelAndView mv = this.getModelAndView();
        mv.addObject("id", id);
        mv.addObject("name", name);
        mv.addObject("mobile", mobile);
        mv.addObject("money", money);
        mv.addObject("status", status);
        mv.addObject("recharge", recharge);
        mv.setViewName("recharge/exchange_edit");

        return mv;
    }

    @RequestMapping(value = UrlCommand.exchange_edit)
    public ModelAndView exchangeEdit(HttpServletRequest request) {

        String rid = request.getParameter("id");
        String status = request.getParameter("status");

        this.rechrageService.updateRechargeStatus(Long.parseLong(rid), Integer.parseInt(status));
        ModelAndView mv = this.getModelAndView();
        mv.addObject("msg", "success");
        mv.setViewName("save_result");

        return mv;
    }

    @RequestMapping(value = UrlCommand.coupon_exchange_list)
    public ModelAndView couponExchangeList(HttpServletRequest request, Page page) throws ParseException {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        long begin = 0;
        long end = System.currentTimeMillis();

        String beginTime = pd.getString("begin");
        String endTime = pd.getString("end");
        String type = request.getParameter("type");
        logger.warn("beginTime:[{}],endTime:[{}],type:[{}]", beginTime, endTime, type);
        if (StringUtils.isNotBlank(type)) {
            pd.put("type", type.trim());
        }
        if (StringUtils.isNotBlank(beginTime)) {
            Date b = sdf.parse(beginTime);
            begin = b.getTime();
        }
        if (StringUtils.isNotBlank(endTime)) {
            Date b = sdf.parse(endTime);
            end = b.getTime() + 1000 * 60 * 60 * 24;
        }

        pd.put("begin", begin);
        pd.put("end", end);
        page.setPd(pd);
        List<CouponExchangeResp> couponExchanges = exchangeService.getCouponExchanges(page);
        mv.setViewName("recharge/coupon_exchange");
        mv.addObject("type", type);
        mv.addObject("varList", couponExchanges);
        mv.addObject("pd", pd);
        if (beginTime == null || StringUtils.isBlank(beginTime)) {
            beginTime = "";
        }
        if (endTime == null || StringUtils.isBlank(endTime)) {
            endTime = "";
        }
        mv.addObject("begin", beginTime);
        mv.addObject("end", endTime);
        mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限

        return mv;

    }

    @RequestMapping(value = UrlCommand.coupon_exchange_to_edit)
    public ModelAndView couponExchangeEdit(HttpServletRequest request) {

        String rid = request.getParameter("id");

        CouponExchangeResp couponExchange = exchangeService.getCouponExchange(Long.parseLong(rid));
        ModelAndView mv = this.getModelAndView();
        mv.addObject("couponExchange", couponExchange);
        mv.setViewName("recharge/coupon_exchange_edit");

        return mv;
    }

}
