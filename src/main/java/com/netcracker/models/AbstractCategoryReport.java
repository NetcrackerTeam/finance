package com.netcracker.models;

import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CreditStatusPaid;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;


public abstract class AbstractCategoryReport {
        private BigInteger abstractCategoryReportId;
        private double amount;
        private BigInteger userReference;

        protected static abstract class BaseBuilder<T extends AbstractCategoryReport, B extends BaseBuilder> {
            protected T actualClass;
            protected B actualClassBuilder;

            protected abstract T getActual();

            protected abstract B getActualBuilder();

            protected BaseBuilder() {
                actualClass = getActual();
                actualClassBuilder = getActualBuilder();
            }

            public B abstractCategoryReportId(BigInteger AbstractCategoryReportId) {
                actualClass.setAbstractCategoryReportId(AbstractCategoryReportId);
                return actualClassBuilder;
            }

            public B userReference(BigInteger userReference) {
                actualClass.setUserReference(userReference);
                return actualClassBuilder;
            }

            public B amount(double amount) {
                actualClass.setAmount(amount);
                return actualClassBuilder;
            }

            public T build() {
                return actualClass;
            }
        }

    public BigInteger getAbstractCategoryReportId() {
        return abstractCategoryReportId;
    }

    public void setAbstractCategoryReportId(BigInteger abstractCategoryReportId) {
        this.abstractCategoryReportId = abstractCategoryReportId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BigInteger getUserReference() {
        return userReference;
    }

    public void setUserReference(BigInteger userReference) {
        this.userReference = userReference;
    }
}
