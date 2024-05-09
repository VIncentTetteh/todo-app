package com.chrisbone.todolist.v1.dto.requests;

import com.chrisbone.todolist.v1.models.Todo;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CategoryRequestDTO(@NotEmpty(message = "Category name can not be empty.") String name, List<Todo> todoList) {
}
