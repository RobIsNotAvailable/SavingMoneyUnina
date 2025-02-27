package com.smu.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverter 
{
    public static enum Currency {EUR, USD};
    
    public static BigDecimal eurToUsd = new BigDecimal("1.06");
    
    public static BigDecimal convertUsdToEur(BigDecimal usd)
    {
        return usd.divide(eurToUsd, RoundingMode.HALF_EVEN);
    }
}
