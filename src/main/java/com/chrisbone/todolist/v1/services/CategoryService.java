package com.chrisbone.todolist.v1.services;

import com.chrisbone.todolist.v1.dto.requests.CategoryRequestDTO;
import com.chrisbone.todolist.v1.dto.responses.CategoryResponseDTO;
import com.chrisbone.todolist.v1.models.Category;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO category);
    CategoryResponseDTO updateCategory(UUID categoryId, CategoryRequestDTO category);
    void deleteCategory(UUID categoryId,UUID user_id);
    CategoryResponseDTO getCategoryById(UUID categoryId,UUID user_id);
    List<CategoryResponseDTO> getAllCategories();
    List<CategoryResponseDTO> getCategoriesByUserId(UUID userId);

    void partialUpdateCategory(UUID categoryId,UUID user_id, Map<String, Object> updates);
}
