package com.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "w_transactions")
public class Wtransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transactionId;
	private String creationDate;
	private String creationTime;
	private String modifiedBy;
	private String modifiedIn;
	private String transactionType;
	private String chanageInSku;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
	private Warehouse warehouse;
	
	public Wtransaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Wtransaction(int transactionId, String creationDate, String creationTime, String modifiedBy,
			String modifiedIn, String transactionType, String chanageInSku, Warehouse warehouse) {
		super();
		this.transactionId = transactionId;
		this.creationDate = creationDate;
		this.creationTime = creationTime;
		this.modifiedBy = modifiedBy;
		this.modifiedIn = modifiedIn;
		this.transactionType = transactionType;
		this.chanageInSku = chanageInSku;
		this.warehouse = warehouse;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedIn() {
		return modifiedIn;
	}

	public void setModifiedIn(String modifiedIn) {
		this.modifiedIn = modifiedIn;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getChanageInSku() {
		return chanageInSku;
	}

	public void setChanageInSku(String chanageInSku) {
		this.chanageInSku = chanageInSku;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	

}
