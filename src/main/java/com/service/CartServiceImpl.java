package com.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CartProductRepository;
import com.dao.CartRepository;
import com.dao.CustomerRepository;
import com.dao.ProductRepository;
import com.model.Cart;
import com.model.CartProduct;
import com.model.Customer;
import com.model.Product;

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

	
    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private ProductRepository productRepo;
    
    @Autowired
    private CartProductRepository cartProductRepo;

    @Override
    public Cart createCartForCustomer(Customer customer) {
        Cart cart = new Cart();
        cart.setCustomer(customer);
        return cartRepo.save(cart);
    }

    @Override
    public Cart getCartByCustomer(Customer customer) {
        return cartRepo.findByCustomer(customer);
    }

    @Transactional
    @Override
    public Product addToCart(Long cartId, int productId) {
        Cart cart = cartRepo.findById(cartId).orElse(null);
        Product product = productRepo.findById(productId).orElse(null);

        if (cart != null && product != null) {
        
            Optional<CartProduct> optionalCartProduct = cartProductRepo.findByProduct(product);
            CartProduct cartProduct;
            if (optionalCartProduct.isPresent()) {
                cartProduct = optionalCartProduct.get();
                cartProduct.setQuantity(cartProduct.getQuantity() + 1);
            } else {
                cartProduct = new CartProduct();
                cartProduct.setCart(cart);
                cartProduct.setProduct(product);
                cartProduct.setQuantity(1);
            }
            cartProductRepo.save(cartProduct);
            return product;
        }
        return null;
    }

    @Transactional
    @Override
    public CartProduct updateProductInCart(Long cartId, int cartProductId, int quantity) {
        Optional<CartProduct> optionalCartProduct = cartProductRepo.findById(cartProductId);
        if (optionalCartProduct.isPresent()) {
            CartProduct cartProduct = optionalCartProduct.get();
            if (cartProduct.getCart().getId().equals(cartId)) {
                cartProduct.setQuantity(quantity);
                return cartProductRepo.save(cartProduct);
            }
        }
        return null;
    }

    @Transactional
    @Override
    public CartProduct removeProductInCart(Long cartId, int cartProductId) {
        Optional<CartProduct> optionalCartProduct = cartProductRepo.findById(cartProductId);
        if (optionalCartProduct.isPresent()) {
            CartProduct cartProduct = optionalCartProduct.get();
            if (cartProduct.getCart().getId().equals(cartId)) {
                cartProductRepo.deleteById(cartProduct.getId());
                return cartProduct;
            }
        }
        return null;
    }


    @Transactional
    @Override
    public Cart clearCart(Long cartId) {
        Cart cart = cartRepo.findById(cartId).orElse(null);
        if (cart != null) {
            cartProductRepo.deleteByCart(cart);
            return cart;
        }
        return null;
    }

    @Override
    public CartProduct getCartItem(int customerId, int cartProductId) {
        Customer customer = customerRepo.findById(customerId).orElse(null);
        if (customer != null) {
            Cart cart = cartRepo.findByCustomer(customer);
            Optional<CartProduct> cartProduct=cartProductRepo.findById(cartProductId);
            if(cartProduct!=null) {
            	CartProduct product=cartProduct.get();
            	return cartProductRepo.findByCartAndProduct(cart,product).orElse(null);
            }
            
        }
        return null;
    }

    @Override
    public List<CartProduct> getAllCartItems(Customer customer) {
        Cart cart = cartRepo.findByCustomer(customer);
        return cartProductRepo.findByCart(cart);
    }

}
