package com.paymybuddy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class PayMyBuddyAppApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void main() {
		// Test case logic goes here
		// For example, you can test if the application starts successfully
		assertDoesNotThrow(() -> PayMyBuddyAppApplication.main(new String[] {}));
	}

}
