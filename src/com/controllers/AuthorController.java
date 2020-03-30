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
import com.models.Author;
import com.models.Book;
import com.services.AuthorService;

public class AuthorController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private AuthorService authorService;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		
		authorService = new AuthorService(jdbcURL, jdbcUsername, jdbcPassword);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String action = request.getServletPath();
		String addressId = request.getParameter("id");
		
		try {
			if (StringUtils.equals(action, "/authors") && StringUtils.isNotBlank(addressId)) {
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
			if (StringUtils.equals(action, "/authors")) {
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
			if (StringUtils.equals(action, "/authors") && StringUtils.isBlank(addressId)) {
				insert(request, response);
			} else {
				onNotFound(request, response);
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		 String action = request.getServletPath();
			String addressId = request.getParameter("id");
			
			try {
				if (StringUtils.equals(action, "/authors") && StringUtils.isBlank(addressId)) {
					findAllAuthors(request, response);
				} else if (StringUtils.isNotBlank(addressId)) {
					findAuthorById(request, response);
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
			
			return authorService.delete(id);
			
		}

			
		private boolean update(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
			
			Integer id = Integer.parseInt(request.getParameter("id"));
			String body = request.getReader().lines()
				    .reduce("", (accumulator, actual) -> accumulator + actual);
			
			ObjectMapper mapper = new ObjectMapper();
			
			Author author = mapper.readValue(body, Author.class);
			return authorService.update(id, author);
			
		}

		private boolean insert(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
			String body = request.getReader().lines()
				    .reduce("", (accumulator, actual) -> accumulator + actual);
			
			ObjectMapper mapper = new ObjectMapper();
			
			Author author = mapper.readValue(body, Author.class);
			
			return authorService.insert(author);
		
		}

		private void findAllAuthors(HttpServletRequest request, HttpServletResponse response)
				throws SQLException, IOException, ServletException {
			List<Author> authors = authorService.findAll();
			
			ObjectMapper mapper = new ObjectMapper();
			
			response.getWriter().println(mapper.writeValueAsString(authors));
			response.setContentType("application/json");
		}
		
		private void findAuthorById(HttpServletRequest request, HttpServletResponse response)
				throws SQLException, IOException, ServletException {
			ObjectMapper mapper = new ObjectMapper();
			String pathInfo = request.getServletPath();
			Author address = authorService.findById(Integer.parseInt(pathInfo.substring(pathInfo.lastIndexOf('/')+1)));
			response.getWriter().println(mapper.writeValueAsString(address));
			response.setContentType("application/json");
		}
		
		private void onNotFound(HttpServletRequest request, HttpServletResponse response)
				throws SQLException, IOException, ServletException {
			
			response.getWriter().println("Not Found");
		}

}
