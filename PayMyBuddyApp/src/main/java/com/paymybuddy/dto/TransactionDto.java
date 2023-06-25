package com.paymybuddy.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class TransactionDto {

	/**
	 * Class used for the combination of data from the 2 tables in DB
	 */
	@Id
	private int id;
	private int sender_id;
	private String email_sender;
	private String firstname_sender;
	private String lastname_sender;
	private int receiver_id;
	private String email_receiver;
	private String firstname_receiver;
	private String lastname_receiver;
	private String type;
	private Double amount;
	private String currency;
	private LocalDateTime date;
	private String description;
	private Double fee;
	
	@Override
	public String toString() {
	    return "TransactionDto{" +
	            "id=" + id +
	            ", sender_id=" + sender_id +
	            ", email_sender='" + email_sender + '\'' +
	            ", firstname_sender='" + firstname_sender + '\'' +
	            ", lastname_sender='" + lastname_sender + '\'' +
	            ", receiver_id=" + receiver_id +
	            ", email_receiver='" + email_receiver + '\'' +
	            ", firstname_receiver='" + firstname_receiver + '\'' +
	            ", lastname_receiver='" + lastname_receiver + '\'' +
	            ", type='" + type + '\'' +
	            ", amount=" + amount +
	            ", currency='" + currency + '\'' +
	            ", date=" + date +
	            ", description='" + description + '\'' +
	            ", fee=" + fee +
	            '}';
	}

}
