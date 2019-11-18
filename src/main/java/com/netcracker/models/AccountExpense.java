package com.netcracker.models;

public class AccountExpense extends AbstractAccountOperation {
    private CategoryExpense categoryExpence;

    public CategoryExpense getCategoryExpence() {
        return categoryExpence;
    }

    public void setCategoryExpence(CategoryExpense categoryExpence) {
        this.categoryExpence = categoryExpence;
    }
}
