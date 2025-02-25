package com.smu.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Report 
{
    IncomeDetails incomeDetails;
    ExpenseDetails expenseDetails;

    private BigDecimal initialBalance;
    private BigDecimal finalBalance;

    private PaymentCard card;
    private LocalDate date;

    public Report(PaymentCard card, LocalDate date)
    {
        
        this.card = card;
        this.date = date.withDayOfMonth(1);
    }
    
    //constructor for the DAO
    public Report(IncomeDetails incomeDetails, ExpenseDetails expenseDetails, BigDecimal initialBalance, BigDecimal finalBalance, PaymentCard card, LocalDate date)
    {
        this(card, date);

        this.expenseDetails = expenseDetails;
        this.incomeDetails = incomeDetails;

        this.initialBalance = initialBalance;
        this.finalBalance = finalBalance;
    }

    /************************************************GETTERS****************************************************** */

    public BigDecimal getInitialBalance() { return initialBalance; }

    public BigDecimal getFinalBalance() { return finalBalance; }

    public PaymentCard getCard() { return card; }

    public LocalDate getDate() { return date; }
    
    /***************************************************DEBUG******************************************************* */
    public String toString()
    {
        return "Initial balance: " + initialBalance + "\n" +
                "Final balance: " + finalBalance + "\n\n" +
                "Income details: " + incomeDetails + "\n\n" +
                "Expense details: " + expenseDetails + "\n\n" ;
    }
}
