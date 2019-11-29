package com.netcracker.models;

public abstract class AbstractAutoOperation extends AbstractAccountOperation {
    private int dayOfMonth;

    protected static abstract class BaseBuilderAO<T extends AbstractAutoOperation, B extends BaseBuilder> extends BaseBuilder<T, B>{
        protected T actualClass;
        protected B actualClassBuilder;

        protected abstract T getActual();

        protected abstract B getActualBuilder();

        protected BaseBuilderAO() {
            actualClass = getActual();
            actualClassBuilder = getActualBuilder();
        }

        public B dayOfMonth(int dayOfMonth) {
            actualClass.setDayOfMonth(dayOfMonth);
            return actualClassBuilder;
        }

        public T build() {
            return actualClass;
        }
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }


}
