package com.chrisbone.todolist.v1.services;

import com.chrisbone.todolist.v1.dto.requests.TodoRequestDTO;
import com.chrisbone.todolist.v1.dto.responses.TodoResponseDTO;
import com.chrisbone.todolist.v1.models.Todo;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TodoService {
    TodoResponseDTO createTodo(TodoRequestDTO todo);
    TodoResponseDTO updateTodo(UUID id, TodoRequestDTO todo);
    void deleteTodo(UUID id);
    TodoResponseDTO getTodoById(UUID id);
    List<TodoResponseDTO> getAllTodos();
    List<TodoResponseDTO> getTodosByUserId(UUID id);
    void partialUpdateTodo(UUID id, Map<String, Object> updates);
}
