package org.FishFromSanDiego.cats.exceptions;

public class OtherCatIsAlreadyThisCatFriendException extends Exception {
    @Override
    public String getMessage() {
        return "Other cat is already this cat's friend";
    }
}
