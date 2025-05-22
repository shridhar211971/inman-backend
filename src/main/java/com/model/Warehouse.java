package com.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Warehouse {
	
	@Id
    private int warehouseId;

    private String location;
    private String status;
    
    
    @OneToMany(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
    private List<Wtransaction> wtransactions;
    
    @OneToMany(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
    private List<OrderTransaction> otransactions;
    
    @OneToOne
    @JoinColumn(name = "warehouse_id")
    private Product product;

	public Warehouse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Warehouse(int warehouseId, String location, String status, List<Wtransaction> wtransactions,
			List<OrderTransaction> otransactions, Product product) {
		super();
		this.warehouseId = warehouseId;
		this.location = location;
		this.status = status;
		this.wtransactions = wtransactions;
		this.otransactions = otransactions;
		this.product = product;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Wtransaction> getWtransactions() {
		return wtransactions;
	}

	public void setWtransactions(List<Wtransaction> wtransactions) {
		this.wtransactions = wtransactions;
	}

	public List<OrderTransaction> getOtransactions() {
		return otransactions;
	}

	public void setOtransactions(List<OrderTransaction> otransactions) {
		this.otransactions = otransactions;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	
}
