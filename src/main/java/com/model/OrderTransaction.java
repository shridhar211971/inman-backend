package com.model;

import com.model.status.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "o_transactions")
public class OrderTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int oTransactionId;
	private String creationDate;
	private String creationTime;
	private String lastModifiedDate;
	private String lastModifiedTime;
	private String lastModifiedBy;
    private OrderStatus transactionStatus;
	private String changeInSku;
	
	@OneToOne
	@JoinColumn(name = "oTransaction_id")
	private Order order;

	public OrderTransaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderTransaction(int oTransactionId, String creationDate, String creationTime, String lastModifiedDate,
			String lastModifiedTime, String lastModifiedBy, OrderStatus transactionStatus, String changeInSku,
			Order order) {
		super();
		this.oTransactionId = oTransactionId;
		this.creationDate = creationDate;
		this.creationTime = creationTime;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedTime = lastModifiedTime;
		this.lastModifiedBy = lastModifiedBy;
		this.transactionStatus = transactionStatus;
		this.changeInSku = changeInSku;
		this.order = order;
	}

	public int getoTransactionId() {
		return oTransactionId;
	}

	public void setoTransactionId(int oTransactionId) {
		this.oTransactionId = oTransactionId;
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

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public OrderStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(OrderStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getChangeInSku() {
		return changeInSku;
	}

	public void setChangeInSku(String changeInSku) {
		this.changeInSku = changeInSku;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
