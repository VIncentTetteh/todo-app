package com.chrisbone.todolist.v1.dto.requests;

import jakarta.validation.constraints.NotEmpty;

public record CategoryRequestDTO(@NotEmpty(message = "Category name can not be empty.") String name) {
}
