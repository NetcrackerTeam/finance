package com.netcracker.models;

public enum CategoryIncome {
    DEFAULT("DEFAULT");

    private String categoryName;

    CategoryIncome(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
