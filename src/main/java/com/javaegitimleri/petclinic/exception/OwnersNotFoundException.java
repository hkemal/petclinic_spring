package com.javaegitimleri.petclinic.exception;

public class OwnersNotFoundException extends RuntimeException {
    public OwnersNotFoundException(String message) {
        super(message);
    }
}