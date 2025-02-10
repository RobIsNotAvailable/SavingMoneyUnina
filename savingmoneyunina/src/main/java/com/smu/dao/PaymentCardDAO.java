package com.smu.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.smu.databaseConnection.DbConnection;
import com.smu.model.PaymentCard;
import com.smu.model.Transaction;
import com.smu.model.User;

public class PaymentCardDAO
{
    static Connection conn = DbConnection.getConnection();

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

    public static List<Transaction> getTransactions(PaymentCard card)
    {
        return TransactionDAO.getByCard(card);
    }
}
