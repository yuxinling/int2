package com.integrate.web.controller;

import com.integrate.enums.SysMsgEnumType;
import com.integrate.model.HotKey;
import com.integrate.url.UrlCommand;
import com.integrate.web.common.Message;
import com.integrate.web.service.HotKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class HotKeyController {


    @Autowired
    private HotKeyService hotKeyService;

    @RequestMapping(value = UrlCommand.hot_keys, method = RequestMethod.GET)
    @ResponseBody
    public void getHotKeys(HttpServletRequest request, HttpServletResponse response) {
        List<HotKey> keys = hotKeyService.getHotKeys();
        Message.writeMsg(response, SysMsgEnumType.SUCCESS, keys);
    }

}
