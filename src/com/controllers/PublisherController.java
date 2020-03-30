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
import com.models.Publisher;
import com.services.PublisherService;


public class PublisherController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PublisherService publisherService;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		
		publisherService = new PublisherService(jdbcURL, jdbcUsername, jdbcPassword);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String action = request.getServletPath();
		String addressId = request.getParameter("id");
		
		try {
			if (StringUtils.equals(action, "/publishers") && StringUtils.isNotBlank(addressId)) {
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
			if (StringUtils.equals(action, "/publishers")) {
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
			if (StringUtils.equals(action, "/publishers") && StringUtils.isBlank(addressId)) {
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
			if (StringUtils.equals(action, "/publishers") && StringUtils.isBlank(addressId)) {
				findAllPublishers(request, response);
			} else if (StringUtils.isNotBlank(addressId)) {
				findPublisherById(request, response);
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
		
		return publisherService.delete(id);
		
	}

	private boolean update(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String body = request.getReader().lines()
			    .reduce("", (accumulator, actual) -> accumulator + actual);
		
		ObjectMapper mapper = new ObjectMapper();
		
		Publisher publisher = mapper.readValue(body, Publisher.class);
		return publisherService.update(id, publisher);		
	}

	private boolean insert(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		Publisher publisher = publisherService.findById(id);
		request.setAttribute("address", publisher);
		
		return publisherService.insert(publisher);
	
	}

	private void findAllPublishers(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Publisher> publishers = publisherService.findAll();
		
		ObjectMapper mapper = new ObjectMapper();
		
		response.getWriter().println(mapper.writeValueAsString(publishers));
		response.setContentType("application/json");
	}
	
	private void findPublisherById(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		String pathInfo = request.getServletPath();
		Publisher publisher = publisherService.findById(Integer.parseInt(pathInfo.substring(pathInfo.lastIndexOf('/')+1)));
		response.getWriter().println(mapper.writeValueAsString(publisher));
		response.setContentType("application/json");
	}
	
	private void onNotFound(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		
		response.getWriter().println("Not Found");
	}
	
}
