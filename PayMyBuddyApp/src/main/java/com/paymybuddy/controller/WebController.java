package com.paymybuddy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.paymybuddy.model.User;
import com.paymybuddy.service.UserService;

@Controller
public class WebController {

	@Autowired
	private UserService userService;

	/**
	 * CLASS used for the definition of the standard pages
	 * 
	 * @return
	 */
	
	
	@GetMapping("")
	public String viewMainPage() {
		return "login";
	}

	@GetMapping("/")
	public String viewSlachPage() {
		return "login";
	}

	@GetMapping("/home")
	public String viewHomePage(Model model) {
		User currentUser = userService.currentUser();
		List<User> connections = currentUser.getContacts();
		model.addAttribute("user", currentUser);
		model.addAttribute("connections", connections);
		return "home";

	}

	@GetMapping("/login")
	public String viewLoginPage() {
		return "login";
	}

}
