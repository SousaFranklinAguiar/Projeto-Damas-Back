package com.projetodamas.projetodamasback.exceptions;

public class ErrorException extends RuntimeException{
    public ErrorException(String error) {
        super(error);
    }
}
