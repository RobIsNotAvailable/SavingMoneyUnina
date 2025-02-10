package com.smu.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.smu.dao.PaymentCardDAO;

public class PaymentCard 
{
    private String cardNumber;
    private String cvv;
    private String pin;
    private LocalDate expirationDate;
    private User owner;
    private BigDecimal balance;


    public PaymentCard(String cardNumber, String cvv, String pin, LocalDate expirationDate, User owner, BigDecimal balance)
    {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.pin = pin;
        this.expirationDate = expirationDate;
        this.owner = owner;
        this.balance = balance;
    }

    public String getCardNumber()
    {
        return cardNumber;
    }

    public String getCvv()
    {
        return cvv;
    }

    public String getPin()
    {
        return pin;
    }

    public LocalDate getExpirationDate()
    {
        return expirationDate;
    }

    public User getOwner()
    {
        return owner;
    }

    public BigDecimal getBalance()
    {
        return balance;
    }

    public List<Transaction> getTransactions()
    {
        return PaymentCardDAO.getTransactions(this);
    }

    public String toString()
    {
        return "Card number: " + cardNumber + " Cvv: " + cvv + " Pin: " + pin + " Expiration Date " + expirationDate.toString() + " Owner: " + owner.getUsername() + " Balance: " + balance;
    }
}
