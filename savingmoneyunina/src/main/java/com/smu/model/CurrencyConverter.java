package com.smu.model;

import java.math.BigDecimal;

public class CurrencyConverter 
{
    public static BigDecimal eurToUsd = new BigDecimal("1.06");
    
    public static BigDecimal convertEurToUsd(BigDecimal amount)
    {
        return amount.multiply(eurToUsd);
    }
}
