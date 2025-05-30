package org.FishFromSanDiego.cats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "such friendship relationship already exists")
public class CatAlreadyFriendedException extends RuntimeException {
}
