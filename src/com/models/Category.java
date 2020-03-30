package com.models;

public class Category {
	
	private Integer id;
	private String name;
	
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
	
	/***********constructors*************/
	public Category(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Category(String name) {
		super();
		this.name = name;
	}
	
}
