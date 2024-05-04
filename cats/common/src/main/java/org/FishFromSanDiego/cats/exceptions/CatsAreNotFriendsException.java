package org.FishFromSanDiego.cats.exceptions;

public class CatsAreNotFriendsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Other cat is not this cat's friend";
    }
}
