package com.integrate.web.service;

import com.integrate.model.HotKey;
import com.integrate.model.Image;
import com.integrate.model.Product;
import com.integrate.web.dao.HotKeyDao;
import com.integrate.web.dao.ImageDao;
import com.integrate.web.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HotKeyService {

    @Autowired
    private HotKeyDao hotKeyDao;


    public List<HotKey> getHotKeys() {
        return hotKeyDao.getHotKey();
    }
}
