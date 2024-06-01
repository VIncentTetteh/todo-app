package com.chrisbone.todolist.v1.dto.requests;

import com.chrisbone.todolist.v1.models.Category;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.UUID;

public record TodoRequestDTO(
        @NotEmpty(message = "title can not be empty")
        String title,
        @NotEmpty(message = "description can not be empty")
        String description,
        @NotEmpty(message = "due date can not be empty")
        LocalDateTime dueDate,
        @NotEmpty(message = "priority lever can not be empty")
        int priorityLevel,
        @NotEmpty(message = "priority lever can not be empty")
        UUID category_id,
        @NotEmpty(message = "priority lever can not be empty")
        UUID user_id
) { }
