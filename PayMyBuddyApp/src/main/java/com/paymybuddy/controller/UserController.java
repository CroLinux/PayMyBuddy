package com.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.model.User;
import com.paymybuddy.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Read - Get all users
	 * 
	 * @return - An Iterable object of User full filled
	 */
	//@GetMapping("/users")
	//public Iterable<User> getAllUsers() {
	//	return userService.getAllUsers();
	//}
	
	/**
    @GetMapping("/users")
    public String showAllUsers(Model model){
        Iterable<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }*/
    
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users"; // renvoie à la page HTML users.html
    }

    @GetMapping("/register")
    public String displayRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
	
	@PostMapping("/process_register")
	public String processRegistration(@ModelAttribute("user") User user) {
	    //return userService.saveUser(user);
		userService.saveUser(user);
		return "redirect:/index";
	}
}
