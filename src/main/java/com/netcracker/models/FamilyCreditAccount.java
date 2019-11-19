package com.netcracker.models;

public class FamilyCreditAccount extends AbstractCreditAccount {
    public static class Builder extends BaseBuilder<AbstractCreditAccount, BaseBuilder>{

      public Builder anotherField(String anotherField) {
           // actualClass.setAnotherField(anotherField);
            return this;
        }

        @Override
        protected FamilyCreditAccount getActual() {
            return new FamilyCreditAccount();
        }

        @Override
        protected Builder getActualBuilder() {
            return this;
        }
    }

}
