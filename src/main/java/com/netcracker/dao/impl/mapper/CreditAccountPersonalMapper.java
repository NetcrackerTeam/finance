package com.netcracker.dao.impl.mapper;

import com.netcracker.models.AbstractCreditAccount;
import com.netcracker.models.Debt;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.enums.CreditStatusPaid;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditAccountPersonalMapper implements RowMapper<PersonalCreditAccount> {

    @Override
    public PersonalCreditAccount mapRow(ResultSet resultSet, int i) throws SQLException {
        CreditDebtMapper debtMapper = new CreditDebtMapper();
        Debt debt = debtMapper.mapRow(resultSet, i);

        AbstractCreditAccount personalCreditAccount =
                new PersonalCreditAccount.Builder()
                        .creditId(resultSet.getBigDecimal("credit_id").toBigInteger())
                        .name(resultSet.getString("name"))
                        .amount(resultSet.getDouble("amount"))
                        .paidAmount(resultSet.getDouble("paid"))
                        .date(resultSet.getDate("date_cr").toLocalDate())
                        .creditRate(resultSet.getDouble("credit_rate"))
                        .dateTo(resultSet.getDate("date_to").toLocalDate())
                        .monthDay(resultSet.getInt("month_day"))
                        .isCommodity(resultSet.getBoolean("commodity"))
                        .debtCredit(debt)
                        .isPaid(CreditStatusPaid.getStatusByKey(resultSet.getBigDecimal("is_paid").toBigInteger()))
                        .build();

        return (PersonalCreditAccount) personalCreditAccount;
    }
}
