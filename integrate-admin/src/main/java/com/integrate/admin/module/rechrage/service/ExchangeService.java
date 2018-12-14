package com.integrate.admin.module.rechrage.service;

import com.integrate.admin.dao.DaoSupport;
import com.integrate.admin.entity.Page;
import com.integrate.admin.module.app.service.ProductService;
import com.integrate.admin.module.city.service.CityService;
import com.integrate.admin.module.rechrage.dao.RechrageDao;
import com.integrate.admin.module.rechrage.model.CouponExchangeResp;
import com.integrate.admin.module.rechrage.model.Recharge;
import com.integrate.admin.module.rechrage.model.Rechrage;
import com.integrate.admin.module.rechrage.model.RechrageResp;
import com.integrate.admin.module.wallet.WalletDao;
import com.integrate.admin.util.PageData;
import com.integrate.common.util.BeanUtil;
import com.integrate.common.util.GlobalIDUtil;
import com.integrate.common.util.StringUtil;
import com.integrate.enums.SysMsgEnumType;
import com.integrate.exception.BusinessException;
import com.integrate.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExchangeService {
    private final static Logger logger = LoggerFactory.getLogger(ExchangeService.class);

    @Autowired
    private DaoSupport dao;

    public List<CouponExchangeResp> getCouponExchanges(Page page) {
        try {
            List<CouponExchangeResp> list = (List<CouponExchangeResp>) dao.findForList("CouponExchangeRespMapper.datalistPage", page);
            logger.warn(" list  size:[{}]", list.size());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public CouponExchangeResp getCouponExchange(long id) {
        try {
            CouponExchangeResp couponExchangeResp = (CouponExchangeResp) dao.findForObject("CouponExchangeRespMapper.getCouponExchangeResp", id);
            return couponExchangeResp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
