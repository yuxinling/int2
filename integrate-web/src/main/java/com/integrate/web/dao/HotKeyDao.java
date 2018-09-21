package com.integrate.web.dao;

import com.integrate.model.HotKey;
import com.integrate.model.Image;
import com.integrate.web.common.AppDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class HotKeyDao {
	@Autowired
	private AppDbService jdbc;
	
	RowMapper<HotKey> rmapper = new RowMapper<HotKey>() {
		@Override
		public HotKey mapRow(ResultSet rs, int rowNum) throws SQLException {
			HotKey r = new HotKey();
			r.setId(rs.getLong("id"));
			r.setKeyWord(rs.getString("keyword"));
			r.setSort(rs.getInt("sort"));
			r.setCreateTime(rs.getLong("create_time"));
			r.setUpdateTime(rs.getLong("update_time"));
			return r;
		}
	};
	
	public List<HotKey> getHotKey(){
		String sql="select * from t_hot_key " ;
		return jdbc.query(sql, rmapper);
	}
}
