package com.wipro.dalimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.wipro.dal.CustomerDAL;
import com.wipro.model.Customer;

@Repository
public class CustomerDAlImpl implements CustomerDAL {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public CustomerDAlImpl(MongoTemplate mongoTemplate) {
		  this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public List<Customer> getAllCustomers(List<String> projectionList) {
		Query query = new Query();
		for(String tempString:projectionList) {
			query.fields().include(tempString);
		}
		return mongoTemplate.find(query,Customer.class);
	}
}
