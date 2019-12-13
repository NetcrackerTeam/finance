package com.netcracker;

import com.netcracker.models.*;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AssertUtils {
    private static final Logger logger = Logger.getLogger(AssertUtils.class);

    public static void assertAutoOperationIncome(AutoOperationIncome expAO, AutoOperationIncome actualAO) {
        assertEquals(expAO.getId(), actualAO.getId());
        assertEquals(expAO.getUserId(), actualAO.getUserId());
        assertEquals(expAO.getCategoryIncome(), actualAO.getCategoryIncome());
        assertEquals(expAO.getAmount(), actualAO.getAmount());
        assertEquals(expAO.getDate(), actualAO.getDate());
        assertEquals(expAO.getDayOfMonth(), actualAO.getDayOfMonth());
    }

    public static void assertAutoOperationExpense(AutoOperationExpense expAO, AutoOperationExpense actualAO) {
        assertEquals(expAO.getId(), actualAO.getId());
        assertEquals(expAO.getUserId(), actualAO.getUserId());
        assertEquals(expAO.getCategoryExpense(), actualAO.getCategoryExpense());
        assertEquals(expAO.getAmount(), actualAO.getAmount());
        assertEquals(expAO.getDate(), actualAO.getDate());
        assertEquals(expAO.getDayOfMonth(), actualAO.getDayOfMonth());
    }

    public static void assertCreditOperation(CreditOperation expCO, CreditOperation actualCO) {
        assertEquals(expCO.getCreditOperationId(), actualCO.getCreditOperationId());
        assertEquals(expCO.getAmount(), actualCO.getAmount());
        assertEquals(expCO.getDate(), actualCO.getDate());
    }

    public static void assertOperationIncome(AccountIncome expOperation, AccountIncome actualOperation) {
        assertEquals(expOperation.getId(), actualOperation.getId());
        assertEquals(expOperation.getUserId(), actualOperation.getUserId());
        assertEquals(expOperation.getCategoryIncome(), actualOperation.getCategoryIncome());
        assertEquals(expOperation.getAmount(), actualOperation.getAmount());
        assertEquals(expOperation.getDate(), actualOperation.getDate());
    }

    public static void assertOperationExpense(AccountExpense expOperation, AccountExpense actualOperation) {
        assertEquals(expOperation.getId(), actualOperation.getId());
        assertEquals(expOperation.getUserId(), actualOperation.getUserId());
        assertEquals(expOperation.getCategoryExpense(), actualOperation.getCategoryExpense());
        assertEquals(expOperation.getAmount(), actualOperation.getAmount());
        assertEquals(expOperation.getDate(), actualOperation.getDate());
    }

    public static void assertOperationsCollections(List<AbstractAccountOperation> expectedCollection,
                                                   List<AbstractAccountOperation> actualCollection) {
        expectedCollection.sort(Comparator.comparing(AbstractAccountOperation::getId));
        actualCollection.sort(Comparator.comparing(AbstractAccountOperation::getId));

        if (expectedCollection.size() == actualCollection.size()) {
            for (int i = 0; i < expectedCollection.size(); i ++) {
                if (expectedCollection.get(i) instanceof AccountIncome) {
                    AssertUtils.assertOperationIncome((AccountIncome) expectedCollection.get(i),
                            (AccountIncome) actualCollection.get(i));
                }
                if (expectedCollection.get(i) instanceof AccountExpense) {
                    AssertUtils.assertOperationExpense((AccountExpense) expectedCollection.get(i),
                            (AccountExpense) actualCollection.get(i));
                }
            }
        } else logger.error("List sizes are not equal");
    }

    public static void assertCreditOperationsCollections(List<CreditOperation> expectedCollection,
                                                   List<CreditOperation> actualCollection) {
        expectedCollection.sort(Comparator.comparing(CreditOperation::getCreditOperationId));
        actualCollection.sort(Comparator.comparing(CreditOperation::getCreditOperationId));

        if (expectedCollection.size() == actualCollection.size()) {
            for (int i = 0; i < expectedCollection.size(); i++) {
                AssertUtils.assertCreditOperation(expectedCollection.get(i), actualCollection.get(i));
            }
        } else logger.error("List sizes are not equal");
    }

    public static void assertAutoOperationsCollections(List<AbstractAutoOperation> expectedCollection,
                                                       List<AbstractAutoOperation> actualCollection) {
        expectedCollection.sort(Comparator.comparing(AbstractAccountOperation::getId));
        actualCollection.sort(Comparator.comparing(AbstractAccountOperation::getId));

        if (expectedCollection.size() == actualCollection.size()) {
            for (int i = 0; i < expectedCollection.size(); i ++) {
                if (expectedCollection.get(i) instanceof AutoOperationIncome) {
                    AssertUtils.assertAutoOperationIncome((AutoOperationIncome) expectedCollection.get(i),
                            (AutoOperationIncome) actualCollection.get(i));
                }
                if (expectedCollection.get(i) instanceof AutoOperationExpense) {
                    AssertUtils.assertAutoOperationExpense((AutoOperationExpense) expectedCollection.get(i),
                            (AutoOperationExpense) actualCollection.get(i));
                }
            }
        } else logger.error("List sizes are not equal");
    }

    public static Date stringToDate(String stringToParse) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(stringToParse);
    }

    public static Integer getCurrentSequenceId(JdbcTemplate jdbcTemplate) {
        String GET_CURRENT_SEQUENCE_ID = "select last_number from user_sequences where sequence_name = 'OBJECTS_ID_S'";
        int newId = jdbcTemplate.queryForObject(GET_CURRENT_SEQUENCE_ID, Integer.class);
        return newId++;
    }
}
