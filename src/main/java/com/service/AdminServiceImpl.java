package com.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AdminRepository;
import com.model.Admin;
import com.model.Customer;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepo;
    
    
    @Override
    public Admin getAdmin(int id) {
        return adminRepo.findById(id).orElse(null);
    }

    @Override
    public Admin updateAdmin(Admin admin,int adminId) {
    	
        Admin existingAdmin = adminRepo.findById(adminId).orElse(null);
        
        if (existingAdmin != null) {
           
            if (admin.getEmail() != null) {
                existingAdmin.setEmail(admin.getEmail());
            }
            if (admin.getUsername() != null) {
                existingAdmin.setUsername(admin.getUsername());
            }
            if (admin.getPassword() != null) {
                existingAdmin.setPassword(admin.getPassword());
            }

            adminRepo.save(existingAdmin);
        }
        return existingAdmin;
    }

	@Override
    public Admin loginAdmin(String username, String password) {
        Admin admin = adminRepo.findByUsername(username);
        
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
	}

    

  

}
