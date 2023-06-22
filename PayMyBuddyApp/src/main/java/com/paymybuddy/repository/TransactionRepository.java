package com.paymybuddy.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paymybuddy.dto.TransactionDto;
import com.paymybuddy.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionDto, Integer> {

	/**
	 * Here we return the list of the transactions done by the User with more data.
	 * 
	 * @param user
	 * @return
	 */
	@Query(value = "SELECT t.id, t.sender_id, us.email AS email_sender, us.firstname AS firstname_sender, us.lastname AS lastname_sender, t.receiver_id, ur.email AS email_receiver, ur.firstname AS firstname_receiver, ur.lastname AS lastname_receiver, t.type, t.amount, t.currency, t.date, t.description, t.fee FROM transaction AS t INNER JOIN user_account AS us ON t.sender_id = us.id INNER JOIN user_account AS ur ON t.receiver_id = ur.id WHERE us.id = :user ORDER BY t.date DESC", nativeQuery = true)
	Iterable<TransactionDto> findTransactionsDoneByUSer(@Param("user") int user);

	/**
	 * Here we return the list of the transactions received by the User with more
	 * data.
	 * 
	 * @param user
	 * @return
	 */
	@Query(value = "SELECT t.id, t.sender_id, us.email AS email_sender, us.firstname AS firstname_sender, us.lastname AS lastname_sender, t.receiver_id, ur.email AS email_receiver, ur.firstname AS firstname_receiver, ur.lastname AS lastname_receiver, t.type, t.amount, t.currency, t.date, t.description, t.fee FROM transaction AS t INNER JOIN user_account AS us ON t.sender_id = us.id INNER JOIN user_account AS ur ON t.receiver_id = ur.id WHERE t.receiver_id = :user ORDER BY t.date DESC", nativeQuery = true)
	Iterable<TransactionDto> findTransactionsReceivedByUSer(@Param("user") int user);

	/**
	 * Simple method from CrudRepository to save a transaction.
	 * 
	 * @param transaction
	 * @return
	 */
	Transaction save(Transaction transaction);

}
