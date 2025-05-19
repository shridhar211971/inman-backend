package com.service;

import java.util.List;

import com.model.Wtransaction;

public interface WarehouseTransactionService {
	
	Wtransaction saveTransaction(Wtransaction wtransaction);
	
	List<Wtransaction> getAllTransactions();

}
