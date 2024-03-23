package org.fishFromSanDiego.lab1.models;

/**
 * The type Account type.
 */
public abstract class AccountType {
    /**
     * Gets name.
     *
     * @return the name
     */
    public abstract String getName();

    /**
     * The type Debit.
     */
    public final static class Debit extends AccountType {

        @Override
        public String getName() {
            return "Debit";
        }
    }

    /**
     * The type Credit.
     */
    public final static class Credit extends AccountType {
        @Override
        public String getName() {
            return "Credit";
        }
    }

    /**
     * The type Deposit.
     */
    public final static class Deposit extends AccountType {
        @Override
        public String getName() {
            return "Deposit";
        }
    }
}
