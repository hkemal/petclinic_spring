package com.javaegitimleri.petclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class OwnersNotFoundException extends RuntimeException {
    public OwnersNotFoundException(String message) {
        super(message);
    }
}
