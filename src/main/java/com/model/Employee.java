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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Employee implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeId;
	
	private String name;
	private String email;
	private Long contactNumber;
	private String address;
	private String userName;
	private String password;
	private int salary;
	
	@ManyToMany(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
	private List<Warehouse> warehouses;
	
	@OneToMany(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
	private List<Wtransaction> wtransactions;
	
	

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Employee(int employeeId, String name, String email, Long contactNumber, String address, String userName,
			String password, int salary, List<Warehouse> warehouses, List<Wtransaction> wtransactions) {
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.email = email;
		this.contactNumber = contactNumber;
		this.address = address;
		this.userName = userName;
		this.password = password;
		this.salary = salary;
		this.warehouses = warehouses;
		this.wtransactions = wtransactions;
	}



	public int getEmployeeId() {
		return employeeId;
	}



	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public Long getContactNumber() {
		return contactNumber;
	}



	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public int getSalary() {
		return salary;
	}



	public void setSalary(int salary) {
		this.salary = salary;
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
