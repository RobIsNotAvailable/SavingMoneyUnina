package com.smu.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Report 
{
    private BigDecimal maxExpense;
    private BigDecimal minExpense;
    private BigDecimal avgExpense;

    private BigDecimal maxIncome;
    private BigDecimal minIncome;
    private BigDecimal avgIncome;

    private BigDecimal initialBalance;
    private BigDecimal finalBalance;

    private PaymentCard card;
    private LocalDate date;

    public Report(BigDecimal maxExpense, BigDecimal minExpense, BigDecimal avgExpense, BigDecimal maxIncome, BigDecimal minIncome, BigDecimal avgIncome, BigDecimal initialBalance, BigDecimal finalBalance, int numberOfExpenses, int numberOfIncomes, PaymentCard card, LocalDate date)
    {
        this.maxExpense = maxExpense;
        this.minExpense = minExpense;
        this.avgExpense = avgExpense;
        this.maxIncome = maxIncome;
        this.minIncome = minIncome;
        this.avgIncome = avgIncome;
        this.initialBalance = initialBalance;
        this.finalBalance = finalBalance;
        this.card = card;
        this.date = date.withDayOfMonth(1);
    }

    public BigDecimal getMaxExpense()
    {
        return maxExpense;
    }

    public BigDecimal getMinExpense()
    {
        return minExpense;
    }

    public BigDecimal getAvgExpense()
    {
        return avgExpense;
    }

    public BigDecimal getMaxIncome()
    {
        return maxIncome;
    }

    public BigDecimal getMinIncome()
    {
        return minIncome;
    }

    public BigDecimal getAvgIncome()
    {
        return avgIncome;
    }

    public BigDecimal getInitialBalance()
    {
        return initialBalance;
    }

    public BigDecimal getFinalBalance()
    {
        return finalBalance;
    }

    public PaymentCard getCard()
    {
        return card;
    }

    public LocalDate getDate()
    {
        return date;
    }
}
