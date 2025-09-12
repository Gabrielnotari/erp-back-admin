package com.erpmarcenaria.SistemaGestao.exceptions;

public class NameValueRequiredException extends RuntimeException {
    public NameValueRequiredException(String message){
        super(message);
    }
}
