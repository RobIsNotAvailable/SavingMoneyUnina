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
import com.smu.model.Category;
import com.smu.model.PaymentCard;
import com.smu.model.Transaction;

public class TransactionDAO 
{
    static Connection conn = DbConnection.getConnection();

    public static List<Transaction> getByCard(PaymentCard card)
    {
        String cardNumber = card.getCardNumber();
        String sql = "SELECT * FROM transaction WHERE card_number = ? ORDER BY date DESC";

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
                Long id = rs.getLong("id");

                transactions.add(new Transaction(amount, description, date, direction, card, id));
            }

            return transactions;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Transaction> getFiltered(PaymentCard card, Transaction.Direction direction)
    {
        String cardNumber = card.getCardNumber();
        String sql = "SELECT * FROM transaction WHERE card_number = ? AND direction = ?::direction ORDER BY date DESC";

        List<Transaction> transactions = new ArrayList<Transaction>();
        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cardNumber);
            ps.setString(2, direction.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                long id = rs.getLong("id");
                BigDecimal amount = rs.getBigDecimal("amount");
                String description = rs.getString("description");
                LocalDate date = rs.getDate("date").toLocalDate();

                transactions.add(new Transaction(amount, description, date, direction, card, id));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return transactions;
    }

    public static List<Transaction> getFiltered(PaymentCard card, LocalDate beginDate, LocalDate endDate)
    {
        String cardNumber = card.getCardNumber();
        String sql = "SELECT * FROM transaction WHERE card_number = ? AND date >= ? AND date <= ? ORDER BY date DESC";

        List<Transaction> transactions = new ArrayList<Transaction>();
        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cardNumber);
            ps.setDate(2, java.sql.Date.valueOf(beginDate));
            ps.setDate(3, java.sql.Date.valueOf(endDate));
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                long id = rs.getLong("id");
                BigDecimal amount = rs.getBigDecimal("amount");
                String description = rs.getString("description");
                LocalDate date = rs.getDate("date").toLocalDate();
                Transaction.Direction direction = Transaction.Direction.valueOf(rs.getString("direction"));

                transactions.add(new Transaction(amount, description, date, direction, card, id));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return transactions;
    }

    public static List<Transaction> getFiltered(PaymentCard card, LocalDate beginDate, LocalDate endDate, Transaction.Direction direction)
    {
        String cardNumber = card.getCardNumber();
        String sql = "SELECT * FROM transaction WHERE card_number = ? AND date >= ? AND date <= ? AND direction = ?::direction ORDER BY date DESC";

        List<Transaction> transactions = new ArrayList<Transaction>();
        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cardNumber);
            ps.setDate(2, java.sql.Date.valueOf(beginDate));
            ps.setDate(3, java.sql.Date.valueOf(endDate));
            ps.setString(4, direction.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                long id = rs.getLong("id");
                BigDecimal amount = rs.getBigDecimal("amount");
                String description = rs.getString("description");
                LocalDate date = rs.getDate("date").toLocalDate();

                transactions.add(new Transaction(amount, description, date, direction, card, id));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return transactions;
    }

    public static List<Transaction> getFiltered(PaymentCard card, LocalDate beginDate, LocalDate endDate, Category category)
    {
        String cardNumber = card.getCardNumber();
        String sql = """
        SELECT * FROM transaction WHERE card_number = ? AND date >= ? AND date <= ? AND 
        id IN (SELECT transaction_id FROM transaction_category WHERE category_id = (SELECT id FROM category WHERE name = ? AND creator_username = ?)) ORDER BY date DESC
        """;
                

        List<Transaction> transactions = new ArrayList<Transaction>();
        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cardNumber);
            ps.setDate(2, java.sql.Date.valueOf(beginDate));
            ps.setDate(3, java.sql.Date.valueOf(endDate));
            ps.setString(4, category.getName());
            ps.setString(5, category.getCreator().getUsername());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                long id = rs.getLong("id");
                BigDecimal amount = rs.getBigDecimal("amount");
                String description = rs.getString("description");
                LocalDate date = rs.getDate("date").toLocalDate();
                Transaction.Direction direction = Transaction.Direction.valueOf(rs.getString("direction"));

                transactions.add(new Transaction(amount, description, date, direction, card, id));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return transactions;
    }

    public static List<Transaction> getFiltered(PaymentCard card, LocalDate beginDate, LocalDate endDate, Category category, Transaction.Direction direction)
    {
        String cardNumber = card.getCardNumber();
        String sql = """
        SELECT * FROM transaction WHERE card_number = ? AND date >= ? AND date <= ? AND 
        id IN (SELECT transaction_id FROM transaction_category WHERE category_id = (SELECT id FROM category WHERE name = ? AND creator_username = ?)) 
        AND direction = ?::direction ORDER BY date DESC
        """;

        List<Transaction> transactions = new ArrayList<Transaction>();
        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cardNumber);
            ps.setDate(2, java.sql.Date.valueOf(beginDate));
            ps.setDate(3, java.sql.Date.valueOf(endDate));
            ps.setString(4, category.getName());
            ps.setString(5, category.getCreator().getUsername());
            ps.setString(6, direction.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                long id = rs.getLong("id");
                BigDecimal amount = rs.getBigDecimal("amount");
                String description = rs.getString("description");
                LocalDate date = rs.getDate("date").toLocalDate();

                transactions.add(new Transaction(amount, description, date, direction, card, id));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return transactions;
    }

    public static Boolean insert(Transaction transaction)
    {
        String sql = "INSERT INTO transaction (id, amount, description, date, direction, card_number) VALUES (?, ?, ?, ?, ?::direction, ?) ";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setLong(1, transaction.getId());
            ps.setBigDecimal(2, transaction.getAmount());
            ps.setString(3, transaction.getDescription());
            ps.setDate(4, java.sql.Date.valueOf(transaction.getDate()));
            ps.setString(5, transaction.getDirection().toString());
            ps.setString(6, transaction.getCard().getCardNumber());
            
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
