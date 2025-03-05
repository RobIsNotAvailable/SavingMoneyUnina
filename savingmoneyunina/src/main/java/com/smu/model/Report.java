package com.smu.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Report 
{
    private IncomeDetails incomeDetails;
    private ExpenseDetails expenseDetails;

    private BigDecimal initialBalance = BigDecimal.valueOf(0.00);
    private BigDecimal finalBalance = BigDecimal.valueOf(0.00);

    private PaymentCard card;
    private LocalDate date;

    public Report(PaymentCard card, LocalDate date)
    {
        this.incomeDetails = new IncomeDetails(null, null, null, null);
        this.expenseDetails = new ExpenseDetails(null, null, null, null);

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
    
    public IncomeDetails getIncomeDetails() { return incomeDetails; }

    public ExpenseDetails getExpenseDetails() {return expenseDetails; }

    public BigDecimal getMonthlyDifference() { return finalBalance.subtract(initialBalance); }
    
    /***************************************************DEBUG******************************************************* */
    public String toString()
    {
        return "Initial balance: " + initialBalance + "\n" +
                "Final balance: " + finalBalance + "\n\n" +
                "Income details: " + incomeDetails + "\n\n" +
                "Expense details: " + expenseDetails + "\n\n" ;
    }
}
