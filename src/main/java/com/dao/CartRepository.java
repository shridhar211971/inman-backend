package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Cart;
import com.model.Customer;
import com.model.Product;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByCustomer(Customer customer);
//	Product findByProductId(int productIdId);


}
