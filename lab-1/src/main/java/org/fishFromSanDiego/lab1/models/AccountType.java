package org.fishFromSanDiego.lab1.models;

public abstract class AccountType {
    public abstract String getName();

    public final static class Debit extends AccountType {

        @Override
        public String getName() {
            return "Debit";
        }
    }

    public final static class Credit extends AccountType {
        @Override
        public String getName() {
            return "Credit";
        }
    }

    public final static class Deposit extends AccountType {
        @Override
        public String getName() {
            return "Deposit";
        }
    }
}
