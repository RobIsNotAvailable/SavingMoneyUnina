package com.smu.model;

import java.math.BigDecimal;

public class MonthlyBalance
{
    private BigDecimal initialBalance = BigDecimal.valueOf(0.00);
    private BigDecimal finalBalance = BigDecimal.valueOf(0.00);

    public MonthlyBalance(BigDecimal initialBalance, BigDecimal finalBalance)
    {
        if (initialBalance != null)
        {
            this.initialBalance = initialBalance.setScale(2);
            this.finalBalance = finalBalance.setScale(2);
        }
    }

    /************************************************GETTERS****************************************************** */

    public BigDecimal getInitialBalance() { return initialBalance; }

    public BigDecimal getFinalBalance() { return finalBalance; }
        
}
