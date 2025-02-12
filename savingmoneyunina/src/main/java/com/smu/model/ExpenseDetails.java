package com.smu.model;

import java.math.BigDecimal;

public class ExpenseDetails 
{
    private BigDecimal maxExpense = BigDecimal.ZERO;
    private BigDecimal minExpense = BigDecimal.ZERO;
    private BigDecimal avgExpense = BigDecimal.ZERO;

    public ExpenseDetails(BigDecimal maxExpense, BigDecimal minExpense, BigDecimal avgExpense)
    {
        if(maxExpense != null)
        {
            this.maxExpense = maxExpense;
            this.minExpense = minExpense;
            this.avgExpense = avgExpense.setScale(2);
        }
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
