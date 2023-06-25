package com.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.User;
import com.paymybuddy.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

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
	 * Get the current user
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
	 * Check if a user exist
	 * 
	 * @return
	 */
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);

	}

	/**
	 * Find a user by his Id
	 * 
	 * @param receiver_id
	 * @return
	 */
	public String findEmailUserById(int receiver_id) {
		String receiverEmail = userRepository.findEmailUserById(receiver_id);
		return receiverEmail;
	}

}
