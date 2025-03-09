package com.smu.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.smu.databaseConnection.DbConnection;
import com.smu.model.IncomeDetails;
import com.smu.model.PaymentCard;

public class IncomeDetailsDAO
{
    static Connection conn = DbConnection.getConnection();

    public static IncomeDetails get(PaymentCard card, LocalDate date)
    {
        String sql = "select max_income, min_income, avg_income, total_income from get_card_monthly_income_details(?,?)";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, card.getCardNumber());
            ps.setDate(2, java.sql.Date.valueOf(date));
            
            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                BigDecimal maxIncome = rs.getBigDecimal("max_income");
                BigDecimal minIncome = rs.getBigDecimal("min_income");
                BigDecimal avgIncome = rs.getBigDecimal("avg_income");
                BigDecimal totalIncome = rs.getBigDecimal("total_income");
                
                return new IncomeDetails(maxIncome, minIncome, avgIncome, totalIncome);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}