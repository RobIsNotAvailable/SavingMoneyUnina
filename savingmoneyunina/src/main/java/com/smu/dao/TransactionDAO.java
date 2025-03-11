package com.smu.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import com.smu.model.Category;
import com.smu.model.PaymentCard;
import com.smu.model.Transaction;

public class TransactionDAO extends DAOconnection
{
    public static List<Transaction> getByCard(PaymentCard card)
    {
        String cardNumber = card.getCardNumber();
        String sql = "SELECT * FROM transaction WHERE card_number = ? ORDER BY date DESC, id DESC";
        List<Transaction> transactions = new ArrayList<>();
        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cardNumber);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                BigDecimal amount = rs.getBigDecimal("amount");
                String description = rs.getString("description");
                LocalDate date = rs.getDate("date").toLocalDate();
                Transaction.Direction direction = Transaction.Direction.valueOf(rs.getString("direction"));
                Long id = rs.getLong("id");

                transactions.add(new Transaction(amount, description, date, direction, card, id));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return transactions;
    }

    public static List<Category> getCategories(Transaction transaction)
    {
        String sql = "SELECT name, creator_username FROM category WHERE id IN (SELECT category_id FROM transaction_category WHERE transaction_id = ?)";

        List<Category> categories = new ArrayList<>();
        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, transaction.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                String name = rs.getString("name");
                String creatorUsername = rs.getString("creator_username");

                categories.add(CategoryDAO.get(name, creatorUsername));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return categories;
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
