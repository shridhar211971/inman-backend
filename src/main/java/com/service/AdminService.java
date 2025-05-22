package com.service;
import com.model.Admin;
import com.model.Customer;

public interface AdminService {
	
	Admin getAdmin(int adminId);
	
	Admin updateAdmin(Admin admin,int adminId);
	
	Admin loginAdmin(String username, String password);

}
