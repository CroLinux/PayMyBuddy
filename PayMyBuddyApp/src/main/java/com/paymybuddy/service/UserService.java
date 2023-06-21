package com.paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymybuddy.dto.TransactionDto;
import com.paymybuddy.model.User;
import com.paymybuddy.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// MAYBE TO REMOVE AT THE END
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * Save new registered User and encode password
	 * 
	 * @param user
	 * @return
	 */
	public User saveUser(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		User savedUser = userRepository.save(user);
		return savedUser;
	}

	/**
	 * Update existing user with provided values
	 * 
	 * @param user
	 * @return
	 */
	public User updateUser(User user) {
		User updatedUser = userRepository.save(user);
		return updatedUser;
	}

	/**
	 * Search existing user by email
	 * 
	 * @param email
	 * @return
	 */
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * Get existing user
	 * 
	 * @return
	 */
	public User currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return getUserByEmail(authentication.getName());
		}
		return null;
	}

	/**
	 * public boolean isEmailAlreadyInDB(int i) { return
	 * userRepository.existsById(i); }
	 */

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);

	}
/**
	public Iterable<TransactionDto> findEmailUserById(int user_id) {
		System.out.println("UserService findEmailUserById");
		Iterable<TransactionDto> list = userRepository.findEmailUserById2(user_id);
		return list;
	}*/

	public String findEmailUserById(int receiver_id) {
		System.out.println("UserService findEmailUserById");
		String receiverEmail = userRepository.findEmailUserById(receiver_id);
		return receiverEmail;
	}
	
	

}
