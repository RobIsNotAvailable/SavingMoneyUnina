package com.smu.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class IncomeDetails
{
    private BigDecimal maxIncome = BigDecimal.ZERO;
    private BigDecimal minIncome = BigDecimal.ZERO;
    private BigDecimal avgIncome = BigDecimal.ZERO;   
    
    public IncomeDetails(BigDecimal maxIncome, BigDecimal minIncome, BigDecimal avgIncome)
    {
        if(maxIncome != null)
        { 
            this.maxIncome = maxIncome;
            this.minIncome = minIncome;
            this.avgIncome = avgIncome.setScale(2, RoundingMode.HALF_UP);
        }
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
