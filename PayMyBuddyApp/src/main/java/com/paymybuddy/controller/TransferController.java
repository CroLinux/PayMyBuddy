package com.paymybuddy.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.model.User;
import com.paymybuddy.dto.TransactionDto;
import com.paymybuddy.model.Transaction;
import com.paymybuddy.service.TransactionService;
import com.paymybuddy.service.UserService;

@Controller
public class TransferController {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/transfer")
	public String viewTransferPage(Model model) {
		User currentUser = userService.currentUser();
		if (currentUser == null) {
			return "redirect:/login";
		} else {
			List<User> connections = currentUser.getContacts();
			List<Transaction> transactionsDone = currentUser.getTransactionsDone();
			List<Transaction> transactionsReceived = currentUser.getTransactionsReceived();
			Iterable<TransactionDto> allTrxDone = transactionService.findTransactionsDoneByUSer(currentUser.getId());
			Iterable<TransactionDto> allTrxReceived = transactionService
					.findTransactionsReceivedByUSer(currentUser.getId());
			model.addAttribute("user", currentUser);
			model.addAttribute("connections", connections);
			model.addAttribute("transactionsDone", transactionsDone);
			model.addAttribute("transactionsReceived", transactionsReceived);
			model.addAttribute("allTrxDone", allTrxDone);
			model.addAttribute("allTrxReceived", allTrxReceived);
			return "transfer";
		}
	}

	@PostMapping("/process_transaction")
	public String processTransaction(@ModelAttribute("transaction") Transaction transaction,
			RedirectAttributes redirectAttributes) {

		User currentUser = userService.currentUser();
		// List<User> connections = currentUser.getContacts();

		// We need to verify if the user has enough money on his balance
		if (currentUser.getBalance() < transaction.getAmount()) {
			//Go back negatively to the page
			redirectAttributes.addAttribute("errorHigherAmount", "true");
			return "redirect:/transfer";

		} else {

			// We create and save the new transaction.
			Transaction newTransaction = new Transaction();
			newTransaction.setSender_id(currentUser.getId());
			newTransaction.setReceiver_id(transaction.getReceiver_id());
			newTransaction.setType("Debit");
			newTransaction.setAmount(transaction.getAmount());
			newTransaction.setCurrency("EUR");
			LocalDateTime now = LocalDateTime.now();
			newTransaction.setDate(now);
			newTransaction.setDescription(transaction.getDescription());
			newTransaction.setFee(0);
			transactionService.saveTransaction(newTransaction);

			// We need to update the balance of the sender_id
			float newBalance = currentUser.getBalance() - transaction.getAmount();
			currentUser.setBalance(newBalance);
			userService.updateUser(currentUser);

			// We need to update the balance of the received_id
			String receiverEmail = userService.findEmailUserById(transaction.getReceiver_id());
			User receiverUser = userService.getUserByEmail(receiverEmail);
			float newReceiverBalance = receiverUser.getBalance() + transaction.getAmount();
			receiverUser.setBalance(newReceiverBalance);
			userService.updateUser(receiverUser);

			// Go back successfully to the page
			redirectAttributes.addAttribute("success", "true");
			return "redirect:/transfer";

		}

	}
}
