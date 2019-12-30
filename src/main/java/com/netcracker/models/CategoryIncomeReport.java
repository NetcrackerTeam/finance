package com.netcracker.models;


import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.models.enums.ReportCategoryIncome;


public class CategoryIncomeReport extends AbstractCategoryReport {
    private ReportCategoryIncome categoryIncome;

    public static class Builder extends BaseBuilder<CategoryIncomeReport, Builder> {

        public Builder categoryIncome(ReportCategoryIncome income) {
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

    public ReportCategoryIncome getCategoryIncome() {
        return categoryIncome;
    }

    public void setCategoryIncome(ReportCategoryIncome categoryIncome) {
        this.categoryIncome = categoryIncome;
    }
}
