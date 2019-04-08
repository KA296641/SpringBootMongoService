package com.wipro.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.dal.CustomerDAL;
import com.wipro.model.Customer;

@RestController
@RequestMapping("/mongoservice")
public class CustomerServiceController {

	@Autowired
	CustomerDAL dal;


	@RequestMapping(value="/fetchAllList", method=RequestMethod.POST) public
	List<Customer> fetchAllList(@RequestBody Customer customer) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<String> projectionList=new ArrayList<String>();
		Map<?,?> custObject = mapper.readValue(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(customer),Map.class); 

		for (Map.Entry<?, ?> custEntry : custObject.entrySet()) {
			
			if(custEntry.getKey().equals("address") && custEntry.getValue()!=null) {
				
				Map<?,?> custAddressObject = mapper.readValue(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(custEntry.getValue()),Map.class);
				
				for(Map.Entry<?, ?> custAddressEntry : custAddressObject.entrySet()) {
					
					Map<?,?> billShipAddress =  mapper.readValue(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(custAddressEntry.getValue()),Map.class);
					
					for(Map.Entry<?, ?> tempAddress : billShipAddress.entrySet()) {
						if((tempAddress.getKey().toString()).equals("location") && custEntry.getValue()!=null) {
							Map<?,?> locationMap =  mapper.readValue(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(tempAddress.getValue()),Map.class);
							for(Map.Entry<?, ?> location : locationMap.entrySet()) {
								if(location.getValue()!=null && Boolean.parseBoolean(location.getValue().toString()))
								projectionList.add(custEntry.getKey().toString()+"."+custAddressEntry.getKey()+"."+tempAddress.getKey().toString()+"."+location.getKey());
							}
						}else {
							if(tempAddress.getValue()!=null && Boolean.parseBoolean(tempAddress.getValue().toString()))
							projectionList.add(custEntry.getKey().toString()+"."+custAddressEntry.getKey()+"."+tempAddress.getKey().toString());
						}
					}
				}
			}else {
				if(custEntry.getValue()!=null && Boolean.parseBoolean(custEntry.getValue().toString()))
				projectionList.add(custEntry.getKey().toString());
			}
		}

		List<Customer> customerList=dal.getAllCustomers(projectionList);

		return customerList; }


}
