package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.WarehouseRepository;
import com.model.Warehouse;
@Service
public class WarehouseServiceImpl implements WarehouseService{

	@Autowired
	WarehouseRepository warehouseRepo;

	@Override
	public Warehouse addWarehouse(Warehouse warehouse) {
		// TODO Auto-generated method stub
		return warehouseRepo.save(warehouse);
	}

	@Override
	public Warehouse getWarehouseById(int warehouseId) {
		// TODO Auto-generated method stub
		return warehouseRepo.findById(warehouseId).orElse(null);
	}

	@Override
	public List<Warehouse> getAllWarehouse() {
		// TODO Auto-generated method stub
		return warehouseRepo.findAll();
	}

	@Override
	public Warehouse updateWarehouse(Warehouse warehouse, int warehouseId) {
		Warehouse updateWarehouse = getWarehouseById(warehouseId);
		if(updateWarehouse!=null) {
			if(warehouse.getStatus()!=null) {
				updateWarehouse.setStatus(warehouse.getStatus());
			}
			return updateWarehouse;
		}else {
			
			return null;
		}
	}

	@Override
	public Warehouse deleteWarehouse(int warehouseId) {
		Warehouse deleteWarehouse=warehouseRepo.findById(warehouseId).orElse(null);
		if(deleteWarehouse!=null) {
			warehouseRepo.delete(deleteWarehouse);
			return deleteWarehouse;
		}else {
			
			return null;
		}
	}
	
	

	

}
