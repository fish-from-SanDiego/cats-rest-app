package org.FishFromSanDiego.cats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "user with such username already exists")
public class UserWithSuchUsernameAlreadyExistsException extends RuntimeException{
}
