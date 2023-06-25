package com.paymybuddy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.paymybuddy.dto.TransactionDto;
import com.paymybuddy.model.Transaction;
import com.paymybuddy.repository.TransactionRepository;

@SpringBootTest
public class TransactionServiceTest {

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private TransactionService transactionService;

	private TransactionDto testTransactionDto;
	private Transaction testTransaction;

	@BeforeEach
	public void setup() {

		testTransactionDto = new TransactionDto();
		testTransactionDto.setId(1);
		testTransactionDto.setSender_id(101);
		testTransactionDto.setEmail_sender("sender@mail.com");
		testTransactionDto.setFirstname_sender("Fsender");
		testTransactionDto.setLastname_sender("Lsender");
		testTransactionDto.setReceiver_id(102);
		testTransactionDto.setEmail_receiver("receiver@mail.com");
		testTransactionDto.setFirstname_receiver("Freceiver");
		testTransactionDto.setLastname_receiver("Lreceiver");
		testTransactionDto.setType("Debit");
		testTransactionDto.setAmount(100.0);
		testTransactionDto.setCurrency("EUR");
		LocalDateTime now = LocalDateTime.now();
		testTransactionDto.setDate(now);
		testTransactionDto.setDescription("Test Transaction");
		testTransactionDto.setFee(0.05);

		testTransaction = new Transaction();
		testTransaction.setId(2);
		testTransaction.setSender_id(201);
		testTransaction.setReceiver_id(202);
		testTransaction.setType("Debit");
		testTransaction.setAmount(200.0);
		testTransaction.setCurrency("EUR");
		LocalDateTime now2 = LocalDateTime.now();
		testTransaction.setDate(now2);
		testTransaction.setDescription("Test2 Transaction");
		testTransaction.setFee(0.10);

		when(transactionRepository.findTransactionsDoneByUser(any(Integer.class)))
				.thenReturn(Arrays.asList(testTransactionDto));
		when(transactionRepository.findTransactionsReceivedByUser(any(Integer.class)))
				.thenReturn(Arrays.asList(testTransactionDto));

		when(transactionRepository.save(any(Transaction.class))).thenReturn(testTransaction);
	}

	@Test
	public void testFindTransactionsDoneByUSer() {
		// Given
		int userId = 101;

		// When
		Iterable<TransactionDto> result = transactionService.findTransactionsDoneByUser(userId);

		// Then
		// We need to convert the expected<Result to a list in order to compare the 2 variables
		List<TransactionDto> expectedResult = Arrays.asList(testTransactionDto);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindTransactionsReceivedByUSer() {
		// Given
		int userId = 102;

		// When
		Iterable<TransactionDto> result = transactionService.findTransactionsReceivedByUser(userId);

		// Then
		// We need to convert the expected<Result to a list in order to compare the 2 variables
		List<TransactionDto> expectedResult = Arrays.asList(testTransactionDto); 
		assertEquals(expectedResult, result);
	}

	@Test
	public void testSaveTransaction() {
		// Given
		Transaction transaction = new Transaction();

		// When
		Transaction result = transactionService.saveTransaction(transaction);

		// Then
		assertEquals(testTransaction, result);
	}

}
