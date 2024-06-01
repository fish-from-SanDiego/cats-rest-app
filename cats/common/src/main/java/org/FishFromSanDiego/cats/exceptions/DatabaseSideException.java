package org.FishFromSanDiego.cats.exceptions;

@Deprecated
public class DatabaseSideException extends Exception {
    @Override
    public String getMessage() {
        return "Exception on the database side";
    }
}
