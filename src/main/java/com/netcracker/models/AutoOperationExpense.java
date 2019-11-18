package com.netcracker.models;

public class AutoOperationExpense extends AbstractAutoOperation {
    private CategoryExpense categoryExpense;

    public CategoryExpense getCategoryExpense() {
        return categoryExpense;
    }

    public void setCategoryExpense(CategoryExpense categoryExpense) {
        this.categoryExpense = categoryExpense;
    }
}
