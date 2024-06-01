package org.FishFromSanDiego.cats.exceptions;

@Deprecated
public class CatBelongsToOtherUserException extends Exception {
    @Override
    public String getMessage() {
        return "This cat belongs to other user";
    }
}
