package com.netcracker.models;


import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.models.enums.ReportCategoryIncome;


public class CategoryIncomeReport extends AbstractCategoryReport {
    private CategoryIncome categoryIncome;
    private String userName;

    public static class Builder extends BaseBuilder<CategoryIncomeReport, Builder> {

        public Builder categoryIncome(CategoryIncome income) {
            actualClass.setCategoryIncome(income);
            return this;
        }

        public Builder userName(String userName) {
            actualClass.setUserName(userName);
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
