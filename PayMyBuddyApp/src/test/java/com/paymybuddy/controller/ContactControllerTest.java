package com.paymybuddy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.model.User;
import com.paymybuddy.service.ContactService;
import com.paymybuddy.service.UserService;

@SpringBootTest
public class ContactControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private ContactService contactService;

	@InjectMocks
	private ContactController contactController;

	@Mock
	private Model model;

	@Mock
	private RedirectAttributes redirectAttributes;

	private User testUser = new User();
	private User contactUser = new User();

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

		contactUser.setId(2);
		contactUser.setEmail("contactuser@mail.com");
		contactUser.setPassword("password");
		contactUser.setFirstname("Mister2");
		contactUser.setLastname("Test2");
		contactUser.setBankname("TestBank2");
		contactUser.setIban("IbanTest2");
		contactUser.setBic("BicTest2");
		contactUser.setBalance(100);
		contactUser.setCurrency("EUR");
		contactUser.setAuthority("ROLE_USER");
		contactUser.setContacts(new ArrayList<>());
	}

	@Test
	public void testGetContact() {
		// Given
		when(userService.currentUser()).thenReturn(testUser);

		// When
		String viewName = contactController.getContact(model);

		// Then
		assertEquals("contact", viewName);
		verify(model).addAttribute("user", testUser);
		verify(model).addAttribute("connections", testUser.getContacts());
	}

	@Test
	public void testGetContact_WhenCurrentUserIsNull() {
		// Given
		when(userService.currentUser()).thenReturn(null);

		// When
		String viewName = contactController.getContact(model);

		// Then
		assertEquals("redirect:/login", viewName);
	}

	@Test
	public void testProcessAddContact() {
		// Given
		String email = "contactuser@mail.com";
		when(userService.currentUser()).thenReturn(testUser);
		when(userService.existsByEmail(email)).thenReturn(true);

		// When
		String viewName = contactController.processAddContact(testUser, email, redirectAttributes);

		// Then
		assertEquals("redirect:/contact", viewName);
		verify(redirectAttributes).addAttribute("success", "true");
		verify(userService).existsByEmail(email);
		verify(contactService).process_add_contact(testUser, email);
	}

	@Test
	public void testProcessAddContact_WhenEmailIsEmpty() {
		// Given
		String email = "";
		when(userService.currentUser()).thenReturn(testUser);

		// When
		String viewName = contactController.processAddContact(testUser, email, redirectAttributes);

		// Then
		assertEquals("redirect:/contact", viewName);
		verify(redirectAttributes).addAttribute("errorEmptyEmail", "true");
	}

	@Test
	public void testProcessAddContact_WhenEmailEqualsCurrentUserEmail() {
		// Given
		String email = "test@mail.com";
		when(userService.currentUser()).thenReturn(testUser);

		// When
		String viewName = contactController.processAddContact(testUser, email, redirectAttributes);

		// Then
		assertEquals("redirect:/contact", viewName);
		verify(redirectAttributes).addAttribute("errorYourEmail", "true");
	}

	@Test
	public void testProcessAddContact_WhenEmailDoesNotExist() {
		// Given
		String email = "nonexistent@mail.com";
		when(userService.currentUser()).thenReturn(testUser);
		when(userService.existsByEmail(email)).thenReturn(false);

		// When
		String viewName = contactController.processAddContact(testUser, email, redirectAttributes);

		// Then
		assertEquals("redirect:/contact", viewName);
		verify(redirectAttributes).addAttribute("errorEmailNotExist", "true");
		verify(userService).existsByEmail(email);
	}

	@Test
	public void testProcessAddContact_WhenContactAlreadyExists() {
		// Given
		String email = "contactuser@mail.com";
		List<User> connections = new ArrayList<>();
		connections.add(contactUser);
		testUser.setContacts(connections);
		when(userService.currentUser()).thenReturn(testUser);
		when(userService.existsByEmail(email)).thenReturn(true);

		// When
		String viewName = contactController.processAddContact(testUser, email, redirectAttributes);

		// Then
		assertEquals("redirect:/contact", viewName);
		verify(redirectAttributes).addAttribute("errorContactAlreadyAdded", "true");
		verify(userService).existsByEmail(email);
	}

	@Test
	public void testProcessDeleteContact() {
		// Given
		String email = "contactuser@mail.com";
		when(userService.currentUser()).thenReturn(testUser);

		// When
		String viewName = contactController.processDeleteContact(testUser, email, redirectAttributes);

		// Then
		assertEquals("redirect:/contact", viewName);
		verify(redirectAttributes).addAttribute("successdelcontact", "true");
		verify(contactService).process_delete_contact(testUser, email);
	}

	@Test
	public void testProcessDeleteContact_WhenCurrentUserIsNull() {
		// Given
		String email = "contactuser@mail.com";
		when(userService.currentUser()).thenReturn(null);

		// When
		String viewName = contactController.processDeleteContact(testUser, email, redirectAttributes);

		// Then
		assertEquals("redirect:/login", viewName);
	}
}
