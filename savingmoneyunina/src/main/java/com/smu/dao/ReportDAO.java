package com.smu.dao;

import java.time.LocalDate;

import com.smu.model.ExpenseDetails;
import com.smu.model.IncomeDetails;
import com.smu.model.MonthlyBalance;
import com.smu.model.PaymentCard;
import com.smu.model.Report;

public class ReportDAO implements DAOconnection
{
    public static Report get(PaymentCard card, LocalDate date)
    {
        if(date.isBefore(PaymentCardDAO.getFirstReportDate(card)) || date.isAfter(LocalDate.now()))
        {
            return new Report(card, date);
        }

        MonthlyBalance monthlyBalance = MonthlyBalanceDAO.get(card, date);
        IncomeDetails incomeDetails = IncomeDetailsDAO.get(card, date);
        ExpenseDetails expenseDetails = ExpenseDetailsDAO.get(card, date);

        return new Report(incomeDetails, expenseDetails, monthlyBalance, card, date);
    }
}
