package com.netcracker.dao.impl;

import com.netcracker.dao.CreditOperationDao;
import com.netcracker.dao.impl.mapper.CreditOperationMapper;
import com.netcracker.dao.utils.ObjectsCreator;
import com.netcracker.models.CreditOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class CreditOperationDaoImpl implements CreditOperationDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CreditOperationDaoImpl (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public CreditOperation getCreditOperationPersonal(BigInteger creditOperationId) {
        return jdbcTemplate.queryForObject(GET_CREDIT_OPERATION_PERSONAL, new Object[]{creditOperationId}, new CreditOperationMapper());
    }

    @Override
    public CreditOperation getCreditOperationFamily(BigInteger creditOperationId) {
        return jdbcTemplate.queryForObject(GET_CREDIT_OPERATION_FAMILY, new Object[]{creditOperationId}, new CreditOperationMapper());
    }

    @Override
    public CreditOperation createFamilyCreditOperation(double amount, LocalDate date, BigInteger creditFamilyAccountId,
                                                       BigInteger userId) {
        BigInteger objectId = ObjectsCreator.createObject(family_object_type_id_1, family_name_2,
                jdbcTemplate, CREATE_OBJECT_CREDIT_OPERATION);

        jdbcTemplate.update(CREATE_CREDIT_OPERATION_FAMILY, objectId, amount, objectId, Date.valueOf(date), objectId,
                creditFamilyAccountId, objectId, userId);

        return getCreditOperationFamily(objectId);
    }

    @Override
    public CreditOperation createPersonalCreditOperation(double amount, LocalDate date, BigInteger creditPersonalAccountId) {
        BigInteger objectId = ObjectsCreator.createObject(personal_object_type_id_1, personal_name_2,
                jdbcTemplate, CREATE_OBJECT_CREDIT_OPERATION);

        jdbcTemplate.update(CREATE_CREDIT_OPERATION_PERSONAL, objectId, amount, objectId, Date.valueOf(date), objectId,
                creditPersonalAccountId);

        return getCreditOperationPersonal(objectId);
    }

    @Override
    public List<CreditOperation> getAllCreditOperationsByCreditFamilyId(BigInteger creditFamilyAccountId) {
        return jdbcTemplate.query(GET_ALL_CREDIT_OPERATIONS_FAMILY, new Object[]{creditFamilyAccountId}, new CreditOperationMapper());
    }

    @Override
    public List<CreditOperation> getAllCreditOperationsByCreditPersonalId(BigInteger creditPersonalAccountId) {
        return jdbcTemplate.query(GET_ALL_CREDIT_OPERATIONS_PERSONAL, new Object[]{creditPersonalAccountId}, new CreditOperationMapper());
    }

    @Override
    public void deleteCreditOperation(BigInteger creditOperationId) {
        jdbcTemplate.update(DELETE_FROM_OBJECTS, creditOperationId);
    }

}
