package com.integrate.web.dao;

import com.integrate.model.Coupon;
import com.integrate.web.common.AppDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CouponDao {
    @Autowired
    private AppDbService jdbc;

    RowMapper<Coupon> rmapper = new RowMapper<Coupon>() {
        @Override
        public Coupon mapRow(ResultSet rs, int rowNum) throws SQLException {
            Coupon r = new Coupon();
            r.setId(rs.getLong("id"));
            r.setMoney(rs.getInt("money"));
            r.setIntegrate(rs.getInt("integrate"));
            return r;
        }
    };

    public List<Coupon> getCoupons() {
        String sql = "select * from t_coupon where usable = 1 order by money asc";
        return jdbc.query(sql, rmapper);
    }

    public Coupon getCoupon(long id) {
        String sql = "select * from t_coupon where id = ? ";
        return jdbc.queryT(sql, rmapper, new Object[]{id});
    }
}
