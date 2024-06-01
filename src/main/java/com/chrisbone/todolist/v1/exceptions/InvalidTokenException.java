package com.chrisbone.todolist.v1.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
