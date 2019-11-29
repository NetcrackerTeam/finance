package com.netcracker.dao.impl;

import com.netcracker.dao.CreditOperationDao;
import com.netcracker.dao.impl.mapper.CreditOperationMapper;
import com.netcracker.models.CreditOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collection;

@Component
public class CreditOperationDaoImpl implements CreditOperationDao {
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public CreditOperationDaoImpl (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public CreditOperation getCreditOperationPersonal(Integer creditOperationId) {
        return jdbcTemplate.queryForObject(GET_CREDIT_OPERATION_PERSONAL, new Object[]{creditOperationId},
                new CreditOperationMapper());
    }

    @Override
    public CreditOperation getCreditOperationFamily(Integer creditOperationId) {
        return jdbcTemplate.queryForObject(GET_CREDIT_OPERATION_FAMILY, new Object[]{creditOperationId},
                new CreditOperationMapper());
    }

    @Override
    public CreditOperation createFamilyCreditOperation(CreditOperation creditOperation, Integer creditFamilyAccountId,
                                                       Integer userId) {
        jdbcTemplate.update(CREATE_CREDIT_OPERATION_FAMILY, creditOperation.getAmount().toString(), creditOperation.getDate(),
                creditFamilyAccountId, userId);
        return creditOperation;
    }

    @Override
    public CreditOperation createPersonalCreditOperation(CreditOperation creditOperation, Integer creditPersonalAccountId) {
        jdbcTemplate.update(CREATE_CREDIT_OPERATION_PERSONAL, creditOperation.getAmount().toString(),
                creditOperation.getDate(), creditPersonalAccountId);
        return creditOperation;
    }

    @Override
    public Collection<CreditOperation> getAllCreditOperationsByCreditFamilyId(Integer creditFamilyAccountId) {
        return jdbcTemplate.query(GET_ALL_CREDIT_OPERATIONS_FAMILY, new Object[]{creditFamilyAccountId},
                new CreditOperationMapper());
    }

    @Override
    public Collection<CreditOperation> getAllCreditOperationsByCreditPersonalId(Integer creditPersonalAccountId) {
        return jdbcTemplate.query(GET_ALL_CREDIT_OPERATIONS_PERSONAL, new Object[]{creditPersonalAccountId},
                new CreditOperationMapper());
    }

    @Override
    public void deleteCreditOperation(Integer creditOperationId) {
        jdbcTemplate.update(DELETE_FROM_OBJECTS, creditOperationId);
        jdbcTemplate.update(DELETE_FROM_ATTRIBUTES, creditOperationId);
        jdbcTemplate.update(DELETE_FROM_OBJREFERENCE, creditOperationId);
    }
}
