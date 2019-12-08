package com.netcracker.models;


import com.netcracker.models.enums.CategoryIncome;


public class CategoryIncomeReport extends AbstractCategoryReport {
    private CategoryIncome categoryIncome;

    public static class Builder extends BaseBuilder<CategoryIncomeReport, Builder> {

        public Builder categoryIncome(CategoryIncome income) {
            actualClass.setCategoryIncome(income);
            return this;
        }

        @Override
        protected CategoryIncomeReport getActual() {
            return new CategoryIncomeReport();
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
}
