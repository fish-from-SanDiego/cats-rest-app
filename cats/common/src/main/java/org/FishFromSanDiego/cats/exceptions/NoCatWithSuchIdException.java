package org.FishFromSanDiego.cats.exceptions;

public class NoCatWithSuchIdException extends Exception {
    @Override
    public String getMessage() {
        return "Can't find a cat with such Id";
    }
}
