package com.netcracker.dao;

import com.netcracker.models.Debt;

import java.math.BigInteger;
import java.util.Date;

public interface CreditDeptDao {

    /**
     * Change starting debt date of personal credit.
     *
     * @param id   debt id
     * @param date new starting debt date
     */
    void updatePersonalDebtDateFrom(BigInteger id, Date date);

    /**
     * Change starting debt date of family credit.
     *
     * @param id   debt id
     * @param date new starting debt date
     */
    void updateFamilyDebtDateFrom(BigInteger id, Date date);

    /**
     * Change ending debt date of personal credit.
     *
     * @param id   debt id
     * @param date new ending debt date
     */
    void updatePersonalDebtDateTo(BigInteger id, Date date);

    /**
     * Change ending debt date of family credit.
     *
     * @param id   debt id
     * @param date new ending debt date
     */
    void updateFamilyDebtDateTo(BigInteger id, Date date);

    /**
     * Change debt total amount of personal credit.
     *
     * @param id     debt id
     * @param amount new debt amount
     */
    void updatePersonalDebtAmount(BigInteger id, double amount);

    /**
     * Change debt total amount of family credit.
     *
     * @param id     debt id
     * @param amount new debt amount
     */
    void updateFamilyDebtAmount(BigInteger id, double amount);

    String UPDATE_DEBT_DATE_FROM_QUERY = "UPDATE ATTRIBUTES\n" +
            "    SET DATE_VALUE = ? /*NEW DATE FROM*/\n" +
            "    WHERE ATTR_ID = 44 /*DATE FROM ATTRIBUTE ID*/\n" +
            "    AND OBJECT_ID = ? /*DEBT ID*/";

    String UPDATE_DEBT_DATE_TO_QUERY = "UPDATE ATTRIBUTES\n" +
            "    SET DATE_VALUE = ? /*NEW DATE TO PAYED*/\n" +
            "    WHERE ATTR_ID = 45 /*DATE TO ATTRIBUTE ID*/\n" +
            "    AND OBJECT_ID = ? /*DEBT ID*/";

    String UPDATE_DEBT_AMOUNT_QUERY = "UPDATE ATTRIBUTES\n" +
            "    SET VALUE = ? /*NEW AMOUNT */\n" +
            "    WHERE ATTR_ID = 46 /*AMOUNT ATTRIBUTE ID*/\n" +
            "    AND OBJECT_ID = ? /*DEBT ID*/";
}
