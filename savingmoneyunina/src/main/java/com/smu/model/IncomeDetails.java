package com.smu.model;

import java.math.BigDecimal;

public class IncomeDetails
{
    private BigDecimal maxIncome;
    private BigDecimal minIncome;
    private BigDecimal avgIncome;   
    
    public IncomeDetails(BigDecimal maxIncome, BigDecimal minIncome, BigDecimal avgIncome)
    {
        this.maxIncome = maxIncome;
        this.minIncome = minIncome;
        this.avgIncome = avgIncome;
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

/***************************************************DEBUG************************************************** */
    public String toString()
    {
        return "Max income: " + maxIncome + "\n" +
                "Min income: " + minIncome + "\n" +
                "Avg income: " + avgIncome;
    }
}
