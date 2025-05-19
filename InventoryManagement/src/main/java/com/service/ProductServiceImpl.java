package com.service;
	
import java.util.List;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CartProductRepository;
import com.dao.CartRepository;
import com.dao.ProductRepository;
import com.model.Cart;
import com.model.Product;

import jakarta.transaction.Transactional;
	
	@Service
	public class ProductServiceImpl implements ProductService{

		@Autowired
		ProductRepository productRepo;
		
		@Autowired
		CartRepository cartRepo;
		
		@Autowired
		CartProductRepository cartProductRepo;
		@Override
		public Product addProduct(Product product) {
			return productRepo.save(product);
		}

		@Override
		@Transactional
		public List<Product> addProducts(List<Product> products) {
			// TODO Auto-generated method stub
			return productRepo.saveAll(products);
		}

		@Override
		public Product getProductById(int productId) {
			// TODO Auto-generated method stub
			return productRepo.findById(productId).orElse(null);
		}

		@Override
		public List<Product> getAllProducts() {
			// TODO Auto-generated method stub
			return productRepo.findAll();
		}

		@Override
		public Product updateProduct(Product product, int productId) {
			Product updateProduct = productRepo.findById(productId).orElse(null);
			
			if(updateProduct != null) {
				if(product.getName()!=null) {
					updateProduct.setName(product.getName());
				}
				if(product.getDescription() != null) {
					updateProduct.setDescription(product.getDescription());
				}
				if(product.getPrice() != null) {
					updateProduct.setPrice(product.getPrice());
				}
				if(product.getSku() != 0) {
					updateProduct.setSku(product.getSku());
				}
				if(product.getRating() != null) {
					updateProduct.setRating(product.getRating());
				}
				if(product.getSuppliers() != null) {
					updateProduct.setSuppliers(product.getSuppliers());
				}
				if(product.getWarehouse() != null) {
					updateProduct.setWarehouse(product.getWarehouse());
				}
				productRepo.save(updateProduct);
			}
			return updateProduct;
		}

		@Override
		public Product deleteProduct(int productId) {
			Product deleteProduct = productRepo.findById(productId).orElse(null);
			
			if(deleteProduct != null) {
				cartProductRepo.deleteByProduct(deleteProduct);
				productRepo.delete(deleteProduct);
			}
			return deleteProduct;
		}

		
		
	}
