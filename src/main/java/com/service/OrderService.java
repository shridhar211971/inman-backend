package com.service;

import java.util.List;

import com.model.Cart;
import com.model.Customer;
import com.model.Order;
import com.model.status.OrderStatus;

public interface OrderService {
	
	Order createOrder(Order order);
	
	Order createOrderFromCart(Cart cart);
	
	Order getOrderById(Long orderId);
	
	List<Order> getAllOrders(Customer customer);
	
	
	Order deleteOrder(Long orderId);
	
	OrderStatus printOrderStatus(Long orderId);

	//	--------------------------------------------- For employee ----------------------------------------------
	
	Order updateOrderStatus(Long orderId, OrderStatus newStatus);
	
	Order updateOrder(Order order, Long orderId);

	
}
