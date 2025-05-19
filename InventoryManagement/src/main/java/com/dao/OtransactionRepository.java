package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Order;
import com.model.OrderTransaction;

public interface OtransactionRepository extends JpaRepository<OrderTransaction, Integer> {

	OrderTransaction findByOrder(Order order);

}
