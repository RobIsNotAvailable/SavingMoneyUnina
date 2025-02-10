package com.smu.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction 
{

    public enum Direction 
    {
        income, expense;
    }

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

    // public void sortInCategories() 
    // {
    //     Boolean matched = false;
    //     for (Category category : getUser().getCategories()) 
    //     {
    //         matched |= insertIfMatches(category);
    //     }
    //     if (!matched)
    //     {
    //         getUser().getCategoryOtherOfUser().addTransaction(this);
    //     }
    // }

    public Boolean insertIfMatches(Category category)
    {
        for (String keyword : category.getKeywords())
        {
            if (description.toLowerCase().contains(keyword.toLowerCase()))
            {
                category.addTransaction(this);
                return true;
            }
        }
        return false;
    }

    public String toString() 
    {
        return "Transaction: " + amount + " " + direction + " " + description + " " + date + " Card: " + card.getCardNumber();
    }
}

