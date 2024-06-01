package com.chrisbone.todolist.v1.exceptions;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
    }
}

