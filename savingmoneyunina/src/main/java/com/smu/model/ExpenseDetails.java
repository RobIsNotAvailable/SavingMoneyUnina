package com.smu.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExpenseDetails 
{
    private BigDecimal maxExpense = BigDecimal.valueOf(0.00);
    private BigDecimal minExpense = BigDecimal.valueOf(0.00);
    private BigDecimal avgExpense = BigDecimal.valueOf(0.00);
    private BigDecimal totalExpense = BigDecimal.valueOf(0.00);

    public ExpenseDetails(BigDecimal maxExpense, BigDecimal minExpense, BigDecimal avgExpense, BigDecimal totalExpense)
    {
        if(maxExpense != null)
        {
            this.maxExpense = maxExpense.setScale(2);
            this.minExpense = minExpense.setScale(2);
            this.avgExpense = avgExpense.setScale(2, RoundingMode.HALF_UP);
            this.totalExpense = totalExpense.setScale(2);
        }
    }
    
    /************************************************GETTERS****************************************************** */
    
    public BigDecimal getMaxExpense() { return maxExpense; }

    public BigDecimal getMinExpense() { return minExpense; }

    public BigDecimal getAvgExpense() { return avgExpense; }

    public BigDecimal getTotalExpense() { return totalExpense; }
}
