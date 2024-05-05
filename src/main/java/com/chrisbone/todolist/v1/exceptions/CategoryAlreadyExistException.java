package com.chrisbone.todolist.v1.exceptions;

import com.chrisbone.todolist.v1.repositories.CategoryRepository;

public class CategoryAlreadyExistException extends RuntimeException{
    public CategoryAlreadyExistException(String message){
        super(message);
    }
}
