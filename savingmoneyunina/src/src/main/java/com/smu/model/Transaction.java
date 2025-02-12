package com.smu.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.smu.dao.TransactionDAO;

public class Transaction 
{

    public enum Direction 
    {
        income, expense;
    }

    private Long id;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private Direction direction;
    private PaymentCard card;
    
    public Transaction(BigDecimal amount, String description, LocalDate date, Direction direction, PaymentCard card) 
    {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.direction = direction;
        this.card = card;
        this.id = TransactionDAO.generateId();
    }

    //constructor for the DAO 
    public Transaction(BigDecimal amount, String description, LocalDate date, Direction direction, PaymentCard card, Long id) 
    {
        this(amount, description, date, direction, card);
        this.id = id;
    }

    /************************************************GETTERS****************************************************** */

    public Long getId() 
    {
        return id;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }

    public String getDescription() 
    {
        return description;
    }

    public LocalDate getDate() 
    {
        return date;
    }

    public Direction getDirection() 
    {
        return direction;
    }

    public PaymentCard getCard() 
    {
        return card;
    }

    public User getUser() 
    {
        return card.getOwner();
    }

    public List <Category> getCategories() 
    {
        return TransactionDAO.getCategories(this);
    }
/************************************************METHODS****************************************************** */

    public void insert()
    {
        TransactionDAO.insert(this);
    }

    public void sortInCategories() 
    {
        Boolean matched = false;
        
        for (Category category : getUserCategories()) 
        {
            matched |= category.insertIfMatches(this);
        }
        if (!matched)
        {
            getUserOtherCategory().insertTransaction(this);
        }
    }

    public List<Category> getUserCategories()
    {
        return getUser().getCategories();
    }

    public Category getUserOtherCategory()
    {
        return getUser().getCategory("Other");
    }

    public String toString() 
    {
        return "Transaction("+ id +"): " + amount + " " + direction + " " + description + " " + date + " Card: " + card.getCardNumber();
    }
}

