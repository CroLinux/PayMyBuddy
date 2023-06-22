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
import com.paymybuddy.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Method to display the users page with all the users We keep this one even if
	 * it's not used as info
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/users")
	public String getAllUsers(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "users";
	}

	/**
	 * Method to display the register page
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/register")
	public String displayRegisterPage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	/**
	 * Method to process a new registration to the App
	 * 
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/process_register")
	public String processRegistration(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		if (userService.existsByEmail(user.getEmail())) {
			redirectAttributes.addAttribute("errorEmailExist", "true");
			return "redirect:/register";
		}
		user.setCurrency("EUR");
		user.setAuthority("ROLE_USER");
		userService.saveUser(user);
		redirectAttributes.addAttribute("success", "true");
		return "redirect:/register";
	}

	/**
	 * Method to display the profile page and informations
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/profile")
	public String getProfile(Model model) {
		User currentUser = userService.currentUser();
		if (currentUser == null) {
			return "redirect:/login";
		} else {
			List<User> connections = currentUser.getContacts();
			model.addAttribute("user", currentUser);
			model.addAttribute("connections", connections);
			return "profile";
		}
	}

	/**
	 * Method to update info from the user from the profile page but no update for
	 * the password --TAKE CARE--
	 * 
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/process_update_user")
	public String processUpdateUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		User userToUpdate = userService.getUserByEmail(user.getEmail());
		userToUpdate.setFirstname(user.getFirstname());
		userToUpdate.setLastname(user.getLastname());
		userToUpdate.setBankname(user.getBankname());
		userToUpdate.setBic(user.getBic());
		userToUpdate.setIban(user.getIban());
		userService.updateUser(userToUpdate);
		redirectAttributes.addAttribute("success", "true");
		return "redirect:/profile";
	}

}
