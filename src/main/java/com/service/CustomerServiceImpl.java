package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CustomerRepository;
import com.model.Customer;

import jakarta.transaction.Transactional;
@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	CustomerRepository customerRepo;
	
	@Override
	public Customer addCustomer(Customer customer) {
		return customerRepo.save(customer);
	}

	@Override
	public Customer getCustomerById(int customerId) {
		return customerRepo.findById(customerId).orElse(null);
	}

	@Override
	public List<Customer> getAllCustomer() {
		return customerRepo.findAll();
	}

	@Override
	public Customer updateCustomer(Customer customer, int customerId) {
		Customer updateCustomer = getCustomerById(customerId);
		if(updateCustomer != null) {
			if(customer.getName() != null) {
				updateCustomer.setName(customer.getName());
			}
			if(customer.getEmail() != null) {
				updateCustomer.setEmail(customer.getEmail());
			}
			if(customer.getContactNumber() != null) {
				updateCustomer.setContactNumber(customer.getContactNumber());
			}
			if(customer.getAddress() != null) {
				updateCustomer.setAddress(customer.getAddress());
			}if(customer.getUserName()!=null) {
				updateCustomer.setUserName(customer.getUserName());
			}if(customer.getPassword()!=null) {
				updateCustomer.setPassword(customer.getPassword());
			}
			customerRepo.save(updateCustomer);
		}
		return updateCustomer;
	}

	@Override
	@Transactional
	public Customer deleteCustomer(int customerId) {
		Customer deleteCustomer = getCustomerById(customerId);
		if(deleteCustomer != null) {
			customerRepo.delete(deleteCustomer);
		}
		return deleteCustomer;
	}

    public Customer loginCustomer(String email, String password) {
        Customer customer = customerRepo.findByEmail(email);
        if (customer != null && customer.getPassword().equals(password)) {
            return customer;
        }
        return null;
    }

}
