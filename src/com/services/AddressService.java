package com.services;

import java.sql.SQLException;
import java.util.List;

import com.models.Address;

import net.codejava.javaee.bookstore.repositories.AddressRepository;


public class AddressService {
	
	private final AddressRepository addressRepository;
	

    public AddressService(String jdbcURL, String jdbcUsername, String jdbcPassword) {
    	this.addressRepository = new AddressRepository(jdbcURL, jdbcUsername, jdbcPassword);
	}

	public List<Address> findAll() throws SQLException {
    	return addressRepository.findAll();
    }
	
	public Address findById(Integer id) throws SQLException {
    	return addressRepository.findById(id);
    }
	
	public boolean insert(Address address) throws SQLException {
		return addressRepository.insert(address);
	}
	
	public boolean update(Integer id, Address request) throws SQLException {
		Address existingAddress = addressRepository.findById(id);
		
		if (existingAddress == null) {
			throw new IllegalArgumentException("Address with id " + id + " does nor exist in Database.");
		}
		existingAddress.setCountry(request.getCountry());
		existingAddress.setCity(request.getCity());
		existingAddress.setStreet(request.getStreet());
		existingAddress.setStreetNo(request.getStreetNo());
		existingAddress.setZipCode(request.getZipCode());
		
		return addressRepository.update(existingAddress);
	}
	
	public boolean delete (Integer addressId) throws SQLException {
		Address existingAddress = addressRepository.findById(addressId);
		if (existingAddress == null) {
			throw new IllegalArgumentException("Address with id " + addressId + " does nor exist in Database.");
		}
		
		return addressRepository.delete(addressId);
	}
}
