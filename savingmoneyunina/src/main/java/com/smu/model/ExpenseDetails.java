package com.smu.model;

import java.math.BigDecimal;

public class ExpenseDetails 
{
    private BigDecimal maxExpense;
    private BigDecimal minExpense;
    private BigDecimal avgExpense;

    public ExpenseDetails(BigDecimal maxExpense, BigDecimal minExpense, BigDecimal avgExpense)
    {
        this.maxExpense = maxExpense;
        this.minExpense = minExpense;
        this.avgExpense = avgExpense;
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
/***************************************************DEBUG************************************************** */
    public String toString()
    {
        return "Max expense: " + maxExpense + "\n" +
            "Min expense: " + minExpense + "\n" +
            "Avg expense: " + avgExpense;
    }
}
