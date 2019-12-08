package com.netcracker.models;

import com.netcracker.models.enums.CategoryExpense;



public class CategoryExpenseReport extends AbstractCategoryReport {
    private CategoryExpense categoryExpense;

    public static class Builder extends BaseBuilder<CategoryExpenseReport, Builder> {


        public Builder categoryExpense(CategoryExpense expense) {
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

    public CategoryExpense getCategoryExpense() {
        return categoryExpense;
    }

    public void setCategoryExpense(CategoryExpense categoryExpense) {
        this.categoryExpense = categoryExpense;
    }
}
