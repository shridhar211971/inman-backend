package com.service;

import java.util.List;

import com.model.Admin;
import com.model.Employee;

public interface EmployeeService {
	
	Employee addEmployee(Employee emp);
	
	List<Employee> addEmployees(List<Employee> employees);
	
	Employee getEmployeeById(int id);
	
	List<Employee> getAllEmployees(); 
	
	Employee updateEmployee(Employee emp, int id);
	
	Employee deleteEmployee(int id);
	
	Employee loginEmployee(String userName, String password);

}
