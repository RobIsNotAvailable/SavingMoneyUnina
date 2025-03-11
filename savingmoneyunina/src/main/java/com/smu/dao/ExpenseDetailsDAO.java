package com.smu.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.smu.model.ExpenseDetails;
import com.smu.model.PaymentCard;

public class ExpenseDetailsDAO extends DAOconnection
{
    public static ExpenseDetails get(PaymentCard card, LocalDate date)
    {
        String sql = "select max_expense, min_expense, avg_expense, total_expense from get_card_monthly_expense_details(?, ?)";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, card.getCardNumber());
            ps.setDate(2, java.sql.Date.valueOf(date));
            
            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                BigDecimal maxExpense = rs.getBigDecimal("max_expense");
                BigDecimal minExpense = rs.getBigDecimal("min_expense");
                BigDecimal avgExpense = rs.getBigDecimal("avg_expense");
                BigDecimal totalExpense = rs.getBigDecimal("total_expense");


                return new ExpenseDetails(maxExpense, minExpense, avgExpense, totalExpense);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }   
}
