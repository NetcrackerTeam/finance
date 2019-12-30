package com.netcracker.models;

import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.ReportCategoryExpense;


public class CategoryExpenseReport extends AbstractCategoryReport {
    private ReportCategoryExpense categoryExpense;

    public static class Builder extends BaseBuilder<CategoryExpenseReport, Builder> {


        public Builder categoryExpense(ReportCategoryExpense expense) {
            actualClass.setCategoryExpense(expense);
            return this;
        }

        @Override
        protected CategoryExpenseReport getActual() {
            return new CategoryExpenseReport();
        }

        @Override
        protected Builder getActualBuilder() {
            return this;
        }
    }

    public ReportCategoryExpense getCategoryExpense() {
        return categoryExpense;
    }

    public void setCategoryExpense(ReportCategoryExpense categoryExpense) {
        this.categoryExpense = categoryExpense;
    }
}
