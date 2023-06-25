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
	
	private static double FEE = 0.5 / 100;

	/**
	 * Method to display the page and the different informations
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/transfer")
	public String viewTransferPage(Model model) {
		User currentUser = userService.currentUser();
		if (currentUser == null) {
			return "redirect:/login";
		} else {
			List<User> connections = currentUser.getContacts();
			List<Transaction> transactionsDone = currentUser.getTransactionsDone();
			List<Transaction> transactionsReceived = currentUser.getTransactionsReceived();
			Iterable<TransactionDto> allTrxDone = transactionService.findTransactionsDoneByUser(currentUser.getId());
			Iterable<TransactionDto> allTrxReceived = transactionService
					.findTransactionsReceivedByUser(currentUser.getId());
			model.addAttribute("user", currentUser);
			model.addAttribute("connections", connections);
			model.addAttribute("transactionsDone", transactionsDone);
			model.addAttribute("transactionsReceived", transactionsReceived);
			model.addAttribute("allTrxDone", allTrxDone);
			model.addAttribute("allTrxReceived", allTrxReceived);
			return "transfer";
		}
	}

	/**
	 * Method to process a transfer to another user.
	 * 
	 * @param transaction
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/process_transaction")
	public String processTransaction(@ModelAttribute("transaction") Transaction transaction,
			RedirectAttributes redirectAttributes) {

		User currentUser = userService.currentUser();

		// We need to verify if the user has enough money on his balance
		if (currentUser.getBalance() < transaction.getAmount()) {
			// Go back negatively to the page
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
			newTransaction.setFee(transaction.getAmount()*FEE);
			transactionService.saveTransaction(newTransaction);

			// We need to update the balance of the sender_id
			double newBalance = currentUser.getBalance() - transaction.getAmount() - newTransaction.getFee();
			currentUser.setBalance(newBalance);
			userService.updateUser(currentUser);

			// We need to update the balance of the received_id
			String receiverEmail = userService.findEmailUserById(transaction.getReceiver_id());
			User receiverUser = userService.getUserByEmail(receiverEmail);
			double newReceiverBalance = receiverUser.getBalance() + transaction.getAmount();
			receiverUser.setBalance(newReceiverBalance);
			userService.updateUser(receiverUser);

			// Go back successfully to the page
			redirectAttributes.addAttribute("success", "true");
			return "redirect:/transfer";

		}

	}

	/**
	 * Method to send a certain amount to user's bank (simulation)
	 * 
	 * @param transaction
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/process_send_to_bank")
	public String processSendToBank(@ModelAttribute("transaction") Transaction transaction,
			RedirectAttributes redirectAttributes) {

		User currentUser = userService.currentUser();

		// We need to verify if the user has enough money on his balance
		if (currentUser.getBalance() < transaction.getAmount()) {
			// Go back negatively to the page
			redirectAttributes.addAttribute("errorHigherAmount", "true");
			return "redirect:/transfer";

		} else {

			// We create and save the new transaction/ transfer to the bank.
			Transaction newTransaction = new Transaction();
			newTransaction.setSender_id(currentUser.getId());
			newTransaction.setReceiver_id(1); // into the DB and the user_account table, the first one will be for the
												// bank
			newTransaction.setType("Debit");
			newTransaction.setAmount(transaction.getAmount());
			newTransaction.setCurrency("EUR");
			LocalDateTime now = LocalDateTime.now();
			newTransaction.setDate(now);
			newTransaction.setDescription("Transfer to my Bank");
			newTransaction.setFee(transaction.getAmount()*FEE);
			transactionService.saveTransaction(newTransaction);

			// We need to update the balance of the sender_id/user
			double newBalance = currentUser.getBalance() - transaction.getAmount() - newTransaction.getFee();
			currentUser.setBalance(newBalance);
			userService.updateUser(currentUser);

			// Go back successfully to the page
			redirectAttributes.addAttribute("successtobank", "true");
			return "redirect:/profile";

		}

	}

	/**
	 * Method to get a certain amount from user's bank (simulation)
	 * 
	 * @param transaction
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/process_get_from_bank")
	public String processGetFromBank(@ModelAttribute("transaction") Transaction transaction,
			RedirectAttributes redirectAttributes) {

		User currentUser = userService.currentUser();
		// List<User> connections = currentUser.getContacts();

		// We create and save the new transaction.
		Transaction newTransaction = new Transaction();
		newTransaction.setSender_id(1);
		newTransaction.setReceiver_id(currentUser.getId());
		newTransaction.setType("Credit");
		newTransaction.setAmount(transaction.getAmount());
		newTransaction.setCurrency("EUR");
		LocalDateTime now = LocalDateTime.now();
		newTransaction.setDate(now);
		newTransaction.setDescription("Get from my Bank");
		newTransaction.setFee(transaction.getAmount()*FEE);
		transactionService.saveTransaction(newTransaction);

		// We need to update the balance of the receiver_id
		double newBalance = currentUser.getBalance() + transaction.getAmount() - newTransaction.getFee();
		currentUser.setBalance(newBalance);
		userService.updateUser(currentUser);

		// Go back successfully to the page
		redirectAttributes.addAttribute("successfrombank", "true");
		return "redirect:/profile";

	}
}
