package com.paymybuddy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.paymybuddy.model.User;
import com.paymybuddy.service.UserService;

@SpringBootTest
public class WebControllerTest {

	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@Mock
	private User currentUser = new User();

	@InjectMocks
	private WebController webController;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = standaloneSetup(webController).build();
	}

	@Test
	void testViewMainPage() throws Exception {
		mockMvc.perform(get("")).andExpect(status().isOk()).andExpect(view().name("login"));
	}

	@Test
	void testViewSlashPage() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("login"));
	}

	@Test
	void testViewHomePage() throws Exception {
		mockMvc.perform(get("/home")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/home-form"));
	}

	@Test
	void testShowHomePage() throws Exception {

		when(userService.currentUser()).thenReturn(currentUser);

		mockMvc.perform(get("/home-form"))
		.andExpect(status().isOk());
	}
/**
	@Test
	void testViewLoginPage() throws Exception {
		mockMvc.perform(get("/login")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login-form"));
	}

	@Test
	void testShowLoginForm() throws Exception {
		mockMvc.perform(get("/login-form")).andExpect(status().isOk()).andExpect(view().name("login"));
	}
*/

	
	@Test
	void testViewLoginPage() throws Exception {
	    mockMvc.perform(get("/login"))
	            .andExpect(status().isOk())
	            .andExpect(view().name("login.html"));
	}
	
}
