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

	@GetMapping("")
	public String viewMainPage() {
		return "redirect:/login";
	}
	
	@GetMapping("/")
	public String viewSlachPage() {
		return "redirect:/login";
	}

	@GetMapping("/home")
	public String viewHomePage(Model model) {
		User currentUser = userService.currentUser();
		if (currentUser == null) {
			return "redirect:/login";
		} else {
			List<User> connections = currentUser.getContacts();
			model.addAttribute("user", currentUser);
			model.addAttribute("connections", connections);
			return "home";
		}
	}

	@GetMapping("/login")
	public String viewLoginPage() {
		return "login";
	}

}
