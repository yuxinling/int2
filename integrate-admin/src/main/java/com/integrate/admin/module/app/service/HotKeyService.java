package com.integrate.admin.module.app.service;

import com.integrate.admin.dao.DaoSupport;
import com.integrate.exception.BusinessException;
import com.integrate.model.HotKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * User: Lam.Yu
 * Date: 2018/9/20
 * Time: 10:15
 */
@Service("hotKeyService")
public class HotKeyService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public HotKey insertHotKey(HotKey key) throws Exception {
        if (key != null) {
            int count = (int) dao.save("HotKeyMapper.insertHotKey", key);
            if (count == 1) {
                return key;
            }
            throw new BusinessException("Insert the hotKey failure.");
        }
        return null;
    }

    public HotKey updateHotKey(HotKey key) throws Exception {
        if (key != null) {
            int count = (int) dao.update("HotKeyMapper.updateHotKey", key);
            if (count == 1) {
                return key;
            }
            throw new BusinessException("Update the hotKey failure.");
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<HotKey> getHotKeys() throws Exception {
        return (List<HotKey>) dao.findForList("HotKeyMapper.getHotKeys", null);
    }


    public int deleteHotKey(Long id) throws Exception {
        return (int) dao.delete("HotKeyMapper.deleteHotKey", id);
    }

    public HotKey getHotKey(Long id) throws Exception {
        return (HotKey) dao.findForObject("HotKeyMapper.getHotKey", id);
    }
}
