package com.smu.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.smu.databaseConnection.DbConnection;
import com.smu.model.ExpenseDetails;
import com.smu.model.IncomeDetails;
import com.smu.model.PaymentCard;
import com.smu.model.Report;

public class ReportDAO 
{
    static Connection conn = DbConnection.getConnection();

    public static Report get(PaymentCard card, LocalDate date)
    {
        BigDecimal startingBalance = null;
        BigDecimal endingBalance = null;	
        String sql = "select * from get_monthly_balances(?,?)";
        date=date.withDayOfMonth(1);

        try 
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, card.getCardNumber());
            ps.setDate(2, java.sql.Date.valueOf(date));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                startingBalance = rs.getBigDecimal("starting_balance");    
                endingBalance = rs.getBigDecimal("ending_balance");    
            }

            IncomeDetails incomeDetails = IncomeDetailsDAO.get(card, date);
            ExpenseDetails expenseDetails = ExpenseDetailsDAO.get(card, date);
            return new Report(incomeDetails, expenseDetails, startingBalance, endingBalance, card, date);            
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return null;
    }
}
