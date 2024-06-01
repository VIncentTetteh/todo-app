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
@RequestMapping("/api/v1/user/todos")
public class TodoController {
    private final TodoService todoService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER') and hasAuthority('SCOPE_WRITE')")
    @PostMapping
    public ResponseEntity<TodoResponseDTO> createTodo(@RequestBody TodoRequestDTO todoRequestDTO) {
        TodoResponseDTO todoResponseDTO = todoService.createTodo(todoRequestDTO);
        return new ResponseEntity<>(todoResponseDTO, HttpStatus.CREATED);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER') and hasAuthority('SCOPE_READ')")
    @GetMapping("/{userId}/{todoId}")
    public ResponseEntity<TodoResponseDTO> getTodoById(@PathVariable UUID userId, @PathVariable UUID todoId) {
        TodoResponseDTO todo = todoService.getTodoById(todoId,userId);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER') and hasAuthority('SCOPE_READ')")
    @GetMapping("/{userId}")
    public ResponseEntity<List<TodoResponseDTO>> getTodosByUserId(@PathVariable UUID userId) {
        List<TodoResponseDTO> todos = todoService.getTodosByUserId(userId);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER') and hasAuthority('SCOPE_UPDATE')")
    @PutMapping("/{todoId}")
    public ResponseEntity<Void> updateTodo(@PathVariable UUID todoId, @RequestBody TodoRequestDTO todoRequestDTO) {
        todoService.updateTodo(todoId, todoRequestDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER') and hasAuthority('SCOPE_UPATE')")
    @PatchMapping("/{userId}/{todoId}")
    public ResponseEntity<Void> partialUpdateTodo(@PathVariable UUID userId, @PathVariable UUID todoId, @RequestBody Map<String, Object> updates) {
        todoService.partialUpdateTodo(todoId,userId, updates);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER') and hasAuthority('SCOPE_DELETE')")
    @DeleteMapping("/{userId}/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable UUID userId, @PathVariable UUID todoId) {
        todoService.deleteTodo(todoId,userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
