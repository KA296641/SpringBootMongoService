package com.wipro.dal;

import java.util.List;

import com.wipro.model.Customer;

public interface CustomerDAL {
	
	List<Customer> getAllCustomers(List<String> projectionList);

}
