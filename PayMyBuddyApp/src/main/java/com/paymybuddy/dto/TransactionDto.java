package com.paymybuddy.dto;

import java.util.Date;

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
	private Float amount;
	private String currency;
	private Date date;
	private String description;
	private float fee;

}
