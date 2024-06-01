package com.chrisbone.todolist.v1.services;

import com.chrisbone.todolist.v1.dto.requests.TodoRequestDTO;
import com.chrisbone.todolist.v1.dto.responses.TodoResponseDTO;
import com.chrisbone.todolist.v1.models.Todo;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TodoService {
    TodoResponseDTO createTodo(TodoRequestDTO todo);
    TodoResponseDTO updateTodo(UUID todoId, TodoRequestDTO todo);
    void deleteTodo(UUID todoId,UUID userId);
    TodoResponseDTO getTodoById(UUID todoId,UUID userId);
    List<TodoResponseDTO> getAllTodos();
    List<TodoResponseDTO> getTodosByUserId(UUID userId);
    void partialUpdateTodo(UUID id,UUID userId, Map<String, Object> updates);
}
