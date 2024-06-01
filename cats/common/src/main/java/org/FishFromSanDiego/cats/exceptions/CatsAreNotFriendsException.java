package org.FishFromSanDiego.cats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "other cat is not this cat's friend")
public class CatsAreNotFriendsException extends RuntimeException {
}
