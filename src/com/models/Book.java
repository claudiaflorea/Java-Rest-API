package com.models;

import java.util.Date;

public class Book {
	
	private Integer id;
	private String isbn;
	private String name;
	private Author author;
	private Category category;
	private Publisher publisher;
	private Date publishDate;

	
	/***********getters & setters*************/
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	
	/************constructors**************/
	public Book(Integer id, String isbn, String name, Author author, Category category, Publisher publisher,
			Date publishDate) {
		super();
		this.id = id;
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.category = category;
		this.publisher = publisher;
		this.publishDate = publishDate;
	}
	
	public Book(String isbn, String name, Author author, Category category, Publisher publisher,
			Date publishDate) {
		super();
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.category = category;
		this.publisher = publisher;
		this.publishDate = publishDate;
	}
	
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
		
	public Book(Integer bookId, String ISBN, String title) {
		super();
		this.id = bookId;
		this.isbn = ISBN;
		this.name = title;
	}
}
