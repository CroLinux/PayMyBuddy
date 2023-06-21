package com.paymybuddy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.model.User;
import com.paymybuddy.service.ContactService;
import com.paymybuddy.service.UserService;

@Controller
public class ContactController {

	@Autowired
	private UserService userService;

	@Autowired
	private ContactService contactService;

	@GetMapping("/contact")
	public String getContact(Model model) {
		User currentUser = userService.currentUser();
		if (currentUser == null) {
			return "redirect:/login";
		} else {
			List<User> connections = currentUser.getContacts();
			model.addAttribute("user", currentUser);
			model.addAttribute("connections", connections);
			return "contact";
		}
	}

	@PostMapping("/process_add_contact")
	public String processAddContact(@ModelAttribute("user") User user, String email,
			RedirectAttributes redirectAttributes) {
		User currentUser = userService.currentUser();
		List<User> connections = currentUser.getContacts();
		boolean emailExists = false;
		// Small test to verify if the provided email is already present into the
		// contact list.
		for (User connection : connections) {
			if (connection.getEmail().equals(email)) {
				emailExists = true;
				break;
			}
		}
		// Here, we verify if the user is actually connected.
		if (currentUser == null) {
			return "redirect:/login";
		}
		// Here, we verify if an email is provided.
		if (email.isEmpty()) {
			redirectAttributes.addAttribute("errorEmptyEmail", "true");
			return "redirect:/contact";
		}
		// Here, we do not authorize to add ourself as contact
		if (email.equals(currentUser.getEmail())) {
			redirectAttributes.addAttribute("errorYourEmail", "true");
			return "redirect:/contact";			
		}
		// Here, we verify if the requested email is not present into the DB.
		if (!userService.existsByEmail(email)) {
			redirectAttributes.addAttribute("errorEmailNotExist", "true");
			return "redirect:/contact";
		}
		// Here, we verify if the requested email is already present into his contact
		// list.
		if (emailExists) {
			redirectAttributes.addAttribute("errorContactAlreadyAdded", "true");
			return "redirect:/contact";
		}
		// Here, we finally add the email as contact.
		else {
			contactService.process_add_contact(currentUser, email);
			redirectAttributes.addAttribute("success", "true");
			return "redirect:/contact";
		}
	}

	@GetMapping("/process_delete_contact")
	// public String processDeleteContact(@ModelAttribute("user") User user,
	// @RequestParam("email") String email, RedirectAttributes redirectAttributes) {

	public String processDeleteContact(@ModelAttribute("user") User user, String email,
			RedirectAttributes redirectAttributes) {
		User currentUser = userService.currentUser();
		if (currentUser == null) {
			return "redirect:/login";
		} else {
			contactService.process_delete_contact(currentUser, email);
			redirectAttributes.addAttribute("successdelcontact", "true");
			return "redirect:/contact";
		}
	}

}
