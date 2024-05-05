package com.chrisbone.todolist.v1.services;

import com.chrisbone.todolist.v1.dto.requests.CategoryRequestDTO;
import com.chrisbone.todolist.v1.dto.responses.CategoryResponseDTO;
import com.chrisbone.todolist.v1.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO category);
    CategoryResponseDTO updateCategory(UUID id, CategoryRequestDTO category);
    void deleteCategory(UUID id);
    CategoryResponseDTO getCategoryById(UUID id);
    List<CategoryResponseDTO> getAllCategories();
    List<CategoryResponseDTO> getCategoriesByUserId(UUID id);
}
