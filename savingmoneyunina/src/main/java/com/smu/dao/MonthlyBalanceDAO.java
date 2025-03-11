package com.smu.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.smu.model.MonthlyBalance;
import com.smu.model.PaymentCard;

public class MonthlyBalanceDAO extends DAOconnection
{
    public static MonthlyBalance get(PaymentCard card, LocalDate date)
    {
        String sql = "select initial_balance, final_balance FROM get_card_monthly_balance(?, ?)";
        date = date.withDayOfMonth(1);

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, card.getCardNumber());
            ps.setDate(2, java.sql.Date.valueOf(date));
            
            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                BigDecimal initialBalance = rs.getBigDecimal("initial_balance");
                BigDecimal finalBalance = rs.getBigDecimal("final_balance");;
                
                return new MonthlyBalance(initialBalance, finalBalance);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
