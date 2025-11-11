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
import org.springframework.web.bind.annotation.*;

import com.model.Cart;
import com.model.CartProduct;
import com.model.Customer;
import com.model.LoginRequest;
import com.model.Order;
import com.model.OrderTransaction;
import com.model.Product;
import com.model.status.OrderStatus;
import com.service.CartService;
import com.service.CustomerService;
import com.service.OrderService;
import com.service.OrderTransactionService;
import com.service.ProductService;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "https://inman-frontend-8gdg.onrender.com"
})
public class CustomerController {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd ");
    DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm:ss");
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

    // ----------------------------------- Customer Login & Registration -----------------------------------
    @PostMapping("/login")
    public ResponseEntity<Customer> loginCustomer(@RequestBody LoginRequest loginRequest) {
        Customer customer = customerService.loginCustomer(loginRequest.getEmail(), loginRequest.getPassword());
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer newCustomer = customerService.addCustomer(customer);
        cartService.createCartForCustomer(newCustomer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/get/customer")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") int id) {
        Customer getCustomer = customerService.getCustomerById(id);
        if (getCustomer != null) {
            return new ResponseEntity<>(getCustomer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/customer/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable("customerId") int customerId) {
        Customer updatedCustomer = customerService.updateCustomer(customer, customerId);
        if (updatedCustomer != null) {
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/customer/{customerId}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") int customerId) {
        Customer deleteCustomer = customerService.deleteCustomer(customerId);
        if (deleteCustomer != null) {
            return new ResponseEntity<>(deleteCustomer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ------------------------------------------- Product -------------------------------------------

    @GetMapping("/{customerId}/getAll/product")
    public ResponseEntity<List<Product>> getAllProducts(@PathVariable("customerId") int customerId) {
        Customer isExist = customerService.getCustomerById(customerId);
        if (isExist != null) {
            List<Product> products = productService.getAllProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    // ------------------------------------------- Cart -------------------------------------------

    @PostMapping("/{customerId}/addToCart/{id}")
    public ResponseEntity<Product> addToCart(@PathVariable("id") int id, @PathVariable("customerId") int customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer != null) {
            Cart existCart = cartService.getCartByCustomer(customer);
            Product product = productService.getProductById(id);
            if (product != null) {
                cartService.addToCart(existCart.getId(), id);
                return new ResponseEntity<>(product, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("{customerId}/get/cartItem/{id}")
    public ResponseEntity<CartProduct> getCartItemById(@PathVariable("id") int cartId, @PathVariable("customerId") int customerId) {
        Customer isExist = customerService.getCustomerById(customerId);
        if (isExist != null) {
            Cart cart = cartService.getCartByCustomer(isExist);
            CartProduct product = cartService.getCartItem(customerId, cartId);
            if (product != null) {
                return new ResponseEntity<>(product, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("{customerId}/getall/cartItems")
    public ResponseEntity<List<CartProduct>> getAllCartItems(@PathVariable("customerId") int customerId) {
        Customer isExist = customerService.getCustomerById(customerId);
        if (isExist != null) {
            List<CartProduct> productItems = cartService.getAllCartItems(isExist);
            if (productItems != null) {
                return new ResponseEntity<>(productItems, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("{customerId}/update/cartItem/{productId}")
    public ResponseEntity<CartProduct> updateCartItem(@RequestBody Product product, @PathVariable("productId") int id, @PathVariable("customerId") int customerId) {
        Customer isExist = customerService.getCustomerById(customerId);
        if (isExist != null) {
            CartProduct updatedProductItem = cartService.updateProductInCart(isExist.getCart().getId(), id, product.getQuantity());
            if (updatedProductItem != null) {
                return new ResponseEntity<>(updatedProductItem, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
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
}
