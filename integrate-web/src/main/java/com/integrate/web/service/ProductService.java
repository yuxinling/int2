package com.integrate.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrate.model.Image;
import com.integrate.model.Product;
import com.integrate.web.dao.ImageDao;
import com.integrate.web.dao.ProductDao;

@Service
public class ProductService {

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private ProductDao productDao;

    public List<Map<String, Object>> getProducts(long categoryId, long lastId, int pageSize) {
        return this.handler(productDao.getProducts(categoryId, lastId, pageSize));
    }

    public List<Map<String, Object>> searchProducts(String keyword, long lastId, int pageSize) {
        return this.handler(productDao.getProducts(keyword, lastId, pageSize));
    }

    public Product getProduct(Long id) {
        Product product = productDao.getProduct(id);
        if (product != null) {
            List<Image> images = imageDao.getImages(product.getId());
            product.setImages(images);
        }
        return product;
    }

    private List<Map<String, Object>> handler(List<Map<String, Object>> datas) {
        if (CollectionUtils.isNotEmpty(datas)) {
            for (Map<String, Object> data : datas) {
                Object id = data.get("category_id");
                if (data.containsKey("category_id")) {
                    Map<String, Object> category = new HashMap<>();
                    category.put("id", data.remove("category_id"));
                    category.put("categoryName", data.remove("category_name"));
                    data.put("category", category);
                }
            }
        }

        return datas;
    }
}
