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
import com.models.Book;
import com.services.BookService;

public class BookController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private BookService bookService;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		
		bookService = new BookService(jdbcURL, jdbcUsername, jdbcPassword);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String action = request.getServletPath();
		String addressId = request.getParameter("id");
		
		try {
			if (StringUtils.equals(action, "/books") && StringUtils.isNotBlank(addressId)) {
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
			if (StringUtils.equals(action, "/books")) {
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
			if (StringUtils.equals(action, "/books") && StringUtils.isBlank(addressId)) {
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
			if (StringUtils.equals(action, "/books") && StringUtils.isBlank(addressId)) {
				findAllBooks(request, response);
			} else if (StringUtils.isNotBlank(addressId)) {
				findBookById(request, response);
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
		
		return bookService.delete(id);
		
	}

	private boolean update(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		String body = request.getReader().lines()
			    .reduce("", (accumulator, actual) -> accumulator + actual);
		
		ObjectMapper mapper = new ObjectMapper();
		
		Book book = mapper.readValue(body, Book.class);
		return bookService.update(id, book);
		
	}

	private boolean insert(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String body = request.getReader().lines()
			    .reduce("", (accumulator, actual) -> accumulator + actual);
		
		ObjectMapper mapper = new ObjectMapper();
		
		Book address = mapper.readValue(body, Book.class);
		
		return bookService.insert(address);
	
	
	}

	private void findAllBooks(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Book> books = bookService.findAll();
		
		ObjectMapper mapper = new ObjectMapper();
		
		response.getWriter().println(mapper.writeValueAsString(books));
		response.setContentType("application/json");
	}
	
	private void findBookById(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		String pathInfo = request.getServletPath();
		Book book = bookService.findById(Integer.parseInt(pathInfo.substring(pathInfo.lastIndexOf('/')+1)));
		response.getWriter().println(mapper.writeValueAsString(book));
		response.setContentType("application/json");
	}
	
	private void onNotFound(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		
		response.getWriter().println("Not Found");
	}

}
