package com.smu.model;

import java.time.LocalDate;
import java.util.List;

public class TransactionFilter 
{
    LocalDate initialDate;
    LocalDate finalDate;
    
    Transaction.Direction direction;

    Category category;

    public TransactionFilter(LocalDate initialDate, LocalDate finalDate, Transaction.Direction direction, Category category) 
    {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.direction = direction;
        this.category = category;
    }

    public List<Transaction> filter(List<Transaction> transactions)
    {
        List<Transaction> filteredTransactions = transactions;

        if(category != null)
            filteredTransactions = category.getTransactions();

        if(initialDate != null)
            filteredTransactions.removeIf(t -> t.getDate().isBefore(initialDate));
        
        if(finalDate != null)
            filteredTransactions.removeIf(t -> t.getDate().isAfter(finalDate));

        if(direction != null)
            filteredTransactions.removeIf(t -> t.getDirection() != direction);

        return filteredTransactions;
    }

}
