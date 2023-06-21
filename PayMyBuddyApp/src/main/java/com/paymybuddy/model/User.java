package com.paymybuddy.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@Table(name = "user_account")
@DynamicUpdate
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

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL, })
	@JoinTable(name = "assoc_user_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "contact_id"))
	private List<User> contacts = new ArrayList<>();
/**
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL, })
	@JoinTable(name = "assoc_user_trx", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "trx_id"))
	private List<Transaction> transactions = new ArrayList<>();
*/
	
	@OneToMany(
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.EAGER)
	@JoinColumn(name = "receiver_id")
	List<Transaction> transactionsReceived = new ArrayList<>();
	
	@OneToMany(
			cascade = CascadeType.ALL, 
			orphanRemoval = true, 
			fetch = FetchType.EAGER)
	@JoinColumn(name = "sender_id")	
	List<Transaction> transactionsDone = new ArrayList<>();
	
	
	@Override
	public String toString() {
	    return "User{" +
	            "id=" + id +
	            ", email='" + email + '\'' +
	            ", password='" + password + '\'' +
	            ", firstname='" + firstname + '\'' +
	            ", lastname='" + lastname + '\'' +
	            ", bankname='" + bankname + '\'' +
	            ", iban='" + iban + '\'' +
	            ", bic='" + bic + '\'' +
	            ", balance=" + balance +
	            ", currency='" + currency + '\'' +
	            ", authority='" + authority + '\'' +
	            "transfersReceived=" + transactionsReceived + '\'' +
				", transfersDone=" + transactionsDone +
	            '}';
	}

	
}
