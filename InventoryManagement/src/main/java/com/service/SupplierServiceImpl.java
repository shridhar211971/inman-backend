package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.SupplierRepository;
import com.model.Supplier;

import jakarta.transaction.Transactional;
@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	SupplierRepository supplierRepo;
	
	@Override
	public Supplier addSupplier(Supplier supplier) {
		return supplierRepo.save(supplier);
	}

	@Override
	public List<Supplier> addSuppliers(List<Supplier> suppliers) {
		return supplierRepo.saveAll(suppliers);
	}
	
	@Override
	public Supplier getSupplier(int supplierId) {
		return supplierRepo.findById(supplierId).orElse(null);
	}

	@Override
	public List<Supplier> getAllSuppliers() {
		return supplierRepo.findAll();
	}

	@Override
	public Supplier updateSupplier(Supplier supplier, int supplierId) {
		Supplier updateSupplier = supplierRepo.findById(supplierId).orElse(null);
		
		if(updateSupplier != null) {
			if(supplier.getName() !=null) {
				updateSupplier.setName(supplier.getName());
			}
			if(supplier.getEmail() != null) {
				updateSupplier.setEmail(supplier.getEmail());
			}
			if(supplier.getContactNumber() != null) {
				updateSupplier.setContactNumber(supplier.getContactNumber());
			}
			if(supplier.getAddress() != null) {
				updateSupplier.setAddress(supplier.getAddress());
			}
			if(supplier.getProducts() != null) {
				updateSupplier.setProducts(supplier.getProducts());
			}
		}
		return updateSupplier;
	}

	@Override
    @Transactional
	public Supplier deleteSupplier(int supplierId) {
		Supplier deleteSupplier = supplierRepo.findById(supplierId).orElse(null);
		if(deleteSupplier != null) {
			supplierRepo.delete(deleteSupplier);
		}
		return deleteSupplier;
	}

	

}
