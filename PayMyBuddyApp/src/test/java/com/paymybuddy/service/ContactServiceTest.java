package com.paymybuddy.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.paymybuddy.model.User;
import com.paymybuddy.repository.UserRepository;

@SpringBootTest
public class ContactServiceTest {
	

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserService userService;

	@InjectMocks
	private ContactService contactService;

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

		when(userRepository.findByEmail(testUser.getEmail())).thenReturn(testUser);
		when(userRepository.findByEmail(contactUser.getEmail())).thenReturn(contactUser);
	}

	@Test
	public void testProcessAddContact() {
		// Given
		String contactEmail = "contactuser@mail.com";

		// When
		contactService.process_add_contact(testUser, contactEmail);

		// Then
		List<User> contacts = testUser.getContacts();
		assertEquals(1, contacts.size());
		assertEquals(contactUser, contacts.get(0));
		verify(userService).updateUser(testUser);
	}

	@Test
	public void testProcessDeleteContact() {
		// Given
		String contactEmail = "contactuser@mail.com";
		List<User> contacts = new ArrayList<>();
		contacts.add(contactUser);
		testUser.setContacts(contacts);

		// When
		contactService.process_delete_contact(testUser, contactEmail);

		// Then
		assertEquals(0, testUser.getContacts().size());
		verify(userService).updateUser(testUser);
	}


}
