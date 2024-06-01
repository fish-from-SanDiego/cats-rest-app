package org.FishFromSanDiego.cats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "can't find a cat with such id")
public class CatNotFoundException extends RuntimeException {
}
