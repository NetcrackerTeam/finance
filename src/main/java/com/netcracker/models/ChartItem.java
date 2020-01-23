package com.netcracker.models;

public class ChartItem {

    private String month;
    private double amountExp;
    private double amountInc;

    public ChartItem(String month, double amountExp, double amountInc) {
        this.month = month;
        this.amountExp = amountExp;
        this.amountInc = amountInc;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getAmountExp() {
        return amountExp;
    }

    public void setAmountExp(double amountExp) {
        this.amountExp = amountExp;
    }

    public double getAmountInc() {
        return amountInc;
    }

    public void setAmountInc(double amountInc) {
        this.amountInc = amountInc;
    }
}
