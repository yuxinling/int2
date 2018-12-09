package com.integrate.web.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.integrate.model.Category;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.integrate.model.Product;
import com.integrate.web.common.AppDbService;

@Component
public class ProductDao {
    private static final String DEFAULT_CHARSET = "utf-8";
    @Autowired
    private AppDbService jdbc;

    RowMapper<Product> rowMapper = new RowMapper<Product>() {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product r = new Product();
            r.setId(rs.getLong("id"));
            r.setName(rs.getString("name"));
            r.setThumbnail(rs.getString("thumbnail"));
            r.setSort(rs.getInt("sort"));
            r.setIntegrate(rs.getInt("integrate"));
            r.setCreateTime(rs.getLong("create_time"));
            r.setUpdateTime(rs.getLong("update_time"));

            Blob blob = rs.getBlob("detail");
            byte[] returnValue = null;
            if (null != blob) {
                returnValue = blob.getBytes(1, (int) blob.length());
            }
            try {
                // ###把byte转化成string
                if (returnValue == null) {
                    r.setDetail(null);
                }
                r.setDetail(new String(returnValue, DEFAULT_CHARSET));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Blob Encoding Error!");
            }

            Category category = new Category();
            category.setId(rs.getLong("category_id"));
            category.setCategoryName(rs.getString("category_name"));

            r.setCategory(category);

            return r;
        }
    };

    public List<Map<String, Object>> getProducts(long categoryId, long lastId, int pageSize) {
        StringBuilder sql = new StringBuilder("select p.id, p.name, p.thumbnail, p.integrate, p.category_id, c.category_name from t_product p, t_category c where p.category_id = c.id ");

        List<Object> params = Lists.newArrayList();
        if (categoryId > 0) {
            sql.append(" and c.id = ? ");
            params.add(categoryId);
        }
        if (lastId > 0) {
            sql.append(" and p.id > ? ");
            params.add(lastId);
        }

        sql.append(" order by p.id asc limit ?");
        params.add(pageSize);
        return jdbc.queryForList(sql.toString(), params.toArray());
    }

    public List<Map<String, Object>> getProducts(String keyword, long lastId, int pageSize) {
        if (StringUtils.isNotBlank(keyword)) {
            keyword = "%" + keyword + "%";
        }
        StringBuilder sql = new StringBuilder("select p.id, p.name, p.thumbnail, p.integrate, p.category_id, c.category_name from t_product p, t_category c where p.category_id = c.id ");

        List<Object> params = Lists.newArrayList();
        sql.append(" and p.name like ? ");
        params.add(keyword);
        if (lastId > 0) {
            sql.append(" and p.id > ? ");
            params.add(lastId);
        }
        sql.append(" order by p.id asc limit ?");
        params.add(pageSize);
        return jdbc.queryForList(sql.toString(), params.toArray());
    }

    public Product getProduct(Long id) {
        String sql = "select p.*,c.category_name from t_product p, t_category c where p.category_id = c.id and p.id = ?";
        return jdbc.queryT(sql, rowMapper, id);
    }
}
