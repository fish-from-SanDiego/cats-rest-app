package org.FishFromSanDiego.cats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "this cat belongs to other user")
public class CatBelongsToOtherUserException extends RuntimeException {
}
