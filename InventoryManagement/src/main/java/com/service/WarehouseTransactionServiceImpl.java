package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.WtransactionRepository;
import com.model.Wtransaction;

@Service
public class WarehouseTransactionServiceImpl implements WarehouseTransactionService{

	@Autowired
	WtransactionRepository wtransactionRepo;
	
	@Override
	public Wtransaction saveTransaction(Wtransaction wtransaction) {
		// TODO Auto-generated method stub
		return wtransactionRepo.save(wtransaction);
	}

	 @Override
	    public List<Wtransaction> getAllTransactions() {
	        return wtransactionRepo.findAll();
	    }

	

}
