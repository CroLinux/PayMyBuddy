package com.paymybuddy.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

@SpringBootTest
class UserTest {

	@Test
	public void testHashCode() {
		User user1 = new User();
		user1.setId(1);
		user1.setEmail("user1@mail.com");

		User user2 = new User();
		user2.setId(1);
		user2.setEmail("user1@mail.com");

		assertEquals(user1.hashCode(), user2.hashCode());
	}

	@Test
	public void testEquals() {
		User user1 = new User();
		user1.setId(1);
		user1.setEmail("user1@mail.com");

		User user2 = new User();
		user2.setId(1);
		user2.setEmail("user1@mail.com");

		User user3 = new User();
		user3.setId(2);
		user3.setEmail("user2@mail.com");

		assertEquals(user1, user2);
		assertNotEquals(user1, user3);
	}

	@Test
	public void testToString() {
		User testUser = new User();
		testUser.setId(1);
		testUser.setEmail("test@mail.com");
		testUser.setPassword("password");
		testUser.setFirstname("Mister");
		testUser.setLastname("Test");
		testUser.setBankname("TestBank");
		testUser.setIban("IbanTest");
		testUser.setBic("BicTest");
		testUser.setBalance(100.0);
		testUser.setCurrency("EUR");
		testUser.setAuthority("ROLE_USER");
		testUser.setContacts(new ArrayList<>());
		testUser.setTransactionsDone(new ArrayList<>());
		testUser.setTransactionsReceived(new ArrayList<>());

		String expected = "User{" + "id=1" + ", email='test@mail.com'" + ", password='password'"
				+ ", firstname='Mister'" + ", lastname='Test'" + ", bankname='TestBank'" + ", iban='IbanTest'"
				+ ", bic='BicTest'" + ", balance=100.0" + ", currency='EUR'" + ", authority='ROLE_USER'"
				+ "transfersReceived=[]'" + ", transfersDone=[]" + "}";

		assertEquals(expected, testUser.toString());
	}
}
