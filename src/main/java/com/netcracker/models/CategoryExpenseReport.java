package com.netcracker.models;

import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.ReportCategoryExpense;


public class CategoryExpenseReport extends AbstractCategoryReport {
    private CategoryExpense categoryExpense;
    private String userName;

    public static class Builder extends BaseBuilder<CategoryExpenseReport, Builder> {

        public Builder categoryExpense(CategoryExpense expense) {
            actualClass.setCategoryExpense(expense);
            return this;
        }
        public Builder userName(String userName) {
            actualClass.setUserName(userName);
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

    public CategoryExpense getCategoryExpense() {
        return categoryExpense;
    }

    public void setCategoryExpense(CategoryExpense categoryExpense) {
        this.categoryExpense = categoryExpense;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
