package com.chrisbone.todolist.v1.dto.objectMappers;

import com.chrisbone.todolist.v1.dto.requests.TodoRequestDTO;
import com.chrisbone.todolist.v1.models.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {
    public Todo convertToTodo(TodoRequestDTO todoRequestDTO){
        return Todo.builder()
                .title(todoRequestDTO.title())
                .description(todoRequestDTO.description())
                .dueDate(todoRequestDTO.dueDate())
                .priorityLevel(todoRequestDTO.priorityLevel())
                .build();
    }
}
