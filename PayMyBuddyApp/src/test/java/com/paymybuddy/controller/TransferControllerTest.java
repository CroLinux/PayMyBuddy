package com.paymybuddy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.dto.TransactionDto;
import com.paymybuddy.model.Transaction;
import com.paymybuddy.model.User;
import com.paymybuddy.service.TransactionService;
import com.paymybuddy.service.UserService;

@SpringBootTest
public class TransferControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private TransactionService transactionService;

	@InjectMocks
	private TransferController transferController;

	@Mock
	private Model model;

	@Mock
	private RedirectAttributes redirectAttributes;
	
	private static double FEE = 0.5 / 100;

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
		testUser.setContacts(new ArrayList<>());
		testUser.setTransactionsDone(new ArrayList<>());
		testUser.setTransactionsReceived(new ArrayList<>());
	}

	@Test
	public void testViewTransferPage_WhenCurrentUserIsNull() {
		// Given
		when(userService.currentUser()).thenReturn(null);

		// When
		String viewName = transferController.viewTransferPage(model);

		// Then
		assertEquals("redirect:/login", viewName);
	}

	@Test
	public void testViewTransferPage_WhenCurrentUserExists() {
		// Given
		when(userService.currentUser()).thenReturn(testUser);
		List<Transaction> transactionsDone = new ArrayList<>();
		List<Transaction> transactionsReceived = new ArrayList<>();
		Iterable<TransactionDto> allTrxDone = new ArrayList<>();
		Iterable<TransactionDto> allTrxReceived = new ArrayList<>();
		when(transactionService.findTransactionsDoneByUser(testUser.getId())).thenReturn(allTrxDone);
		when(transactionService.findTransactionsReceivedByUser(testUser.getId())).thenReturn(allTrxReceived);

		// When
		String viewName = transferController.viewTransferPage(model);

		// Then
		assertEquals("transfer", viewName);
		verify(model).addAttribute("user", testUser);
		verify(model).addAttribute("connections", testUser.getContacts());
		verify(model).addAttribute("transactionsDone", transactionsDone);
		verify(model).addAttribute("transactionsReceived", transactionsReceived);
		verify(model).addAttribute("allTrxDone", allTrxDone);
		verify(model).addAttribute("allTrxReceived", allTrxReceived);
	}

	@Test
	public void testProcessTransaction_WhenCurrentUserHasEnoughBalance() {
		// Given
		Transaction transaction = new Transaction();
		transaction.setAmount(50);
		transaction.setReceiver_id(2);
		when(userService.currentUser()).thenReturn(testUser);
		when(userService.findEmailUserById(2)).thenReturn("receiver@mail.com");
		User receiverUser = new User();
		receiverUser.setId(2);
		receiverUser.setBalance(0);
		when(userService.getUserByEmail("receiver@mail.com")).thenReturn(receiverUser);
		Transaction newTransaction = new Transaction();
		LocalDateTime now = LocalDateTime.now();
		newTransaction.setSender_id(testUser.getId());
		newTransaction.setReceiver_id(transaction.getReceiver_id());
		newTransaction.setType("Debit");
		newTransaction.setAmount(transaction.getAmount());
		newTransaction.setCurrency("EUR");
		newTransaction.setDate(now);
		newTransaction.setDescription(transaction.getDescription());
		double fee = transaction.getAmount() * FEE;
		newTransaction.setFee(fee);
		when(transactionService.saveTransaction(newTransaction)).thenReturn(newTransaction);

		// When
		String viewName = transferController.processTransaction(transaction, redirectAttributes);

		// Then
		assertEquals("redirect:/transfer", viewName);
		assertEquals(49.75, testUser.getBalance());
		assertEquals(50, receiverUser.getBalance());
	}

	@Test
	public void testProcessTransaction_WhenCurrentUserDoesNotHaveEnoughBalance() {
		// Given
		Transaction transaction = new Transaction();
		transaction.setAmount(150);
		when(userService.currentUser()).thenReturn(testUser);

		// When
		String viewName = transferController.processTransaction(transaction, redirectAttributes);

		// Then
		assertEquals("redirect:/transfer", viewName);
		assertEquals(100, testUser.getBalance());
		verify(redirectAttributes).addAttribute("errorHigherAmount", "true");
	}

	@Test
	public void testProcessSendToBank_WhenCurrentUserHasEnoughBalance() {
		// Given
		Transaction transaction = new Transaction();
		transaction.setAmount(50);
		when(userService.currentUser()).thenReturn(testUser);
		Transaction newTransaction = new Transaction();
		LocalDateTime now = LocalDateTime.now();
		newTransaction.setSender_id(testUser.getId());
		newTransaction.setReceiver_id(1);
		newTransaction.setType("Debit");
		newTransaction.setAmount(transaction.getAmount());
		newTransaction.setCurrency("EUR");
		newTransaction.setDate(now);
		newTransaction.setDescription("Transfer to my Bank");
		double fee = transaction.getAmount() * FEE;
		newTransaction.setFee(fee);
		when(transactionService.saveTransaction(newTransaction)).thenReturn(newTransaction);

		// When
		String viewName = transferController.processSendToBank(transaction, redirectAttributes);

		// Then
		assertEquals("redirect:/profile", viewName);
		assertEquals(49.75, testUser.getBalance());
	}

	@Test
	public void testProcessSendToBank_WhenCurrentUserDoesNotHaveEnoughBalance() {
		// Given
		Transaction transaction = new Transaction();
		transaction.setAmount(150);
		when(userService.currentUser()).thenReturn(testUser);

		// When
		String viewName = transferController.processSendToBank(transaction, redirectAttributes);

		// Then
		assertEquals("redirect:/transfer", viewName);
		assertEquals(100, testUser.getBalance());
		verify(redirectAttributes).addAttribute("errorHigherAmount", "true");
	}

	@Test
	public void testProcessGetFromBank() {
		// Given
		Transaction transaction = new Transaction();
		transaction.setAmount(50);
		when(userService.currentUser()).thenReturn(testUser);
		Transaction newTransaction = new Transaction();
		LocalDateTime now = LocalDateTime.now();
		newTransaction.setSender_id(1);
		newTransaction.setReceiver_id(testUser.getId());
		newTransaction.setType("Credit");
		newTransaction.setAmount(transaction.getAmount());
		newTransaction.setCurrency("EUR");
		newTransaction.setDate(now);
		newTransaction.setDescription("Get from my Bank");
		double fee = transaction.getAmount() * FEE;
		newTransaction.setFee(fee);
		when(transactionService.saveTransaction(newTransaction)).thenReturn(newTransaction);

		// When
		String viewName = transferController.processGetFromBank(transaction, redirectAttributes);

		// Then
		assertEquals("redirect:/profile", viewName);
		assertEquals(149.75, testUser.getBalance());
	}
}
