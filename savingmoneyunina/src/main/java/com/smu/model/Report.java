package com.smu.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Report 
{
    private IncomeDetails incomeDetails;
    private ExpenseDetails expenseDetails;

    private MonthlyBalance monthlyBalance;

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
    public Report(IncomeDetails incomeDetails, ExpenseDetails expenseDetails, MonthlyBalance monthlyBalance, PaymentCard card, LocalDate date)
    {
        this(card, date);

        this.expenseDetails = expenseDetails;
        this.incomeDetails = incomeDetails;

        this.monthlyBalance = monthlyBalance;
    }

    /************************************************GETTERS****************************************************** */

    public MonthlyBalance getMonthlyBalance() { return monthlyBalance; }

    public BigDecimal getInitialBalance() { return monthlyBalance.getInitialBalance(); }

    public BigDecimal getFinalBalance() { return monthlyBalance.getFinalBalance(); }

    public PaymentCard getCard() { return card; }

    public LocalDate getDate() { return date; }
    
    public IncomeDetails getIncomeDetails() { return incomeDetails; }

    public ExpenseDetails getExpenseDetails() {return expenseDetails; }

    public BigDecimal getMonthlyDifference() { return getFinalBalance().subtract(getInitialBalance()); }
}
