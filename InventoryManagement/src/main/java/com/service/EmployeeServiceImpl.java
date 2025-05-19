package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.EmployeeRepository;
import com.model.Admin;
import com.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    
	@Autowired
	EmployeeRepository employeeRepo;
	
	@Override
    public Employee addEmployee(Employee emp) {
        return employeeRepo.save(emp);
    }
	
	@Override
    public List<Employee> addEmployees(List<Employee> employees) {
        return employeeRepo.saveAll(employees);
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepo.findById(id).orElse(null);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee updateEmployee(Employee emp, int id) {
    	
        Employee existingEmployee = employeeRepo.findById(id).orElse(null);
        
        if (existingEmployee != null) {

        	if (emp.getName() != null) {
                existingEmployee.setName(emp.getName());
            }
            if (emp.getEmail() != null) {
                existingEmployee.setEmail(emp.getEmail());
            }
            if (emp.getContactNumber() != null) {
                existingEmployee.setContactNumber(emp.getContactNumber());
            }
            if (emp.getUserName() != null) {
                existingEmployee.setUserName(emp.getUserName());
            }
            if (emp.getPassword() != null) {
                existingEmployee.setPassword(emp.getPassword());
            }
             employeeRepo.save(existingEmployee);
             
            return existingEmployee;
            
        }else {
        	
        	return null;
        }
    }

    @Override
    public Employee deleteEmployee(int id) {
    	
        Employee e = employeeRepo.findById(id).orElse(null);
        
        if (e != null) {
        	
            employeeRepo.delete(e);  
        }
        return e;
    }

	@Override
	public Employee loginEmployee(String userName, String password) {
		Employee employee = employeeRepo.findByUserName(userName);
		if( employee!= null&& employee.getPassword().equals(password)) {
			return employee;
		}
		// TODO Auto-generated method stub
		return null;
	}
}
