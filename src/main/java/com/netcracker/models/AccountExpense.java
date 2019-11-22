package com.netcracker.models;

import com.netcracker.models.enums.CategoryExpense;

public class AccountExpense extends AbstractAccountOperation {
    private CategoryExpense categoryExpense;


    public static class Builder extends BaseBuilder<AccountExpense, Builder> {


        public Builder categoryIncome(CategoryExpense expense) {
            actualClass.setCategoryExpense(expense);
            return this;
        }

        @Override
        protected AccountExpense getActual() {
            return new AccountExpense();
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
