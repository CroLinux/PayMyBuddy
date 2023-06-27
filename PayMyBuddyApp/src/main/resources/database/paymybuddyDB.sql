-- Creation script for PAYMYBUDDY Database - OPCR - Project 6 --
-- We verify if the DB is already existing and if yes, we delete this one --
DROP DATABASE IF EXISTS paymybuddy;
-- We create (again) the DB for the project --
CREATE DATABASE paymybuddy;
-- We select this new DB --
USE paymybuddy;
-- Start of the creation of the tables --
-- Table structure for table 'buddyuser' --
CREATE TABLE user_account
(
   id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
   email VARCHAR (255) NOT NULL UNIQUE,
   password VARCHAR (255) NOT NULL,
   firstname VARCHAR (255) NOT NULL,
   lastname VARCHAR (255) NOT NULL,
   bankname VARCHAR (255) NOT NULL,
   iban VARCHAR (255) NOT NULL,
   bic VARCHAR (255) NOT NULL,
   balance FLOAT DEFAULT NULL,
   currency VARCHAR (10) NOT NULL DEFAULT 'EUR',
   authority VARCHAR (10) NOT NULL DEFAULT 'ROLE_USER'
);
-- Table structure for association table 'assoc_user_user' --
CREATE TABLE assoc_user_user
(
   user_id INTEGER NOT NULL,
   contact_id INTEGER NOT NULL,
   PRIMARY KEY
   (
      user_id,
      contact_id
   ),
   FOREIGN KEY (user_id) REFERENCES user_account (id),
   FOREIGN KEY (contact_id) REFERENCES user_account (id)
);
-- Table structure for table 'transaction' --
CREATE TABLE transaction
(
   id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
   sender_id INTEGER NOT NULL,
   receiver_id INTEGER NOT NULL,
   type VARCHAR (255),
   amount FLOAT NOT NULL,
   currency VARCHAR (10) NOT NULL,
   date datetime,
   description VARCHAR (255),
   fee FLOAT NOT NULL,
   FOREIGN KEY (sender_id) REFERENCES user_account (id),
   FOREIGN KEY (receiver_id) REFERENCES user_account (id)
);


-- Insert fake users and number 1 for the Bank simulation --
INSERT INTO user_account VALUES
(1,'My Bank','password','My Bank','My Bank','MY Bank','MB111222333444','BICFRMB111',123.45,'EUR','NO_ROLE'),
(2,'user1@mail.com','$2a$10$SHxXlGbeHGGDg5Hx.vpdDuLXGk45slMCIeMTd9knI9amj7ulfzxqG','User','One','Bank User1','FR1234567890','BICFRUSR1',100,'EUR','ROLE_USER'),
(3,'user2@mail.com','$2a$10$5mJTbljlpTyjQkr8WrKhquYCvpPa9.4/ZjPBrebxw8FQ8oUhEml.i','User','Two','Bank User2','FR2234567890','BICFRUSR2',100,'EUR','ROLE_USER');

INSERT INTO transaction VALUES
(1, 1, 3,'Credit', 100.00, 'EUR', '19/05/23 11:20:12', 'Get from my Bank', 0.5),
(2, 3, 2,'Debit', 15.00, 'EUR', '19/05/23 12:20:12', 'Pret', 0.75),
(3, 2, 1,'Debit', 1.00, 'EUR', '19/05/23 13:20:12', 'Transfer to my Bank', 0.005),
(4, 2, 3,'Debit', 10.00, 'EUR', '19/05/23 14:20:12', 'Don', 0.05);

INSERT INTO assoc_user_user VALUES
(3 ,2),(2 ,3);
