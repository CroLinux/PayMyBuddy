package com.paymybuddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_account")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "bankname")
	private String bankname;

	@Column(name = "iban")
	private String iban;
	
	@Column(name = "bic")
	private String bic;
	
	@Column(name = "balance")
	private float balance;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "authority")
	private String authority;

}
