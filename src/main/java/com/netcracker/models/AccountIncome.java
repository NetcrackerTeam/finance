package com.netcracker.models;

public class AccountIncome extends AbstractAccountOperation {
    private CategoryIncome categoryIncome;

    public CategoryIncome getCategoryIncome() {
        return categoryIncome;
    }

    public void setCategoryIncome(CategoryIncome categoryIncome) {
        this.categoryIncome = categoryIncome;
    }
}
