package com.service;

import java.util.List;

import com.model.Customer;

public interface CustomerService {
	
	Customer addCustomer(Customer customer);
	
	Customer getCustomerById(int customerId);
	
	List<Customer> getAllCustomer();
	
	Customer updateCustomer(Customer customer, int customerId);
	
	Customer deleteCustomer(int customerId);
	
	Customer loginCustomer(String email, String password);
	

}
