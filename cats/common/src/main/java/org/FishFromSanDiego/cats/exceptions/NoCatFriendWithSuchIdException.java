package org.FishFromSanDiego.cats.exceptions;

public class NoCatFriendWithSuchIdException extends Exception {
    @Override
    public String getMessage() {
        return "This cat doesn't have a friend with such id";
    }
}
