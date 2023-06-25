package com.paymybuddy.model;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionTest {

	private Transaction transaction1;
	private Transaction transaction2;

	@BeforeEach
	public void setup() {
		transaction1 = new Transaction();
		transaction1.setId(1);
		transaction1.setSender_id(2);
		transaction1.setReceiver_id(3);
		transaction1.setType("Type");
		transaction1.setAmount(100.0);
		transaction1.setCurrency("EUR");
		transaction1.setDate(LocalDateTime.now());
		transaction1.setDescription("Description");
		transaction1.setFee(5.0);

		transaction2 = new Transaction();
		transaction2.setId(1);
		transaction2.setSender_id(2);
		transaction2.setReceiver_id(3);
		transaction2.setType("Type");
		transaction2.setAmount(100.0);
		transaction2.setCurrency("EUR");
		transaction2.setDate(transaction1.getDate());
		transaction2.setDescription("Description");
		transaction2.setFee(5.0);
	}

	@Test
	public void testHashCode() {
		Assertions.assertEquals(transaction1.hashCode(), transaction2.hashCode());
	}

	@Test
	public void testEquals() {
		Assertions.assertEquals(transaction1, transaction2);
	}

	@Test
	public void testToString() {
		Transaction transaction = new Transaction();
		transaction.setId(1);
		transaction.setSender_id(2);
		transaction.setReceiver_id(3);
		transaction.setType("Type");
		transaction.setAmount(100.0);
		transaction.setCurrency("EUR");
		transaction.setDate(LocalDateTime.now());
		transaction.setDescription("Description");
		transaction.setFee(5.0);

		String expected = "Transaction{" + "id=1" + ", sender_id=2" + ", receiver_id=3" + ", type='Type'"
				+ ", amount=100.0" + ", currency='EUR'" + ", date=" + transaction.getDate()
				+ ", description='Description'" + ", fee=5.0" + "}";

		Assertions.assertEquals(expected, transaction.toString());
	}
}
