package com.paymybuddy.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class TransactionDtoTest {

    private TransactionDto transactionDto1;
    private TransactionDto transactionDto2;
    private TransactionDto transactionDto3;

    @BeforeEach
    public void setup() {
        transactionDto1 = new TransactionDto();
        transactionDto1.setId(1);
        transactionDto1.setSender_id(2);
        transactionDto1.setEmail_sender("sender@mail.com");
        transactionDto1.setFirstname_sender("Mister");
        transactionDto1.setLastname_sender("Test");
        transactionDto1.setReceiver_id(3);
        transactionDto1.setEmail_receiver("receiver@mail.com");
        transactionDto1.setFirstname_receiver("Miss");
        transactionDto1.setLastname_receiver("Test");
        transactionDto1.setType("Debit");
        transactionDto1.setAmount(100.0);
        transactionDto1.setCurrency("EUR");
        transactionDto1.setDate(LocalDateTime.now());
        transactionDto1.setDescription("TransferTest");
        transactionDto1.setFee(5.0);

        transactionDto2 = new TransactionDto();
        transactionDto2.setId(1);
        transactionDto2.setSender_id(2);
        transactionDto2.setEmail_sender("sender@mail.com");
        transactionDto2.setFirstname_sender("Mister");
        transactionDto2.setLastname_sender("Test");
        transactionDto2.setReceiver_id(3);
        transactionDto2.setEmail_receiver("receiver@mail.com");
        transactionDto2.setFirstname_receiver("Miss");
        transactionDto2.setLastname_receiver("Test");
        transactionDto2.setType("Debit");
        transactionDto2.setAmount(100.0);
        transactionDto2.setCurrency("EUR");
        transactionDto2.setDate(transactionDto1.getDate());
        transactionDto2.setDescription("TransferTest");
        transactionDto2.setFee(5.0);
        
        transactionDto3 = new TransactionDto();
        transactionDto3.setId(1);
        transactionDto3.setSender_id(2);
        transactionDto3.setEmail_sender("senderX@mail.com");
        transactionDto3.setFirstname_sender("MisterX");
        transactionDto3.setLastname_sender("TestX");
        transactionDto3.setReceiver_id(3);
        transactionDto3.setEmail_receiver("receiver@mail.com");
        transactionDto3.setFirstname_receiver("Miss");
        transactionDto3.setLastname_receiver("Test");
        transactionDto3.setType("Debit");
        transactionDto3.setAmount(1000.0);
        transactionDto3.setCurrency("EUR");
        transactionDto3.setDate(transactionDto1.getDate());
        transactionDto3.setDescription("TransferTest");
        transactionDto3.setFee(50.0);
    }

    @Test
    public void testHashCode() {
        Assertions.assertEquals(transactionDto1.hashCode(), transactionDto2.hashCode());
        Assertions.assertNotEquals(0, transactionDto1.hashCode());
        Assertions.assertNotEquals(transactionDto3.hashCode(), transactionDto1.hashCode());
    }

    @Test
    public void testEquals() {
        Assertions.assertEquals(transactionDto1, transactionDto2);
        Assertions.assertNotEquals(transactionDto1, transactionDto3);
    }

    @Test
    public void testToString() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(1);
        transactionDto.setSender_id(2);
        transactionDto.setEmail_sender("sender@mail.com");
        transactionDto.setFirstname_sender("Mister");
        transactionDto.setLastname_sender("Test");
        transactionDto.setReceiver_id(3);
        transactionDto.setEmail_receiver("receiver@mail.com");
        transactionDto.setFirstname_receiver("Miss");
        transactionDto.setLastname_receiver("Test");
        transactionDto.setType("Debit");
        transactionDto.setAmount(100.0);
        transactionDto.setCurrency("EUR");
        transactionDto.setDate(LocalDateTime.now());
        transactionDto.setDescription("TransferTest");
        transactionDto.setFee(5.0);

        String expected = "TransactionDto{" +
                "id=1" +
                ", sender_id=2" +
                ", email_sender='sender@mail.com'" +
                ", firstname_sender='Mister'" +
                ", lastname_sender='Test'" +
                ", receiver_id=3" +
                ", email_receiver='receiver@mail.com'" +
                ", firstname_receiver='Miss'" +
                ", lastname_receiver='Test'" +
                ", type='Debit'" +
                ", amount=100.0" +
                ", currency='EUR'" +
                ", date=" + transactionDto.getDate() +
                ", description='TransferTest'" +
                ", fee=5.0" +
                "}";

        Assertions.assertEquals(expected, transactionDto.toString());
    }
}
