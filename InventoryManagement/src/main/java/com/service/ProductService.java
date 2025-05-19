package com.service;

import java.util.List;

import com.model.Product;

public interface ProductService {
	
	Product addProduct(Product product);
	
	 List<Product> addProducts(List<Product> products);
	
	Product getProductById(int productId);
	
	
	List<Product> getAllProducts();
	
	Product updateProduct(Product product,int productId);
	
	Product deleteProduct(int productId);


}
