package com.model;


import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Admin implements Serializable{
	@Id
	private int adminId;
	private String email;
	private String username;
	private String password;
	
	@OneToMany(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
	private List<Warehouse> warehouses;
	
	@OneToMany(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
	private List<Wtransaction> wtransactions;
	
	

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Admin(int adminId, String email, String username, String password, List<Warehouse> warehouses,
			List<Wtransaction> wtransactions) {
		super();
		this.adminId = adminId;
		this.email = email;
		this.username = username;
		this.password = password;
		this.warehouses = warehouses;
		this.wtransactions = wtransactions;
	}



	public int getAdminId() {
		return adminId;
	}



	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public List<Warehouse> getWarehouses() {
		return warehouses;
	}



	public void setWarehouses(List<Warehouse> warehouses) {
		this.warehouses = warehouses;
	}



	public List<Wtransaction> getWtransactions() {
		return wtransactions;
	}



	public void setWtransactions(List<Wtransaction> wtransactions) {
		this.wtransactions = wtransactions;
	}
		
}
