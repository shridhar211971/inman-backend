package com.service;

import java.util.List;

import com.model.Supplier;

public interface SupplierService {
	
	Supplier addSupplier(Supplier supplier);
	
	List<Supplier> addSuppliers(List<Supplier> suppliers);
	
	Supplier getSupplier(int supplierId);
	
	List<Supplier> getAllSuppliers();
	
	Supplier updateSupplier(Supplier supplier, int supplierId);
	
	Supplier deleteSupplier(int supplierId);
	
}
