package com.smu.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.smu.model.Category;
import com.smu.model.PaymentCard;
import com.smu.model.Transaction;
import com.smu.model.Transaction.Direction;
import com.smu.model.User;

public class CategoryDAO implements DAOconnection
{
    public static Category get(String name, String creatorUsername)
    {
        String sql = "SELECT * FROM category WHERE name = ? AND creator_username = ?";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, creatorUsername);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                User creator = UserDAO.get(creatorUsername);
                
                Category category = new Category(name, creator);

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

        List<Transaction> transactions = new ArrayList<>();

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, category.getName());
            ps.setString(2, category.getCreator().getUsername());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                BigDecimal amount = rs.getBigDecimal("amount");
                String description = rs.getString("description");
                LocalDate date = rs.getDate("date").toLocalDate();
                Direction direction = Direction.valueOf(rs.getString("direction"));
                PaymentCard card = PaymentCardDAO.get(rs.getString("card_number"));
                Long id = rs.getLong("id");

                Transaction transaction = new Transaction(amount, description, date, direction, card, id);
                transactions.add(transaction);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return transactions;
    }

    public static List<String> getKeywords(Category category)
    {
        String sql = "SELECT * FROM keyword WHERE category_id = (SELECT id FROM category WHERE name = ? AND creator_username = ?)";

        List<String> keywords = new ArrayList<>();
        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, category.getName());
            ps.setString(2, category.getCreator().getUsername());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                String keyword = rs.getString("keyword");
                keywords.add(keyword);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return keywords;
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
