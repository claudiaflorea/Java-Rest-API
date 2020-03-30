package com.services;

import java.sql.SQLException;
import java.util.List;

import com.models.Address;
import com.models.Category;

import net.codejava.javaee.bookstore.repositories.CategoryRepository;

public class CategoryService {
	
private final CategoryRepository categoryRepository;
	

    public CategoryService(String jdbcURL, String jdbcUsername, String jdbcPassword) {
    	this.categoryRepository = new CategoryRepository(jdbcURL, jdbcUsername, jdbcPassword);
	}

	public List<Category> findAll() throws SQLException {
    	return categoryRepository.findAll();
    }
	
	public Category findById(Integer id) throws SQLException {
    	return categoryRepository.findById(id);
    }
	
	public boolean insert (Category category) throws SQLException {
		return categoryRepository.insert(category);
	}
	
	public boolean update (Integer id, Category request) throws SQLException {
		Category existingCategory = categoryRepository.findById(id);
		
		if (existingCategory == null) {
			throw new IllegalArgumentException("Category with id " + id + " does nor exist in Database.");
		}
		existingCategory.setName(request.getName());
		
		return categoryRepository.update(existingCategory);
	}
	
	public boolean delete (Integer id) throws SQLException {
		Category existingCatgeory = categoryRepository.findById(id);
		if (existingCatgeory == null) {
			throw new IllegalArgumentException("Category with id " + id + " does nor exist in Database.");
		}
		
		return categoryRepository.delete(id);
	}

}
