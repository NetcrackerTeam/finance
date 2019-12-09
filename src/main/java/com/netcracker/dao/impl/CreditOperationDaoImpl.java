package com.netcracker.dao.impl;

import com.netcracker.dao.CreditOperationDao;
import com.netcracker.dao.impl.mapper.CreditOperationMapper;
import com.netcracker.models.CreditOperation;
import com.netcracker.utils.ObjectsCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

@Repository
public class CreditOperationDaoImpl implements CreditOperationDao {
    private JdbcTemplate jdbcTemplate;

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
    public CreditOperation createFamilyCreditOperation(long amount, Date date, BigInteger creditFamilyAccountId,
                                                       BigInteger userId) {
        BigInteger objectIdInteger = ObjectsCreator.createObject(family_object_type_id_1, family_name_2,
                jdbcTemplate, CREATE_OBJECT_CREDIT_OPERATION);
        BigDecimal objectId = new BigDecimal(objectIdInteger);

        jdbcTemplate.update(CREATE_CREDIT_OPERATION_FAMILY, objectId, amount, objectId, date, objectId,
                new BigDecimal(creditFamilyAccountId), objectId, new BigDecimal(userId));

        return getCreditOperationFamily(objectIdInteger);
    }

    @Override
    public CreditOperation createPersonalCreditOperation(long amount, Date date, BigInteger creditPersonalAccountId) {
        BigInteger objectIdInteger = ObjectsCreator.createObject(personal_object_type_id_1, personal_name_2,
                jdbcTemplate, CREATE_OBJECT_CREDIT_OPERATION);
        BigDecimal objectId = new BigDecimal(objectIdInteger);

        jdbcTemplate.update(CREATE_CREDIT_OPERATION_PERSONAL, objectId, amount, objectId, date, objectId,
                new BigDecimal(creditPersonalAccountId));

        return getCreditOperationPersonal(objectIdInteger);
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
    }

}
