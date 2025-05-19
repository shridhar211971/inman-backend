package com.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CartRepository;
import com.dao.CustomerRepository;
import com.dao.OrderRepository;
import com.dao.ProductRepository;
import com.model.Cart;
import com.model.CartProduct;
import com.model.Customer;
import com.model.Order;
import com.model.Product;
import com.model.status.OrderStatus;
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	CartRepository cartRepo;

	@Override
	public Order createOrder(Order order) {
		return orderRepo.save(order);
	}
	
	@Override
	public Order createOrderFromCart(Cart cart) {
	    Customer customer = cart.getCustomer();
	    Set<CartProduct> products = cart.getCartProducts();

	    if (customer != null && products != null && !products.isEmpty()) {
	        Order order = new Order();
	        order.setCustomer(customer);
	        order.setCartProducts(products);
	        order.setStatus(OrderStatus.NEW);

	        BigDecimal totalPrice = BigDecimal.ZERO;
	        for (CartProduct product : products) {
	            totalPrice = totalPrice.add(product.getProduct().getPrice());
	        }
	        order.setTotalAmount(totalPrice);

	        Order savedOrder = orderRepo.save(order);

	        // Clear the products from the cart after creating the order
	        cart.setCartProducts(null);
	        cartRepo.save(cart);

	        return savedOrder;
	    }else {
	    	
	    	return null;
	    }

	}


	@Override
	public Order getOrderById(Long orderId) {
		// TODO Auto-generated method stub
		return orderRepo.findById(orderId).orElse(null);
	}

	@Override
	public List<Order> getAllOrders(Customer customer) {
		// TODO Auto-generated method stub
		return orderRepo.findAll();
	}

	@Override
	public Order updateOrder(Order order, Long orderId) {
		Order updateOrder = getOrderById(orderId);
		
		if(updateOrder != null && updateOrder.getStatus() == OrderStatus.NEW) {
			if(order.getCustomer()!=null) {
				updateOrder.setCustomer(order.getCustomer());
			}
			if(order.getOrderDate()!=null) {
				updateOrder.setOrderDate(order.getOrderDate());
			}
			if(order.getStatus()!=null) {
				updateOrder.setStatus(order.getStatus());
			}
			orderRepo.save(updateOrder);
			return updateOrder;
		}else {
			return null;
		}
	}
	  @Override
	    public Order deleteOrder(Long orderId) {
	        Order order = orderRepo.findById(orderId).orElse(null);
	        if (order != null) {
	            if (order.getStatus() == OrderStatus.NEW) {
	                List<Product> products = order.getProducts();
	                for (Product product : products) {
	                    product.setSku(product.getSku() + 1);  // Assuming 'sku' is used as the stock quantity here
	                    productRepo.save(product);
	                }
	            }
	            orderRepo.delete(order);
	            return order;
	        }
	        return null;
	    }

	  @Override
	    public OrderStatus printOrderStatus(Long orderId) {
	        Order order = orderRepo.findById(orderId).orElse(null);
	        return order != null ? order.getStatus() : null;
	    }

	    @Override
	    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
	        Order order = orderRepo.findById(orderId).orElse(null);
	        if (order != null) {
	            order.setStatus(newStatus);
	            return orderRepo.save(order);
	        }
	        return null;
	    }

		
	

}
