package com.netcracker.models;

public abstract class AbstractAutoOperation extends AbstractAccountOperation {
    private int dayOfMonth;


    public static class BuilderAutoOperation extends BaseBuilder<AbstractAutoOperation, BaseBuilder> {

        public BuilderAutoOperation dayOfMonth(int dayOfMonth) {
            actualClass.setDayOfMonth(dayOfMonth);
            return this;
        }
        @Override
        protected AbstractAutoOperation getActual() {
            return null;
        }

        @Override
        protected BaseBuilder getActualBuilder() {
            return null;
        }
    }

        public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }


}
