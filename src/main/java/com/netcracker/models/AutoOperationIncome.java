package com.netcracker.models;

import com.netcracker.models.enums.CategoryIncome;

public class AutoOperationIncome extends AbstractAutoOperation {
    private CategoryIncome categoryIncome;

    public static class Builder extends BaseBuilderAO<AutoOperationIncome, Builder> {

        public Builder categoryIncome(CategoryIncome income) {
            actualClass.setCategoryIncome(income);
            return this;
        }

        @Override
        protected AutoOperationIncome getActual() {
            return new AutoOperationIncome();
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
