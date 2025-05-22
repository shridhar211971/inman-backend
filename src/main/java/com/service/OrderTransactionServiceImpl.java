package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.OrderRepository;
import com.dao.OtransactionRepository;
import com.model.Order;
import com.model.OrderTransaction;
@Service
public class OrderTransactionServiceImpl implements OrderTransactionService {

	@Autowired
	OtransactionRepository orderTransactionRepo;
	
	@Autowired
	OrderRepository orderRepo;
	
	@Override
	public OrderTransaction saveOrderTransaction(OrderTransaction orderTransaction) {
		return orderTransactionRepo.save(orderTransaction);
	}

	@Override
	public OrderTransaction updateOrderTransaction(OrderTransaction orderTransaction,int orderId) {
		OrderTransaction updateOrderTransaction = orderTransactionRepo.findById(orderId).orElse(null);
		
		
	    updateOrderTransaction=orderTransaction;
		orderTransactionRepo.save(updateOrderTransaction);
		
		return updateOrderTransaction;
	}

	@Override
	public OrderTransaction getOrderTransaction(Order order) {
		Optional<Order> getOrder = orderRepo.findById(order.getOrderId());
		if(getOrder != null) {
			return orderTransactionRepo.findByOrder(order);	
		}else {
			return null;
		}
	}

}
