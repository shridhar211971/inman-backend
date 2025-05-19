package com.service;

import com.model.Order;
import com.model.OrderTransaction;

public interface OrderTransactionService {
	
	OrderTransaction saveOrderTransaction(OrderTransaction orderTransaction);
	
	OrderTransaction updateOrderTransaction(OrderTransaction orderTransaction,int orderId);
	
	OrderTransaction getOrderTransaction(Order order);

}
