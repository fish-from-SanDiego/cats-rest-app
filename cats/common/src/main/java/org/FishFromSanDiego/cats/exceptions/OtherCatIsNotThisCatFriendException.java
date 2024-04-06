package org.FishFromSanDiego.cats.exceptions;

public class OtherCatIsNotThisCatFriendException extends Exception {
    @Override
    public String getMessage() {
        return "Other cat is not this cat's friend";
    }
}
