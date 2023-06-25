package com.paymybuddy.service;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.paymybuddy.model.User;
import com.paymybuddy.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	UserService userService;

	private User testUser = new User();

	private String encodedPassword;

	@BeforeEach
	public void setup() {
		encodedPassword = "encodedPassword";

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
    public void testSaveUser() {
    	// Given
    	when(passwordEncoder.encode(testUser.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
    	
    	// When
        User savedUser = userService.saveUser(testUser);
    	
    	// Then
    	assertEquals(encodedPassword, savedUser.getPassword());

    }

	@Test
	public void testUpdateUser() {
		// Given
		User updatedUser = testUser;
		updatedUser.setFirstname("TestUpdate");
		when(userRepository.save(any(User.class))).thenReturn(updatedUser);

		// When
		User result = userService.updateUser(updatedUser);

		// Then
		assertNotEquals("password", result.getFirstname());
		assertEquals("TestUpdate", result.getFirstname());
	}

	@Test
	public void testGetUserByEmail() {
		// Given
		String email = "test@mail.com";
		when(userRepository.findByEmail(email)).thenReturn(testUser);

		// When
		User result = userService.getUserByEmail(email);

		// Then
		assertEquals("Mister", result.getFirstname());

	}

	@Test
    public void testExistsByEmail() {
        // Given
        when(userRepository.existsByEmail("test@mail.com")).thenReturn(true);

        // When
        boolean result = userService.existsByEmail("test@mail.com");

        // Then
        assertEquals(true, result);
    }

	@Test
	public void testFindEmailUserById() {
		// Given
		int Id = 1;
		when(userRepository.findEmailUserById(Id)).thenReturn("test@mail.com");

		// When
		String resultEmail = userService.findEmailUserById(Id);

		// Then
		assertEquals(testUser.getEmail(), resultEmail);
	}

}
