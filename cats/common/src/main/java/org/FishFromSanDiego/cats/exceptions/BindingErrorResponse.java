package org.FishFromSanDiego.cats.exceptions;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class BindingErrorResponse {
    int status;
    List<ErrorInfo> errors;

    @Value
    @AllArgsConstructor
    public static class ErrorInfo {
        String field;
        String defaultMessage;
    }
}
