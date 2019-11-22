package com.netcracker.dao.impl.mapper;

import com.netcracker.models.AbstractCreditAccount;
import com.netcracker.models.Debt;
import com.netcracker.models.FamilyCreditAccount;
import com.netcracker.models.PersonalCreditAccount;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditAccountFamilyMapper implements RowMapper<FamilyCreditAccount> {
    @Override
    public FamilyCreditAccount mapRow(ResultSet resultSet, int i) throws SQLException {

        //ToDO: waiting for debt mapper
        Debt debt = new Debt.Builder().build();

        AbstractCreditAccount personalCreditAccount =
                new FamilyCreditAccount.Builder()
                        .creditId(resultSet.getBigDecimal("credit_id").toBigInteger())
                        .name(resultSet.getString("name"))
                        .amount(Long.valueOf(resultSet.getString("amount")))
                        .paidAmount(Long.valueOf(resultSet.getString("paid")))
                        .date(resultSet.getDate("date_cr").toLocalDate())
                        .creditRate(Long.valueOf(resultSet.getString("credit_rate")))
                        .dateTo(resultSet.getDate("date_to").toLocalDate())
                        .monthDay(Integer.valueOf(resultSet.getString("month_day")))
                        .debtCredit(debt)
                        .isPaid(isPaidParser(resultSet.getString("is_paid")))
                        .build();

        return (FamilyCreditAccount) personalCreditAccount;
    }

    private boolean isPaidParser (String queryResult) {
        //ToDo: waiting for enum isPaid
        return queryResult.equals("YES");
    }
}
