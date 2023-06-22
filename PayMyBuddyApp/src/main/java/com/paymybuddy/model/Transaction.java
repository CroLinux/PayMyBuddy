package com.paymybuddy.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "sender_id")
	private int sender_id;

	@Column(name = "receiver_id")
	private int receiver_id;

	@Column(name = "type")
	private String type;

	@Column(name = "amount")
	private Float amount;

	@Column(name = "currency")
	private String currency;

	@Column(name = "date")
	private LocalDateTime date ;

	@Column(name = "description")
	private String description;

	@Column(name = "fee")
	private float fee;

}
