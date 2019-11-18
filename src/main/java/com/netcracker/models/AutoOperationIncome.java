package com.netcracker.models;

public class AutoOperationIncome extends AbstractAutoOperation {
    private CategoryIncome categoryIncome;

    public CategoryIncome getCategoryIncome() {
        return categoryIncome;
    }

    public void setCategoryIncome(CategoryIncome categoryIncome) {
        this.categoryIncome = categoryIncome;
    }
}
