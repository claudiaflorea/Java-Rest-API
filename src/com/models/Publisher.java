package com.models;


public class Publisher {

	private Integer id;
	private String name;
	private Address address;
	
	/***********getters & setters*************/
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	/***********constructors*************/
	public Publisher(Integer id, String name, Address address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
	}
	public Publisher() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Publisher(Integer publisherId, String publisherName) {
		super();
		this.id = publisherId;
		this.name = publisherName;
	}
	
	public Publisher(String name, Address address) {
		super();
		this.name = name;
		this.address = address;
	}
}
