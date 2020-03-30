package com.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.Address;
import com.models.Category;
import com.services.CategoryService;

public class CategoryController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private CategoryService categoryService;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		
		categoryService = new CategoryService(jdbcURL, jdbcUsername, jdbcPassword);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String action = request.getServletPath();
		String addressId = request.getParameter("id");
		
		try {
			if (StringUtils.equals(action, "/categories") && StringUtils.isNotBlank(addressId)) {
				update(request, response);
			} else {
				onNotFound(request, response);
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	String action = request.getServletPath();
		
		try {
			if (StringUtils.equals(action, "/categories")) {
				delete(request, response);
			} else {
				onNotFound(request, response);
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		String action = request.getServletPath();
		String addressId = request.getParameter("id");
		
		try {
			if (StringUtils.equals(action, "/categories") && StringUtils.isBlank(addressId)) {
				insert(request, response);
			} else {
				onNotFound(request, response);
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}
	 
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		String addressId = request.getParameter("id");
		
		try {
			if (StringUtils.equals(action, "/categories") && StringUtils.isBlank(addressId)) {
				findAllCategories(request, response);
			} else if (StringUtils.isNotBlank(addressId)) {
				findCategoryById(request, response);
			}
			else {
				onNotFound(request, response);
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private boolean delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
			
		int id = Integer.parseInt(request.getParameter("id"));
		
		return categoryService.delete(id);
		
	}

	private boolean update(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
			
		Integer id = Integer.parseInt(request.getParameter("id"));
		String body = request.getReader().lines()
			    .reduce("", (accumulator, actual) -> accumulator + actual);
		
		ObjectMapper mapper = new ObjectMapper();
		
		Category category = mapper.readValue(body, Category.class);
		return categoryService.update(id, category);
	}

	private boolean insert(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String body = request.getReader().lines()
			    .reduce("", (accumulator, actual) -> accumulator + actual);
		
		ObjectMapper mapper = new ObjectMapper();
		
		Category category = mapper.readValue(body, Category.class);
		
		return categoryService.insert(category);
	}

	private void findAllCategories(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Category> categories = categoryService.findAll();
		
		ObjectMapper mapper = new ObjectMapper();
		
		response.getWriter().println(mapper.writeValueAsString(categories));
		response.setContentType("application/json");
	}
	
	private void findCategoryById(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		String pathInfo = request.getServletPath();
		String categoryId = request.getParameter("id");
		
		Category category = categoryService.findById(Integer.parseInt(categoryId));
		response.getWriter().println(mapper.writeValueAsString(category));
		response.setContentType("application/json");
	}
	
	private void onNotFound(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		
		response.getWriter().println("Not Found");
	}

}
