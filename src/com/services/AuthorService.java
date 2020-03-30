package com.services;

import java.sql.SQLException;
import java.util.List;

import com.models.Author;

import net.codejava.javaee.bookstore.repositories.AuthorRepository;


public class AuthorService {
	
private final AuthorRepository authorRepository;
	

    public AuthorService(String jdbcURL, String jdbcUsername, String jdbcPassword) {
    	this.authorRepository = new AuthorRepository(jdbcURL, jdbcUsername, jdbcPassword);
	}

	public List<Author> findAll() throws SQLException {
    	return authorRepository.findAll();
    }
	
	public Author findById(Integer id) throws SQLException {
    	return authorRepository.findById(id);
    }
	
	public boolean insert (Author author) throws SQLException {
		return authorRepository.insert(author);
	}
	
	public boolean update (Integer id, Author request) throws SQLException {
		Author existingAutor = authorRepository.findById(id);
		
		if (existingAutor == null) {
			throw new IllegalArgumentException("Author with id " + id + " does nor exist in Database.");
		}
		existingAutor.setFirstName(request.getFirstName());
		existingAutor.setLastName(request.getLastName());
		existingAutor.setBirthDate(request.getBirthDate());
		existingAutor.setAddress(request.getAddress());
		
		return authorRepository.update(existingAutor);
	}
	
	public boolean delete (Integer authorId) throws SQLException {
		Author existingAuthor = authorRepository.findById(authorId);
		if (existingAuthor == null) {
			throw new IllegalArgumentException("Author with id " + authorId + " does nor exist in Database.");
		}
		
		return authorRepository.delete(authorId);
	}

}
