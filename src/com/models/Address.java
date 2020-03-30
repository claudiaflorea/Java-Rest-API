package com.models;


public class Address {

	private Integer id;
	private String country;
	private String city;
	private String street;
	private String streetNo;
	private String zipCode;
	
	/***********getters & setters*************/
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreetNo() {
		return streetNo;
	}
	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	/***********constructors*************/
	public Address(Integer id, String country, String city, String street, String streetNo, String zipCode) {
		super();
		this.id = id;
		this.country = country;
		this.city = city;
		this.street = street;
		this.streetNo = streetNo;
		this.zipCode = zipCode;
	}
	
	public Address(String country, String city, String street, String streetNo, String zipCode) {
		super();
		this.country = country;
		this.city = city;
		this.street = street;
		this.streetNo = streetNo;
		this.zipCode = zipCode;
	}
	
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}
		
}
