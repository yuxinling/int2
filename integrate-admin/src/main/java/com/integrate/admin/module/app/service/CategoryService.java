package com.integrate.admin.module.app.service;

import com.integrate.admin.dao.DaoSupport;
import com.integrate.model.Category;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("categoryService")
public class CategoryService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public Category insertCategory(Category category) throws Exception {
		if (category != null) {
			int count = (int) dao.save("CategoryMapper.insertCategory", category);
			if (count == 1) {
				return category;
			}
		}
		return null;
	}
	
	public Category updateCategory(Category category) throws Exception {
		if (category != null) {
			int count = (int) dao.update("CategoryMapper.updateCategory", category);
			if (count == 1) {
				return category;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Category> getCategorys() throws Exception {
		return (List<Category>) dao.findForList("CategoryMapper.getCategorys", null);
	}
	
	public Category getCategory(Long id) throws Exception {
		Category Category =  (Category) dao.findForObject("CategoryMapper.getCategory", id);
		return Category;
	}

	public int deleteCategory(Long id) throws Exception {
		return (int) dao.delete("CategoryMapper.deleteCategory", id);
	}

}
