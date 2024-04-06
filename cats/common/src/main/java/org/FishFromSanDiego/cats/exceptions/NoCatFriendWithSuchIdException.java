package org.FishFromSanDiego.cats.exceptions;

public class NoCatFriendWithSuchIdException extends Exception {
    @Override
    public String getMessage() {
        return "This cat can't have a friend with such id (it isn't found)";
    }
}
