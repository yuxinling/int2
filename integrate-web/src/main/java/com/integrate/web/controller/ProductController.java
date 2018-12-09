package com.integrate.web.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.integrate.common.util.StringUtil;
import com.integrate.enums.SysMsgEnumType;
import com.integrate.model.Product;
import com.integrate.url.UrlCommand;
import com.integrate.web.common.Message;
import com.integrate.web.service.ProductService;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @ResponseBody
    @RequestMapping(value = UrlCommand.product, method = RequestMethod.GET)
    public void getProducts(HttpServletRequest request, HttpServletResponse response) {
        String lastId = StringUtil.getString(request.getParameter("lastId"));
        String pageSize = StringUtil.getString(request.getParameter("pageSize"));
        String categoryId = StringUtil.getString(request.getParameter("categoryId"));
        if (StringUtils.isBlank(pageSize)) pageSize = "20";
        if (!StringUtils.isNumeric(pageSize)) {
            Message.writeError(response, SysMsgEnumType.PARAM_LACK);
            return;
        }
        if (StringUtils.isBlank(lastId)) {
            lastId = "0";
        }
        if (StringUtils.isBlank(categoryId)) {
            categoryId = "0";
        }
        if (StringUtils.isNotBlank(lastId) && !StringUtils.isNumeric(lastId)) {
            Message.writeError(response, SysMsgEnumType.PARAM_LACK);
            return;
        }

        List<Map<String, Object>> products = productService.getProducts(Long.parseLong(categoryId), Long.parseLong(lastId), Integer.valueOf(pageSize));
        Message.writeMsg(response, SysMsgEnumType.SUCCESS, products);
    }

    @ResponseBody
    @RequestMapping(value = UrlCommand.product_search, method = RequestMethod.GET)
    public void searchProducts(HttpServletRequest request, HttpServletResponse response) {

        String keyword = StringUtil.getString(request.getParameter("kw"));
        String lastId = StringUtil.getString(request.getParameter("lastId"));
        String pageSize = StringUtil.getString(request.getParameter("pageSize"));
        if (StringUtils.isBlank(pageSize)) pageSize = "20";
        if (!StringUtils.isNumeric(pageSize)) {
            Message.writeError(response, SysMsgEnumType.PARAM_LACK);
            return;
        }
        if (StringUtils.isBlank(lastId)) {
            lastId = "0";
        }
        if (StringUtils.isNotBlank(lastId) && !StringUtils.isNumeric(lastId)) {
            Message.writeError(response, SysMsgEnumType.PARAM_LACK);
            return;
        }
        if (StringUtils.isBlank(keyword)) {
            Message.writeError(response, SysMsgEnumType.SEARCH_FAIL);
        }
        List<Map<String, Object>> products = productService.searchProducts(keyword, Long.valueOf(lastId), Integer.valueOf(pageSize));
        Message.writeMsg(response, SysMsgEnumType.SUCCESS, products);
    }

    @ResponseBody
    @RequestMapping(value = UrlCommand.product_detail, method = RequestMethod.GET)
    public void getProduct(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id) {
        Product product = productService.getProduct(id);
        Message.writeMsg(response, SysMsgEnumType.SUCCESS, product);
    }

}
