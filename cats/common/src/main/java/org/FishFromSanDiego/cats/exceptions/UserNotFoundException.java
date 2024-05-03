package org.FishFromSanDiego.cats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Can't find a user with such Id";
    }
}
