package com.chrisbone.todolist.v1.exceptions;

public class TodoAlreadyExistsException extends RuntimeException {
    public TodoAlreadyExistsException(String message) {
        super(message);
    }
}

