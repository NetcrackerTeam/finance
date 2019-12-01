package com.netcracker.models;

public abstract class AbstractAutoOperation extends AbstractAccountOperation {
    private int dayOfMonth;

    protected static abstract class BaseBuilderAO<T extends AbstractAutoOperation, B extends BaseBuilder> extends BaseBuilder<T, B>{

        public B dayOfMonth(int dayOfMonth) {
            actualClass.setDayOfMonth(dayOfMonth);
            return actualClassBuilder;
        }
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }


}
