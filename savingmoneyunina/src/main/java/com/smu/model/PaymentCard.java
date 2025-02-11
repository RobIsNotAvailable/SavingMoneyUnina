package com.smu.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.smu.dao.PaymentCardDAO;
import com.smu.model.Transaction.Direction;

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
    
    /***************************************************************GETTERS**************************************************************** */
    
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

    public List<Transaction> getTransactions(Transaction.Direction direction)
    {
        return PaymentCardDAO.getTransactions(this, direction);
    }

    public List<Transaction> getTransactions(LocalDate beginDate, LocalDate endDate)
    {
        return PaymentCardDAO.getTransactions(this, beginDate, endDate);
    }

    public List<Transaction> getTransactions(LocalDate beginDate, LocalDate endDate , Transaction.Direction direction)
    {
        return PaymentCardDAO.getTransactions(this, beginDate, endDate, direction);
    }
    
    public List<Transaction> getTransactions(LocalDate beginDate, LocalDate endDate, Category category)
    {
        return PaymentCardDAO.getTransactions(this, beginDate, endDate, category);
    }

    public List<Transaction> getTransactions(LocalDate beginDate, LocalDate endDate, Category category, Direction direction)
    {
        return PaymentCardDAO.getTransactions(this, beginDate, endDate, category, direction);
    }

    public Report getReport(LocalDate date)
    {
        return PaymentCardDAO.getReport(this, date);
    }

    /***************************************************************METHODS**************************************************************** */
    public void executeTransaction(Transaction transaction)
    {
        if (transaction.getDirection() == Transaction.Direction.income) 
            balance = balance.add(transaction.getAmount());
        else    
            balance = balance.subtract(transaction.getAmount());

        PaymentCardDAO.update(this);    
        transaction.insert();
        transaction.sortInCategories();
    }
    /***************************************************************DEBUG**************************************************************** */
    public String toString()
    {
        return "Card number: " + cardNumber + " Cvv: " + cvv + " Pin: " + pin + " Expiration Date " + expirationDate.toString() + " Balance: " + balance;
    }

}
