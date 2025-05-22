package com.model;

import java.math.BigDecimal;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String name;
    private String description;
    private BigDecimal price;
    private int sku;
    private Double rating;
    private int quantity;
    
    @Lob
    @Column(name = "product_image", columnDefinition = "LONGBLOB")
    private byte[] productImage;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<CartProduct> cartProducts;

    @OneToOne(mappedBy = "product")
    private Warehouse warehouse;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Supplier> suppliers;

    public Product() {
    }

	public Product(int productId, String name, String description, BigDecimal price, int sku, Double rating,
			int quantity, byte[] productImage, Set<CartProduct> cartProducts, Warehouse warehouse,
			Set<Supplier> suppliers) {
		super();
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.sku = sku;
		this.rating = rating;
		this.quantity = quantity;
		this.productImage = productImage;
		this.cartProducts = cartProducts;
		this.warehouse = warehouse;
		this.suppliers = suppliers;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getSku() {
		return sku;
	}

	public void setSku(int sku) {
		this.sku = sku;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public byte[] getProductImage() {
		return productImage;
	}

	public void setProductImage(byte[] productImage) {
		this.productImage = productImage;
	}

	public Set<CartProduct> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(Set<CartProduct> cartProducts) {
		this.cartProducts = cartProducts;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Set<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(Set<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

    
}
