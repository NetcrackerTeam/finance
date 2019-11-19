package com.netcracker.models;

public abstract class AbstractAutoOperation extends AbstractAccountOperation {
    private int dayOfMonth;


    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

}
