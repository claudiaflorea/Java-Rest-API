package com.services;

import java.sql.SQLException;
import java.util.List;

import com.models.Address;
import com.models.Publisher;

import net.codejava.javaee.bookstore.repositories.PublisherRepository;

public class PublisherService {
	
private final PublisherRepository publisherRepository;
	

    public PublisherService(String jdbcURL, String jdbcUsername, String jdbcPassword) {
    	this.publisherRepository = new PublisherRepository(jdbcURL, jdbcUsername, jdbcPassword);
	}

	public List<Publisher> findAll() throws SQLException {
    	return publisherRepository.findAll();
    }
	
	public Publisher findById(Integer id) throws SQLException {
    	return publisherRepository.findById(id);
    }
	
	public boolean insert (Publisher publisher) throws SQLException {
		return publisherRepository.insert(publisher);
	}
	
	public boolean update (Integer id, Publisher request) throws SQLException {
		Publisher existingPublisher = publisherRepository.findById(id);
		
		if (existingPublisher == null) {
			throw new IllegalArgumentException("Publisher with id " + id + " does nor exist in Database.");
		}
		existingPublisher.setName(request.getName());
		existingPublisher.setAddress(request.getAddress());
		
		return publisherRepository.update(existingPublisher);
	}
	
	public boolean delete (Integer id) throws SQLException {
		Publisher existingPublisher = publisherRepository.findById(id);
		if (existingPublisher == null) {
			throw new IllegalArgumentException("Publisher with id " + id + " does nor exist in Database.");
		}
		
		return publisherRepository.delete(id);
	}

}
