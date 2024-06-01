package com.chrisbone.todolist.v1.controllers;

import com.chrisbone.todolist.v1.dto.requests.CategoryRequestDTO;
import com.chrisbone.todolist.v1.dto.responses.CategoryResponseDTO;
import com.chrisbone.todolist.v1.services.CategoryService;
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
@RequestMapping("/api/v1/user/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryRequestDTO);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN') and hasAnyAuthority('SCOPE_WRITE','SCOPE_READ')")
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER') and hasAuthority('SCOPE_READ')")
    @GetMapping("/{userId}/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable UUID userId, @PathVariable UUID categoryId) {
        CategoryResponseDTO category = categoryService.getCategoryById(categoryId,userId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN') and hasAuthority('SCOPE_UPDATE')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<Void> updateCategory(@PathVariable UUID categoryId, @RequestBody CategoryRequestDTO categoryRequestDTO) {
        categoryService.updateCategory(categoryId, categoryRequestDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER') and hasAnyAuthority('SCOPE_READ','SCOPE_UPDATE')")
    @PatchMapping("/{userId}/{categoryId}")
    public ResponseEntity<Void> partialUpdateCategory(@PathVariable UUID userId, @PathVariable UUID categoryId, @RequestBody Map<String, Object> updates) {
        categoryService.partialUpdateCategory(categoryId,userId, updates);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER') and hasAuthority('SCOPE_DELETE')")
    @DeleteMapping("/{userId}/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID userId, @PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId,userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
