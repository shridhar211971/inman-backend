package com.dao;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import com.model.*;

import jakarta.transaction.Transactional;

public interface CartProductRepository extends JpaRepository<CartProduct, Integer> {
	
    Optional<CartProduct> findByCartAndProduct(Cart cart, CartProduct cartProduct);

	void deleteByCart(Cart cart);

	List<CartProduct> findByCart(Cart cart);

	Optional<CartProduct> findByProduct(Product product);
	
	 @Transactional
	    @Modifying
	    @Query("DELETE FROM CartProduct cp WHERE cp.product = :product")
	    void deleteByProduct(Product product);
}

