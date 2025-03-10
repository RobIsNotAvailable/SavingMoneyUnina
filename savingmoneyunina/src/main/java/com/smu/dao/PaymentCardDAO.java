package com.smu.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.smu.model.PaymentCard;
import com.smu.model.Report;
import com.smu.model.Transaction;
import com.smu.model.User;

public class PaymentCardDAO implements DAOconnection
{
    public static PaymentCard get(String cardNumber)
    {
        String sql = "SELECT * FROM payment_card WHERE card_number = ?";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cardNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                String cvv = rs.getString("cvv");
                String pin = rs.getString("pin");
                LocalDate expirationDate = rs.getDate("expiration_date").toLocalDate();
                User owner = UserDAO.get(rs.getString("owner_username"));
                BigDecimal balance = rs.getBigDecimal("balance");

                return new PaymentCard(cardNumber, cvv, pin, expirationDate, owner, balance);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static Report getReport(PaymentCard card, LocalDate date)
    {
        return ReportDAO.get(card, date);
    }

    public static List <Transaction> getTransactions(PaymentCard card)
    {
        return TransactionDAO.getByCard(card);
    }

    public static BigDecimal getTotalMonthlyIncome(PaymentCard card, LocalDate date)
    {
        return IncomeDetailsDAO.get(card, date).getTotalIncome();
    }

    public static BigDecimal getTotalMonthlyExpense(PaymentCard card, LocalDate date)
    {
        return ExpenseDetailsDAO.get(card, date).getTotalExpense();
    }

    public static void update(PaymentCard card)
    {
        String sql = "UPDATE payment_card SET balance = ? WHERE card_number = ?";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, card.getBalance());
            ps.setString(2, card.getCardNumber());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static LocalDate getFirstReportDate(PaymentCard card)
    {
        String sql = "SELECT MIN(date) FROM monthly_balance where card_number = ?";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, card.getCardNumber());
            ResultSet rs = ps.executeQuery();

            
            if (rs.next())
            {
                LocalDate date = rs.getDate("min").toLocalDate();
                return date;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}   
