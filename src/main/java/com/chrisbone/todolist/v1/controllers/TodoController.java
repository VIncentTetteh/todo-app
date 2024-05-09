package com.chrisbone.todolist.v1.controllers;

import com.chrisbone.todolist.v1.dto.requests.TodoRequestDTO;
import com.chrisbone.todolist.v1.dto.responses.TodoResponseDTO;
import com.chrisbone.todolist.v1.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {
    private final TodoService todoService;

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<TodoResponseDTO> createTodo(@RequestBody TodoRequestDTO todoRequestDTO) {
        TodoResponseDTO todoResponseDTO = todoService.createTodo(todoRequestDTO);
        return new ResponseEntity<>(todoResponseDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<TodoResponseDTO>> getAllTodos() {
        List<TodoResponseDTO> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponseDTO> getTodoById(@PathVariable UUID todoId) {
        TodoResponseDTO todo = todoService.getTodoById(todoId);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TodoResponseDTO>> getTodosByUserId(@PathVariable UUID userId) {
        List<TodoResponseDTO> todos = todoService.getTodosByUserId(userId);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PutMapping("/{todoId}")
    public ResponseEntity<Void> updateTodo(@PathVariable UUID todoId, @RequestBody TodoRequestDTO todoRequestDTO) {
        todoService.updateTodo(todoId, todoRequestDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PatchMapping("/{todoId}")
    public ResponseEntity<Void> partialUpdateTodo(@PathVariable UUID todoId, @RequestBody Map<String, Object> updates) {
        todoService.partialUpdateTodo(todoId, updates);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable UUID todoId) {
        todoService.deleteTodo(todoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
