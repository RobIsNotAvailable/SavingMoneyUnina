package com.smu.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExpenseDetails 
{
    private BigDecimal maxExpense = BigDecimal.ZERO;
    private BigDecimal minExpense = BigDecimal.ZERO;
    private BigDecimal avgExpense = BigDecimal.ZERO;
    private BigDecimal totalExpense = BigDecimal.ZERO;

    public ExpenseDetails(BigDecimal maxExpense, BigDecimal minExpense, BigDecimal avgExpense, BigDecimal totalExpense)
    {
        if(maxExpense != null)
        {
            this.maxExpense = maxExpense;
            this.minExpense = minExpense;
            this.avgExpense = avgExpense.setScale(2,RoundingMode.HALF_UP);
            this.totalExpense = totalExpense;
        }
    }
    
    /************************************************GETTERS****************************************************** */
    
    public BigDecimal getMaxExpense() { return maxExpense; }

    public BigDecimal getMinExpense() { return minExpense; }

    public BigDecimal getAvgExpense() { return avgExpense; }

    public BigDecimal getTotalExpense() { return totalExpense; }
    /***************************************************DEBUG************************************************** */
    public String toString()
    {
        return "Max expense: " + maxExpense + "\n" +
            "Min expense: " + minExpense + "\n" +
            "Avg expense: " + avgExpense;
    }
}
