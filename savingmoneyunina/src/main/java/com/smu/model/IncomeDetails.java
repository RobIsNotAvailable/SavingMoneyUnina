package com.smu.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class IncomeDetails
{
    private BigDecimal maxIncome = BigDecimal.valueOf(0.00);
    private BigDecimal minIncome = BigDecimal.valueOf(0.00);
    private BigDecimal avgIncome = BigDecimal.valueOf(0.00);
    private BigDecimal totalIncome = BigDecimal.valueOf(0.00);
    
    public IncomeDetails(BigDecimal maxIncome, BigDecimal minIncome, BigDecimal avgIncome, BigDecimal totalIncome)
    {
        if (maxIncome != null)
        {
            this.maxIncome = maxIncome.setScale(2);
            this.minIncome = minIncome.setScale(2);
            this.avgIncome = avgIncome.setScale(2, RoundingMode.HALF_UP);
            this.totalIncome = totalIncome.setScale(2);
        }
    }

    /************************************************GETTERS****************************************************** */

    public BigDecimal getMaxIncome() { return maxIncome; }

    public BigDecimal getMinIncome() { return minIncome; }

    public BigDecimal getAvgIncome() { return avgIncome; }

    public BigDecimal getTotalIncome() { return totalIncome; }
    
    /***************************************************DEBUG************************************************** */
    public String toString()
    {
        return "Max income: " + maxIncome + "\n" +
                "Min income: " + minIncome + "\n" +
                "Avg income: " + avgIncome;
    }
}
