package com.netcracker.models;

public class ChartItem {

    private String month;
    private double amountExp;
    private double amountInc;
    private double amountExpPred;
    private double amountIncPred;

    public ChartItem(String month, double amountExp, double amountInc) {
        this.month = month;
        this.amountExp = amountExp;
        this.amountInc = amountInc;
    }

    public ChartItem(double amountExpPred, double amountIncPred, String month) {
        this.amountExpPred = amountExpPred;
        this.amountIncPred = amountIncPred;
        this.month = month;
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

    public double getAmountExpPred() {
        return amountExpPred;
    }

    public void setAmountExpPred(double amountExpPred) {
        this.amountExpPred = amountExpPred;
    }

    public double getAmountIncPred() {
        return amountIncPred;
    }

    public void setAmountIncPred(double amountIncPred) {
        this.amountIncPred = amountIncPred;
    }
}
