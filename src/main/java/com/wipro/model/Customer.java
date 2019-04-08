package com.wipro.model;

import org.springframework.data.annotation.Id;


public class Customer {

	@Id
	public String id;
	public String firstName;
	public String lastName;
	public String mobile;
	public Object address;

}