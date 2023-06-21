package com.paymybuddy.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.User;
import com.paymybuddy.repository.UserRepository;

@Service
public class ContactService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	/**
	 * public User process_add_contact(int userId, int newUserId) {
	 * 
	 * Optional<User> user = userRepository.findById(userId); Optional<User> contact
	 * = userRepository.findById(newUserId);
	 * 
	 * if (user != null && contact != null) { user.getContacts().add(contact);
	 * userService. .save(user); }else {
	 * 
	 * }
	 * 
	 * return null;
	 * 
	 * 
	 * }
	 */

	public void process_add_contact(User user, String email) {
		List<User> contacts = user.getContacts();
		User newContact = userRepository.findByEmail(email);
		contacts.add(newContact);
		userService.updateUser(user);
	}

	/**
	 * Optional<User> userId1 = userRepository.findById(userId); Optional<User>
	 * userId2 = userRepository.findById(newUserId);
	 * 
	 * if (userId1 != null && userId2 != null) { userId1.getContacts().add(userId2);
	 * userRepository.save(userId1); return userId1; }
	 */
	public void process_delete_contact(User currentUser, String email) {
		List<User> contacts = currentUser.getContacts();
		User newContact = userRepository.findByEmail(email);
		contacts.remove(newContact);
		userService.updateUser(currentUser);
	}

}
