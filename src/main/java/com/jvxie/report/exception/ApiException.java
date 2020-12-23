package com.jvxie.report.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends RuntimeException {
    private String message;

    public ApiException(String message) {
        this.message = message;
    }
}
