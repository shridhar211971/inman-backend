package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

//	Product findByCartItem(CartItem cartItem);
	
}
