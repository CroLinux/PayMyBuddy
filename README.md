![15716565363662_image1](https://github.com/CroLinux/PayMyBuddy/assets/119310403/1409c3e1-06be-423b-9d75-c4cc3cf05f0b)


# Pay My Buddy

(Web application / Spring Boot application prototype for Openclassrooms, project number 6.)

# Presentation of the project:

At this point, we should create a prototype of a Web Application which will allow the user to transfer money to his Bank and to one of his contact. And, naturally, this user should be able via this application to receive money from his Bank and from a contact.

# Main features of the Application:

- A new User should be able to create an account by providing some information.
- A registered User should be able to login via his email and password.
- A connected User should be able to see his status.
- A connected User should be able to modify his information.
- A connected User should be able to manage his contacts.
- A connected User should be able to see all of his transactions.
- A connected User should be able to process some transfer to his Bank of to a Contact.

# Getting Started 

# Prerequisites

	•	Java 17
	•	Maven
	•	MySQL
	•	MySQL Driver
	•	Thymeleaf
	•	Spring Web
	•	Spring Data JPA
	•	Spring Security
	•	Spring Boot DevTools
	•	Lombok

# UML Diagram

<img width="986" alt="AD - PayMyBuddy UML" src="https://github.com/CroLinux/PayMyBuddy/assets/119310403/647acde3-7a00-4fee-a2d1-96bf53ec0329">

# Database Diagram

<img width="966" alt="AD PayMyBuddy DB" src="https://github.com/CroLinux/PayMyBuddy/assets/119310403/7440b96c-6e2b-4e32-a478-220063d57427">

# Start the Application

MySQL, Java and Maven are configured on your computer. 
Import the project in your IDE from here, and then, you need first to implement the database, create the tables and inserts some data. For that, please execute the script « /PayMyBuddyApp/src/main/resources/database/paymybuddyDB.sql ».

( On a Mac: mysql -u root -p < /Users/***your_user***/git/PayMyBuddy/PayMyBuddyApp/src/main/resources/database/paymybuddyDB.sql )

Set your local variables DB_USERNAME and DB_PASSWORD with your Database credentials, or modify them directly into the application properties files (/PayMyBuddyApp/src/main/resources/application.properties).

Start the application. Open a browser to this URL: http://localhost:8080/login to access to the login page and log-in using an existing account (user1@mail.com or user2@mail.com, both password is ‘password’) or register a new one.

INFO: The first user, with the id=1, is for the moment the one for the Bank in order to simulate the connection.

# Screenshots

<img width="1460" alt="Capture d’écran 2023-06-26 à 12 44 20" src="https://github.com/CroLinux/PayMyBuddy/assets/119310403/3a475bb2-bad8-45f2-8d6c-1389e009a989">

<img width="1460" alt="Capture d’écran 2023-06-26 à 12 43 49" src="https://github.com/CroLinux/PayMyBuddy/assets/119310403/82a7bd33-0a84-4df4-9ddd-bad77cf50d52">



