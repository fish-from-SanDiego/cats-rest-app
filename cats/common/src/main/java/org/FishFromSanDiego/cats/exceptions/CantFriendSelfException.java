package org.FishFromSanDiego.cats.exceptions;

public class CantFriendSelfException extends Exception {
    @Override
    public String getMessage() {
        return "Can't add cat itself as its friend ";
    }
}
