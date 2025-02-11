package com.smu.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.smu.databaseConnection.DbConnection;
import com.smu.model.Category;
import com.smu.model.PaymentCard;
import com.smu.model.Transaction;
import com.smu.model.Transaction.Direction;
import com.smu.model.User;

public class CategoryDAO 
{
    //methods should never return null, but an empty list
    static Connection conn = DbConnection.getConnection();

    public static Category get(String name, String creator_username)
    {
        String sql = "SELECT * FROM category WHERE name = ? AND creator_username = ?";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, creator_username);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                String description = rs.getString("description");
                User creator = UserDAO.get(creator_username);
                
                Category category = new Category(name, description, creator);

                List<Transaction> categorizedTransactions = CategoryDAO.getTransactions(category);
                category.setTransactions(categorizedTransactions);

                List<String> keywords = CategoryDAO.getKeywords(category);
                category.setKeywords(keywords);

                return category;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();

        }

        return null;
    }

    public static List<Transaction> getTransactions(Category category)
    {
        String sql = "SELECT * FROM transaction WHERE id IN (SELECT transaction_id FROM transaction_category WHERE category_id = (SELECT id FROM category WHERE name = ? AND creator_username = ?)) ORDER BY date DESC";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, category.getName());
            ps.setString(2, category.getCreator().getUsername());
            ResultSet rs = ps.executeQuery();

            List<Transaction> transactions = new ArrayList<Transaction>();

            while (rs.next())
            {
                BigDecimal amount = rs.getBigDecimal("amount");
                String description = rs.getString("description");
                LocalDate date = rs.getDate("date").toLocalDate();
                Direction direction = Direction.valueOf(rs.getString("direction"));
                PaymentCard card = PaymentCardDAO.get(rs.getString("card_number"));

                Transaction transaction = new Transaction(amount, description, date, direction, card);
                transactions.add(transaction);
            }

            return transactions;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static List<String> getKeywords(Category category)
    {
        String sql = "SELECT * FROM keyword WHERE category_id = (SELECT id FROM category WHERE name = ? AND creator_username = ?)";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, category.getName());
            ps.setString(2, category.getCreator().getUsername());
            ResultSet rs = ps.executeQuery();

            List<String> keywords = new ArrayList<String>();

            while (rs.next())
            {
                String keyword = rs.getString("keyword");
                keywords.add(keyword);
            }

            return keywords;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static void insertTransaction(Transaction transaction, Category category)
    {
        String sql = "INSERT INTO transaction_category (transaction_id, category_id) VALUES(?,(Select id FROM category WHERE name = ? AND creator_username = ?))";
        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, transaction.getId());
            ps.setString(2, category.getName());
            ps.setString(3, category.getCreator().getUsername());

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
