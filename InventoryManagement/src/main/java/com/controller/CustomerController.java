package com.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Admin;
import com.model.Cart;
import com.model.CartProduct;
import com.model.Customer;
import com.model.LoginRequest;
import com.model.Order;
import com.model.OrderTransaction;
import com.model.Product;
import com.model.Warehouse;
import com.model.status.OrderStatus;
import com.service.CartService;
import com.service.CustomerService;
import com.service.OrderService;
import com.service.OrderTransactionService;
import com.service.ProductService;
import com.service.WarehouseService;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd ");  //("yyyy/MM/dd HH:mm:ss")
	DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm:ss");//("yyyy/MM/dd HH:mm:ss")

	LocalDateTime now = LocalDateTime.now();
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderTransactionService orderTransactionService;
	
	@Autowired
	private ProductService productService;
	
	
	@Autowired
    private WarehouseService warehouseService;
	
// ------------------------------------------------  Customer -------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<Customer> loginCustomer(@RequestBody LoginRequest loginRequest){
        Customer customer = customerService.loginCustomer(loginRequest.getEmail(), loginRequest.getPassword());
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

	
	@PostMapping("/add")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
		Customer newCustomer = customerService.addCustomer(customer);
		cartService.createCartForCustomer(newCustomer);
		return new ResponseEntity<>(newCustomer,HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}/get/customer")
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") int id){
		Customer getCustomer= customerService.getCustomerById(id);
		if(getCustomer != null) {
			 return new ResponseEntity<>(getCustomer, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/update/customer/{customerId}")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer,@PathVariable("customerId") int customerId){
		Customer updatedCustomer = customerService.updateCustomer(customer, customerId);
		if(updatedCustomer != null) {
			return new ResponseEntity<>(updatedCustomer,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/customer/{customerId}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") int customerId){
		Customer deleteCustomer = customerService.deleteCustomer(customerId);
		if(deleteCustomer != null) {
			return new ResponseEntity<>(deleteCustomer,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getAll/product")
    public ResponseEntity<List<Product>> getAllProducts(@PathVariable("Id") int id) {
        
		Customer isExist = customerService.getCustomerById(id);
         
         if (isExist != null) {
	    	List<Product> products =  productService.getAllProducts();
	        return new ResponseEntity<>(products, HttpStatus.OK);
         }else {
        	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
         }
    }

// ------------------------------------------  Cart-Item  -----------------------------------------------------
	
	@PostMapping("/{customerId}/addToCart/{id}")
    public ResponseEntity<Product> addToCart(@PathVariable("id") int id , @PathVariable("customerId") int customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer != null) {
        	
        	Cart existCart= cartService.getCartByCustomer(customer);
        	Product product = productService.getProductById(id);
            if (product != null) {
            	
                cartService.addToCart(existCart.getId(),id);
                return new ResponseEntity<>(product, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

	    @GetMapping("{customerId}/get/cartItem/{id}")
	    public ResponseEntity<CartProduct> getCartItemById(@PathVariable("id") int cartId,@PathVariable("customerId") int customerId) {
	    	 Customer isExist = customerService.getCustomerById(customerId);
			 
	    	 if(isExist != null) {
				Cart cart = cartService.getCartByCustomer(isExist);
				CartProduct product = cartService.getCartItem(customerId,cartId);
				 if (product != null) {
					 return new ResponseEntity<>(product, HttpStatus.OK);
				 } else {
					 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				 }
			 }else {
		    	 return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			 }
	    }

	    @GetMapping("{customerId}/getall/cartItems")
	    public ResponseEntity<List<CartProduct>> getAllCartItems(@PathVariable("customerId") int customerId) {
	    	Customer isExist = customerService.getCustomerById(customerId);
			 
	    	 if(isExist != null) {
	    		 List<CartProduct> productItems = cartService.getAllCartItems(isExist);
	    		 if(productItems !=null) {
	    			 return new ResponseEntity<>(productItems, HttpStatus.OK);
	    		 }else {
	    			 return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
	    		 } 
	    	 }else {
	 	        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	    	 }
	    	
	    }

	    @PutMapping("{customerId}/update/cartItem/{productId}")
	    public ResponseEntity<CartProduct> updateCartItem(@RequestBody Product product, @PathVariable("productId") int id,@PathVariable("customerId")int customerId) {
	    	Customer isExist = customerService.getCustomerById(customerId);
	    	
			
	    	 if(isExist != null) {
	    		 CartProduct updatedProductItem= cartService.updateProductInCart(isExist.getCart().getId(), id, product.getQuantity());
                  
	    		 if (updatedProductItem != null) {
                     
	    			 return new ResponseEntity<>(updatedProductItem, HttpStatus.OK);
	    		 } else {
	    			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    		 }
	    		 
	    	 }else {
		 	        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	    	 }
	    }

	    @DeleteMapping("{customerId}/delete/item/{productId}")
	    public ResponseEntity<CartProduct> deleteCartItem(@PathVariable("productId") int id, @PathVariable("customerId") int customerId) {
	        Customer isExist = customerService.getCustomerById(customerId);

	        if (isExist != null) {
	            CartProduct deletedCartItem = cartService.removeProductInCart(isExist.getCart().getId(), id);
	            if (deletedCartItem != null) {
	                return new ResponseEntity<>(deletedCartItem, HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            }
	        } else {
	            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	        }
	    }
	    
	    
// --------------------------------------------- Order  -----------------------------------------------------
	    
	    @PostMapping("{customerId}/place/order")
	    public ResponseEntity<Order> createOrder(@PathVariable("customerId") int customerId) {
	    	Customer c = customerService.getCustomerById(customerId);
	    	if(c!=null) {
	    		
	    		Order createdOrder = new Order();
	    		
	    		createdOrder.setCustomer(c);
	    		Set<CartProduct> cartItems = new HashSet<CartProduct>(cartService.getAllCartItems(c)) ;
	    		
	    		createdOrder.setCartProducts(cartItems);
	    		createdOrder.setOrderDate(dtf.format(now));
	    		createdOrder.setStatus(OrderStatus.NEW);
	    		
	    		BigDecimal totalAmount = BigDecimal.ZERO;
	            for (CartProduct item : cartItems) {
	                BigDecimal itemTotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
	                totalAmount = totalAmount.add(itemTotal);
	            }
	            createdOrder.setTotalAmount(totalAmount);
	    		
	    		Order savedOrder = orderService.createOrder(createdOrder);	    		
//	    		Warehouse warehouse = warehouseService.getWarehouseById(101);
	        	
	        	OrderTransaction transaction = new OrderTransaction();
	        	transaction.setCreationDate(dtf.format(now));
	        	transaction.setCreationTime(dtfTime.format(now));
	        	transaction.setLastModifiedDate(dtf.format(now));
	        	transaction.setLastModifiedTime(dtfTime.format(now));
	        	transaction.setLastModifiedBy("CUS ("+customerId+")");
	        	transaction.setTransactionStatus(OrderStatus.NEW);
	            transaction.setOrder(savedOrder);
	        	transaction.setChangeInSku("NA");
	        	orderTransactionService.saveOrderTransaction(transaction);
	    		
	    		
	    		return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
	    	}else {
	 	        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	    	}
	    }

	    @GetMapping("{customerId}/get/order/{orderId}")
	    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId,@PathVariable("customerId") int customerId) {
	    	Customer isExist = customerService.getCustomerById(customerId);
			
	    	 if(isExist != null) {
	    		 
	    		 Order order = orderService.getOrderById(orderId);
	    		 if (order != null) {
	    			 return new ResponseEntity<>(order, HttpStatus.OK);
	    		 } else {
	    			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    		 }
	    	 }else {
		 	        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	    	 }
	    }
	    @GetMapping("{customerId}/get/all/order")
	    public ResponseEntity<List<Order>> getAllOrders(@PathVariable("customerId") int customerId) {
	        Customer customer = customerService.getCustomerById(customerId);
	        
	        if (customer != null) {
	            List<Order> orders = orderService.getAllOrders(customer);
	            if (orders != null) {
	                for (Order order : orders) {
	                    Set<CartProduct> cartItems = new HashSet<>(cartService.getAllCartItems(customer));
	                    order.setCartProducts(cartItems);
	                }
	                return new ResponseEntity<>(orders, HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            }
	        } else {
	            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	        }
	    }


	    @PutMapping("{customerId}/update/order/{orderId}")
	    public ResponseEntity<Order> updateOrder(@RequestBody Order order, @PathVariable Long orderId,@PathVariable("customerId") int customerId) {
	    	Customer isExist = customerService.getCustomerById(customerId);
			
	    	 if(isExist != null) {
	    		 Order updatedOrder = orderService.updateOrder(order, orderId);
	    		 
	    		 if (updatedOrder != null) {
	    			
			 	        return new ResponseEntity<>(updatedOrder,HttpStatus.OK);
	    		 }else {
	 	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	    		 }
	    		 
	    	 }else {
		            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	    	 }
	    	
	    }

	    @DeleteMapping("{customerId}/delete/order/{orderId}")
	    public ResponseEntity<Order> deleteOrder(@PathVariable Long orderId,@PathVariable("customerId") int customerId) {
	    	Customer isExist = customerService.getCustomerById(customerId);
			
	    	 if(isExist != null) {
	    		Order deleteOrder = orderService.deleteOrder(orderId);
	    		if(deleteOrder != null) {
		 	        return new ResponseEntity<>(deleteOrder,HttpStatus.OK);
	    		}else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    		}
	    	 }else {
		            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	    	 }   
	
	    }

	    

	    @GetMapping("{customerId}/get/status/{orderId}")
	    public ResponseEntity<OrderStatus> printOrderStatus(@PathVariable Long orderId,@PathVariable("customerId") int customerId) {
	    	Customer isExist = customerService.getCustomerById(customerId);
			
	    	 if(isExist != null) {
	    		 OrderStatus orderStatus = orderService.printOrderStatus(orderId);
	    		 if (orderStatus != null) {
	    			 return new ResponseEntity<>(orderStatus, HttpStatus.OK);
	    		 } else {
	    			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    		 }
	    		 
	    	 }else {
		            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	    	 }
	    }
}



