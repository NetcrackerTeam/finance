package com.netcracker.models;

import java.util.List;

public class AccountIncome extends AbstractAccountOperation {
    private CategoryIncome categoryIncome;

    public static class Builder extends BaseBuilder<AccountIncome, Builder> {


        public Builder categoryIncome(CategoryIncome income) {
            actualClass.setCategoryIncome(income);
            return this;
        }

        @Override
        protected AccountIncome getActual() {
            return new AccountIncome();
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
