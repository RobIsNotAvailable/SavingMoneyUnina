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
    
    /***************************************************************GETTERS**************************************************************** */
    
    public String getCardNumber() { return cardNumber; }

    public String getCvv() { return cvv; }

    public String getPin() { return pin; }

    public LocalDate getExpirationDate() { return expirationDate; }

    public User getOwner() { return owner; }

    public BigDecimal getBalance() { return balance; }

    public Report getReport(LocalDate date) { return PaymentCardDAO.getReport(this, date); }

    public BigDecimal getTotalMonthlyIncome(LocalDate date) { return PaymentCardDAO.getTotalMonthlyIncome(this, date); }

    public BigDecimal getTotalMonthlyExpense(LocalDate date) { return PaymentCardDAO.getTotalMonthlyExpense(this, date); }

    public List<Transaction> getTransactions() { return PaymentCardDAO.getTransactions(this); }
    
    public List<Transaction> filterTransactions(TransactionFilter transactionFilter, List<Transaction> transactions) { return transactionFilter.filter(transactions); }

    /***************************************************************METHODS**************************************************************** */
    public void executeTransaction(Transaction transaction)
    {
        if (transaction.getDirection() == Transaction.Direction.INCOME) 
            balance = balance.add(transaction.getAmount());
        else    
        {   
            if (transaction.getAmount().compareTo(balance) > 0)
                throw new IllegalArgumentException("Insufficient balance for the transaction."); 
            
            balance = balance.subtract(transaction.getAmount());
        }

        transaction.insert();
        transaction.sortInCategories();
        PaymentCardDAO.update(this);
    }

    public LocalDate getFirstReportDate()
    {
        return PaymentCardDAO.getFirstReportDate(this);
    }
}
