package com.smu.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import java.util.ArrayList;

import com.smu.databaseConnection.DbConnection;
import com.smu.model.PaymentCard;
import com.smu.model.Transaction;

public class TransactionDAO 
{
    static Connection conn = DbConnection.getConnection();

    public static List<Transaction> getByCard(PaymentCard card)
    {
        String cardNumber = card.getCardNumber();
        String sql = "SELECT * FROM transaction WHERE card_number = ?";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cardNumber);
            ResultSet rs = ps.executeQuery();

            List<Transaction> transactions = new ArrayList<Transaction>();

            while (rs.next())
            {
                BigDecimal amount = rs.getBigDecimal("amount");
                String description = rs.getString("description");
                LocalDate date = rs.getDate("date").toLocalDate();
                Transaction.Direction direction = Transaction.Direction.valueOf(rs.getString("direction"));

                transactions.add(new Transaction(amount, description, date, direction, card));
            }

            return transactions;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static Boolean insert(Transaction transaction)
    {
        String sql = "INSERT INTO transaction (id, amount, description, date, direction, card_number) VALUES (?,?,?,?,?::direction,?) ";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setBigDecimal(1, transaction.getAmount());
            ps.setString(2, transaction.getDescription());
            ps.setDate(3, java.sql.Date.valueOf(transaction.getDate()));
            ps.setString(4, transaction.getDirection().toString());
            ps.setString(5, transaction.getCard().getCardNumber());

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
   
    public static Long generateId()
    {
        String sql = "SELECT MAX(id) FROM transaction";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return rs.getLong(1) + 1;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0L;
    }
}
