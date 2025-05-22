package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	Admin findByUsername(String username);

}
