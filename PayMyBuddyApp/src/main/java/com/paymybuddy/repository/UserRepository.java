package com.paymybuddy.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paymybuddy.dto.TransactionDto;
import com.paymybuddy.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	/**
	 * Different checks to find and verify a user
	 * 
	 * @param email
	 * @return
	 */
	User findByEmail(String email);

	// To verify if the email is into the DB.
	boolean existsByEmail(String email);

	boolean existsUserByEmailAndContacts_Email(String userEmail, String contactEmail);

	/**
	 * Method to get back the email by providing the user_id
	 * 
	 * @param user
	 * @return
	 */
	@Query(value = "SELECT DISTINCT(usr.email) FROM user_account AS usr INNER JOIN transaction AS t ON t.receiver_id = usr.id WHERE t.receiver_id = :user", nativeQuery = true)
	String findEmailUserById(@Param("user") int user);

	@Query(value = "SELECT ur.email AS email_receiver FROM transaction AS t INNER JOIN user_account AS us ON t.sender_id = us.id INNER JOIN user_account AS ur ON t.receiver_id = ur.id WHERE t.receiver_id = :user", nativeQuery = true)
	Iterable<TransactionDto> findEmailUserById2(@Param("user") int user);

}
