package com.netcracker.models;

import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;

public class HistoryOperation extends AbstractAccountOperation {
    private CategoryIncome categoryIncome;
    private CategoryExpense categoryExpense;

    public static class Builder extends BaseBuilder<HistoryOperation, Builder> {


        public Builder categoryIncome(CategoryIncome income) {
            actualClass.setCategoryIncome(income);
            return this;
        }

        public Builder categoryExpense(CategoryExpense expense) {
            actualClass.setCategoryExpense(expense);
            return this;
        }

        @Override
        protected HistoryOperation getActual() {
            return new HistoryOperation();
        }

        @Override
        protected Builder getActualBuilder() {
            return this;
        }
    }


    public CategoryIncome getCategoryIncome() {
        return categoryIncome;
    }

    public void setCategoryIncome(CategoryIncome categoryIncome) {
        this.categoryIncome = categoryIncome;
    }

    public CategoryExpense getCategoryExpense() {
        return categoryExpense;
    }

    public void setCategoryExpense(CategoryExpense categoryExpense) {
        this.categoryExpense = categoryExpense;
    }
}
