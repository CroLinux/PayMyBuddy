package com.paymybuddy.service;

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
	 * Method to add a new contact to the list
	 * 
	 * @param user
	 * @param email
	 */
	public void process_add_contact(User user, String email) {
		List<User> contacts = user.getContacts();
		User newContact = userRepository.findByEmail(email);
		contacts.add(newContact);
		userService.updateUser(user);
	}

	/**
	 * Method to remove a contact from a list
	 * 
	 * @param currentUser
	 * @param email
	 */
	public void process_delete_contact(User currentUser, String email) {
		List<User> contacts = currentUser.getContacts();
		User newContact = userRepository.findByEmail(email);
		contacts.remove(newContact);
		userService.updateUser(currentUser);
	}

}
