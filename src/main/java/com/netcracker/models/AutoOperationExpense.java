package com.netcracker.models;

public class AutoOperationExpense extends AbstractAutoOperation {
    private CategoryExpense categoryExpense;



    public static class Builder extends BaseBuilder<AutoOperationExpense, Builder> {

        public Builder categoryIncome(CategoryExpense expense) {
            actualClass.setCategoryExpense(expense);
            return this;
        }

        @Override
        protected AutoOperationExpense getActual() {
            return new AutoOperationExpense();
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
