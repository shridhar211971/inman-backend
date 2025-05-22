package com.service;

import java.util.List;
import java.util.Set;

import com.model.Cart;
import com.model.Customer;
import com.model.Product;
import com.model.CartProduct;

public interface CartService {
    Cart createCartForCustomer(Customer customer);
    
    Cart getCartByCustomer(Customer customer);
    
    Product addToCart(Long cartId, int productId);
    
    CartProduct removeProductInCart(Long cartId, int cartProductId );
    
    CartProduct updateProductInCart(Long cartId, int cartProductId, int quantity);
    
    Cart clearCart(Long cartId);
    
    CartProduct getCartItem(int customerId, int cartProductId);
    
    List<CartProduct> getAllCartItems(Customer customer);
    

}
