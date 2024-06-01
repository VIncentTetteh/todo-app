package com.chrisbone.todolist.v1.controllers;

import com.chrisbone.todolist.v1.dto.responses.CategoryResponseDTO;
import com.chrisbone.todolist.v1.dto.responses.TodoResponseDTO;
import com.chrisbone.todolist.v1.services.CategoryService;
import com.chrisbone.todolist.v1.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminResourceController {

    private final CategoryService categoryService;
    private final TodoService todoService;
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') and hasAnyAuthority('SCOPE_WRITE','SCOPE_READ')")
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN') and hasAuthority('SCOPE_READ')")
    @GetMapping("/todos")
    public ResponseEntity<List<TodoResponseDTO>> getAllUserTodos() {
        List<TodoResponseDTO> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }
}
