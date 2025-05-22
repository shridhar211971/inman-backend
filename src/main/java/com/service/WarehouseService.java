package com.service;


import java.util.List;

import com.model.Warehouse;

public interface WarehouseService {
	
	Warehouse addWarehouse(Warehouse warehouse);
	
	Warehouse getWarehouseById(int warehouseId);
	
	List<Warehouse> getAllWarehouse();
	
	Warehouse updateWarehouse(Warehouse warehouse, int warehouseId);
	
	Warehouse deleteWarehouse(int warehouseId);
	
	
				

}
