package com.integrate.web.service;

import com.integrate.model.Category;
import com.integrate.web.dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public List<Category> getCategorys() {
        return categoryDao.getCategorys();
    }
}
