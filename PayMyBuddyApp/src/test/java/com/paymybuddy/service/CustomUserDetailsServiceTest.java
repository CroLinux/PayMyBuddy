package com.paymybuddy.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.paymybuddy.model.User;
import com.paymybuddy.repository.UserRepository;

@SpringBootTest
public class CustomUserDetailsServiceTest {
	
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private CustomUserDetailsService userDetailsService;

	private User testUser;

	@BeforeEach
	public void setup() {
		testUser = new User();
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
	}

	@Test
	public void testLoadUserByUsername_ValidUser() {
		// Given
		String email = "test@mail.com";
		when(userRepository.findByEmail(email)).thenReturn(testUser);

		// When
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);

		// Then
		assertEquals(testUser.getEmail(), userDetails.getUsername());
		assertEquals(testUser.getPassword(), userDetails.getPassword());
		//assertEquals(1, userDetails.getAuthorities().size());
		//assertEquals(new SimpleGrantedAuthority(testUser.getAuthority().strip()),
		//		userDetails.getAuthorities().iterator().next());
	}

	
	@Test
	public void testLoadUserByUsername_InvalidUser() {
		// Given
		String email = "invalid@mail.com";
		when(userRepository.findByEmail(email)).thenReturn(null);

		// When
		
		//Then
		assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(email));
	}
	









}
