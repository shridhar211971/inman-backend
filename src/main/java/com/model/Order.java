package com.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.model.status.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name=" orders ")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	private String orderDate;
	private BigDecimal totalAmount;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@OneToMany(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
	private List<Product> products;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    private Set<CartProduct> cartProducts;
	
	@OneToOne(mappedBy = "order")
	private OrderTransaction otransactions;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(Long orderId, String orderDate, BigDecimal totalAmount, OrderStatus status, List<Product> products,
			Set<CartProduct> cartProducts, OrderTransaction otransactions, Customer customer) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
		this.status = status;
		this.products = products;
		this.cartProducts = cartProducts;
		this.otransactions = otransactions;
		this.customer = customer;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Set<CartProduct> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(Set<CartProduct> cartProducts) {
		this.cartProducts = cartProducts;
	}

	public OrderTransaction getOtransactions() {
		return otransactions;
	}

	public void setOtransactions(OrderTransaction otransactions) {
		this.otransactions = otransactions;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


}
