package com.paymybuddy.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.dto.TransactionDto;
import com.paymybuddy.model.Transaction;

@Service
public class TransactionService {
	
	@Autowired
	TransactionRepository transactionRepository;

	
	public Iterable<TransactionDto> findTransactionsDoneByUSer (int user) {
		Iterable<TransactionDto> allTrxDone = transactionRepository.findTransactionsDoneByUSer(user);
		return allTrxDone;		
	}


	public Iterable<TransactionDto> findTransactionsReceivedByUSer(int user) {
		Iterable<TransactionDto> allTrxReceived = transactionRepository.findTransactionsReceivedByUSer(user);
		return allTrxReceived;
	}



	public Transaction saveTransaction(Transaction transaction) {
		Transaction newTransaction = transactionRepository.save(transaction);
		return newTransaction;
		
	}
	
}
