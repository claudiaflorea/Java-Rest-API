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
import com.services.AddressService;

public class AddressController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private AddressService addressService;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		
		addressService = new AddressService(jdbcURL, jdbcUsername, jdbcPassword);
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String action = request.getServletPath();
		String addressId = request.getParameter("id");
		
		try {
			if (StringUtils.equals(action, "/addresses") && StringUtils.isNotBlank(addressId)) {
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
			if (StringUtils.equals(action, "/addresses")) {
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
			if (StringUtils.equals(action, "/addresses") && StringUtils.isBlank(addressId)) {
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
			if (StringUtils.equals(action, "/addresses") && StringUtils.isBlank(addressId)) {
				findAllAdresses(request, response);
			} else if (StringUtils.isNotBlank(addressId)) {
				findAdressById(request, response);
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
		
		return addressService.delete(id);
		
	}

	private boolean update(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
			
		Integer id = Integer.parseInt(request.getParameter("id"));
		String body = request.getReader().lines()
			    .reduce("", (accumulator, actual) -> accumulator + actual);
		
		ObjectMapper mapper = new ObjectMapper();
		
		Address address = mapper.readValue(body, Address.class);
		return addressService.update(id, address);
		
	}

	private boolean insert(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String body = request.getReader().lines()
			    .reduce("", (accumulator, actual) -> accumulator + actual);
		
		ObjectMapper mapper = new ObjectMapper();
		
		Address address = mapper.readValue(body, Address.class);
		
		return addressService.insert(address);
	
	}

	private void findAllAdresses(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Address> addresses = addressService.findAll();
		
		ObjectMapper mapper = new ObjectMapper();
		
		response.getWriter().println(mapper.writeValueAsString(addresses));
		response.setContentType("application/json");
	}
	
	private void findAdressById(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		String pathInfo = request.getServletPath();
		String addressId = request.getParameter("id");
		
		Address address = addressService.findById(Integer.parseInt(addressId));
		response.getWriter().println(mapper.writeValueAsString(address));
		response.setContentType("application/json");
	}
	
	private void onNotFound(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		
		response.getWriter().println("Not Found");
	}
}
