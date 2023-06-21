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
	 * Read - Get all users
	 * 
	 * @return - An Iterable object of User full filled
	 */
	// @GetMapping("/users")
	// public Iterable<User> getAllUsers() {
	// return userService.getAllUsers();
	// }

	/**
	 * @GetMapping("/users") public String showAllUsers(Model model){ Iterable<User>
	 * users = userService.getAllUsers(); model.addAttribute("users", users); return
	 * "users"; }
	 */

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
	public String processRegistration(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
	    if (userService.existsByEmail(user.getEmail()) ) {
	        redirectAttributes.addAttribute("errorEmailExist", "true");
	        return "redirect:/register";
	    }
		userService.saveUser(user);
	    redirectAttributes.addAttribute("success", "true");
	    return "redirect:/register";
	}

	/**
	 * @GetMapping("/profile") public String showUserProfile(Model model,
	 * Authentication authentication) { String userEmail = authentication.getName();
	 * // Obtient l'adresse e-mail de l'utilisateur connecté User user =
	 * userService.getUserByEmail(userEmail); // Obtient l'utilisateur à partir de
	 * l'adresse e-mail
	 * 
	 * model.addAttribute("user", user); // Passe l'utilisateur à la vue
	 * 
	 * return "profile"; // Retourne le nom de la vue à afficher (par exemple,
	 * profile.html) }
	 */
	/**
	@GetMapping("/profile")
	public String getProfile(Model model) {
		User currentUser = userService.currentUser();
		System.out.println("getProfile - currentUser " + currentUser);
		if (currentUser == null) {
			// log.info("Unknown user, you had been redirected to login page");
			return "redirect:/login";
		} else {
			List<User> connections = userService.currentUser().getContacts();
			System.out.println("getProfile - List<User> connections " + connections);
			// display user information and connections
			model.addAttribute("user", currentUser);
			model.addAttribute("connections", connections);
			return "profile";
		}
	}
	*/
	
	@GetMapping("/profile")
	public String getProfile(Model model) {
	    User currentUser = userService.currentUser();
	    if (currentUser == null) {
	        return "redirect:/login";
	    } else {
	        List<User> connections = currentUser.getContacts(); // Utilisez currentUser au lieu de userService.currentUser()
	        // Afficher les informations de l'utilisateur et les connexions
	        model.addAttribute("user", currentUser);
	        model.addAttribute("connections", connections);
	        return "profile";
	    }
	}
	
	@PostMapping("/process_update_user")
	public String processUpdateUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		User userToUpdate = userService.getUserByEmail(user.getEmail());
		System.out.println(userToUpdate.getPassword());
		//userToUpdate.setPassword(userToUpdate.getPassword());
		userToUpdate.setFirstname(user.getFirstname());
		userToUpdate.setLastname(user.getLastname());
		userToUpdate.setBankname(user.getBankname());
		userToUpdate.setBic(user.getBic());
		userToUpdate.setIban(user.getIban());
		System.out.println(userToUpdate.getPassword());
		userService.updateUser(userToUpdate);
		//User updatedUser = userService.saveUser(userToUpdate);
		// return userService.saveUser(user);
		System.out.println("user" + user);
		System.out.println(userToUpdate.getPassword());
		//userService.saveUser(user);
	    redirectAttributes.addAttribute("success", "true");
	    return "redirect:/profile";
		//return "redirect:/login";
		//return "register";
	}
	/**
	@PostMapping("/process_update_user")
    public String processUpdateUser(@ModelAttribute("user") User user, Model model) {
		System.out.println("Update user" + user);
        // Effectuez ici les actions nécessaires pour traiter la mise à jour de l'utilisateur
        // Par exemple, vous pouvez appeler userService.updateUser(user) pour mettre à jour les informations dans la base de données

        // Simulez la mise à jour en mettant à jour les informations dans l'objet User
		User userToUpdate = userService.getUserByEmail(user.getEmail());
		System.out.println("Update user" + userToUpdate);
		//userToUpdate.setEmail(user.getEmail());
		userToUpdate.setPassword(user.getPassword());
		userToUpdate.setFirstname(user.getFirstname());
		userToUpdate.setLastname(user.getLastname());
		userToUpdate.setBankname(user.getBankname());
		userToUpdate.setBic("EEEE");
		userToUpdate.setIban(user.getIban());
		//userToUpdate.setBalance(user.getBalance());

        // Appel au service ou repository pour mettre à jour l'utilisateur
        // userService.updateUser(user);
        userService.saveUser(user);

        // Ajoutez des attributs au modèle pour afficher des messages de réussite ou d'erreur dans votre vue
        model.addAttribute("success", "User updated successfully!");

        // Redirigez vers une autre page ou renvoyez la vue appropriée
        return "redirect:/profile"; // Remplacez "profile" par l'URL ou la vue que vous souhaitez afficher après la mise à jour
    }*/
	


	
	
}
