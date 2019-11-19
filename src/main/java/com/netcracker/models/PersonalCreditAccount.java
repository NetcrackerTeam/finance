package com.netcracker.models;

public class PersonalCreditAccount extends AbstractCreditAccount {
    public static class Builder extends BaseBuilder<AbstractCreditAccount, BaseBuilder> {

        public Builder anotherField(String anotherField) {
            // actualClass.setAnotherField(anotherField);
            return this;
        }

        @Override
        protected PersonalCreditAccount getActual() {
            return new PersonalCreditAccount() ;
        }

        @Override
        protected Builder getActualBuilder() {
            return this;
        }
    }
}
