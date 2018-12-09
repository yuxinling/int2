package com.integrate.web.dao;

import com.integrate.model.Category;
import com.integrate.model.HotKey;
import com.integrate.web.common.AppDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CategoryDao {
	@Autowired
	private AppDbService jdbc;
	
	RowMapper<Category> rmapper = new RowMapper<Category>() {
		@Override
		public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
			Category r = new Category();
			r.setId(rs.getLong("id"));
			r.setCategoryName(rs.getString("category_name"));
			r.setUsable(rs.getInt("usable"));
			r.setSort(rs.getInt("sort"));
			r.setCreateTime(rs.getLong("create_time"));
			r.setUpdateTime(rs.getLong("update_time"));
			return r;
		}
	};
	
	public List<Category> getCategorys(){
		String sql="select * from t_category where usable = 1 order by sort asc" ;
		return jdbc.query(sql, rmapper);
	}
}
