package com.paymybuddy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.model.User;
import com.paymybuddy.service.UserService;

@SpringBootTest
public class UserControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@Mock
	private Model model;

	@Mock
	private RedirectAttributes redirectAttributes;

	private User testUser = new User();

	@BeforeEach
	public void setup() {
		testUser.setId(1);
		testUser.setEmail("test@mail.com");
		testUser.setPassword("password");
		testUser.setFirstname("Mister");
		testUser.setLastname("Test");
		testUser.setBankname("TestBank");
		testUser.setIban("IbanTest");
		testUser.setBic("BicTest");
		testUser.setBalance(100);
		testUser.setCurrency("EUR");
		testUser.setAuthority("ROLE_USER");
		testUser.setContacts(new ArrayList<>());
	}

	@Test
	public void testDisplayRegisterPage() {
		// When
		String viewName = userController.displayRegisterPage(model);

		// Then
		assertEquals("register", viewName);
		verify(model).addAttribute("user", new User());
	}

	@Test
	public void testProcessRegistration_WhenEmailExists() {
		// Given
		User user = new User();
		user.setEmail("existinguser@mail.com");
		when(userService.existsByEmail(user.getEmail())).thenReturn(true);

		// When
		String viewName = userController.processRegistration(user, redirectAttributes);

		// Then
		assertEquals("redirect:/register", viewName);
		verify(redirectAttributes).addAttribute("errorEmailExist", "true");
		verify(userService).existsByEmail(user.getEmail());
	}

	@Test
	public void testProcessRegistration_WhenEmailDoesNotExist() {
		// Given
		User user = new User();
		user.setEmail("newuser@mail.com");
		when(userService.existsByEmail(user.getEmail())).thenReturn(false);

		// When
		String viewName = userController.processRegistration(user, redirectAttributes);

		// Then
		assertEquals("redirect:/register", viewName);
		verify(userService).saveUser(user);
		verify(redirectAttributes).addAttribute("success", "true");
	}

	@Test
	public void testGetProfile_WhenCurrentUserIsNull() {
		// Given
		when(userService.currentUser()).thenReturn(null);

		// When
		String viewName = userController.getProfile(model);

		// Then
		assertEquals("redirect:/login", viewName);
	}

	@Test
	public void testGetProfile_WhenCurrentUserExists() {
		// Given
		when(userService.currentUser()).thenReturn(testUser);

		// When
		String viewName = userController.getProfile(model);

		// Then
		assertEquals("profile", viewName);
		verify(model).addAttribute("user", testUser);
		verify(model).addAttribute("connections", testUser.getContacts());
	}

	@Test
	public void testProcessUpdateUser() {
		// Given
		User updatedUser = new User();
		updatedUser.setEmail("test@mail.com");
		updatedUser.setFirstname("Updated");
		updatedUser.setLastname("User");
		updatedUser.setBankname("UpdatedBank");
		updatedUser.setIban("UpdatedIban");
		updatedUser.setBic("UpdatedBic");
		when(userService.getUserByEmail(testUser.getEmail())).thenReturn(testUser);

		// When
		String viewName = userController.processUpdateUser(updatedUser, redirectAttributes);

		// Then
		assertEquals("redirect:/profile", viewName);
		verify(userService).updateUser(testUser);
		verify(redirectAttributes).addAttribute("success", "true");
	}
}
