package com.netcracker.dao.impl;

import com.netcracker.dao.CreditOperationDao;
import com.netcracker.dao.impl.mapper.CreditOperationMapper;
import com.netcracker.models.CreditOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

@Component
public class CreditOperationDaoImpl implements CreditOperationDao {
    protected JdbcTemplate jdbcTemplate;


    @Autowired
    public CreditOperationDaoImpl (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public CreditOperation getCreditOperationPersonal(BigInteger creditOperationId) {
        return jdbcTemplate.queryForObject(GET_CREDIT_OPERATION_PERSONAL, new Object[]{new BigDecimal(creditOperationId)},
                new CreditOperationMapper());
    }

    @Override
    public CreditOperation getCreditOperationFamily(BigInteger creditOperationId) {
        return jdbcTemplate.queryForObject(GET_CREDIT_OPERATION_FAMILY, new Object[]{new BigDecimal(creditOperationId)},
                new CreditOperationMapper());
    }

    @Override
    public CreditOperation createFamilyCreditOperation(CreditOperation creditOperation, BigInteger creditFamilyAccountId,
                                                       BigInteger userId) {
        jdbcTemplate.update(CREATE_CREDIT_OPERATION_FAMILY, creditOperation.getAmount().toString(), creditOperation.getDate(),
                new BigDecimal(creditFamilyAccountId), new BigDecimal(userId));
        return creditOperation;
    }

    @Override
    public CreditOperation createPersonalCreditOperation(CreditOperation creditOperation, BigInteger creditPersonalAccountId) {
        jdbcTemplate.update(CREATE_CREDIT_OPERATION_PERSONAL, creditOperation.getAmount().toString(),
                creditOperation.getDate(), new BigDecimal(creditPersonalAccountId));
        return creditOperation;
    }

    @Override
    public Collection<CreditOperation> getAllCreditOperationsByCreditFamilyId(BigInteger creditFamilyAccountId) {
        return jdbcTemplate.query(GET_ALL_CREDIT_OPERATIONS_FAMILY, new Object[]{new BigDecimal(creditFamilyAccountId)},
                new CreditOperationMapper());
    }

    @Override
    public Collection<CreditOperation> getAllCreditOperationsByCreditPersonalId(BigInteger creditPersonalAccountId) {
        return jdbcTemplate.query(GET_ALL_CREDIT_OPERATIONS_PERSONAL, new Object[]{new BigDecimal(creditPersonalAccountId)},
                new CreditOperationMapper());
    }

    @Override
    public void deleteCreditOperation(BigInteger creditOperationId) {
        jdbcTemplate.update(DELETE_FROM_OBJECTS, new BigDecimal(creditOperationId));
        jdbcTemplate.update(DELETE_FROM_ATTRIBUTES, new BigDecimal(creditOperationId));
        jdbcTemplate.update(DELETE_FROM_OBJREFERENCE, new BigDecimal(creditOperationId));
    }
}
