package com.services;

import java.sql.SQLException;
import java.util.List;

import com.models.Book;

import net.codejava.javaee.bookstore.repositories.BookRepository;


public class BookService {
	
	private final BookRepository bookRepository;
	

    public BookService(String jdbcURL, String jdbcUsername, String jdbcPassword) {
    	this.bookRepository = new BookRepository(jdbcURL, jdbcUsername, jdbcPassword);
	}

	public List<Book> findAll() throws SQLException {
    	return bookRepository.findAll();
    }
	
	public Book findById(Integer id) throws SQLException {
    	return bookRepository.findById(id);
    }
	
	public boolean insert (Book book) throws SQLException {
		return bookRepository.insert(book);
	}
	
	public boolean update (Integer id, Book request) throws SQLException {
		Book existingBook = bookRepository.findById(id);
		
		if (existingBook == null) {
			throw new IllegalArgumentException("Book with id " + id + " does nor exist in Database.");
		}
		existingBook.setIsbn(request.getIsbn());
		existingBook.setName(request.getName());
		existingBook.setAuthor(request.getAuthor());
		existingBook.setCategory(request.getCategory());
		existingBook.setPublisher(request.getPublisher());
		existingBook.setPublishDate(request.getPublishDate());
		return bookRepository.update(existingBook);
	}
	
	public boolean delete (Integer bookId) throws SQLException {
		Book existingBook = bookRepository.findById(bookId);
		if (existingBook == null) {
			throw new IllegalArgumentException("Book with id " + bookId + " does nor exist in Database.");
		}
		
		return bookRepository.delete(bookId);
	}

}
