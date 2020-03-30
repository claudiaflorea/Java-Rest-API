package com.models;

import java.util.Date;

public class Author {
	
	private Integer id;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private Address address;
	
	/***********getters & setters*************/
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	/***********constructors*************/
	public Author(Integer id, String firstName, String lastName, Date birthDate, Address address) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.address = address;
	}
	public Author() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Author(Integer authorId, String lastName, String firstName, Date birthDate) {
		super();
		this.id = authorId;
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthDate = birthDate;
	}
	
	public Author(String firstName, String lastName, Date birthDate, Address address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.address = address;
	}
	
}
