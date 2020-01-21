package com.netcracker.models;

import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;

public class HistoryOperation extends AbstractAccountOperation {
    private CategoryIncome categoryIncome;
    private CategoryExpense categoryExpense;
    private  String userName;
    private String dateStr;

    public static class Builder extends BaseBuilder<HistoryOperation, Builder> {


        public Builder categoryIncome(CategoryIncome income) {
            actualClass.setCategoryIncome(income);
            return this;
        }

        public Builder categoryExpense(CategoryExpense expense) {
            actualClass.setCategoryExpense(expense);
            return this;
        }
        public Builder userName(String userName){
            actualClass.setName(userName);
            return this;
        }

        public Builder dateStr(String dateStr){
            actualClass.setDateStr(dateStr);
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

    public String getName() {
        return userName;
    }

    public void setName(String userName) {
        this.userName = userName;
    }

    public String getDateStr(){return dateStr;}

    public void setDateStr(String dateStr){this.dateStr = dateStr;}
}
