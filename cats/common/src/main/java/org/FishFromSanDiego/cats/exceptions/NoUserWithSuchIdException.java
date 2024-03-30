package org.FishFromSanDiego.cats.exceptions;

public class NoUserWithSuchIdException extends Exception {
    @Override
    public String getMessage() {
        return "Can't find a user with such Id";
    }
}
